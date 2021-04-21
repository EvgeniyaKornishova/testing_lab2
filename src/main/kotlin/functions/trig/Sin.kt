package functions.trig

import functions.MathFunction
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.pow

open class Sin(accuracy: Double): MathFunction(accuracy) {
    private fun factorial(num: Int): Long =
        if (num > 1) factorial(num - 1) * num else 1

    private fun toBaseRange(x: Double): Double {
        return if (x.absoluteValue > PI) {
            val k = x % (2 * PI)
            when {
                k < -PI -> k + 2 * PI
                k > PI -> k - 2 * PI
                else -> k
            }
        } else
            x
    }

    private fun tailor(x: Double, n: Int): Double = ((-1.0).pow(n) * x.pow(2*n+1)) / factorial(2*n+1)


    override fun invoke(x: Double): Double {
        val x = toBaseRange(x)

        var result = 0.0
        var current = 10.0
        var prev = 0.0
        var n = 0
        while (abs(prev - current) >= accuracy) {
            prev = current
            current = tailor(x, n)
            result += current
            n++
        }
        return result
    }
}