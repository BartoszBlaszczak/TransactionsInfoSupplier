package hex.transactionsinfosupplier.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime.now

class FeeCalculatorTest : FunSpec({
	
	val sut = FeeCalculator(listOf(
		TransactionFeeWage(Money.of(10_000), 0.001.toBigDecimal()),
		TransactionFeeWage(Money.of(5_000), 0.011.toBigDecimal()),
		TransactionFeeWage(Money.of(1_000), 0.035.toBigDecimal()),
		TransactionFeeWage(Money.of(2_500), 0.025.toBigDecimal()),
	))
	
	fun transaction(value: Int) = Transaction(ClientId(1), Money.of(value), now())
	
	test("calculate fee") {
		//given
		listOf(
			transaction(100) to Money.of(3.5.toBigDecimal()),
			transaction(1_000) to Money.of(35),
			transaction(2_000) to Money.of(60),
			transaction(3_000) to Money.of(78),
			transaction(6_000) to Money.of(101),
			transaction(12_000) to Money.of(105),
		).forAll { (transaction, fee) ->
			// when & then
			sut.calculate(transaction) shouldBe fee
		}
	}
})
