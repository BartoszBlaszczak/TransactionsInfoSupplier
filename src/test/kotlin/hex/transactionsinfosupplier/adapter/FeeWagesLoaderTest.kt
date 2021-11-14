package hex.transactionsinfosupplier.adapter

import hex.transactionsinfosupplier.SpringKotest
import hex.transactionsinfosupplier.domain.Money
import hex.transactionsinfosupplier.domain.TransactionFeeWage
import io.kotest.matchers.collections.shouldContainExactly

class FeeWagesLoaderTest(sut: FeeWagesLoader) : SpringKotest({
	
	test("load") {
		// when
		val fees = sut.load()
		// then
		fees shouldContainExactly listOf(
			TransactionFeeWage(Money.of(1000), "0.035".toBigDecimal()),
			TransactionFeeWage(Money.of(2500), "0.025".toBigDecimal()),
			TransactionFeeWage(Money.of(5000), "0.011".toBigDecimal()),
			TransactionFeeWage(Money.of(10_000), "0.001".toBigDecimal()),
		)
	}
})
