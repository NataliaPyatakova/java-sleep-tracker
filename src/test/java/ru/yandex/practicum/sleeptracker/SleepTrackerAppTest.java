package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    void testMainWithEmptyArgs() {

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            String[] args = new String[0];
            SleepTrackerApp.main(args);
            String expected = "Не задан путь к файлу" + System.lineSeparator();
            assertEquals(expected, outputStream.toString());

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testMainWithRightArgs() {
        String filePath = "src" + File.separator + "test" + File.separator + "java" + File.separator + "ru" + File.separator
                + "yandex" + File.separator + "practicum" + File.separator + "sleeptracker" + File.separator
                + "sleep_log_test.txt";
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            String[] args = new String[]{filePath};
            SleepTrackerApp.main(args);
            String expected = "Всего сессий сна: 13" + System.lineSeparator()
                    + "Максимальная длительность сессии (в минутах): 500" + System.lineSeparator()
                    + "Минимальная длительность сессии (в минутах): 45" + System.lineSeparator()
                    + "Средняя длительность сессии (в минутах): 345" + System.lineSeparator()
                    + "Количество плохих сессий сна: 2" + System.lineSeparator()
                    + "Количество бессонных ночей: 20" + System.lineSeparator()
                    + "Тип пользователя: Голубь" + System.lineSeparator();
            assertEquals(expected, outputStream.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}


