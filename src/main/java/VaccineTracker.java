import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class VaccineTracker extends TimerTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Doctolib doctolib = new Doctolib(new HttpRequester(), new Browser(), "Suresnes");
        TimerTask task = new VaccineTracker(doctolib, new CitiesScope(List.of("Suresnes", "Nanterre", "Puteaux", "Saint-Cloud", "Neuilly-sur-Seine")));
        timer.schedule(task, 1000, 60_000);
    }

    private final Doctolib doctolib;
    private final CitiesScope citiesScope;

    public VaccineTracker(Doctolib doctolib, CitiesScope citiesScope) {
        this.doctolib = doctolib;
        this.citiesScope = citiesScope;
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + " run on " + LocalDateTime.now());
        var offices = doctolib.getOffices().stream()
                .filter(office -> citiesScope.isEligible(office.city()))
                .collect(Collectors.toList());
        for (Office office : offices) {
            List<Availabilities> availabilities = doctolib.getAvailabilities(office).stream()
                    .filter(Availabilities::isBeforeTwoDays)
                    .collect(Collectors.toList());
            boolean hasSlots = availabilities.stream()
                    .anyMatch(Availabilities::hasSlots);
            if (hasSlots) {
                System.out.println("Open " + office + " for availabilities " + availabilities);
                doctolib.openLink(office);
            }
        }
    }

}
