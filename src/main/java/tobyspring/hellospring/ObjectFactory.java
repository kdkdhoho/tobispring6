package tobyspring.hellospring;

/**
 * 오브젝트를 생성하는 관심사를 가진 ObjectFactory 클래스이다.</br>
 * Client는 이 클래스를 의존하고, 본인이 필요한 오브젝트를 조회만 하면 된다.
 *
 * 이제 구현체가 바뀌면 ObjectFactory에서 코드 한 줄만 바뀌면 된다.
 *
 * 지금 이 구조가 스프링의 핵심이다.</br>
 */
public class ObjectFactory {

    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }
}
