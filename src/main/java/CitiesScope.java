import java.util.List;

public record CitiesScope(List<String> cities) {

    public boolean isEligible(String city) {
        return cities.isEmpty() || cities.contains(city);
    }
}
