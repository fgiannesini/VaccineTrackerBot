import java.time.LocalDate;

public record Availabilities(LocalDate date, int slots) {

    public boolean isBeforeTwoDays() {
        return date.isBefore(LocalDate.now().plusDays(2));
    }

    public boolean hasSlots() {
        return slots > 0;
    }
}
