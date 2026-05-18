package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MinDurationInMinutesFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int result = 0;
        Optional<Long> minDuration = sessions.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()).toMinutes())
                .min(Long::compareTo);
        if (minDuration.isPresent()) {
            result = minDuration.get().intValue();
        }
        return new SleepAnalysisResult("Минимальная длительность сессии (в минутах)", result);
    }
}
