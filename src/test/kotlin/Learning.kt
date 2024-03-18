import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Learning {

    @Test
    fun testLongestCommonPrefix() {
        assertEquals("f", longestCommonPrefix(arrayOf("f", "f", "f")))
        assertEquals("", longestCommonPrefix(arrayOf("f", "o", "f")))
        assertEquals("", longestCommonPrefix(arrayOf("s", "a", "b")))
        assertEquals("fl", longestCommonPrefix(arrayOf("fl", "fl", "fl")))
        assertEquals("fl", longestCommonPrefix(arrayOf("fla", "flb", "flc")))
        assertEquals("a", longestCommonPrefix(arrayOf("ab", "a")))
        assertEquals("fl", longestCommonPrefix(arrayOf("flower", "flow", "flight")))
    }

    private fun longestCommonPrefix(strs: Array<String>): String {
        return strs.fold("") { acc: String, s: String ->
            s.forEachIndexed { index: Int, c: Char ->
                if (acc.getOrNull(index) != c || acc.getOrNull(index) == null) {
                    return@fold acc.substring(0..<index)
                }
            }
            acc
        }
    }

    @Test
    fun testIsValid() {
        assert(isValid("()"))
        assert(isValid("()[]{}"))
        assertFalse(isValid("(]"))
        assertFalse(isValid("([)]"))
        assertFalse(isValid("({()])()"))
    }

    // https://leetcode.com/problems/valid-parentheses/submissions/
    private fun isValid(s: String): Boolean {
        if (s.length % 2 == 1) return false

        val result = s.replace("()", "").replace("{}", "").replace("[]", "")

        return if (result.isEmpty()) {
            true
        } else if (result == s) {
            false
        } else {
            isValid(result)
        }
    }

    @Test
    fun testMergeTwoLists() {

    }

//    private fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
//
//    }

    class ListNode(var value: Int) {
        var next: ListNode? = null
    }

    @Test
    fun testSingleNumber() {
        assertEquals(1, singleNumber1(intArrayOf(2, 2, 1)))
        assertEquals(4, singleNumber1(intArrayOf(4, 1, 2, 1, 2)))
    }

    private fun singleNumber1(nums: IntArray): Int {
        nums.forEach { num ->
            if (nums.count { num == it } == 1) {
                return num
            }
        }
        return 0
    }

    private fun singleNumber(nums: IntArray): Int {
        val numbers = mutableListOf<Int>()

        nums.forEach {
            if (it in numbers) {
                numbers.remove(it)
            } else {
                numbers.add(it)
            }
        }
        return numbers.first()
    }

    @Test
    fun testIsPalindrome() {
        assert(isPalindrome("aba"))
        assert(isPalindrome("abba"))
        assert(isPalindrome("A man, a plan, a canal: Panama"))
        assertFalse(isPalindrome("race a car"))
    }

    private fun isPalindrome(s: String): Boolean {
        val cleaned = s.filter { it.isLetterOrDigit() }

        if (cleaned.isEmpty()) return true

        for (i in 0..(cleaned.length - 1) / 2) {
            val firstChar = cleaned[i]
            val secondChar = cleaned[cleaned.length - 1 - i]
            if (Character.toLowerCase(firstChar) != Character.toLowerCase(secondChar)) {
                return false
            }
        }
        return true
    }

    private fun isPalindrome1(s: String): Boolean {
        val cleaned = s.filter { it.isLetterOrDigit() }.lowercase()

        if (cleaned.isEmpty()) return true

        if (cleaned.length % 2 == 0) {
            val firstHalf = cleaned.slice(0..<cleaned.length / 2)
            val secondHalf = cleaned.slice(cleaned.length / 2..<cleaned.length)
            return firstHalf == secondHalf.reversed()
        } else {
            val firstHalf = cleaned.slice(0..<cleaned.length / 2)
            val secondHalf = cleaned.slice((cleaned.length + 1) / 2..<cleaned.length)
            return firstHalf == secondHalf.reversed()
        }
    }


    @Test
    fun testMaxProfit() {
//        assertEquals(1, maxProfit(intArrayOf(7, 1, 2)))
        assertEquals(5, maxProfit(intArrayOf(7, 1, 5, 3, 6, 4)))
//        assertEquals(0, maxProfit(intArrayOf(7, 6, 4, 3, 1)))
    }

    //https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
    private fun maxProfit(prices: IntArray): Int {
        val profits = mutableListOf<Int>()

        for (i in 0..prices.size - 2) {
            profits.add(prices[i + 1] - prices[i])
        }

        var didBuyStock = false
        profits.forEachIndexed { index, i ->
            if (i < 0 && didBuyStock) {
                println("sell at ${index.plus(1)}")
                didBuyStock = false
            } else if (i > 0 && didBuyStock.not()) {
                println("buy at ${index.plus(1)}")
                didBuyStock = true
            }
        }

        if (didBuyStock) println("Sell at ${prices.size}")

//        profits.fold(mutableListOf<Int>(0)) { acc: MutableList<Int>, i: Int ->
//
//            if (i > 0) {
//                acc[acc.size - 1] = acc[acc.size - 1] + i
//            } else {
//                acc.add(0)
//            }
//
//            acc
//        }

        return 0
    }

    @Test
    fun testRotate() {
        val image1 = mutableListOf<MutableList<Int>>(
            mutableListOf(1, 2),
            mutableListOf(4, 5)
        )
        rotate(image1)
        println(image1)
//        assertEquals(
//            expected = mutableListOf<MutableList<Int>>(
//                mutableListOf(4, 1),
//                mutableListOf(5, 2),
//            ), actual = image1
//        )
//
//        val image = mutableListOf<MutableList<Int>>(
//            mutableListOf(1, 2, 3),
//            mutableListOf(4, 5, 6),
//            mutableListOf(7, 8, 9)
//        )
//        rotate(image)
//        assertEquals(
//            expected = mutableListOf<MutableList<Int>>(
//                mutableListOf(7, 4, 1), mutableListOf(8, 5, 2), mutableListOf(9, 6, 3)
//            ), actual = image
//        )
    }

    fun rotate(matrix: MutableList<MutableList<Int>>): Unit {
        val size = matrix.count()
        val sides = 4

        var lastNumber = 0
        lastNumber = matrix[0][1]
        matrix[0][1] = matrix[0][0]


        // 0,0 -> 0,2
        // 0,2 -> 2,2
        // 2,2 -> 0,2
        // 0,2 -> 0,0

        // 0,0 -> 0,1
        // 0,1 -> 1,1
        // 1,1 -> 0,1
        // 0,1 -> 0,0


    }

    @Test
    fun testSearchInsert() {
        var result = searchInsert(intArrayOf(1, 3, 5, 6), 5)
        assertEquals(2, result)
        result = searchInsert(intArrayOf(1, 3, 5, 6), 2)
        assertEquals(1, result)
    }

    fun searchInsert(nums: IntArray, target: Int): Int {
        val index = nums.binarySearch(target)
        return if (index >= 0) index else index * -1 - 1
    }

    @Test
    fun reverse() {
        val result = reverse(123)
        println(result)
        assertEquals(321, result)
    }

    fun reverse(x: Int): Int {
        return x.xor(x)
    }

    interface Source<T> {
        fun nextT(): T
    }

    @Test
    fun generics() {
        val from: Array<out Any>
        fun <T : Any> sort(list: List<T>) {}
        val list = listOf(1, null)

        //        sort(list)
        fun copy(from: Array<out Any>, to: Array<Any>) {}

        var objects1: Source<Any> = object : Source<Any> {
            override fun nextT(): Int {
                return 123
            }
        }

        doSomething(1)

        println(objects1.nextT())
    }

    inline fun <reified T> doSomething(value: T) {
        println("Doing something with type: ${T::class.simpleName}")    // OK
    }

    data class asdf(val a: Int)

    @Test
    fun removeDuplicates() {
        val nums = intArrayOf(1, 1, 2)
        assertEquals(2, removeDuplicates(nums))
        assertEquals(intArrayOf(1, 2, 2), nums)
    }

    fun removeDuplicates(nums: IntArray): Int {
        val toSet = nums.toSet()
        toSet.forEachIndexed { index, i ->
            nums[index] = i
        }
        return toSet.size
    }

    @Test
    fun removeElement() {
        val nums = intArrayOf(3, 2, 2, 3)
        val output = removeElement(nums, 3)
        assertEquals(2, output)
        assertEquals(intArrayOf(2, 2, 2, 3), nums)
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {
        return nums.fold(0) { acc: Int, i: Int ->
            if (i != `val`) {
                nums[acc] = i
                acc + 1
            } else acc
        }
    }

}

