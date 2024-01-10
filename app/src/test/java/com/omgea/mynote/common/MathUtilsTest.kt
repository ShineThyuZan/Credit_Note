package com.omgea.mynote.common


import com.google.common.truth.Truth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MathUtilsTest {
    @Test
    fun factorial() {
        val math = MathUtils.factorial(3)
        val math2 = 9
        assertNotEquals(math, math2)
        println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk$math")
    }

    @Test
    fun useEqualTo() {
        val exception = "Coading"
        val actual = "Coading"
        assertEquals(actual, exception)
    }

    @Test
    fun stringTesting() {
        val obj: Any = "codewithAchitlay"
        val obj2: Any = "codewith"
        assertNotEquals(obj, obj2)
    }

    @Test
    fun currentTimeToLong() {
        val currentTime = MathUtils.currentTimeToLong()
        println("current time +++ $currentTime")
    }

    @Test
    fun convertLongToTime() {
        val convertFormat = MathUtils.convertLongToTime(1704389400000)
        val dayAgo = MathUtils.timeAgo(1656955800000)
        val timeAgo = MathUtils.timeAgoDateAndFormat(1656955800000)
        val expectValue = "2023.09.05 00:00"
        val (timeAgoFormat, hello) = MathUtils.convertCreatedDate(1704740430000)
        //   println("convert format +++ $convertFormat")
        //  println("dayAgo format +++ $dayAgo")
        println("timeAgo format +++ $timeAgoFormat +++ $hello")
        //   println("TimeAgo format +++ $timeAgo")
        // Truth.assertThat(convertFormat).isEqualTo(expectValue)
    }
    @Test
    fun convertDateToLong() {
        val inputString = MathUtils.convertDateToLong(date = "2023.09.05")
        val expectLongTime = 1693848600000
        val state = Truth.assertThat(inputString).isEqualTo(expectLongTime)

        println("input.string +++ $inputString")
        println("input.state +++ $state")
    }
}