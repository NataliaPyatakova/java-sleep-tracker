package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enumeration.TypeSleep;

import java.util.List;
import java.util.function.Function;

public class TypeSleepFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        String typeSleep = TypeSleep.PIGEON.getDisplayName();
        long countOwlNights = sessions.stream()
                .filter(SleepingSession::checkTimeInOwlPeriod)
                .count();
        long countLarkNights = sessions.stream()
                .filter(SleepingSession::checkTimeInLarkPeriod)
                .count();
        if (countOwlNights > countLarkNights) {
            typeSleep = TypeSleep.OWL.getDisplayName();
        } else if (countLarkNights > countOwlNights) {
            typeSleep = TypeSleep.LARK.getDisplayName();
        }
        return new SleepAnalysisResult("Тип пользователя: " + typeSleep, -1);
    }
}
