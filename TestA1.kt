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

    // Additional test cases...
}
