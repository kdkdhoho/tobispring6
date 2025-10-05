package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import tobyspring.hellospring.exrate.ExchangeRateData;

public class ErApiExRateExtractor implements ExRateExtractor {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public BigDecimal extract(String json) throws JsonProcessingException {
        return mapper.readValue(json, ExchangeRateData.class).rates().get("KRW");
    }
}
