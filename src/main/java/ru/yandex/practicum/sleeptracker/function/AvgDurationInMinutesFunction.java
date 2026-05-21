package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AvgDurationInMinutesFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double result = sessions.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()).toMinutes())
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
        return new SleepAnalysisResult("Средняя длительность сессии (в минутах)", (int) result);
    }
}
