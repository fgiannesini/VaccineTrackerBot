import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Doctolib {

    private final ObjectMapper objectMapper;
    private final HttpRequester httpRequester;

    public Doctolib(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
        this.objectMapper = new ObjectMapper();
    }

    public List<Office> getOffices() {
        var response = httpRequester.run("https://www.doctolib.fr/vaccination-covid-19/suresnes?force_max_limit=2&ref_visit_motive_ids[]=6970,7005");
        try {
            JsonNode jsonNode = this.objectMapper.readTree(response);
            JsonNode doctors = jsonNode.findValue("data").findValue("doctors");
            List<Office> officeList = new ArrayList<>();
            for (JsonNode doctor : doctors) {
                officeList.add(new Office(doctor.findValue("id").asLong(), doctor.findValue("city").asText()));
            }
            return officeList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
