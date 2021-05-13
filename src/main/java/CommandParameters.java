import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.validators.PositiveInteger;

import java.util.List;
import java.util.Optional;

public class CommandParameters {

    @Parameter(names = "-cities",
            description = """
                    Comma-separated list of cities eligible. \
                    If this parameter is not set all cities are eligible. \
                    Example: "Paris,Antony\"""",
            order = 2,
            echoInput = true)
    private List<String> cities;

    @Parameter(names = "-period",
            description = """
                    Period to check doctolib (in second). \
                    Example: 60""",
            order = 3,
            echoInput = true,
            validateWith = PositiveInteger.class)
    private long period;

    @Parameter(names = "-location",
            description = """
                    City where you live. \
                    Example : "Paris\"""",
            required = true,
            order = 1,
            echoInput = true)
    private String location;

    public static Optional<CommandParameters> from(String[] args) {
        CommandParameters commandParameters = new CommandParameters();
        JCommander jCommander = JCommander.newBuilder()
                .programName("VaccineTrackerBot")
                .addObject(commandParameters)
                .build();
        try {
            jCommander.parse(args);
            return Optional.of(commandParameters);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            jCommander.usage();
            return Optional.empty();
        }
    }

    private CommandParameters() {
        this.period = 60;
        this.cities = List.of();
    }

    public List<String> cities() {
        return cities;
    }

    public long period() {
        return period;
    }

    public String location() {
        return location;
    }
}
