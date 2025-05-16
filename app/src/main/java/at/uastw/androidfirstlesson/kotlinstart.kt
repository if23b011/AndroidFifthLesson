package at.uastw.androidfirstlesson

fun main() {
    val name = "Christoph"
    var nameAsVar = "Oswald"
    val day: Int = 7
    val pi = 3.1415
    printWeather(salutation = "Mr", name = name)
    printWeather("Andrea", "Mitter", "Ms")
    printWeather(lastName = "Gibbson", name = "Joey", salutation = "Mr")
    printName("Joey", null, null)
    printWeather(name = "Maria", salutation = "Mrs")

    val raceCar = Car("BMW", 1000)
    raceCar.horsepower

    greeter("Hi", ::getMyName)
    greeter("Hello", { number ->
        if (number > 2) {
            "John"
        } else {
            "Joey"
        }
    })

    greeter("Hello", {
        if (it > 2) {
            "John"
        } else {
            "Joey"
        }
    })

    greeter("Hello") {
        if (it > 2) {
            "John"
        } else {
            "Joey"
        }
    }
}

fun getMyName(number: Int): String {
    if (number > 10) {
        return "Joe"
    } else {
        return "Joey"
    }
}

fun greeter(greeting: String, getName: (Int) -> String) {
    val name = getName(234)

    println("$greeting $name")
}


fun lambdaTest(functionName: (Int, Int) -> Double) {

}


fun printName(name: String, lastName: String?, repeat: Int?) {
    if (repeat != null) {
        val calculation = 3 + repeat
    }

    val product = 5 * (repeat ?: 4)

    val quotient = 5 / repeat!!

    println("Hi $name")
}

fun printWeather(name: String, lastName: String = "Oswald", salutation: String) {
    println("Hello ${name} $lastName")
    println("The weather is sunny")

    val temp = 37.5

    if (temp > 30) {
        println("Hello")
    } else {
        println("Hi")
    }

    for (i in 1..5) {
        println("Number $i")
    }

    when (name) {
        "Christoph" -> {
            println("Hi ther")
        }

        else -> {

        }
    }
}

open class Car(val make: String, val horsepower: Int) {
    open fun makeSound() {
        println("Loud sound")
    }
}

class ElectricCar(val battery: Double, make: String, horsepower: Int) :
    Car(make, horsepower) {
    override fun makeSound() {
        println("Not so loud sound")
    }
}