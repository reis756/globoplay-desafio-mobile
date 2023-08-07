package com.reisdeveloper.domain.usecase

import app.cash.turbine.test
import com.reisdeveloper.domain.Error
import com.reisdeveloper.domain.FakeMovieRepository
import com.reisdeveloper.domain.MainCoroutineRule
import com.reisdeveloper.domain.ResultWrapper
import com.reisdeveloper.domain.usecases.GetListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetListUseCaseTest {

    private lateinit var repository: FakeMovieRepository

    private lateinit var getListUseCase: GetListUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = FakeMovieRepository()
        getListUseCase = GetListUseCase(repository)
    }


    @Test
    fun get_list_use_case_success() = runBlocking {
        repository.setReturnError(false)

        val result = getListUseCase.invoke("416541")

        result.test {
            Assert.assertEquals(ResultWrapper.Loading, awaitItem())
            Assert.assertEquals(
                ResultWrapper.Success(repository.movieListMock),
                awaitItem()
            )
            Assert.assertEquals(ResultWrapper.DismissLoading, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun get_list_use_case_error() = runBlocking {
        repository.setReturnError(true)

        val result = getListUseCase.invoke("416541")

        result.test {
            try {
                Assert.assertEquals(ResultWrapper.Loading, awaitItem())
                Assert.assertEquals(
                    ResultWrapper.Failure(Error.UnknownException(Throwable("Test exception1"))),
                    awaitItem()
                )
                cancelAndConsumeRemainingEvents()
                awaitComplete()

            } catch (e: Throwable) {
                Assert.assertEquals(ResultWrapper.DismissLoading, awaitItem())
                awaitComplete()
            }
        }
    }
}