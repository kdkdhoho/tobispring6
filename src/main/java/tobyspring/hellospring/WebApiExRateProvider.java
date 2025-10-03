package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(String currency)  throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // HttpURLConnection 타입으로 받으면 다루기가 한결 수월해진다.
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); // InputStream: 네트워크로 넘어오는 데이터를 바이트 형태로 리턴해준다.
        br.close();

        String response = br.lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
        return data.rates().get("KRW");
    }
}
