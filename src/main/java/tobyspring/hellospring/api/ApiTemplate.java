package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {

    private final ApiExecutor apiExecutor;
    private final ExRateExtractor exRateExtractor;

    // 기본 생성자로 콜백 메서드에 대해 Default 값을 설정합니다.
    public ApiTemplate() {
        this.apiExecutor = new SimpleApiExecutor();
        this.exRateExtractor = new ErApiExRateExtractor();
    }

    // 생성자로 ApiTemplate이 Default로 사용할 콜백 메서드를 지정할 수 있습니다.
    public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exRateExtractor = exRateExtractor;
    }

    // Default로 설정된 콜백 메서드를 사용하는 메서드입니다.
    public BigDecimal getExRate(String url) {
        return getExRate(url, apiExecutor, exRateExtractor);
    }

    public BigDecimal getExRate(String url, ApiExecutor exRateExecutor) {
        return getExRate(url, exRateExecutor, exRateExtractor);
    }

    public BigDecimal getExRate(String url, ExRateExtractor exRateExtractor) {
        return getExRate(url, apiExecutor, exRateExtractor);
    }

    /**
     * 템플릿 메서드입니다.
     *
     * @param url             API 요청을 보낼 URL 입니다.
     * @param apiExecutor     API 요청을 실행시킬 콜백 메서드입니다.
     * @param exRateExtractor JSON 응답값을 ExRate로 응답하는 콜백 메서드입니다.
     * @return 환율을 반환합니다.
     */
    public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
