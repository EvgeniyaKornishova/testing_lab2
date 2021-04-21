package functions.system

import functions.MathFunction
import functions.log.*
import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.pow

open class SecondFunction(
    accuracy: Double,
    private val ln: Ln = Ln(accuracy),
    private val log2: Log2 = Log2(accuracy),
    private val log3: Log3 = Log3(accuracy),
    private val log5: Log5 = Log5(accuracy),
    private val log10: Log10 = Log10(accuracy),
): MathFunction(accuracy) {
    override fun invoke(x: Double): Double {
        if (abs(x - 1) < accuracy )
            throw IllegalArgumentException("X could not be 1 second function")
        return (
                (log5(x) + log3(x)) /
                (log2(x).pow(2)) /
                (ln(x) - log2(x)) /
                log3(x)
               ) + (
                ln(x) /
                (log10(x) + ln(x))
               )
    }
}