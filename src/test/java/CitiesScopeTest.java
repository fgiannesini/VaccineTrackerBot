import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

class CitiesScopeTest {

    @Test
    void should_be_eligible() {
        CitiesScope citiesScope = new CitiesScope(List.of("Suresnes", "Puteaux"));
        assertThat(citiesScope.isEligible("Suresnes")).isTrue();
        assertThat(citiesScope.isEligible("Puteaux")).isTrue();
    }

    @Test
    void should_not_be_eligible() {
        CitiesScope citiesScope = new CitiesScope(List.of("Suresnes", "Puteaux"));
        assertThat(citiesScope.isEligible("Saint-Cloud")).isFalse();
    }

    @Test
    void should_be_eligible_is_scope_is_empty() {
        CitiesScope citiesScope = new CitiesScope(emptyList());
        assertThat(citiesScope.isEligible("Suresnes")).isTrue();
        assertThat(citiesScope.isEligible("Puteaux")).isTrue();
    }
}