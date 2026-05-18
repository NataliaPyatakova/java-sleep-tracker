package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.CountSleeplessNightsFunction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountSleeplessNightsFunctionTest {

    private List<SleepingSession> listSleepingSession;
    private static CountSleeplessNightsFunction function;
    private static SleepingSession session0;
    private static SleepingSession session1;
    private static SleepingSession session2;

    @BeforeAll
    public static void setup() {
        function = new CountSleeplessNightsFunction();
        session0 = SleepingSession.addSleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
        session1 = SleepingSession.addSleepingSession("02.10.25 23:15;03.10.25 07:30;GOOD");
        session2 = SleepingSession.addSleepingSession("05.10.25 13:30;05.10.25 14:15;NORMAL");
    }

    @BeforeEach
    public void beforeEach() {
        listSleepingSession = new ArrayList<>();
    }

    @Test
    void testCountSleeplessNightsWithNoSession() {
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithOneSession() {
        listSleepingSession.add(session0);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithTwoSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithMissedSessions() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        listSleepingSession.add(session2);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(2, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithOnlyOneWrongSession() {
        listSleepingSession.add(session2);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        //считаем, что если в логе нет ни одного перехода через ночь, то и ночи нет
        assertEquals(0, result.getResult());
        assertEquals("Количество бессонных ночей", result.getDescription());
    }
}
