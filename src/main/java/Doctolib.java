import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Doctolib {

    private final ObjectMapper objectMapper;
    private final HttpRequester httpRequester;
    private final Browser browser;

    public Doctolib(HttpRequester httpRequester, Browser browser) {
        this.httpRequester = httpRequester;
        this.browser = browser;
        this.objectMapper = new ObjectMapper();
    }

    public List<Office> getOffices() {
        var response = httpRequester.run("https://www.doctolib.fr/vaccination-covid-19/suresnes?force_max_limit=2&ref_visit_motive_ids[]=6970,7005");
        try {
            JsonNode jsonNode = this.objectMapper.readTree(response);
            JsonNode doctors = jsonNode.findValue("data").findValue("doctors");
            List<Office> officeList = new ArrayList<>();
            for (JsonNode doctor : doctors) {
                officeList.add(new Office(
                        doctor.findValue("id").asLong(),
                        doctor.findValue("city").asText(),
                        doctor.findValue("link").asText()));
            }
            return officeList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOfficeAvailabilities(Office office) {
        var response = httpRequester.run(String.format("https://www.doctolib.fr/search_results/%d.json", office.id()));
        try {
            JsonNode jsonNode = this.objectMapper.readTree(response);
            return jsonNode.findValue("total").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void openLink(Office office) {
        browser.openUrl(String.format("https://www.doctolib.fr%s", office.link()));
    }
}
