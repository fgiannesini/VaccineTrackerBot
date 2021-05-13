import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class AvailabilitiesTest {

    @Test
    void should_return_true_if_before_two_days() {
        assertThat(new Availabilities(LocalDate.now(),1).isBeforeTwoDays()).isTrue();
        assertThat(new Availabilities(LocalDate.now().plusDays(1),1).isBeforeTwoDays()).isTrue();
    }

    @Test
    void should_return_false_if_after_two_days() {
        assertThat(new Availabilities(LocalDate.now().plusDays(2),1).isBeforeTwoDays()).isFalse();
    }

    @Test
    void should_return_true_if_has_slots() {
        assertThat(new Availabilities(LocalDate.now(),1).hasSlots()).isTrue();
        assertThat(new Availabilities(LocalDate.now(),2).hasSlots()).isTrue();
    }

    @Test
    void should_return_false_if_has_not_slots() {
        assertThat(new Availabilities(LocalDate.now(),0).hasSlots()).isFalse();
        assertThat(new Availabilities(LocalDate.now(),-1).hasSlots()).isFalse();
    }
}