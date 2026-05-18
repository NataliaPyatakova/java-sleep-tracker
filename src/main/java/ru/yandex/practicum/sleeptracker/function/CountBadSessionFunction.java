package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class CountBadSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long countSession = sessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult("Количество плохих сессий сна", (int) countSession);
    }
}
