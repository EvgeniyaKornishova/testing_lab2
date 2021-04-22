import functions.log.Ln
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import utils.CSV
import utils.IntervalCalculator

class LnFunctionUnitTest {
    companion object{
        private const val accuracy = 1e-5
        private val ln = Ln(accuracy)

        @JvmStatic
        @AfterAll
        fun print(){
            val csvWriter = CSV("Ln.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(ln, 0.001, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/log.csv"])
    fun `ln tests`(x: Double){
        Assertions.assertEquals(kotlin.math.ln(x), ln(x), accuracy)
    }

    @Test
    fun `log infinity test`(){
        Assertions.assertEquals(Double.NEGATIVE_INFINITY, ln(0.0), accuracy)
    }
}
