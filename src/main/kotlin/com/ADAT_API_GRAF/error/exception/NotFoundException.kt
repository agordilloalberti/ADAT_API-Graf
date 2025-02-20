package com.AGS.ADAT_API.Graf.error.exception

class NotFoundException(message: String) : Exception("Not found exception (404). $message") {
}