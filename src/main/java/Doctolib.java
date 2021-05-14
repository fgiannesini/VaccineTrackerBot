import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Doctolib {

    private final ObjectMapper objectMapper;
    private final HttpRequester httpRequester;
    private final Browser browser;
    private final String location;

    public Doctolib(HttpRequester httpRequester, Browser browser, String location) {
        this.httpRequester = httpRequester;
        this.browser = browser;
        this.location = location;
        this.objectMapper = new ObjectMapper();
    }

    public List<Office> getOffices() {
        String url = String.format("https://www.doctolib.fr/vaccination-covid-19/%s?force_max_limit=2&ref_visit_motive_ids[]=6970,7005", location.toLowerCase());
        var response = httpRequester.run(url);
        try {
            JsonNode jsonNode = this.objectMapper.readTree(response);
            return toStream(jsonNode.findValue("data").findValue("doctors").iterator())
                    .map(doctor -> new Office(
                            doctor.findValue("id").asLong(),
                            doctor.findValue("city").asText(),
                            doctor.findValue("link").asText()))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while retrieving the office list. Is url " + url + " valid ?",e);
        }
    }

    public List<Availabilities> getAvailabilities(Office office) {
        String url = String.format("https://www.doctolib.fr/search_results/%d.json", office.id());
        var response = httpRequester.run(url);
        try {
            JsonNode jsonNode = this.objectMapper.readTree(response);
            return toStream(jsonNode.findValue("availabilities").iterator())
                    .map(availability -> new Availabilities(
                            LocalDate.parse(availability.findValue("date").asText()),
                            (int) toStream(availability.findValue("slots").elements()).count()
                    ))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while retrieving the office list. Is url " + url + " valid ?", e);
        }
    }

    private <T> Stream<T> toStream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public void openLink(Office office) {
        browser.openUrl(String.format("https://www.doctolib.fr%s", office.link()));
    }
}
