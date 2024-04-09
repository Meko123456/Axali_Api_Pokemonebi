package com.merabk.axaliapipokemonebi.data.mapper

import com.merabk.axaliapipokemonebi.util.Constants.ERROR_NO_INTERNET_TITLE
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

interface ErrorMapper {

    operator fun invoke(exception: Throwable): Throwable

    class ErrorMapperImpl @Inject constructor() : ErrorMapper {

        override fun invoke(exception: Throwable): Throwable = when (exception) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException ->
                NoInternetException()

            else -> exception
        }
    }
}

class NoInternetException : Exception(
    ERROR_NO_INTERNET_TITLE
)
