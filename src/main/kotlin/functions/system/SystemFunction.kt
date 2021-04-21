package functions.system

import functions.MathFunction

open class SystemFunction(
    accuracy: Double,
    private val firstFunction: FirstFunction = FirstFunction(accuracy),
    private val secondFunction: SecondFunction = SecondFunction(accuracy)
): MathFunction(accuracy) {
    override fun invoke(x: Double): Double {
        return if (x <= 0.0) firstFunction(x) else secondFunction(x)
    }
}