package utils

import java.io.File
import java.io.FileWriter
import java.lang.IllegalArgumentException

class CSV(
    filename: String,
    headers: List<String>
): AutoCloseable {
    private val file: File = File(filename)
    private val fileWriter: FileWriter

    init {
        if (!file.exists())
            if (file.createNewFile())
                print("Created new file")
            else
                throw IllegalArgumentException("File with specified name could not be created")

        fileWriter = FileWriter(file)
        fileWriter.write(headers.joinToString(",", postfix = "\n"))
    }


    fun <T> write(vararg values: T) {
        val csvString: String = values.asList().joinToString(",", postfix = "\n")

        fileWriter.write(csvString)
    }

    override fun close(){
        fileWriter.close()
    }
}