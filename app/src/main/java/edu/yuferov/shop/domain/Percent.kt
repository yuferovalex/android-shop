package edu.yuferov.shop.domain

class Percent(val value: Double) {

    val fraction: Double
        get() = value / 100.0

    operator fun plus(rhs: Double) = rhs * (1.0 + fraction)

    operator fun times(rhs: Double) = rhs * fraction

    operator fun compareTo(rhs: Double): Int {
        return this.value.compareTo(rhs)
    }
}

operator fun Double.plus(rhs: Percent) = rhs + this

operator fun Double.times(rhs: Percent) = rhs * this

operator fun Double.div(rhs: Percent) = this / rhs.fraction

operator fun Double.minus(rhs: Percent) = this * (1.0 - rhs.fraction)