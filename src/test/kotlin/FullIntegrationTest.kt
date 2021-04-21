import functions.system.SystemFunction
import org.junit.After
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import utils.CSV
import utils.IntervalCalculator
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.test.assertFailsWith

class FullIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private var systemFunction: SystemFunction = SystemFunction(accuracy)

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("FullSystemFunction.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(systemFunction, -20.0, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }

    @Test
    fun `system test`(){
        Assertions.assertEquals(-0.144337,  systemFunction(-5 * PI / 3  ), accuracy)
        Assertions.assertEquals(-3.854884,  systemFunction(-11 * PI / 12), accuracy)
        Assertions.assertEquals(0.0,        systemFunction(-PI / 2      ), accuracy)
        Assertions.assertEquals(0.193487,   systemFunction(-2 * PI / 5  ), accuracy)
        Assertions.assertEquals(-3.354884,  systemFunction(-PI / 12     ), accuracy)

        assertFailsWith<IllegalArgumentException> {
            systemFunction(-2* PI)
        }

        assertFailsWith<IllegalArgumentException> {
            systemFunction(-PI)
        }

        assertFailsWith<IllegalArgumentException> {
            systemFunction(0.0)
        }

        Assertions.assertEquals(1.13524,    systemFunction(0.2  ), accuracy)
        Assertions.assertEquals(3.07092,    systemFunction(0.4  ), accuracy)
        Assertions.assertEquals(-8.29507,   systemFunction(1.8  ), accuracy)
        Assertions.assertEquals(-4.786223,  systemFunction(2.0  ), accuracy)
        Assertions.assertEquals(-0.815775,  systemFunction(2.9  ), accuracy)
        Assertions.assertEquals(-0.027190,  systemFunction(3.9  ), accuracy)
        Assertions.assertEquals(0.494117,   systemFunction(8.0  ), accuracy)
        Assertions.assertEquals(0.547624,   systemFunction(10.0 ), accuracy)

        assertFailsWith<IllegalArgumentException> {
            systemFunction(1.0)
        }
    }
}