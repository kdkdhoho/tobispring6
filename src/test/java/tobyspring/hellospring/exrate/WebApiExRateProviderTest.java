package tobyspring.hellospring.exrate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tobyspring.hellospring.api.ApiTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // JUnit5에서 Mockito 관련 어노테이션을 사용하기 위해 필요한 어노테이션
class WebApiExRateProviderTest {

    @InjectMocks // Mockito가 SUT 객체를 생성하고, 필요한 의존성을 주입한다.
    private WebApiExRateProvider webApiExRateProvider;

    @Mock
    private ApiTemplate apiTemplate;

    @Captor
    private ArgumentCaptor<String> urlCaptor;

    @Test
    void 지정된_URL로_API를_호출한다() {
        // given
        String currency = "KRW";

        // when
        webApiExRateProvider.getExchangeRate(currency);

        // then
        verify(apiTemplate).getExRate(urlCaptor.capture());
        assertThat(urlCaptor.getValue()).isEqualTo("https://open.er-api.com/v6/latest/" + currency);
    }
}
