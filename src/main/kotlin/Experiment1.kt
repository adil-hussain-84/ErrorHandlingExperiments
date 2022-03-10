class Experiment1 {

    data class MyException(val domain: String, val code: Int) : Exception()

    companion object {

        fun validateInput(someParam: Int): Result<Boolean> {
            return if (someParam == 0) {
                Result.failure(MyException("SomeDomain", 123))
            } else {
                Result.success(true)
            }
        }

        fun validateInputV2(someParam: Int): Result<Unit> {
            return if (someParam == 0) {
                Result.failure(MyException("SomeDomain", 123))
            } else {
                Result.success(Unit)
            }
        }

        fun highLevelOperation(input1: Int, input2: Int): Result<Int> {
            val result1 = lowLevelOperation(input1).onFailure { return Result.failure(it) }
            val result2 = lowLevelOperation(input2).onFailure { return Result.failure(it) }

            val success1 = result1.getOrNull()!!
            val success2 = result2.getOrNull()!!

            return Result.success(success1 + success2)
        }

        private fun lowLevelOperation(input: Int): Result<Int> {
            return if (input == 0) {
                Result.failure(MyException("SomeDomain", 123))
            } else {
                Result.success(input)
            }
        }

        fun highLevelOperationV2(input1: Int, input2: Int): Result<Int> {
            val value1 = lowLevelOperation(input1).unwrapValueOrBailOutWith { return Result.failure(it) }
            val value2 = lowLevelOperation(input2).unwrapValueOrBailOutWith { return Result.failure(it) }

            return Result.success(value1 + value2)
        }
    }
}

inline fun <T> Result<T>.unwrapValueOrBailOutWith(action: (throwable: Throwable) -> Nothing): T {
    val exception = exceptionOrNull()

    if (exception != null) {
        action(exception)
    }

    return getOrNull()!!
}