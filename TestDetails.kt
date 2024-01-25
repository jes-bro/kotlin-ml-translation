/**
 * Test file for the calculateConfusionMatrixDetails function
 * This also implicitly tests calculateFalseRates and
 * calculateTrueNegativeRate
 */
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculateConfusionMatrixDetailssTest {

    @Test
    fun testWithRegularMatrix() {
        // Initialize test matrix
        val matrix = arrayOf(
            arrayOf(2, 1, 0),
            arrayOf(1, 3, 0),
            arrayOf(0, 0, 4)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert that it works as expected
        assertEquals(Details(TP=2, FP=1, FN=1, TN=7), result[0])
        assertEquals(Details(TP=3, FP=1, FN=1, TN=6), result[1])
        assertEquals(Details(TP=4, FP=0, FN=0, TN=7), result[2])
    }

    @Test
    fun testWithZerosMatrix() {
        // Initialize matrix of zeros
        val matrix = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert that there are no predictions
        assertEquals(Details(TP=0, FP=0, FN=0, TN=0), result[0])
        assertEquals(Details(TP=0, FP=0, FN=0, TN=0), result[1])
        assertEquals(Details(TP=0, FP=0, FN=0, TN=0), result[2])
    }

    @Test
    fun testWithOneClass() {
        // Nested array of creates an array with an array with a 5 in it
        val matrix = arrayOf(arrayOf(5))
        val numClasses = 1
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        assertEquals(Details(TP=5, FP=0, FN=0, TN=0), result[0])
    }

    @Test
    fun testWithPerfectPredictions() {
        val matrix = arrayOf(
            arrayOf(3, 0, 0),
            arrayOf(0, 3, 0),
            arrayOf(0, 0, 3)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert that predictions were perfect
        assertEquals(Details(TP=3, FP=0, FN=0, TN=6), result[0])
        assertEquals(Details(TP=3, FP=0, FN=0, TN=6), result[1])
        assertEquals(Details(TP=3, FP=0, FN=0, TN=6), result[2])
    }

    @Test
    fun testWithOnlyIncorrectPredictions() {
        val matrix = arrayOf(
            arrayOf(0, 3, 0),
            arrayOf(0, 0, 3),
            arrayOf(3, 0, 0)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert that all predictions are incorrect
        assertEquals(Details(TP=0, FP=3, FN=3, TN=3), result[0])
        assertEquals(Details(TP=0, FP=3, FN=3, TN=3), result[1])
        assertEquals(Details(TP=0, FP=3, FN=3, TN=3), result[2])
    }
}