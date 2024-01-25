import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CalculateConfusionMatrixDetailsTest {

    @Test
    fun testWithStandardMatrix() {
        val matrix = arrayOf(
            arrayOf(2, 1, 0),
            arrayOf(1, 3, 0),
            arrayOf(0, 0, 4)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert details for each class
        assertEquals(Detail(TP=2, FP=1, FN=1, TN=7), result[0])
        assertEquals(Detail(TP=3, FP=1, FN=1, TN=6), result[1])
        assertEquals(Detail(TP=4, FP=0, FN=0, TN=7), result[2])
    }

    @Test
    fun testWithAllZerosMatrix() {
        val matrix = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert details for each class (all should be zeros)
        assertEquals(Detail(TP=0, FP=0, FN=0, TN=0), result[0])
        assertEquals(Detail(TP=0, FP=0, FN=0, TN=0), result[1])
        assertEquals(Detail(TP=0, FP=0, FN=0, TN=0), result[2])
    }

    @Test
    fun testWithOneClass() {
        val matrix = arrayOf(arrayOf(5))
        val numClasses = 1
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert details for the single class
        assertEquals(Detail(TP=5, FP=0, FN=0, TN=0), result[0])
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

        // Assert details for each class (all predictions are correct)
        assertEquals(Detail(TP=3, FP=0, FN=0, TN=6), result[0])
        assertEquals(Detail(TP=3, FP=0, FN=0, TN=6), result[1])
        assertEquals(Detail(TP=3, FP=0, FN=0, TN=6), result[2])
    }

    @Test
    fun testWithAllIncorrectPredictions() {
        val matrix = arrayOf(
            arrayOf(0, 3, 0),
            arrayOf(0, 0, 3),
            arrayOf(3, 0, 0)
        )
        val numClasses = 3
        val result = calculateConfusionMatrixDetails(matrix, numClasses)

        // Assert details for each class (all predictions are incorrect)
        assertEquals(Detail(TP=0, FP=3, FN=3, TN=3), result[0])
        assertEquals(Detail(TP=0, FP=3, FN=3, TN=3), result[1])
        assertEquals(Detail(TP=0, FP=3, FN=3, TN=3), result[2])
    }
}