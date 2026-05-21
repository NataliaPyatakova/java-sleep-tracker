package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;
import ru.yandex.practicum.sleeptracker.exception.WrongSleepingSessionException;

import static org.junit.jupiter.api.Assertions.*;

public class SleepingSessionTest {

    @Test
    void testAddSleepingSessionNullData() {
        try {
            SleepingSession.addSleepingSession(null);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Строка в файле пустая", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionNoData() {
        String data = "";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Строка в файле состоит из пробелов", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithoutFirstRegex() {
        String data = "01.10.25 23:15 02.10.25 07:30;GOOD";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Неверный формат строки. Ожидаются разделители данных ;", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithoutSecondRegex() {
        String data = "01.10.25 23:15;02.10.25 07:30 GOOD";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Неверный формат строки. Ожидаются разделители данных ;", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithIncorrectDateOrder() {
        String data = "03.10.25 23:15;02.10.25 07:30;GOOD";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Дата начала сессии позже даты конца сессии", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithWrongFirstDate() {
        String data = "78.10.25 23:15;02.10.25 07:30;GOOD";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Неверный формат даты", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithWrongSecondDate() {
        String data = "01.10.25 23:15;78.10.25 07:30;GOOD";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Неверный формат даты", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionDataWithWrongSleepQuality() {
        String data = "01.10.25 23:15;02.10.25 07:30;PERFECT";
        try {
            SleepingSession.addSleepingSession(data);
        } catch (WrongSleepingSessionException e) {
            assertEquals("Неизвестное качество сна", e.getMessage());
        }
    }

    @Test
    void testAddSleepingSessionWithCorrectData() {
        String data = "01.10.25 23:15;02.10.25 07:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertEquals("01.10.25 23:15 02.10.25 07:30 GOOD",session.toString());
        assertEquals(SleepQuality.GOOD,session.getSleepQuality());
    }

    //НОЧЬ СО СНОМ = есть сон в периоде 0 - 6
    @Test
    void testCheckDateTimeWithStartDateBeforeEndDateAfterNightPeriod() {
        String data = "01.10.25 23:15;02.10.25 07:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkDateTime());
    }

    @Test
    void testCheckDateTimeWithStartDateInEndDateAfterNightPeriod() {
        String data = "02.10.25 00:15;02.10.25 07:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkDateTime());
    }

    @Test
    void testCheckDateTimeWithStartDateBeforeEndDateInNightPeriod() {
        String data = "01.10.25 23:15;02.10.25 03:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkDateTime());
    }

    @Test
    void testCheckDateTimeWithStartDateInEndDateInNightPeriod() {
        String data = "02.10.25 00:15;02.10.25 03:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkDateTime());
    }

    @Test
    void testCheckDateTimeWithStartDateBeforeEndDateBeforeNightPeriod() {
        String data = "02.10.25 10:15;02.10.25 15:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkDateTime());
    }

    //СОВА = после 23 И после 9
    @Test
    void testCheckTimeInOwlPeriodStartDateAfterEndDateAfter() {
        String data = "01.10.25 23:15;02.10.25 10:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkTimeInOwlPeriod());
    }

    @Test
    void testCheckTimeInOwlPeriodStartDateBeforeEndDateAfter() {
        String data = "01.10.25 22:15;02.10.25 10:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInOwlPeriod());
    }

    @Test
    void testCheckTimeInOwlPeriodStartDateAfterEndDateBefore() {
        String data = "01.10.25 23:15;02.10.25 08:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInOwlPeriod());
    }

    @Test
    void testCheckTimeInOwlPeriodStartDateBeforeEndDateBefore() {
        String data = "01.10.25 22:15;02.10.25 08:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInOwlPeriod());
    }

    @Test
    void testCheckTimeInOwlPeriodStartDateAfter0EndDateAfter() {
        String data = "02.10.25 00:15;02.10.25 10:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkTimeInOwlPeriod());
    }

    //ЖАВОРОНОК = до 22 и до 7
    @Test
    void testCheckTimeInLarkPeriodStartDateBeforeEndDateBefore() {
        String data = "01.10.25 21:15;02.10.25 06:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertTrue(session.checkTimeInLarkPeriod());
    }

    @Test
    void testCheckTimeInLarkPeriodStartDateBeforeEndDateAfter() {
        String data = "01.10.25 21:15;02.10.25 08:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInLarkPeriod());
    }

    @Test
    void testCheckTimeInLarkPeriodStartDateAfterEndDateBefore() {
        String data = "01.10.25 23:15;02.10.25 06:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInLarkPeriod());
    }

    @Test
    void testCheckTimeInLarkPeriodStartDateAfterEndDateAfter() {
        String data = "01.10.25 23:15;02.10.25 10:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInLarkPeriod());
    }

    @Test
    void testCheckTimeInLarkPeriodStartDateAfter0EndDateBefore() {
        String data = "02.10.25 00:15;02.10.25 02:30;GOOD";
        SleepingSession session = SleepingSession.addSleepingSession(data);
        assertFalse(session.checkTimeInLarkPeriod());
    }
}
