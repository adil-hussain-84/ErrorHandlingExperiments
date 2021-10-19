import kotlin.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Experiment2JavaTest {

    @Test
    public void test_1() {
        Experiment2.Result<Boolean> result = Experiment2.Companion.validateInput(0);

        Experiment2.Error error = result.errorOrNull();

        assertNotNull(error);

        assertEquals("SomeDomain", error.getDomain());
        assertEquals(123, error.getCode());
    }

    @Test
    public void test_2() {
        Experiment2.Result<Boolean> result = Experiment2.Companion.validateInput(1);

        Boolean value = result.getValueOrNull();

        assertNotNull(value);
        assertTrue(value);
    }

    @Test
    public void test_4() {
        Experiment2.Result<Unit> result = Experiment2.Companion.validateInputV2(0);

        assertNull(result.getValueOrNull());
        Experiment2.Error error = result.errorOrNull();

        assertNotNull(error);

        assertEquals("SomeDomain", error.getDomain());
        assertEquals(123, error.getCode());

    }

    @Test
    public void test_5() {
        Experiment2.Result<Unit> result = Experiment2.Companion.validateInputV2(1);

        if (result instanceof Experiment2.Error) {
            fail("Validation should have succeeded but got " + result);
        }

        Experiment2.Success<Unit> success = (Experiment2.Success<Unit>) result;

        assertEquals(Unit.INSTANCE, success.getValue());
    }

    @Test
    public void test_6() {
        Experiment2.Result<Integer> result = Experiment2.Companion.highLevelOperation(0, 1);

        assertTrue(result.isFailure());

        Experiment2.Error error = (Experiment2.Error) result;

        assertEquals("SomeDomain", error.getDomain());
        assertEquals(123, error.getCode());
    }

    @Test
    public void test_7() {
        Experiment2.Result<Integer> result = Experiment2.Companion.highLevelOperation(1, 1);

        Integer value = result.unwrapValueOrBailOutWith((error) -> {
            fail("Failed unwrapping value. Encountered error: $it");
            return null;
        });

        assertEquals(2, value);
    }

    @Test
    public void test_8() {
        Experiment2.Result<Integer> result = add(0, 1);

        assertTrue(result.isFailure());

        Experiment2.Error error = (Experiment2.Error) result;

        assertEquals("SomeDomain", error.getDomain());
        assertEquals(123, error.getCode());
    }

    private Experiment2.Result<Integer> add(Integer input1, Integer input2) {
        Integer value1 = Experiment2.Companion.lowLevelOperation(input1).unwrapValueOrBailOutWith((error) -> {
            return error;
        });

        Integer value2 = Experiment2.Companion.lowLevelOperation(input2).unwrapValueOrBailOutWith((error) -> {
            return error;
        });

        return new Experiment2.Success<>(value1 + value2);
    }
}
