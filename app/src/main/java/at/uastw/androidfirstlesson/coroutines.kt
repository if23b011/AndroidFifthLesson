package at.uastw.androidfirstlesson

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            printWeatherReport()
        }
    }
    println("Method took ${time/1000.0} seconds")
}

suspend fun printWeatherReport() {
    delay(1000)

    coroutineScope {
        val temperature = async { getTemperature() }
        val weather = async { getWeather() }
        val temperatureVal = temperature.await()
        val weatherVal = weather.await()

        println("Weather is $weatherVal, temperature is $temperatureVal")
    }

}

suspend fun getTemperature() : String {
    delay(4000)
    return "17°C"
}

suspend fun getWeather(): String {
    delay(3000)
    return "Sunny"
}