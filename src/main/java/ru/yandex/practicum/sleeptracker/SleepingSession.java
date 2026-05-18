package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;
import ru.yandex.practicum.sleeptracker.exception.WrongSleepingSessionException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final SleepQuality sleepQuality;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final LocalTime START_NIGHT =  LocalTime.of(0, 0);
    private static final LocalTime END_NIGHT =  LocalTime.of(6, 0);

    private SleepingSession(LocalDateTime startDateTime, LocalDateTime endDateTime, SleepQuality sleepQuality) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    @Override
    public String toString() {
        return startDateTime.format(FORMATTER) + " " + endDateTime.format(FORMATTER) + " " + sleepQuality;
    }

    public static SleepingSession addSleepingSession(String data) {
        checkData(data);
        String[] strDataArr = data.split(";");
        LocalDateTime startDateTime = getLocalDateTime(strDataArr[0]);
        LocalDateTime endDateTime = getLocalDateTime(strDataArr[1]);
        SleepQuality sleepQuality = getSleepQuality(strDataArr[2]);
        if (startDateTime.isAfter(endDateTime)) {
            throw new WrongSleepingSessionException("Дата начала сессии позже даты конца сессии");
        }
        return new SleepingSession(startDateTime, endDateTime, sleepQuality);
    }

    public boolean checkDateTime() {
        return checkStartDateTime() || checkEndDateTime();
    }

    private boolean checkStartDateTime() {
        LocalTime startTime = startDateTime.toLocalTime();
        return startTime.isBefore(START_NIGHT) || (startTime.isAfter(START_NIGHT) && startTime.isBefore(END_NIGHT));
    }

    private boolean checkEndDateTime() {
        LocalTime endTime = endDateTime.toLocalTime();
        return endTime.isAfter(END_NIGHT) || (endTime.isAfter(START_NIGHT) && endTime.isBefore(END_NIGHT));
    }

    private static void checkData(String data) {
        if (data == null) {
            throw new WrongSleepingSessionException("Строка в файле пустая");
        }
        if (data.isEmpty()) {
            throw new WrongSleepingSessionException("Строка в файле состоит из пробелов");
        }
        if (data.indexOf(';') != 14 || data.lastIndexOf(';') != 29) {
            throw new WrongSleepingSessionException("Неверный формат строки. Ожидаются разделители данных ;");
        }
        if (data.length() != 33 && data.length() != 34 && data.length() != 36) {
            throw new WrongSleepingSessionException("Неверный формат строки");
        }
    }

    private static SleepQuality getSleepQuality(String data) {
        try {
            return SleepQuality.valueOf(data);
        } catch (IllegalArgumentException e) {
            throw new WrongSleepingSessionException("Неизвестное качество сна");
        }
    }

    private static LocalDateTime getLocalDateTime(String data) {
        try {
            return LocalDateTime.parse(data, FORMATTER);
        } catch (Exception e) {
            throw new WrongSleepingSessionException("Неверный формат даты");
        }
    }
}
