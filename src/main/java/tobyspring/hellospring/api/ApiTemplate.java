package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {

    /**
     * 템플릿 메서드입니다.
     *
     * @param url             API 요청을 보낼 URL 입니다.
     * @param apiExecutor     API 요청을 실행시킬 콜백 메서드입니다.
     * @param exRateExtractor JSON 응답값을 ExRate로 응답하는 콜백 메서드입니다.
     * @return 환율을 반환합니다.
     */
    public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        // 1. URI를 준비하고 예외처리를 위한 작업을 하는 코드입니다.
        // 외부에서 API를 호출하기 위한 기본 틀입니다. 외부 API를 호출하는 한, 변경되지 않는 영역입니다.
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // 2. API를 호출하고 서버로부터 응답을 가져오는 코드입니다.
        // 추후 언제든지 API를 호출하는 기술과 방법이 변경될 수 있습니다. 이는 변경되고 확장하는 성질을 가집니다.
        String response;
        try {
            response = apiExecutor.execute(uri); // 콜백을 호출합니다. 참조 정보로 uri를 전달합니다.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 3. JSON 문자열을 파싱해서 필요한 정보를 추출하는 코드입니다.
        // 1번에서 어떤 URL로 API를 호출하냐에 따라 JSON 응답 구조가 달라질 수 있고, 그에 따라 변경되어야 합니다. 즉, 변경하는 성질을 가집니다.
        try {
            return exRateExtractor.extract(response); // 콜백을 호출합니다. 참조 정보로 response를 전달합니다.
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
