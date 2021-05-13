import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class VaccineTrackerTest {

    @Test
    void should_track_offices() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        Office vernon = new Office(1666665, "Vernon", "/link/Vernon");
        doReturn(List.of(suresnes, vernon)).when(doctolib).getOffices();
        doReturn(2).when(doctolib).getOfficeAvailabilities(suresnes);
        doReturn(1).when(doctolib).getOfficeAvailabilities(vernon);

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib);
        vaccineTracker.run(List.of("Suresnes", "Vernon"));

        verify(doctolib, times(1)).openLink(suresnes);
        verify(doctolib, times(1)).openLink(vernon);
    }

    @Test
    void should_not_track_office_if_no_availabilities() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        doReturn(List.of(suresnes)).when(doctolib).getOffices();
        doReturn(0).when(doctolib).getOfficeAvailabilities(suresnes);

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib);
        vaccineTracker.run(List.of("Suresnes"));

        verify(doctolib, never()).openLink(any());
    }


    @Test
    void should_track_offices_from_nearest_cities() {
        Doctolib doctolib = mock(Doctolib.class);
        Office suresnes = new Office(1268497, "Suresnes", "/link/Suresnes");
        Office vernon = new Office(1666665, "Vernon", "/link/Vernon");
        doReturn(List.of(suresnes, vernon)).when(doctolib).getOffices();
        doReturn(2).when(doctolib).getOfficeAvailabilities(suresnes);
        doReturn(1).when(doctolib).getOfficeAvailabilities(vernon);
        var nearestCities = List.of("Suresnes");

        VaccineTracker vaccineTracker = new VaccineTracker(doctolib);
        vaccineTracker.run(nearestCities);

        verify(doctolib, times(1)).openLink(suresnes);
        verify(doctolib, never()).openLink(vernon);
    }

}