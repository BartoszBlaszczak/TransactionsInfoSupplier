package hex.transactionsinfosupplier.adapter

import hex.transactionsinfosupplier.SpringKotest
import hex.transactionsinfosupplier.domain.ClientId
import hex.transactionsinfosupplier.domain.Money
import hex.transactionsinfosupplier.domain.TransactionsInfo
import io.kotest.matchers.collections.shouldContainExactly
import java.time.LocalDateTime

class TransactionsInfoControllerTest(sut: TransactionsInfoController) : SpringKotest({
	
	test("get infos") {
		// given
		val clients = listOf(1, 2)
		
		// when
		val infos = sut.transactionsInfo(clients)
		
		// then
		infos shouldContainExactly listOf(
			TransactionsInfo(
				ClientId(1),
				transactionsNumber = 2,
				totalValue = Money.of("442.44"),
				fee = Money.of("15.49"),
				lastTransactionDate = LocalDateTime.parse("2020-12-01T13:22:11")
			),
			TransactionsInfo(
				ClientId(2),
				transactionsNumber = 1,
				totalValue = Money.of(212),
				fee = Money.of("7.42"),
				lastTransactionDate = LocalDateTime.parse("2020-12-01T01:01")
			)
		)
	}
})
