package functions.system

import functions.MathFunction
import functions.trig.Cos
import functions.trig.Cot
import functions.trig.Sin

open class FirstFunction(
    accuracy: Double,
    private val sin: Sin = Sin(accuracy),
    private val cos: Cos = Cos(accuracy),
    private val cot: Cot = Cot(accuracy)
): MathFunction(accuracy) {
    override fun invoke(x: Double): Double {
        return (cot(x) - sin(x)) * cos(x)
    }
}