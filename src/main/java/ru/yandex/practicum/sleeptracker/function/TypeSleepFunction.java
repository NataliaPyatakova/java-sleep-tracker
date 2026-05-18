package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;

import java.util.List;
import java.util.function.Function;

public class TypeSleepFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long countSession = sessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult("Количество плохих сессий сна", (int) countSession);
    }
}
/*
Для этого выполните два шага:

    Для каждой ночи на основе времени засыпания и пробуждения определите, относится ночь к типу «сова», «жаворонок» или «голубь».
        «Сова» — если время засыпания было после 23:00, а время пробуждения — после 9:00.
        «Жаворонок» — если время засыпания было до 22:00, а время пробуждения до — 7:00.
        «Голубь» — во всех остальных случаях.
        Бессонные ночи и дневные сессии сна в подсчёте должны игнорироваться.
    Посчитайте количество ночей каждого типа и выберите, какой встречается чаще всего. Именно к этому типу нужно отнести пользователя.
    Если есть сомнения, например, количество ночей двух типов совпадает, считайте, что пользователь относится к «голубям».
 */