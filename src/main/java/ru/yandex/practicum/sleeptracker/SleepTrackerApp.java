package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> listFunction = new ArrayList<>();

    public static void main(String[] args) {
        //Приложение должно принимать на вход как аргумент командной строки путь к файлу с логом сна
        String filePath = "";
        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.println("Не задан путь к файлу");
            return;
        }

        List<SleepingSession> listSleepingSession = new ArrayList<>();
        loadSessions(listSleepingSession, filePath);
        listFunction.add(new CountAllSessionFunction());
        listFunction.add(new MaxDurationInMinutesFunction());
        listFunction.add(new MinDurationInMinutesFunction());
        listFunction.add(new AvgDurationInMinutesFunction());
        listFunction.add(new CountBadSessionFunction());
        listFunction.add(new CountSleeplessNightsFunction());
        listFunction.add(new TypeSleepFunction());
        listFunction.forEach(function -> System.out.println(function.apply(listSleepingSession)));
    }

    private static void loadSessions(List<SleepingSession> listSleepingSession, String filePath) {

        try (FileReader fileReader = new FileReader(filePath, StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(fileReader);
            br.lines().map(SleepingSession::addSleepingSession)
                    .filter(Objects::nonNull)
                    .forEach(listSleepingSession::add);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}