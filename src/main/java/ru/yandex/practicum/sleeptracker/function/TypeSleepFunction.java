package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enumeration.TypeSleep;

import java.util.List;
import java.util.function.Function;

public class TypeSleepFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        TypeSleep typeSleep = TypeSleep.Голубь;
        long countOwlNights = sessions.stream()
                .filter(SleepingSession::checkTimeInOwlPeriod)
               // .peek(System.out::println)
                .count();
        long countLarkNights = sessions.stream()
                .filter(SleepingSession::checkTimeInLarkPeriod)
               // .peek(System.out::println)
                .count();
        if (countOwlNights > countLarkNights) {
            typeSleep = TypeSleep.Сова;
        } else if (countLarkNights > countOwlNights) {
            typeSleep = TypeSleep.Жаворонок;
        }
        return new SleepAnalysisResult("Тип пользователя: " + typeSleep, -1);
    }
}
