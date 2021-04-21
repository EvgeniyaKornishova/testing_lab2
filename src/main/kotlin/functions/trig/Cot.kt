package functions.trig

import functions.MathFunction
import java.lang.IllegalArgumentException
import kotlin.math.PI
import kotlin.math.abs

open class Cot(
    accuracy: Double,
    private val sin: Sin = Sin(accuracy),
    private val cos: Cos = Cos(accuracy)
) : MathFunction(accuracy) {
    override fun invoke(x: Double): Double {
        if (abs(x % PI) < accuracy)
            throw IllegalArgumentException("In ctg function x could not be Пk")
        return cos(x)/sin(x)
    }

}