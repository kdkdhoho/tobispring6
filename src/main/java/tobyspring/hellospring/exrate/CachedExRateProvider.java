package tobyspring.hellospring.exrate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import tobyspring.hellospring.payment.ExRateProvider;

public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;
    private final Map<String, BigDecimal> cache = new ConcurrentHashMap<>();
    private LocalDateTime cacheExpiryTime;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) throws IOException {
        if (cache.containsKey(currency) && cacheExpiryTime.isAfter(LocalDateTime.now())) {
            System.out.println("Cache Value: " + cache.get(currency));
            return cache.get(currency);
        }

        BigDecimal fetchedExRate = target.getExchangeRate(currency);
        cacheExpiryTime = LocalDateTime.now().plusSeconds(3);
        cache.put(currency, fetchedExRate);
        System.out.println("Cache Update cache: " + cache);
        return fetchedExRate;
    }
}
