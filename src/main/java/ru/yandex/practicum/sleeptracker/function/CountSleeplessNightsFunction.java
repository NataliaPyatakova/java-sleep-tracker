package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CountSleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int countAllNights = 0;
        Optional<LocalDateTime> startSessions = sessions.stream()
                .map(SleepingSession::getStartDateTime)
                .min(LocalDateTime::compareTo);
        Optional<LocalDateTime> endSessions = sessions.stream()
                .map(SleepingSession::getEndDateTime)
                .max(LocalDateTime::compareTo);
        if (startSessions.isPresent() && endSessions.isPresent()) {
            countAllNights = Period.between(startSessions.get().toLocalDate(), endSessions.get().toLocalDate()).getDays();
            //System.out.println(ChronoUnit.DAYS.between(startSessions.get().toLocalDate(), endSessions.get().toLocalDate()));
        }
        //считаем сессии с ночным сном - считаем их ночами
        long countNightsWithSleep = sessions.stream()
                .filter(SleepingSession::checkDateTime)
                .count();
        int result = countAllNights - (int) countNightsWithSleep;
        return new SleepAnalysisResult("Количество бессонных ночей", result);
    }
}
