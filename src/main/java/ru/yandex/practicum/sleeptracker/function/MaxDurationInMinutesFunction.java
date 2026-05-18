package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaxDurationInMinutesFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int result = 0;
        Optional<Long> maxDuration = sessions.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()).toMinutes())
                .max(Long::compareTo);
        if (maxDuration.isPresent()) {
            result = maxDuration.get().intValue();
        }
        return new SleepAnalysisResult("Максимальная длительность сессии (в минутах)", result);
    }
}
