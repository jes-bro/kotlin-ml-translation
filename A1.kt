import com.google.gson.Gson
import java.io.File

fun calculateConfusionMatrix(predictions: List<Int>, gtLabels: List<Int>, numClasses: Int): Array<Array<Int>> {
    var confusionMatrix = Array(numClasses) {Array(numClasses) {0} }
    gtLabels.zip(predictions).forEach() { pair -> confusionMatrix[pair.component1()][pair.component2()]++ }
    return confusionMatrix
}

fun printConfusionMatrix(matrix: Array<Array<Int>>) {
    for (row in matrix) {
        println(row.joinToString(" "))
    }
}

data class Detail(var TP: Int = 0, var FP: Int = 0, var FN: Int = 0, var TN: Int = 0)
fun calculateConfusionMatrixDetails(matrix: Array<Array<Int>>, numClasses: Int): MutableMap<Int, Detail> {
    val details = mutableMapOf<Int, Detail>()

    for ( i in 0 until numClasses) {
        var TP: Int = matrix[i][i]
        var FP: Int = calculateFalseRates(matrix, numClasses, i, TP, isFP=true)
        var FN: Int = calculateFalseRates(matrix, numClasses, i, TP, isFP=true)
        var TN: Int = calculateTrueNegativeRate(matrix, numClasses, i)
        details[i] = Detail(TP=TP, FP=FP, FN=FN, TN=TN)
    }
    return details
}

fun calculateFalseRates(matrix: Array<Array<Int>>, numClasses: Int, i: Int, TP: Int, isFP: Boolean): Int {
    var total: Int = 0

    when (isFP) {
        // FN
        true -> {
            for (j in 0 until numClasses) {
                total += matrix[j][i]
            }
        }
        // FP
        false -> for (j in 0 until numClasses) {
            total += matrix[i][j]
        }
    }
    return total - TP
}

fun calculateTrueNegativeRate(matrix: Array<Array<Int>>, numClasses: Int, i: Int): Int {
    var trueNegatives: Int = 0

    for (rowIndex in 0 until numClasses) {
        if (rowIndex != i) {
            for (colIndex in 0 until numClasses) {
                if (colIndex != i) {
                    trueNegatives += matrix[rowIndex][colIndex]
                }
            }
        }
    }
    return trueNegatives
}
fun main() {
    // Read the JSON file
    val jsonFile = File("model0.json")
    val jsonContent = jsonFile.readText()

    // Use Gson to parse the JSON into an instance of Json4Kotlin_Base
    val data = Gson().fromJson(jsonContent, Json4Kotlin_Base::class.java)

    // Retrieve class mappings
    val classIndices = data.class_indices
    // println(classIndices)

    // Retrieve predictions
    val predictions = data.predictions_and_labels.map { it.prediction_idx }
    // println(predictions)

    // Retrieve ground truth labels
    val gtLabels = data.predictions_and_labels.map { it.gt_idx }
    // println(gtLabels)

    val numClasses = predictions.toSet().size
    // print(numClasses)
    val matrix = calculateConfusionMatrix(predictions, gtLabels, numClasses)

    val details = calculateConfusionMatrixDetails(matrix, numClasses)

    printConfusionMatrix(matrix)
    println(details)
}
