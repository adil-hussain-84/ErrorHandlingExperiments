class Experiment2 {

    sealed class Result<out T> {

        val isFailure: Boolean
            get() = this is Error

        fun errorOrNull(): Error? {
            return if (this is Error) {
                this
            } else {
                null
            }
        }

        fun getValueOrNull(): T? {
            return if (this is Success) {
                this.value
            } else {
                null
            }
        }

        inline fun unwrapValueOrBailOutWith(action: (error: Error) -> Nothing): T {
            return when (this) {
                is Error -> action(this)
                is Success -> this.value
            }
        }
    }

    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val domain: String, val code: Int) : Result<Nothing>()

    companion object {

        fun validateInput(someParam: Int): Result<Boolean> {
            return if (someParam == 0) {
                Error("SomeDomain", 123)
            } else {
                Success(true)
            }
        }

        fun validateInputV2(someParam: Int): Result<Unit> {
            return if (someParam == 0) {
                Error("SomeDomain", 123)
            } else {
                Success(Unit)
            }
        }

        fun highLevelOperation(input1: Int, input2: Int): Result<Int> {
            val value1 = lowLevelOperation(input1).unwrapValueOrBailOutWith { return it }
            val value2 = lowLevelOperation(input2).unwrapValueOrBailOutWith { return it }

            return Success(value1 + value2)
        }

        fun lowLevelOperation(input: Int): Result<Int> {
            return if (input == 0) {
                Error("SomeDomain", 123)
            } else {
                Success(input)
            }
        }
    }
}