import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class ConfusionMatrixTest {

    @Test
    fun testBasicFunctionality() {
        val predictions = listOf(0, 1, 1, 2)
        val gtLabels = listOf(0, 1, 2, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(1, 0, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 1)
        )

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testEmptyInput() {
        val predictions = listOf<Int>()
        val gtLabels = listOf<Int>()
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testAllCorrectPredictions() {
        val predictions = listOf(0, 1, 2)
        val gtLabels = listOf(0, 1, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(1, 0, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 0, 1)
        )

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testAllIncorrectPredictions() {
        val predictions = listOf(1, 2, 0)
        val gtLabels = listOf(0, 1, 2)
        val numClasses = 3
        val expected = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 0, 1),
            arrayOf(1, 0, 0)
        )

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testWithOneClass() {
        val predictions = listOf(0, 0, 0)
        val gtLabels = listOf(0, 0, 0)
        val numClasses = 1
        val expected = arrayOf(arrayOf(3))

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }

    @Test
    fun testWithLargeNumberOfClasses() {
        val predictions = (0 until 100).toList()
        val gtLabels = (0 until 100).toList()
        val numClasses = 100
        val expected = Array(numClasses) { i -> Array(numClasses) { j -> if (i == j) 1 else 0 } }

        assertArrayEquals(expected, calculateConfusionMatrix(predictions, gtLabels, numClasses))
    }
}
