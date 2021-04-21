import functions.log.Ln
import functions.log.Log5
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

class Log5FunctionIntegrationTest {

    companion object {
        private val ln5: Double by lazy { kotlin.math.ln(5.0) }
        private const val accuracy = 1e-5
        private lateinit var log5: Log5

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

            log5 = Log5(accuracy, ln)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("Log5.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(log5, 0.001, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @ParameterizedTest
    @CsvFileSource(resources = ["/log.csv"])
    fun `log tests`(x: Double){
        Assertions.assertEquals(
            kotlin.math.ln(x) / ln5,
            log5(x),
            accuracy
        )
    }

    @Test
    fun `log exception test`(){
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            log5(-1.0)
        }
    }
}
