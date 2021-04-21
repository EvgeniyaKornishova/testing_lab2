import functions.system.FirstFunction
import functions.system.SecondFunction
import functions.system.SystemFunction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import utils.CSV
import utils.DotsCalculator
import utils.IntervalCalculator
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.test.assertFailsWith

class SystemFunctionIntegrationTest {
    companion object {
        private const val accuracy = 1e-5
        private lateinit var systemFunction: SystemFunction

        @JvmStatic
        @BeforeAll
        private fun init() {
            val firstFunction = mock(FirstFunction::class.java)
            val secondFunction = mock(SecondFunction::class.java)

            Mockito.`when`(firstFunction(-2*PI      )).thenThrow(IllegalArgumentException())
            Mockito.`when`(firstFunction(-5*PI/3    )).thenReturn(-0.144337)
            Mockito.`when`(firstFunction(-PI           )).thenThrow(IllegalArgumentException())
            Mockito.`when`(firstFunction(-11*PI/12  )).thenReturn(-3.854884)
            Mockito.`when`(firstFunction(-PI/2      )).thenReturn(0.0)
            Mockito.`when`(firstFunction(-2*PI/5    )).thenReturn( 0.193487)
            Mockito.`when`(firstFunction(-PI/12     )).thenReturn(-3.354884)
            Mockito.`when`(firstFunction(0.0        )).thenThrow(IllegalArgumentException())

            Mockito.`when`(secondFunction(0.2   )).thenReturn(1.13524)
            Mockito.`when`(secondFunction(0.4   )).thenReturn(3.07092)
            Mockito.`when`(secondFunction(1.0   )).thenThrow(IllegalArgumentException())
            Mockito.`when`(secondFunction(1.7   )).thenReturn(-11.5252)
            Mockito.`when`(secondFunction(2.0   )).thenReturn(-4.786223)
            Mockito.`when`(secondFunction(2.9   )).thenReturn(-0.815775)
            Mockito.`when`(secondFunction(3.9   )).thenReturn(-0.027190)
            Mockito.`when`(secondFunction(8.0   )).thenReturn(0.494117)
            Mockito.`when`(secondFunction(10.0  )).thenReturn(0.547624)


            systemFunction = SystemFunction(accuracy, firstFunction, secondFunction)
        }

        @JvmStatic
        @AfterAll
        fun print(){
            val csvWriter = CSV("SystemFunction.csv", listOf("x", "y"))
            val calculator = DotsCalculator()
            val calculations = calculator(systemFunction,
            listOf(
                -2*PI, -5*PI/3, -PI, -11*PI/12, -PI/2, -2*PI/5, -PI/12,
                0.0, 0.2, 0.4, 1.0, 1.7, 2.0, 2.9, 3.9, 8.0, 10.0
            ))
            calculations.forEach { x -> csvWriter.write(x.first, x.second) }

            csvWriter.close()
        }
    }

    @Test
    fun `test negative and zero`(){
        assertEquals(-0.144337, systemFunction(-5*PI/3  ), accuracy)
        assertEquals(-3.854884, systemFunction(-11*PI/12), accuracy)
        assertEquals(0.0,       systemFunction(-PI/2    ), accuracy)
        assertEquals(0.193487,  systemFunction(-2*PI/5  ), accuracy)
        assertEquals(-3.354884, systemFunction(-PI/12   ), accuracy)

        assertFailsWith<IllegalArgumentException> {
            systemFunction(-2*PI)
        }

        assertFailsWith<IllegalArgumentException> {
            systemFunction(-PI)
        }

        assertFailsWith<IllegalArgumentException> {
            systemFunction(0.0)
        }
    }

    @Test
    fun `test positive`(){
        assertEquals(1.13524,   systemFunction( 0.2 ), accuracy)
        assertEquals(3.07092,   systemFunction( 0.4 ), accuracy)
        assertEquals(-11.5252,  systemFunction( 1.7 ), accuracy)
        assertEquals(-4.786223, systemFunction( 2.0 ), accuracy)
        assertEquals(-0.815775, systemFunction( 2.9 ), accuracy)
        assertEquals(-0.027190, systemFunction( 3.9 ), accuracy)
        assertEquals(0.494117,  systemFunction( 8.0 ), accuracy)
        assertEquals(0.547624,  systemFunction( 10.0), accuracy)

        assertFailsWith<IllegalArgumentException> {
            systemFunction(1.0)
        }
    }
}