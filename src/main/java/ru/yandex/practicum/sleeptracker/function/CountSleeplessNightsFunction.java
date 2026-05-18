package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class CountSleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int result = 0;
        sessions.stream()
                .filter(SleepingSession::checkDateTime)
                .peek(System.out::println)
                .count();
        return new SleepAnalysisResult("Количество бессонных ночей", result);
    }
}

/*
ночи с 12го октября по 30 октября - бессонные
Бессонной ночью считается ночь, когда не было ни одной сессии сна, пересекающей интервал от 0:00 до 6:00.
То есть, если пользователь спал с 23:00 до 3:00, ночь не будет считаться бессонной,
также как если он спал с 2:00 до 7:00. А вот если сон был только с 7:00 до 11:00, такую ночь мы запишем в бессонные.
Также будем считать, что если первая сессия сна в файле началась после 12 дня,
потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
Подумайте о случае, когда интервал логирования начинается в одном месяце, а заканчивается в другом!

общее кол-во ночей
ChronoUnit.DAYS.between здесь гораздо более удобный вариант, и тем более этот класс относится к тому же API (Java Time), поэтому лучше его использовать
 */
