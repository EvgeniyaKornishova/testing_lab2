import functions.system.FirstFunction
import functions.trig.Cos
import functions.trig.Cot
import functions.trig.Sin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.Mockito
import utils.CSV
import utils.IntervalCalculator
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.tan

class FirstFunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var firstFunction: FirstFunction

        @JvmStatic
        @BeforeAll
        fun setup() {
            val cot: Cot = Mockito.mock(Cot::class.java)
            val cos: Cos = Mockito.mock(Cos::class.java)
            val sin: Sin = Mockito.mock(Sin::class.java)

            fun cotan_mock(x: Double): Double {
                if (abs(x % PI) < accuracy)
                    throw IllegalArgumentException()

                if (abs(x % (PI / 2)) < accuracy)
                    return 0.0

                return 1 / tan(x)
            }

            Mockito.`when`(cot(anyDouble())).thenAnswer { x -> cotan_mock(x.getArgument(0)) }
            Mockito.`when`(cos(anyDouble())).thenAnswer { x -> kotlin.math.cos(x.getArgument(0)) }
            Mockito.`when`(sin(anyDouble())).thenAnswer { x -> kotlin.math.sin(x.getArgument(0)) }

            firstFunction = FirstFunction(accuracy, sin, cos, cot)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("FirstFunction.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(firstFunction, -20.0, 0.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }

    @Test
    fun `test first part`() {
        Assertions.assertEquals(-3.854884, firstFunction(-11 * PI / 12), accuracy)
    }

    @Test
    fun `test second part`() {
        Assertions.assertEquals(0.0, firstFunction(-PI / 2), accuracy)
    }

    @Test
    fun `test third part`() {
        Assertions.assertEquals(0.193487, firstFunction(-2 * PI / 5), accuracy)
    }

    @Test
    fun `test fourth part`() {
        Assertions.assertEquals(-3.354884, firstFunction(-PI / 12), accuracy)
    }

    @Test
    fun `test extra point`() {
        Assertions.assertEquals(-0.144337, firstFunction(-5 * PI / 3), accuracy)
    }


    @Test
    fun `test exceptions`(){
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            firstFunction(-2 * PI)
        }

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            firstFunction(-PI)
        }

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            firstFunction(0.0)
        }
    }
}