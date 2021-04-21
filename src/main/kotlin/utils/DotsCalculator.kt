package utils

import functions.MathFunction

class DotsCalculator {
    operator fun invoke(
        func: MathFunction,
        dots: List<Double>
    ): List<Pair<Double, Double>> {
        var result = mutableListOf<Pair<Double, Double>>()

        for (x in dots) {
            try {
                result.add(Pair(x, func(x)))
            } catch (e: IllegalArgumentException) {
                result.add(Pair(x, Double.NaN))
            }
        }

        return result
    }
}
