import java.util.List;
import java.util.stream.Collectors;

public class VaccineTracker {

    private final Doctolib doctolib;
    private final List<String> nearestCities;

    public VaccineTracker(Doctolib doctolib, List<String> nearestCities) {
        this.doctolib = doctolib;
        this.nearestCities = nearestCities;
    }


    public void run() {
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
