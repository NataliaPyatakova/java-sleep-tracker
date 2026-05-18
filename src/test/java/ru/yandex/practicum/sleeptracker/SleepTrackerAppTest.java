package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SleepTrackerAppTest {

    @Test
    void testMain() {
        SleepTrackerApp.main(null);
    }

    @Test
    void testLoadSessions() {
        List<SleepingSession> listSleepingSession = new ArrayList<>();
        SleepTrackerApp.loadSessions(listSleepingSession);
        assertFalse(listSleepingSession.isEmpty());
    }
}


