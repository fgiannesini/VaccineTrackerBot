import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DoctolibTest {

    private Browser browser;
    private HttpRequester httpRequester;

    @BeforeEach
    void setUp() {
        browser = mock(Browser.class);
        httpRequester = mock(HttpRequester.class);
    }

    @Test
    public void should_get_vaccine_offices() throws IOException, URISyntaxException {
        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-offices.json")).toURI()));
        doReturn(response).when(httpRequester).run("https://www.doctolib.fr/vaccination-covid-19/suresnes?force_max_limit=2&ref_visit_motive_ids[]=6970,7005");

        Doctolib doctolib = new Doctolib(httpRequester, browser, "Suresnes");
        List<Office> vaccineOffices = doctolib.getOffices();

        assertThat(vaccineOffices).containsExactlyInAnyOrder(
                new Office(1268497, "Suresnes", "/centre-de-sante/suresnes/centre-de-vaccination-covid-suresnes"),
                new Office(1666665, "Vernon", "/hopital-public/evreux/ch-eure-seine-centre-de-vaccination-covid-vacci-covid-27")
        );
    }

    @Test
    void should_get_office_with_availabilities() throws URISyntaxException, IOException {
        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-office-with-availabilities.json")).toURI()));
        doReturn(response).when(httpRequester).run("https://www.doctolib.fr/search_results/1268151.json");

        Doctolib doctolib = new Doctolib(httpRequester, browser, "Suresnes");
        var availabilities = doctolib.getAvailabilities(new Office(1268151, "Athis-Mons", "/link"));
        assertThat(availabilities).containsExactlyInAnyOrder(
                new Availabilities(LocalDate.of(2021, Month.MAY, 13), 0),
                new Availabilities(LocalDate.of(2021, Month.MAY, 14), 33)
        );
    }

    @Test
    void should_get_office_without_availabilities() throws URISyntaxException, IOException {
        String response = Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("doctolib-office-without-availabilities.json")).toURI()));
        doReturn(response).when(httpRequester).run("https://www.doctolib.fr/search_results/1268497.json");

        Doctolib doctolib = new Doctolib(httpRequester, browser, "Suresnes");
        var availabilities = doctolib.getAvailabilities(new Office(1268497, "Suresnes", "/link"));
        assertThat(availabilities).isEmpty();
    }

    @Test
    void should_open_the_office_link() {
        Doctolib doctolib = new Doctolib(httpRequester, browser, "Suresnes");

        doctolib.openLink(new Office(1268497, "Suresnes", "/centre-de-sante/suresnes/centre-de-vaccination-covid-suresnes"));

        verify(browser).openUrl("https://www.doctolib.fr/centre-de-sante/suresnes/centre-de-vaccination-covid-suresnes");
    }
}