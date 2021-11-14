package hex.transactionsinfosupplier.domain

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline value class Money private constructor(val amount: BigDecimal): Comparable<Money>{
	companion object {
		fun of(amount: BigDecimal) = Money(amount.setScale(2, RoundingMode.HALF_UP))
		fun of(amount: Int) = of(amount.toBigDecimal())
		fun of(amount: String) = of(amount.replace(",", ".").toBigDecimal())
		val ZERO = of(BigDecimal.ZERO)
	}
	override fun compareTo(other: Money): Int = amount.compareTo(other.amount)
	
	operator fun plus(other: Money) = of(amount + other.amount)
	operator fun minus(other: Money) = of(amount - other.amount)
	operator fun times(value: BigDecimal) = of(amount * value)
	operator fun div(value: BigDecimal) = of(amount / value)
}