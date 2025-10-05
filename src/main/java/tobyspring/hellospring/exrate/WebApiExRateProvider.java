package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import tobyspring.hellospring.api.ApiExecutor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "https://open.er-api.com/v6/latest/";

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = BASE_URL + currency;

        // 템플릿 메서드(runApiForExRate)를 호출하는 Client 코드입니다.
        // 클라이언트가 콜백을 만들어 템플릿 메서드를 호출하는 구조를 가집니다.
        // 물론 람다식으로 콜백 메서드를 전달할 수 있습니다.
        // **특징으로, Client 콜백 메서드가 Client코드의 final 변수에 참조가 가능합니다.**
        return runApiForExRate(url, new SimpleApiExecutor());
    }

    // 템플릿에 해당하는 메서드입니다.
    // API를 호출하는 로직을 콜백 형태(메서드 파라미터 형태)로 전달받습니다.
    private BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor) {
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
            response = apiExecutor.execute(uri); // 콜백을 호출합니다. 이때 참조정보로 uri를 전달합니다.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 3. JSON 문자열을 파싱해서 필요한 정보를 추출하는 코드입니다.
        // 1번에서 어떤 URL로 API를 호출하냐에 따라 JSON 응답 구조가 달라질 수 있고, 그에 따라 변경되어야 합니다. 즉, 변경하는 성질을 가집니다.
        ExchangeRateData data;
        try {
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal extractExRate(String response) throws JsonProcessingException {
        ExchangeRateData data;
        data = mapper.readValue(response, ExchangeRateData.class);
        return data.rates().get("KRW");
    }
}
