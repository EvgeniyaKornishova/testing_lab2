package functions.log

import functions.MathFunction

open class Log2 (
    accuracy: Double,
    private val ln: Ln = Ln(accuracy),
) : MathFunction(accuracy)
{
    private val ln2 = ln(2.0)

    override fun invoke(x: Double): Double = ln(x) / ln2
}