package tobyspring.hellospring.api;

import java.io.IOException;
import java.net.URI;

public interface ApiExecutor {

    /**
     * API 요청을 보낼 콜백 메서드입니다.
     *
     * @param uri API 요청을 보낼 URI 입니다.
     * @return JSON 응답값을 리턴합니다.
     * @throws IOException
     */
    String execute(URI uri) throws IOException;
}
