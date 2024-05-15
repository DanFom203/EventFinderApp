package com.itis.feature.events.impl.data

import retrofit2.HttpException
import com.itis.common.core.resources.ResourceManager
import com.itis.feature.events.impl.R
import com.itis.feature.events.impl.data.exceptions.ApiException
import javax.inject.Inject

class ExceptionHandlerDelegate @Inject constructor(
    private val resManager: ResourceManager
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> {
                        ApiException.BadRequestException(message = resManager.getString(R.string.bad_request))
                    }
                    401 -> {
                        ApiException.UserNotAuthorizedException(message = resManager.getString(R.string.user_not_authorized))
                    }
                    404 -> {
                        ApiException.ErrorNotFoundException(message = resManager.getString(R.string.error_not_found))
                    }
                    429 -> {
                        ApiException.TooManyRequestsException(message = resManager.getString(R.string.too_many_requests))
                    }
                    500 -> {
                        ApiException.InternalServerErrorException(message = resManager.getString(R.string.internal_server_error))
                    }

                    else -> {
                        ex
                    }
                }
            }

            else -> {
                ex
            }
        }
    }
}