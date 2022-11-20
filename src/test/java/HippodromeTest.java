import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    
    @Test
    void testNullInConstructor() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null)),
                () -> {
                    try {
                        new Hippodrome(null);
                    } catch (IllegalArgumentException e) {
                        String expectedAnswer = "Horses cannot be null.";
                        assertEquals(expectedAnswer, e.getMessage());
                    }
                }
        );
    }

    @Test
    void testEmptyListInConstructor() {
        List<Horse> list = new ArrayList<>();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list)),
                () -> {
                    try {
                        new Hippodrome(list);
                    } catch (IllegalArgumentException e) {
                        String expectedAnswer = "Horses cannot be empty.";
                        assertEquals(expectedAnswer, e.getMessage());
                    }
                }
        );
    }

    @Test
    void getHorsesTest() {
        List<Horse> horseList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            String name = "Horse" + i;
            int speed = ThreadLocalRandom.current().nextInt(1, 5);
            int distance = ThreadLocalRandom.current().nextInt(1, 5);
            horseList.add(new Horse(name, speed, distance));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);

        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();
        Horse horse = mock(Horse.class);

        for (int i = 0; i < 50; i++) {
            horses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        verify(horse, times(50)).move();
    }

    @Test
    void getWinnerTest() {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("name1", 1, 10));
        horses.add(new Horse("name2", 2, 20));
        horses.add(new Horse("name3", 3, 30));
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(30, hippodrome.getWinner().getDistance());
    }
}