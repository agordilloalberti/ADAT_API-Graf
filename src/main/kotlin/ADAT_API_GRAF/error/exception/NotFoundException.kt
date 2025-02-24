package ADAT_API_GRAF.error.exception

class NotFoundException(message: String) : Exception("Not found exception (404). $message") {
}