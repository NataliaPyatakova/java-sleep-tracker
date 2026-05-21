package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.MaxDurationInMinutesFunction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDurationInMinutesFunctionTest {

    private List<SleepingSession> listSleepingSession;
    private static MaxDurationInMinutesFunction function;
    private static SleepingSession session0;
    private static SleepingSession session1;

    @BeforeAll
    public static void setup() {
        function = new MaxDurationInMinutesFunction();
        session0 = SleepingSession.addSleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
        session1 = SleepingSession.addSleepingSession("05.10.25 13:30;05.10.25 14:15;NORMAL");
    }

    @BeforeEach
    public void beforeEach() {
        listSleepingSession = new ArrayList<>();
    }

    @Test
    void testMaxDurationInMinutesWithNoSession() {
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(0, result.getResult());
        assertEquals("Максимальная длительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void testMaxDurationInMinutesWithOneSession() {
        listSleepingSession.add(session0);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(495, result.getResult());
        assertEquals("Максимальная длительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void testMaxDurationInMinutesWithTwoSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals(495, result.getResult());
        assertEquals("Максимальная длительность сессии (в минутах)", result.getDescription());
    }

}
