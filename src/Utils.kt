import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun emptyArray(size: Int, initialValue: Int = 0): Array<IntArray> {
    val elements = (0 until size).map {
        IntArray(size) { initialValue }
    }.toTypedArray()
    return arrayOf(
        *elements
    )
}

fun print(arrays: Array<IntArray>) {
    arrays.forEach { arr ->
        arr.forEach {
            print("$it, ")
        }
        println()
    }
}
