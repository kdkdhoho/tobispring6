package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExRateExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

    private static final String BASE_URL = "https://open.er-api.com/v6/latest/";

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = BASE_URL + currency;
        ApiTemplate apiTemplate = new ApiTemplate();
        return apiTemplate.getExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());
    }
}
