import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    private final List<Horse> horses = new ArrayList<>();

    @Test
    public void nullListExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullListExceptionMessageTest() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException exception) {
            assertEquals("Horses cannot be null.", exception.getMessage());
        }
    }

    @Test
    public void emptyListExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    public void emptyListExceptionMessageTest() {
        try {
            new Hippodrome(horses);
        } catch (IllegalArgumentException exception) {
            assertEquals("Horses cannot be empty.", exception.getMessage());
        }
    }

    @Test
    public void getHorsesTest() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("horse" + i, 10, 10));
        }
        assertEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    public void moveTest() {
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();
        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    public void getWinnerTest() {
        Horse horse = new Horse("name5", 5,50);
        for (int i = 1; i <= 4; i++) {
            horses.add(new Horse("name" + i, i*1, i*10));
        }
        horses.add(horse);
        Hippodrome hippodrome = new Hippodrome(horses);
        assertSame(horse, hippodrome.getWinner());
    }
}
