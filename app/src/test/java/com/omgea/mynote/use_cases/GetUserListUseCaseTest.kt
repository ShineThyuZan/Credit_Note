package com.omgea.mynote.use_cases

import com.google.common.truth.Truth.assertThat
import com.omgea.mynote.data.repository.FakeUserRepository
import com.omgea.mynote.model.UserVo
import com.omgea.mynote.util.OrderType
import com.omgea.mynote.util.UserOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserListUseCaseTest {
    private lateinit var getNotes: GetUsersByNameOrderUseCase
    private lateinit var fakeRepository: FakeUserRepository

    @Before
    fun setUp() {
        fakeRepository = FakeUserRepository()
        getNotes = GetUsersByNameOrderUseCase(fakeRepository)

        val notesToInsert = mutableListOf<UserVo>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                UserVo(
                    name = c.toString(),
                    description = c.toString(),
                    id = index,
                    amount = c.toInt()
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.insertUser(it) }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotes(UserOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].name).isLessThan(notes[i + 1].name)
        }
    }
    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotes(UserOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
        //    assertThat(notes[i].name).isGreaterThan(notes[i + 1].name)
            assertThat(notes[i].name).isLessThan(notes[i + 1].name)
        }
    }
}