import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class VaccineTrackerTest {

    @Test
    void should_track_offices() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        Office vernon = new Office(1666665, "Vernon", "/link/Vernon");
        doReturn(List.of(suresnes, vernon)).when(doctolib).getOffices();
        doReturn(List.of(new Availabilities(LocalDate.now(), 1))).when(doctolib).getAvailabilities(suresnes);
        doReturn(List.of(new Availabilities(LocalDate.now(), 1))).when(doctolib).getAvailabilities(vernon);

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib, new CitiesScope(List.of("Suresnes", "Vernon")));
        vaccineTracker.run();

        verify(doctolib, times(1)).openLink(suresnes);
        verify(doctolib, times(1)).openLink(vernon);
    }

    @Test
    void should_not_track_office_if_no_availabilities() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        doReturn(List.of(suresnes)).when(doctolib).getOffices();
        doReturn(emptyList()).when(doctolib).getAvailabilities(suresnes);

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib, new CitiesScope(List.of("Suresnes")));
        vaccineTracker.run();

        verify(doctolib, never()).openLink(any());
    }

    @Test
    void should_not_track_office_if_no_availabilities_today_or_tomorrow() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        doReturn(List.of(suresnes)).when(doctolib).getOffices();
        doReturn(List.of(new Availabilities(LocalDate.now().plusDays(2), 1))).when(doctolib).getAvailabilities(suresnes);

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib, new CitiesScope(List.of("Suresnes")));
        vaccineTracker.run();

        verify(doctolib, never()).openLink(any());
    }

    @Test
    void should_track_offices_from_nearest_cities() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        Office vernon = new Office(1666665, "Vernon", "/link/Vernon");
        doReturn(List.of(suresnes, vernon)).when(doctolib).getOffices();
        doReturn(List.of(new Availabilities(LocalDate.now(), 1))).when(doctolib).getAvailabilities(suresnes);
        doReturn(List.of(new Availabilities(LocalDate.now(), 1))).when(doctolib).getAvailabilities(vernon);
        var nearestCities = List.of("Suresnes");

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib, new CitiesScope(nearestCities));
        vaccineTracker.run();

        verify(doctolib, times(1)).openLink(suresnes);
        verify(doctolib, never()).openLink(vernon);
    }

}