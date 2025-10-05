package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigDecimal;

public interface ExRateExtractor {

    /**
     * JSON 응답값을 추출하여 환율 정보로 반환하는 콜백 메서드입니다.
     *
     * @param json JSON 응답값입니다.
     * @return 환율 정보를 반환합니다.
     * @throws JsonProcessingException
     */
    BigDecimal extract(String json) throws JsonProcessingException;
}
