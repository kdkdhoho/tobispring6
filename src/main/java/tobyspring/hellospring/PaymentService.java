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
        BigDecimal exRateKRW = getExchangeRateKRW(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRateKRW);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRateKRW, convertedAmount, validUntil);
    }

    private BigDecimal getExchangeRateKRW(String currency) throws IOException {
    // 메서드로 분리하는 것으로도 충분히 코드가 보기 편안해졌다.
    // 그럼에도 PaymentService 클래스 관점에서 보면 여전히 두 개의 서로 다른 관심사를 가지고 있다.
    // 비즈니스 로직이 변경되거나 기술적 구현 방식이 변경되면 PaymentService는 변경되어야 한다.
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // HttpURLConnection 타입으로 받으면 다루기가 한결 수월해진다.
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); // InputStream: 네트워크로 넘어오는 데이터를 바이트 형태로 리턴해준다.
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExchangeRateData data = mapper.readValue(response, ExchangeRateData.class);
        BigDecimal exRate = data.rates().get("KRW");
        return exRate;
    }

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
