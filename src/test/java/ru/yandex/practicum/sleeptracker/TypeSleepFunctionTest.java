package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enumeration.TypeSleep;
import ru.yandex.practicum.sleeptracker.function.TypeSleepFunction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeSleepFunctionTest {

    private List<SleepingSession> listSleepingSession;
    private static TypeSleepFunction function;
    private static SleepingSession session0;
    private static SleepingSession session1;
    private static SleepingSession sessionOwl;
    private static SleepingSession sessionOwl1;
    private static SleepingSession sessionLark;
    private static SleepingSession sessionLark1;
    TypeSleep typeSleepDefault = TypeSleep.Голубь;
    TypeSleep typeSleepOwl = TypeSleep.Сова;
    TypeSleep typeSleepLark = TypeSleep.Жаворонок;

    @BeforeAll
    public static void setup() {
        function = new TypeSleepFunction();
        session0 = SleepingSession.addSleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
        session1 = SleepingSession.addSleepingSession("02.10.25 23:15;03.10.25 07:30;GOOD");
        sessionOwl = SleepingSession.addSleepingSession("05.10.25 23:30;06.10.25 14:15;NORMAL");
        sessionOwl1 = SleepingSession.addSleepingSession("07.10.25 23:30;08.10.25 14:15;NORMAL");
        sessionLark = SleepingSession.addSleepingSession("06.10.25 21:30;07.10.25 06:15;NORMAL");
        sessionLark1 = SleepingSession.addSleepingSession("09.10.25 21:30;10.10.25 06:15;NORMAL");
    }

    @BeforeEach
    public void beforeEach() {
        listSleepingSession = new ArrayList<>();
    }

    @Test
    void testTypeSleepFunctionWithNoSession() {
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepDefault, result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithOneNoTypeSession() {
        listSleepingSession.add(session0);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepDefault, result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithTwoNoTypeSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepDefault, result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithOneOwlSession() {
        listSleepingSession.add(sessionOwl);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepOwl, result.getDescription());
    }

    @Test
    void testCountSleeplessNightsWithOneLarkSession() {
        listSleepingSession.add(sessionLark);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepLark, result.getDescription());
    }
    @Test
    void testCountSleeplessNightsWithOneLarkOneOwlSession() {
        listSleepingSession.add(sessionLark);
        listSleepingSession.add(sessionOwl);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepDefault, result.getDescription());
    }
    @Test
    void testCountSleeplessNightsWithOneLarkOneOwlTwoOtherSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        listSleepingSession.add(sessionLark);
        listSleepingSession.add(sessionOwl);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepDefault, result.getDescription());
    }
    @Test
    void testCountSleeplessNightsWithOneLarkTwoOwlTwoOtherSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        listSleepingSession.add(sessionLark);
        listSleepingSession.add(sessionOwl);
        listSleepingSession.add(sessionOwl1);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepOwl, result.getDescription());
    }
    @Test
    void testCountSleeplessNightsWithTwoLarkOneOwlTwoOtherSession() {
        listSleepingSession.add(session0);
        listSleepingSession.add(session1);
        listSleepingSession.add(sessionLark);
        listSleepingSession.add(sessionLark1);
        listSleepingSession.add(sessionOwl);
        SleepAnalysisResult result = function.apply(listSleepingSession);
        assertEquals("Тип пользователя: " + typeSleepLark, result.getDescription());
    }
}
