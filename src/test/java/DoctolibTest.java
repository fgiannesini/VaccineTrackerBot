import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class DoctolibTest {

    @Test
    public void should_get_vaccine_offices() throws IOException, URISyntaxException {
        var httpRequester = mock(HttpRequester.class);

        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-offices.json")).toURI()));
        doReturn(response).when(httpRequester).run(anyString());

        Doctolib doctolib = new Doctolib(httpRequester);
        List<Office> vaccineOffices = doctolib.getOffices();

        assertThat(vaccineOffices).containsExactlyInAnyOrder(new Office(1268497, "Suresnes"), new Office(1666665, "Vernon"));
    }

}