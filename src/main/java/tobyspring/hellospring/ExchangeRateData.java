package tobyspring.hellospring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Map;

// ObjectMapper를 통해 Json의 값을 매핑할 때, Json에는 있지만 Record 타입에는 해당 변수를 받아줄 필드가 없으면 에러가 발생한다.
// 하지만 모든 필드를 받기 싫은 경우, 아래 어노테이션을 달아주면 받아줄 필드가 없다면 해당 필드는 무시한다.
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRateData(
        String result,
        Map<String, BigDecimal> rates
) {
}
