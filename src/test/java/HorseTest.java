import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class HorseTest {

    @Test
    void nullInConstructor() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3, 1)),
                () -> {
                    try {
                        new Horse(null, 3, 1);
                    } catch (IllegalArgumentException e) {
                        assertEquals("Name cannot be null.", e.getMessage());
                    }
                }
        );
    }

    @ParameterizedTest
    @ValueSource (strings = {"", " ", "   "})
    void emptyStringOrSpacesInNameFieldOfConstructor(String name) {
        assertAll (
                () -> assertThrows(IllegalArgumentException.class, () -> new Horse(name, 3, 1)),
                () -> {
                    try {
                        new Horse(name, 3, 1);
                    } catch (IllegalArgumentException e) {
                        assertEquals("Name cannot be blank.", e.getMessage());
                    }
                }
        );
    }


    @Test
    void wrongNumbersInSpeedAndDistanceFieldsOfConstructor() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Horse("anyName", -1, 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Horse("anyName", 1, -1)),
                () -> {
                    try {
                        new Horse("anyName", -1, 1);
                    } catch (IllegalArgumentException e) {
                        assertEquals("Speed cannot be negative.", e.getMessage());
                    }
                },
                () -> {
                    try {
                        new Horse("anyName", 1, -1);
                    } catch (IllegalArgumentException e) {
                        assertEquals("Distance cannot be negative.", e.getMessage());
                    }
                }
        );
    }

    @Test
    void getNameTest() {
        Horse horse = new Horse("anyName", 1, 1);
        String expectedName = "anyName";
        assertEquals(expectedName, horse.getName());
    }

    @Test
    void getSpeedTest() {
        Horse horse = new Horse("anyName", 1, 1);
        int expectedSpeed = 1;
        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    void getDistanceTest() {
        Horse horseOne = new Horse("anyName", 1, 2);
        Horse horseTwo = new Horse("anyName", 1);
        int expectedOne = 2;
        int expectedDefault = 0;

        assertAll(
                () -> assertEquals(expectedOne, horseOne.getDistance()),
                () -> assertEquals(expectedDefault, horseTwo.getDistance())
        );
    }

    @Test
    void moveUsesGetRandomDouble() {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("anyName", 1, 2);
            horse.move();
            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvSource ({"0.2, 2.6", "0.3, 2.9", "0.5, 3.5", "0.677, 4.031000000000001", "0.9, 4.7"})
    void moveCalculatesCorrectDistance(double random, double distance) {
        try (MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)){
            staticHorse.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(random);
            Horse horse = new Horse("anyName", 3, 2);
            horse.move();
            double horseDistance = horse.getDistance();
            staticHorse.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
            assertEquals(horseDistance, distance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}