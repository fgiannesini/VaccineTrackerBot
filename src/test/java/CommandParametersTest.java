import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CommandParametersTest {

    @Test
    void should_set_location_url_parameter() {
        Optional<CommandParameters> commandParameters = CommandParameters.from(new String[]{"-location", "Suresnes"});
        assertThat(commandParameters).map(CommandParameters::location).hasValue("Suresnes");
    }

    @Test
    void should_fail_if_location_url_parameter_is_not_set() {
        assertThat(CommandParameters.from(new String[]{})).isEmpty();
    }

    @Test
    void should_set_period_parameter() {
        Optional<CommandParameters> commandParameters = CommandParameters.from(new String[]{"-location", "Suresnes", "-period", "100"});
        assertThat(commandParameters).map(CommandParameters::period).hasValue(100L);
    }

    @Test
    void should_set_a_default_value_to_period_parameter() {
        Optional<CommandParameters> commandParameters = CommandParameters.from(new String[]{"-location", "Suresnes"});
        assertThat(commandParameters).map(CommandParameters::period).hasValue(60L);
    }

    @Test
    void should_check_period_parameter_is_positive() {
        assertThat(CommandParameters.from(new String[]{"-location", "Suresnes", "-period", "-1"})).isEmpty();
    }

    @Test
    void should_set_cities_parameter() {
        Optional<CommandParameters> commandParameters = CommandParameters.from(new String[]{"-location", "Suresnes", "-cities", "Suresnes,Vernon"});
        assertThat(commandParameters).map(CommandParameters::cities).contains(List.of("Suresnes", "Vernon"));
    }

    @Test
    void should_set_a_default_value_to_cities_parameter() {
        Optional<CommandParameters> commandParameters = CommandParameters.from(new String[]{"-location", "Suresnes"});
        assertThat(commandParameters).map(CommandParameters::cities).contains(List.of());
    }
}