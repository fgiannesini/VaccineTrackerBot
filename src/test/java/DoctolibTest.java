import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
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

        assertThat(vaccineOffices).containsExactlyInAnyOrder(
                new Office(1268497, "Suresnes", "/centre-de-sante/suresnes/centre-de-vaccination-covid-suresnes"),
                new Office(1666665, "Vernon", "/hopital-public/evreux/ch-eure-seine-centre-de-vaccination-covid-vacci-covid-27")
        );
    }

    @Test
    void should_get_office_with_availabilities() throws URISyntaxException, IOException {
        var httpRequester = mock(HttpRequester.class);

        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-office-with-availabilities.json")).toURI()));
        doReturn(response).when(httpRequester).run("https://www.doctolib.fr/search_results/1268151.json");

        Doctolib doctolib = new Doctolib(httpRequester);
        int availabilities = doctolib.getOfficeAvailabilities(new Office(1268151, "Athis-Mons", "/link"));
        assertThat(availabilities).isEqualTo(33);
    }

    @Test
    void should_get_office_without_availabilities() throws URISyntaxException, IOException {
        var httpRequester = mock(HttpRequester.class);

        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-office-without-availabilities.json")).toURI()));
        doReturn(response).when(httpRequester).run("https://www.doctolib.fr/search_results/1268497.json");

        Doctolib doctolib = new Doctolib(httpRequester);
        int availabilities = doctolib.getOfficeAvailabilities(new Office(1268497, "Suresnes", "/link"));
        assertThat(availabilities).isEqualTo(0);
    }
}