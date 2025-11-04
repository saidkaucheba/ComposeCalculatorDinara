package com.example.composecalculatordinara

class CalculatorLogic {
    // переменные значений, состояний и проверок
    private var tekuschiiVvod = "0"
    private var pervoeChislo: Double = 0.0
    private var tekuschayaOperetsiya: String? = null
    private var sbrositVvod = false
    private var posledneeChislo: Double = 0.0  // запоминаем последний число операции
    private var poslednyayaOperatsiya: String? = null  // запоминаем последнюю операцию
    private var povtoritOperatsiyu = false  // флаг повторения операции

    //берём значение с дисплея
    fun getCurrentDisplay(): String = tekuschiiVvod

    fun inputDigit(digit: String) {
        if (sbrositVvod) {
            tekuschiiVvod = digit
            sbrositVvod = false
        } else {
            tekuschiiVvod = if (tekuschiiVvod == "0") digit else tekuschiiVvod + digit
        }
        povtoritOperatsiyu = false  // Сбрасываем флаг при вводе новой цифры
    }

    fun inputDecimal() {
        if (sbrositVvod) {
            tekuschiiVvod = "0."
            sbrositVvod = false
        } else if (!tekuschiiVvod.contains(".")) {
            tekuschiiVvod += "."
        }
        povtoritOperatsiyu = false  // Сбрасываем флаг при вводе точки
    }

    fun inputOperation(operation: String) {
        if (tekuschayaOperetsiya != null && !sbrositVvod) {
            calculateResult()
        }
        pervoeChislo = tekuschiiVvod.toDouble()
        tekuschayaOperetsiya = operation
        sbrositVvod = true
        povtoritOperatsiyu = false  // Сбрасываем флаг при выборе новой операции
    }

    fun calculateResult(): String {
        // Если нажали = после другой операции (не повторение)
        if (!povtoritOperatsiyu && tekuschayaOperetsiya != null) {
            posledneeChislo = tekuschiiVvod.toDouble()  // Запоминаем второе число
            poslednyayaOperatsiya = tekuschayaOperetsiya        // Запоминаем операцию
        }

        // Если это повторение операции (снова нажали =)
        if (povtoritOperatsiyu && poslednyayaOperatsiya != null) {
            val tekuscheeZnachenie = tekuschiiVvod.toDouble()
            val result = performCalculation(tekuscheeZnachenie, posledneeChislo, poslednyayaOperatsiya!!)
            tekuschiiVvod = formatResult(result)
            return tekuschiiVvod
        }

        // Обычное вычисление
        if (tekuschayaOperetsiya != null) {
            val vtoroeChislo = tekuschiiVvod.toDouble()
            val result = performCalculation(pervoeChislo, vtoroeChislo, tekuschayaOperetsiya!!)
            tekuschiiVvod = formatResult(result)

            // Запоминаем для возможного повторения
            posledneeChislo = vtoroeChislo
            poslednyayaOperatsiya = tekuschayaOperetsiya
            povtoritOperatsiyu = true  // Устанавливаем флаг для следующего повторения
        }

        tekuschayaOperetsiya = null
        sbrositVvod = true
        return tekuschiiVvod
    }

    private fun performCalculation(a: Double, b: Double, operation: String): Double {
        return when (operation) {
            "+" -> add(a, b)
            "-" -> subtract(a, b)
            "*" -> multiply(a, b)
            "/" -> {
                if (b == 0.0) {
                    throw ArithmeticException("Нельзя делить на ноль")
                } else {
                    divide(a, b)
                }
            }
            else -> b
        }
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            // Ограничиваем количество знаков после запятой
            String.format("%.8f", result).removeSuffix("0").removeSuffix("0").removeSuffix(".")
        }
    }

    fun clear() {
        tekuschiiVvod = "0"
        pervoeChislo = 0.0
        tekuschayaOperetsiya = null
        sbrositVvod = false
        posledneeChislo = 0.0
        poslednyayaOperatsiya = null
        povtoritOperatsiyu = false
    }

    fun clearEntry() {
        tekuschiiVvod = "0"
        sbrositVvod = false
        povtoritOperatsiyu = false
    }

    // математические операции
    private fun add(a: Double, b: Double): Double = a + b
    private fun subtract(a: Double, b: Double): Double = a - b
    private fun multiply(a: Double, b: Double): Double = a * b
    private fun divide(a: Double, b: Double): Double = a / b
}
