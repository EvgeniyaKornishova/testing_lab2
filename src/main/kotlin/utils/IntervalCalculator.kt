package utils

import functions.MathFunction

class IntervalCalculator {
    operator fun invoke(
        func: MathFunction,
        start: Double,
        end: Double,
        step: Double = 0.1
    ): List<Pair<Double, Double>>{
        var result = mutableListOf<Pair<Double, Double>>()
        var current = start

        while (current <= end){
            try {
                result.add(Pair(current, func(current)))
            }catch (e: IllegalArgumentException){
                result.add(Pair(current, Double.NaN))
            }

            current += step
        }

        return result
    }
}