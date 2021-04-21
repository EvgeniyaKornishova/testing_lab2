package functions.log

import functions.MathFunction
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.pow

open class Ln(accuracy: Double): MathFunction(accuracy * 0.01) {
    private fun tailor(x: Double, n: Int) = (-1.0).pow(n+1) * (x - 1).pow(n) / n

    override fun invoke(x: Double): Double {
        if (x < -accuracy)
            throw IllegalArgumentException("X must be positive or 0")

        if (x.absoluteValue < accuracy)
            return Double.NEGATIVE_INFINITY

        if (abs(x - 1.0) < accuracy)
            return 0.0

        if ((x - 1.0) > accuracy) {
            val t = (x - 1.0) / (x + 1.0)
            return this(1.0 + t) - this(1.0 - t)
        }

        var result = 0.0
        var current = 10.0
        var prev = 0.0
        var n = 1
        while (abs(prev - current) >= accuracy){
            prev = current
            current = tailor(x, n)
            result += current
            n++
        }
        return result
    }

}