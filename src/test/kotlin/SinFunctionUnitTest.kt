import functions.trig.Sin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import utils.CSV
import utils.IntervalCalculator

class SinFunctionUnitTest {
    companion object {
        private const val accuracy = 1e-5
        private val sin = Sin(accuracy)

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("Sin.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(sin, -20.0, 0.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/sin_unit.csv"])
    fun `sin tests`(x: Double){
        Assertions.assertEquals(kotlin.math.sin(x), sin(x), accuracy)
    }
}