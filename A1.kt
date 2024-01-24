/**
 * Script that generates a confusion matrix and corresponding metrics.
 *
 * Functions:
 * Functions include calculateConfusionMatrix, printConfusionMatrix, (both are
 * pretty self-explanatory), calculateConfusionMatrix details, which calculates
 * TP rate, FP rate, FN rate and TN rate for a given class, calculateFalseRates,
 * and calculateTrueNegativeRate.
 *
 * Usage:
 * The main function of the script reads the json file in the kotlin-ml-translation
 * directory, loads it as a json4kotlin base class, which is architected to
 * take in data from json files with a structure particular to the AI for society
 * assignment, and then calculates the confusion matrix and corresponding metrics
 * for each class.
 *
 * (PS I'm trying to get into the habit of documenting code super well because
 * I'm getting tired of seeing undocumented, unsupported, and broken research
 * code).
 *
 * I learned that in kotlin docstrings you don't need to specify the types of
 * anything because it's statically typed!
 */

import com.google.gson.Gson // for processing the json file (harder than I thought)
import java.io.File

/**
 * Calculate the confusion matrix with the classes from the loaded json file.
 *
 * @param predictions The predicted classes from the results of a forward pass
 * through a neural network (NN) trained to classify desserts.
 * @param gtLabels The actual labels of the images that were fed to the model.
 * @param numClasses The number of classes the model can use to classify desserts.
 * @return A confusion matrix corresponding to the predictions and labels for
 * all of the classes.
 */
fun calculateConfusionMatrix(predictions: List<Int>, gtLabels: List<Int>, numClasses: Int): Array<Array<Int>> {
    val confusionMatrix = Array(numClasses) {Array(numClasses) {0} }
    gtLabels.zip(predictions).forEach() { pair -> confusionMatrix[pair.component1()][pair.component2()]++ }
    return confusionMatrix
}

/**
 * Print the confusion matrix for model evaluation.
 *
 * You have to print each row of the matrix separately in kotlin, which
 * I was not anticipating, hence the need for the function.
 *
 * @param matrix The confusion matrix calculated from the data in the json file
 * storing data from the model evaluation.
 */
fun printConfusionMatrix(matrix: Array<Array<Int>>) {
    for (row in matrix) {
        println(row.joinToString(" "))
    }
}

// A neat way to store the details of each class as a unit
data class Detail(var TP: Int = 0, var FP: Int = 0, var FN: Int = 0, var TN: Int = 0)

/**
 * Calculate the TP Rate, FP Rate, FN Rate, and TN Rate of each class. Store
 * them in a mutable map that maps dessert classes to their metric information.
 *
 * Specifically, the classes (ints) are mapped to a data class called Detail that
 * contains the rates.
 *
 * @param matrix The confusion matrix corresponding to the classes from the
 * inference.
 * @param numClasses The number of classes the model can sort pictures into.
 * @return A map of the classes to their details.
 */
fun calculateConfusionMatrixDetails(matrix: Array<Array<Int>>, numClasses: Int): MutableMap<Int, Detail> {
    val details = mutableMapOf<Int, Detail>()

    for ( i in 0 until numClasses) {
        val TP: Int = matrix[i][i]
        val FP: Int = calculateFalseRates(matrix, numClasses, i, TP, isFP=true)
        val FN: Int = calculateFalseRates(matrix, numClasses, i, TP, isFP=false)
        val TN: Int = calculateTrueNegativeRate(matrix, numClasses, i)
        // data class makes this part much shorter than it is in python!
        details[i] = Detail(TP=TP, FP=FP, FN=FN, TN=TN)
    }
    return details
}

/**
 * Calculate the false postive rate or false negative rate for a given class,
 * depending on whether isFP is true or not.
 *
 * @param matrix The confusion matrix corresponding to the classes at hand.
 * @param numClasses an int representing the number of classes.
 * @param i An int representing the index of the class.
 * @param TP The true positive rate of the class, needed to calculate the
 * false rates.
 * @param isFP A boolean representing whether or not to calculate the FP rate.
 * If false, the false negative rate is calculated instead.
 * @return Either the FP rate or FN rate of the class.
 */
fun calculateFalseRates(matrix: Array<Array<Int>>, numClasses: Int, i: Int, TP: Int, isFP: Boolean): Int {
    var total: Int = 0

    when (isFP) {
        // FP
        true -> {
            for (j in 0 until numClasses) {
                total += matrix[j][i]
            }
        }
        // FN
        false -> for (j in 0 until numClasses) {
            total += matrix[i][j]
        }
    }
    return total - TP
}

/**
 * Calculate the true negative rate for a given class.
 *
 * @param matrix The confusion matrix corresponding to the classes at hand.
 * @param numClasses an int representing the number of classes.
 * @param i An int representing the index of the class.
 * @return The true negative rate (also the number of true negatives, hence
 * the variable name).
 */
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

/**
 * Read the json file, retrieve the indices that correspond to the class
 * names, retrieve the predictions from the model evaluation, retrieve
 * the ground truth labels that the images actually
 * have, store the number of classes as a variable, and calculate the
 * confusion matrix and metrics with all of that information. Then, print
 * the information.
 */
fun main() {
    // Read the JSON file
    val jsonFile = File("model0.json")
    val jsonContent = jsonFile.readText()

    // Use Gson to parse the JSON into an instance of Json4Kotlin_Base
    // Scoured the Internet to figure this out
    val data = Gson().fromJson(jsonContent, Json4Kotlin_Base::class.java)

    // Retrieve class mappings
    val classIndices = data.class_indices // Was used to calculate numClasses
    // and ended up using a different way
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