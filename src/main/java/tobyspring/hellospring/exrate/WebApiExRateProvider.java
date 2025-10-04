package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "https://open.er-api.com/v6/latest/";

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = BASE_URL + currency;

        // 1. URI를 준비하고 예외처리를 위한 작업을 하는 코드
        // 특정 URL로부터 환율 정보를 가져오기 위해 준비하는 기본 틀이다. == 외부 API를 호출하는 한, 변경되지 않는다.
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // 2. API를 호출하고 서버로부터 응답을 가져오는 코드
        // API를 호출하는 기술과 방법이 변경될 수 있다. == 변경되고 확장하는 성질을 가진다.
        String response;
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = br.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 3. JSON 문자열을 파싱해서 필요한 정보를 추출하는 코드
        // 어떤 API를 호출하냐에 따라 JSON 응답 구조가 달라질 수 있고, 그에 따라 변경되어야 한다. == 변경하는 성질을 가진다.
        ExchangeRateData data;
        try {
            data = mapper.readValue(response, ExchangeRateData.class);
            return data.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
