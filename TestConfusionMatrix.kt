/**
 * Test file for the calculateConfusionMatrix function
 */
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class ConfusionMatrixTest {

    @Test
    fun testWithEasyValues() {
        val predictions = listOf(0, 1, 1, 2)
        val gtLabels = listOf(0, 1, 2, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(1, 0, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 1)
        )
        // Test that it works as expected with easy values
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testZerosInput() {
        val predictions = listOf<Int>()
        val gtLabels = listOf<Int>()
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )
        // Assert it works as expected with no predictions
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testOnlyCorrectPredictions() {
        val predictions = listOf(0, 1, 2)
        val gtLabels = listOf(0, 1, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(1, 0, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 0, 1)
        )
        // Assert it works when all predictions are correct
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testOnlyIncorrectPredictions() {
        val predictions = listOf(1, 2, 0)
        val gtLabels = listOf(0, 1, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 0, 1),
            arrayOf(1, 0, 0)
        )
        // Assert it works when predictions are incorrect
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testOneClass() {
        val predictions = listOf(0, 0, 0)
        val gtLabels = listOf(0, 0, 0)
        val numClasses = 1
        val expected = arrayOf(arrayOf(3))
        // Assert it works with one class
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testWithLargeNumberOfClasses() {
        val predictions = (0 until 100).toList()
        val gtLabels = (0 until 100).toList()
        val numClasses = 100
        val expected = Array(numClasses) { i -> Array(numClasses) { j -> if (i == j) 1 else 0 } }
        // Assert it works with many classes
        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }
}
