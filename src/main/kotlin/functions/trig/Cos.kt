package functions.trig

import functions.MathFunction
import kotlin.math.PI

open class Cos(
    accuracy: Double,
    private val sin: Sin = Sin(accuracy)
): MathFunction(accuracy) {
    override fun invoke(x: Double): Double {
        return sin(x + PI/2)
    }
}