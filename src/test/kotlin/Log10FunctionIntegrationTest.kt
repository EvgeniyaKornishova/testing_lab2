import functions.log.Ln
import functions.log.Log10
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

class Log10FunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var log10: Log10

        @JvmStatic
        @BeforeAll
        fun setup() {
            val ln: Ln = Mockito.mock(Ln::class.java)

            fun ln_mock(x: Double): Double {
                if (x < 0.0)
                    throw IllegalArgumentException()

                return kotlin.math.ln(x)
            }

            Mockito.`when`(ln(ArgumentMatchers.anyDouble())).thenAnswer { x -> ln_mock(x.getArgument(0)) }

            log10 = Log10(accuracy, ln)
        }

        @JvmStatic
        @AfterAll
        fun print(){
            val csvWriter = CSV("Log10.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(log10, 0.001, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @ParameterizedTest
    @CsvFileSource(resources = ["/log.csv"])
    fun `log tests`(x: Double){
        Assertions.assertEquals(kotlin.math.log10(x), log10(x), accuracy)
    }

    @Test
    fun `log exception test`(){
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            log10(-1.0)
        }
    }

}
