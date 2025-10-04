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

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = br.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExchangeRateData data;
        try {
            data = mapper.readValue(response, ExchangeRateData.class);
            return data.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
