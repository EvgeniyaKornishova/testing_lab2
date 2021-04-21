import functions.log.*
import functions.system.SecondFunction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import utils.CSV
import utils.IntervalCalculator
import java.lang.IllegalArgumentException
import kotlin.math.ln


class SecondFunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var secondFunction: SecondFunction
        private val ln2: Double by lazy { ln(2.0) }
        private val ln3: Double by lazy { ln(3.0) }
        private val ln5: Double by lazy { ln(5.0) }

        @JvmStatic
        @BeforeAll
        fun setup() {
            val ln: Ln = Mockito.mock(Ln::class.java)
            val log2: Log2 = Mockito.mock(Log2::class.java)
            val log3: Log3 = Mockito.mock(Log3::class.java)
            val log5: Log5 = Mockito.mock(Log5::class.java)
            val log10: Log10 = Mockito.mock(Log10::class.java)

            Mockito.`when`(ln(ArgumentMatchers.anyDouble())).thenAnswer { x -> kotlin.math.ln(x.getArgument(0)) }
            Mockito.`when`(log2(ArgumentMatchers.anyDouble()))
                .thenAnswer { x -> kotlin.math.ln(x.getArgument(0)) / ln2 }
            Mockito.`when`(log3(ArgumentMatchers.anyDouble()))
                .thenAnswer { x -> kotlin.math.ln(x.getArgument(0)) / ln3 }
            Mockito.`when`(log5(ArgumentMatchers.anyDouble()))
                .thenAnswer { x -> kotlin.math.ln(x.getArgument(0)) / ln5 }
            Mockito.`when`(log10(ArgumentMatchers.anyDouble())).thenAnswer { x -> kotlin.math.log10(x.getArgument(0)) }

            secondFunction = SecondFunction(accuracy, ln, log2, log3, log5, log10)
        }

        @JvmStatic
        @AfterAll
        fun print() {
            val csvWriter = CSV("SecondFunction.csv", listOf("x", "y"))
            val calculator = IntervalCalculator()
            val calculations = calculator(secondFunction, 0.001, 20.0)

            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }


    @Test
    fun `test first part`() {
        Assertions.assertEquals(1.13524, secondFunction(0.2), accuracy)
        Assertions.assertEquals(3.07092, secondFunction(0.4), accuracy)
    }

    @Test
    fun `test second part`() {
        Assertions.assertEquals(-8.29507,   secondFunction(1.8), accuracy)
        Assertions.assertEquals(-4.786224,  secondFunction(2.0), accuracy)
    }

    @Test
    fun `test third part`() {
        Assertions.assertEquals(-0.815775, secondFunction(2.9), accuracy)
        Assertions.assertEquals(-0.027190, secondFunction(3.9), accuracy)
    }

    @Test
    fun `test fourth part`() {
        Assertions.assertEquals(0.494117, secondFunction(8.0), accuracy)
        Assertions.assertEquals(0.547624, secondFunction(10.0), accuracy)
    }

    @Test
    fun `test exceptions`() {
        Assertions.assertThrows(IllegalArgumentException::class.java){
            secondFunction(1.0)
        }
    }
}