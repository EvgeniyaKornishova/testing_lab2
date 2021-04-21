import functions.system.SystemFunction
import utils.CSV
import utils.IntervalCalculator

fun main() {
    val systemFunction = SystemFunction(1e-5)
    val csvWriter = CSV("out.csv", listOf("x", "y"))
    val calculator = IntervalCalculator()

    val calculations = calculator(systemFunction, -5.0, 5.0, 0.05)

    for (value in calculations){
        csvWriter.write(value.first, value.second)
    }

    csvWriter.close()
}