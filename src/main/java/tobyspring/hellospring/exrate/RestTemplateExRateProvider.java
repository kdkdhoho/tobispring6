package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.payment.ExRateProvider;

public class RestTemplateExRateProvider implements ExRateProvider {

    private static final String BASE_URL = "https://open.er-api.com/v6/latest/";

    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = BASE_URL + currency;
        ExRateData result = restTemplate.getForObject(url, ExRateData.class);
        return result.rates().get("KRW");
    }
}
