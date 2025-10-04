package tobyspring.hellospring.learningtest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Clock 클래스를 이해하기 위한 학습 테스트
 */
public class ClockTest {

    @Test
    void Clock을_사용해서_LocalDateTime_now를_가져온다() {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dateTime1 = LocalDateTime.now(clock);
        LocalDateTime dateTime2 = LocalDateTime.now(clock);

        assertThat(dateTime2).isAfter(dateTime1);
    }

    @Test
    void Clock을_테스트에서_사용할_때_내가_원하는_시간을_지정해서_현재_시간을_가져온다() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dateTime1 = LocalDateTime.now(clock);
        LocalDateTime dateTime2 = LocalDateTime.now(clock);

        assertThat(dateTime1).isEqualTo(dateTime2);
    }
}
