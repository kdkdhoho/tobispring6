package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 기존 동작에 성능 개선을 위해 캐싱을 도입하게 되었다.</br>
 * CachedExRateProvider는 '데코레이터 패턴'을 적용하여, 기존 코드는 수정하지 않고 캐시 기능을 추가했다.</br>
 * 물론 오브젝트 구성 정보가 변경되어야 하므로 ObjectFactory는 변경되어야 한다.
 */
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
