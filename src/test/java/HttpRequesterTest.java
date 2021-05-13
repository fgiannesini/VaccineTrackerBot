import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpRequesterTest {

    @Test
    public void should_request_a_site() {
        HttpRequester requester = new HttpRequester();
        String response = requester.run("https://www.google.fr");
        assertThat(response).isNotBlank();
    }

    @Test
    public void should_throw_an_exception_if_failed() {
        assertThatThrownBy(() -> new HttpRequester().run("")).isInstanceOf(RuntimeException.class);
    }
}
