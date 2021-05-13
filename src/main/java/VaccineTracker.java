import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class VaccineTracker extends TimerTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Doctolib doctolib = new Doctolib(new HttpRequester(), new Browser());
        TimerTask task = new VaccineTracker(doctolib, List.of("Suresnes", "Nanterre", "Puteaux", "Saint-Cloud", "Neuilly-sur-Seine"));
        timer.schedule(task, 1000, 60_000);
    }

    private final Doctolib doctolib;
    private final List<String> nearestCities;

    public VaccineTracker(Doctolib doctolib, List<String> nearestCities) {
        this.doctolib = doctolib;
        this.nearestCities = nearestCities;
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + " run on " + LocalDateTime.now());
        var offices = doctolib.getOffices().stream()
                .filter(office -> nearestCities.contains(office.getCity()))
                .collect(Collectors.toList());
        for (Office office : offices) {
            int availabilities = doctolib.getOfficeAvailabilities(office);
            if (availabilities > 0) {
                System.out.println("Open " + office + " as " + availabilities + " slots are available");
                doctolib.openLink(office);
            }
        }
    }

}
