import java.util.List;
import java.util.stream.Collectors;

public class VaccineTracker {

    private final Doctolib doctolib;

    public VaccineTracker(Doctolib doctolib) {
        this.doctolib = doctolib;
    }

    public void run(List<String> nearestCities) {
        var offices = doctolib.getOffices().stream()
                .filter(office -> nearestCities.contains(office.getCity()))
                .collect(Collectors.toList());
        for (Office office : offices) {
            int availabilities = doctolib.getOfficeAvailabilities(office);
            if (availabilities > 0) {
                doctolib.openLink(office);
            }
        }
    }
}
