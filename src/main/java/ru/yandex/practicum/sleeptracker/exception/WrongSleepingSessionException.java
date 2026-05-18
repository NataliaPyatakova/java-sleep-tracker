package ru.yandex.practicum.sleeptracker.exception;

public class WrongSleepingSessionException extends RuntimeException {
    public WrongSleepingSessionException(String message) {
        super(message);
    }
}
