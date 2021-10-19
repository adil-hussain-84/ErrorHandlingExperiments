import org.junit.jupiter.api.Test
import kotlin.test.*

class Experiment1KotlinTest {

    @Test
    fun test_1() {
        val result = Experiment1.validateInput(0)

        assertNull(result.getOrNull())
        val exception = assertIs<Experiment1.MyException>(result.exceptionOrNull())

        assertEquals("SomeDomain", exception.domain)
        assertEquals(123, exception.code)
    }

    @Test
    fun test_2() {
        val result = Experiment1.validateInput(1)

        assertNull(result.exceptionOrNull())
        val success = assertNotNull(result.getOrNull())

        assertTrue(success)
    }

    @Test
    fun test_3() {
        val result = Experiment1.validateInput(1)

        when (result.isFailure) {
            true -> fail("Validation should have succeeded but got $result")
            false -> assertEquals(true, result.getOrNull())
        }
    }

    @Test
    fun test_4() {
        val result = Experiment1.validateInput(1)

        if (result.isFailure) {
            fail("Validation should have succeeded but got $result)")
        }

        val success = result.getOrNull()!!

        assertTrue(success)
    }

    @Test
    fun test_5() {
        val result = Experiment1.validateInputV2(1)

        if (result.isFailure) {
            fail("Validation should have succeeded but got $result)")
        }

        val success = result.getOrNull()!!

        assertEquals(Unit, success)
    }

    @Test
    fun test_6() {
        val result = Experiment1.highLevelOperation(0, 1)

        assertNull(result.getOrNull())
        val exception = assertIs<Experiment1.MyException>(result.exceptionOrNull())

        assertEquals("SomeDomain", exception.domain)
        assertEquals(123, exception.code)
    }

    @Test
    fun test_7() {
        val result = Experiment1.highLevelOperation(1, 1)

        assertNull(result.exceptionOrNull())
        val success = assertNotNull(result.getOrNull())

        assertEquals(2, success)
    }

    @Test
    fun test_8() {
        val result = Experiment1.highLevelOperationV2(0, 1)

        assertNull(result.getOrNull())
        val exception = assertIs<Experiment1.MyException>(result.exceptionOrNull())

        assertEquals("SomeDomain", exception.domain)
        assertEquals(123, exception.code)
    }

    @Test
    fun test_9() {
        val result = Experiment1.highLevelOperationV2(1, 1)

        assertNull(result.exceptionOrNull())
        val success = assertNotNull(result.getOrNull())

        assertEquals(2, success)
    }
}