import functions.log.Ln
import functions.log.Log3
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import utils.CSV
import utils.IntervalCalculator
import kotlin.test.assertFailsWith

class Log3FunctionIntegrationTest {
    companion object {
        private val ln3: Double by lazy { kotlin.math.ln(3.0) }
        private const val accuracy = 1e-5
        private lateinit var log3: Log3

        @JvmStatic
        @BeforeAll
        fun setup() {
            val ln: Ln = Mockito.mock(Ln::class.java)

            fun ln_mock(x: Double): Double {
                if (x < -accuracy)
                    throw IllegalArgumentException()

                return kotlin.math.ln(x)
            }

            Mockito.`when`(ln(ArgumentMatchers.anyDouble())).thenAnswer { x -> ln_mock(x.getArgument(0)) }

            log3 = Log3(accuracy, ln)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("Log3.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(log3, 0.001, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @ParameterizedTest
    @CsvFileSource(resources = ["/log.csv"])
    fun `log tests`(x: Double){
        Assertions.assertEquals(
            kotlin.math.ln(x) / ln3,
            log3(x),
            accuracy
        )
    }

    @Test
    fun `log exception test`(){
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            log3(-1.0)
        }
    }
}
