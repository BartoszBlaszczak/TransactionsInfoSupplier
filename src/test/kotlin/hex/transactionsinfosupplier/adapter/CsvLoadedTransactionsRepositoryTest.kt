package hex.transactionsinfosupplier.adapter

import hex.transactionsinfosupplier.SpringKotest
import hex.transactionsinfosupplier.domain.ClientId
import hex.transactionsinfosupplier.domain.Money
import hex.transactionsinfosupplier.domain.Transaction
import io.kotest.matchers.collections.shouldContainExactly
import java.time.LocalDateTime

class CsvLoadedTransactionsRepositoryTest(sut: CsvLoadedTransactionsRepository) : SpringKotest({
	
	test("getTransactionsFor") {
		// given
		val clientId = ClientId(1)
		// when
		val transactions = sut.getTransactionsFor(clientId)
		// then
		transactions shouldContainExactly listOf(
			Transaction(clientId, Money.of("243.33".toBigDecimal()), LocalDateTime.parse("2020-12-11T14:57:22")),
			Transaction(clientId, Money.of("199.11".toBigDecimal()), LocalDateTime.parse("2020-12-01T13:22:11")),
		)
	}
})
