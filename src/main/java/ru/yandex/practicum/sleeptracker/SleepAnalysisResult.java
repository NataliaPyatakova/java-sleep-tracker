package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {

    private final String description;
    private final int result;

    public SleepAnalysisResult(String description, int result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }
    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        if (result < 0) {
            return description;
        }
        return description + ": "+ result;
    }
}
