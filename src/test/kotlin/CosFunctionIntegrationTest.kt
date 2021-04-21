import functions.trig.Cos
import functions.trig.Sin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import utils.CSV
import utils.IntervalCalculator
import kotlin.math.PI

class CosFunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var cos: Cos


        @JvmStatic
        @BeforeAll
        fun setup() {
            val sin: Sin = Mockito.mock(Sin::class.java)

            Mockito.`when`(sin(ArgumentMatchers.anyDouble())).thenAnswer { x -> kotlin.math.sin(x.getArgument(0)) }

            cos = Cos(accuracy, sin)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("Cos.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(cos, -20.0, 0.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @Test
    fun `test first part`() {
        Assertions.assertEquals(-0.965926,  cos(-11 * PI / 12   ), accuracy)
    }

    @Test
    fun `test second part`() {
        Assertions.assertEquals(0.0,        cos(-PI / 2         ), accuracy)
    }

    @Test
    fun `test third part`() {
        Assertions.assertEquals(0.309017,   cos(-2 * PI / 5     ), accuracy)
    }

    @Test
    fun `test fourth part`() {
        Assertions.assertEquals(0.965926,   cos(-PI / 12        ), accuracy)
    }

    @Test
    fun `extra points`() {
        Assertions.assertEquals(1.0,        cos(-2 * PI         ), accuracy)
        Assertions.assertEquals(0.5,        cos(-5 * PI / 3     ), accuracy)
        Assertions.assertEquals(-1.0,       cos(-PI                ), accuracy)
        Assertions.assertEquals(1.0,        cos(0.0             ), accuracy)
    }
}