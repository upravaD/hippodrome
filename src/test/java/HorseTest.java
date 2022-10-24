import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    private final String EXPECTED_NAME = "exampleName";
    private final int EXPECTED_SPEED = 10;
    private final int EXPECTED_DISTANCE = 100;
    private final Horse HORSE = new Horse(EXPECTED_NAME, EXPECTED_SPEED, EXPECTED_DISTANCE);

    @Test
    public void nullNameExceptionTest(){
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, EXPECTED_SPEED, EXPECTED_DISTANCE));
    }

    @Test
    public void nullNameExceptionMessageTest(){
        try{
            new Horse(null, EXPECTED_SPEED, EXPECTED_DISTANCE);
        } catch (IllegalArgumentException exception){
            assertEquals("Name cannot be null.", exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void isBlankNameExceptionTest(String name){
        IllegalArgumentException exception = assertThrows
                (IllegalArgumentException.class, () -> new Horse(name, EXPECTED_SPEED, EXPECTED_DISTANCE));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void nameExceptionTest(){
        try {
            Field name = HORSE.getClass().getDeclaredField("name");
            name.setAccessible(true);
            String horseName = (String) name.get(HORSE);
            assertEquals(EXPECTED_NAME, horseName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void speedExceptionTest(){
        IllegalArgumentException exception = assertThrows
                (IllegalArgumentException.class, () -> new Horse(EXPECTED_NAME, -EXPECTED_SPEED, EXPECTED_DISTANCE));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void distanceExceptionTest(){
        IllegalArgumentException exception = assertThrows
                (IllegalArgumentException.class, () -> new Horse(EXPECTED_NAME, EXPECTED_SPEED, -EXPECTED_DISTANCE));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getNameTest(){
        assertEquals(EXPECTED_NAME, HORSE.getName());
    }

    @Test
    public void getSpeedTest(){
        assertEquals(EXPECTED_SPEED, HORSE.getSpeed());
    }

    @Test
    public void getDistanceTest(){
        assertEquals(EXPECTED_DISTANCE, HORSE.getDistance());
        assertEquals(0, new Horse(EXPECTED_NAME, EXPECTED_SPEED).getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 1.0, 1.5, 2.8, 8.2, 0.82})
    public void moveUseRandomTest(double randomValue){
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            HORSE.move();
            assertEquals(EXPECTED_DISTANCE + EXPECTED_SPEED*randomValue, HORSE.getDistance());
        }
    }

}
