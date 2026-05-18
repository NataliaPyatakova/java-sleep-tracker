package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final String DIRECTORY = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    private static final String LOG_FILE = "sleep_log.txt";
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> listFunction = new ArrayList<>();

    public static void main(String[] args) {


        List<SleepingSession> listSleepingSession = new ArrayList<>();
        try (FileReader fileReader = new FileReader(DIRECTORY + LOG_FILE, StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(fileReader);
            br.lines().map(line -> {
                        try {
                            return SleepingSession.addSleepingSession(line);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(listSleepingSession::add);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        listSleepingSession.stream()
               // .peek(sleepingSession -> System.out.println(Duration.between(sleepingSession.getStartDateTime(),  sleepingSession.getEndDateTime())))
                .forEach(System.out::println);


        listFunction.add(new CountAllSessionFunction());
        listFunction.add(new MaxDurationInMinutesFunction());
        listFunction.add(new MinDurationInMinutesFunction());
        listFunction.add(new AvgDurationInMinutesFunction());
        listFunction.add(new CountBadSessionFunction());
        listFunction.add(new CountSleeplessNightsFunction());
        //listFunction.add(new TypeSleepFunction());

        listFunction.forEach(function -> System.out.println(function.apply(listSleepingSession)));

    }
}