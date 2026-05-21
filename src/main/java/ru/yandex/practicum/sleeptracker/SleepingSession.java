package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.enumeration.SleepQuality;
import ru.yandex.practicum.sleeptracker.exception.WrongSleepingSessionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final SleepQuality sleepQuality;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final LocalTime START_NIGHT = LocalTime.of(0, 0);
    private static final LocalTime END_NIGHT = LocalTime.of(6, 0);
    private static final LocalTime START_NIGHT_OWL = LocalTime.of(23, 0);
    private static final LocalTime END_NIGHT_OWL = LocalTime.of(9, 0);
    private static final LocalTime START_NIGHT_LARK = LocalTime.of(22, 0);
    private static final LocalTime END_NIGHT_LARK = LocalTime.of(7, 0);

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
        try {
            checkData(data);
            String[] strDataArr = data.split(";");
            LocalDateTime startDateTime = getLocalDateTime(strDataArr[0].trim());
            LocalDateTime endDateTime = getLocalDateTime(strDataArr[1].trim());
            SleepQuality sleepQuality = getSleepQuality(strDataArr[2].trim());
            if (startDateTime.isAfter(endDateTime)) {
                throw new WrongSleepingSessionException("Дата начала сессии позже даты конца сессии");
            }
            return new SleepingSession(startDateTime, endDateTime, sleepQuality);
        } catch (WrongSleepingSessionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean checkDateTime() {
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        LocalDateTime startCurrentNight = LocalDateTime.of(endDateTime.toLocalDate(), START_NIGHT);
        LocalDateTime endCurrentNight = LocalDateTime.of(endDateTime.toLocalDate(), END_NIGHT);
        return (checkTimeInNightPeriod(startTime) || checkTimeInNightPeriod(endTime))
                || (startDateTime.isBefore(startCurrentNight) && endDateTime.isAfter(endCurrentNight));
    }

    public boolean checkTimeInOwlPeriod() {
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        //начало ночи совы либо после 23 - то есть период с 23 до 24. либо до конца стандартной ночи - то есть период с 00 до 06
        return (startTime.isAfter(START_NIGHT_OWL) || startTime.isBefore(END_NIGHT)) && endTime.isAfter(END_NIGHT_OWL);
    }

    public boolean checkTimeInLarkPeriod() {
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        //время в периоде жаворонка - и это разные дни -отсекаем случай позднего укладывания после 0 и супер раннего подъема
        return startTime.isBefore(START_NIGHT_LARK) && endTime.isBefore(END_NIGHT_LARK) && startDate.isBefore(endDate);
    }

    private boolean checkTimeInNightPeriod(LocalTime time) {
        return time.isAfter(START_NIGHT) && time.isBefore(END_NIGHT);
    }

    private static void checkData(String data) {
        if (data == null) {
            throw new WrongSleepingSessionException("Строка в файле пустая");
        }
        if (data.isEmpty()) {
            throw new WrongSleepingSessionException("Строка в файле состоит из пробелов");
        }
        if (data.split(";").length != 3) {
            throw new WrongSleepingSessionException("Неверный формат строки. Ожидаются разделители данных ;");
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
