package functions.log
import functions.MathFunction

open class Log10 (
    accuracy: Double,
    private val ln: Ln = Ln(accuracy),
) : MathFunction(accuracy)
{
    private val ln10 = ln(10.0)

    override fun invoke(x: Double): Double = ln(x) / ln10
}
