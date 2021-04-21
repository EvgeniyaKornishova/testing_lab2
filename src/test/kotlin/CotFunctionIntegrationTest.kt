import functions.trig.Cos
import functions.trig.Cot
import functions.trig.Sin
import org.junit.After
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import utils.CSV
import utils.IntervalCalculator
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.test.assertFailsWith

class CotFunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var cot: Cot


        @JvmStatic
        @BeforeAll
        fun setup() {
            val sin: Sin = Mockito.mock(Sin::class.java)
            val cos: Cos = Mockito.mock(Cos::class.java)

            Mockito.`when`(sin(ArgumentMatchers.anyDouble())).thenAnswer { x -> kotlin.math.sin(x.getArgument(0)) }
            Mockito.`when`(cos(ArgumentMatchers.anyDouble())).thenAnswer { x -> kotlin.math.cos(x.getArgument(0)) }

            cot = Cot(accuracy, sin, cos)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("Cot.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(cot, -20.0, 0.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @Test
    fun `test first part`() {
        Assertions.assertEquals(3.732051, cot(-11 * PI / 12), accuracy)
    }

    @Test
    fun `test second part`() {
        Assertions.assertEquals(0.0, cot(-PI / 2), accuracy)
    }

    @Test
    fun `test third part`() {
        Assertions.assertEquals(-0.32492, cot(-2 * PI / 5), accuracy)
    }

    @Test
    fun `test fourth part`() {
        Assertions.assertEquals(-3.732051, cot(-PI / 12), accuracy)
    }

    @Test
    fun `test extra point`(){
        Assertions.assertEquals(0.577350, cot(-5 * PI / 3), accuracy)
    }

    @Test
    fun `test exceptions`() {
        assertFailsWith<IllegalArgumentException> {
            cot(-2 * PI)
        }

        assertFailsWith<IllegalArgumentException> {
            cot(-PI)
        }

        assertFailsWith<IllegalArgumentException> {
            cot(0.0)
        }
    }
}