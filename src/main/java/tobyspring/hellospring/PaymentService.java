package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        // 환율 가져오기
        // 현재는 Java의 URL을 사용해서 API를 통해 데이터를 가져오고 있다.
        // 하지만 URL이 아닌 최신 표준 기술을 사용한다거나, Open API가 아닌 DB에서 값을 가져오는 식으로 변경될 수 있다.
        // 즉, 기술적인 관심사이다.
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // HttpURLConnection 타입으로 받으면 다루기가 한결 수월해진다.
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); // InputStream: 네트워크로 넘어오는 데이터를 바이트 형태로 리턴해준다.
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
        BigDecimal exRate = data.rates().get("KRW");

        // 아래 두 코드는 비즈니스 로직에 관련된 계산이다. 즉, 서비스의 관심사이다.
        // 서비스의 비즈니스 로직이 변경되면, 아래 두 코드도 변경되어야 한다.
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        // 중요한 것은, 두 관심사가 변경되는 시점이 다르다는 것이다.

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
