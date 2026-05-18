package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AvgDurationInMinutesFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int result = 0;
        Optional<Long> sumDuration = sessions.stream()
                .map(session -> Duration.between(session.getStartDateTime(), session.getEndDateTime()).toMinutes())
                .reduce(Long::sum);
        if (sumDuration.isPresent()) {
            result = sumDuration.get().intValue() / sessions.size();
        }
        return new SleepAnalysisResult("Средняя длительность сессии (в минутах)", result);
    }
}
