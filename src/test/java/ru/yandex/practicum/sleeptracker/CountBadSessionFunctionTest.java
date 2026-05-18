package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.CountBadSessionFunction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountBadSessionFunctionTest {

    private List<SleepingSession> listSleepingSession;
    private static CountBadSessionFunction function;
    private static SleepingSession session0;
    private static SleepingSession session1;
    private static SleepingSession session2;
    private static SleepingSession session3;

    @BeforeAll
    public static void setup() {
        function = new CountBadSessionFunction();
        session0 = SleepingSession.addSleepingSession("01.10.25 23:15;02.10.25 07:30;BAD");
        session1 = SleepingSession.addSleepingSession("05.10.25 13:30;05.10.25 14:15;NORMAL");
        session2 = SleepingSession.addSleepingSession("06.10.25 13:30;07.10.25 14:15;GOOD");
        session3 = SleepingSession.addSleepingSession("08.10.25 13:30;09.10.25 14:15;BAD");
    }

    @BeforeEach
    public void beforeEach() {
        listSleepingSession = new ArrayList<>();
    }

    @Test
    void testCountBadSessionWithNoAddedSession() {
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Количество плохих сессий сна", result.getDescription());
    }

    @Test
    void testCountBadSessionWithNoSession() {
        listSleepingSession.add(session1);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Количество плохих сессий сна", result.getDescription());
    }

    @Test
    void testCountBadSessionWithOneSession() {
        listSleepingSession.add(session0);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(1, result.getResult());
        assertEquals("Количество плохих сессий сна", result.getDescription());
    }

    @Test
    void testCountBadSessionWithTwoSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        listSleepingSession.add(session2);
        listSleepingSession.add(session3);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(2, result.getResult());
        assertEquals("Количество плохих сессий сна", result.getDescription());
    }
}
