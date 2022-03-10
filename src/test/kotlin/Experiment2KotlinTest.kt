import org.junit.jupiter.api.Test
import kotlin.test.*

class Experiment2KotlinTest {

    @Test
    fun test_1() {
        val result = Experiment2.validateInput(0)

        val error = assertNotNull(result.errorOrNull())

        assertEquals("SomeDomain", error.domain)
        assertEquals(123, error.code)
    }

    @Test
    fun test_2() {
        val value = Experiment2.validateInput(1).unwrapValueOrExitWith {
            fail("Failed unwrapping value. Encountered error: $it")
        }
        assertTrue(value)
    }

    @Test
    fun test_3() {
        when (val result = Experiment2.validateInput(1)) {
            is Experiment2.Error -> fail("Validation should have succeeded but got $result")
            is Experiment2.Success -> assertTrue(result.value)
        }
    }

    @Test
    fun test_4() {
        val result = Experiment2.validateInputV2(0)

        assertNull(result.getValueOrNull())
        val error = assertNotNull(result.errorOrNull())

        assertEquals("SomeDomain", error.domain)
        assertEquals(123, error.code)
    }

    @Test
    fun test_5() {
        val value = Experiment2.validateInputV2(1).unwrapValueOrExitWith {
            fail("Failed unwrapping value. Encountered error: $it")
        }
        assertEquals(Unit, value)
    }

    @Test
    fun test_6() {
        val result = Experiment2.highLevelOperation(0, 1)

        val error = assertNotNull(result.errorOrNull())

        assertEquals("SomeDomain", error.domain)
        assertEquals(123, error.code)
    }

    @Test
    fun test_7() {
        val value = Experiment2.highLevelOperation(1, 1).unwrapValueOrExitWith {
            fail("Failed unwrapping value. Encountered error: $it")
        }
        assertEquals(2, value)
    }
}