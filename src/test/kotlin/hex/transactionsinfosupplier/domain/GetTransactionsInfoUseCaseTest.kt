package hex.transactionsinfosupplier.domain

import hex.transactionsinfosupplier.domain.port.GetTransactionsInfoLogger
import hex.transactionsinfosupplier.domain.port.TransactionsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.time.LocalDateTime.now

class GetTransactionsInfoUseCaseTest : FunSpec({
	
	val logger = mockk<GetTransactionsInfoLogger>()
	val slot = mutableListOf<GetTransactionsInfoLoggingDetails>()
	every { logger.log(capture(slot)) } just Runs
	
	fun sut(transactions: List<Transaction>) = GetTransactionsInfoUseCase(
		repository = object : TransactionsRepository {
			override fun getTransactionsFor(clientId: ClientId) = transactions.filter { it.clientId == clientId }
		},
		feeCalculator = FeeCalculator(
			listOf(
				TransactionFeeWage(Money.of(1_000), 0.035.toBigDecimal()),
				TransactionFeeWage(Money.of(2_500), 0.025.toBigDecimal()),
				TransactionFeeWage(Money.of(5_000), 0.011.toBigDecimal()),
				TransactionFeeWage(Money.of(10_000), 0.001.toBigDecimal()),
			)
		),
		logger = logger
	)
	
	test("get transactions info for all clients") {
		// given
		val id1 = ClientId(1)
		val id2 = ClientId(2)
		val id3 = ClientId(3)
		
		val t1 = Transaction(id1, Money.of(1_000), now())
		val t2 = Transaction(id1, Money.of(2_000), now())
		val t3 = Transaction(id1, Money.of(3_000), now().plusDays(1))
		
		val t4 = Transaction(id2, Money.of(4_000), now())
		val t5 = Transaction(id2, Money.of(5_000), now().plusDays(2))
		
		// when
		slot.clear()
		val transactionsInfo = sut(listOf(t1, t2, t3, t4, t5)).getTransactionsInfo(listOf(id1, id2, id3))
		
		// then
		transactionsInfo shouldContainExactly listOf(
			TransactionsInfo(
				id1,
				transactionsNumber = 3,
				totalValue = Money.of(6_000),
				fee = Money.of(173),
				lastTransactionDate = t3.dateTime
			),
			TransactionsInfo(
				id2,
				transactionsNumber = 2,
				totalValue = Money.of(9_000),
				fee = Money.of(189),
				lastTransactionDate = t5.dateTime
			),
			TransactionsInfo(
				id3,
				transactionsNumber = 0,
				totalValue = Money.ZERO,
				fee = Money.ZERO,
				lastTransactionDate = null
			),
		)
		slot[0].clientId shouldBe id1
		slot[0].commission shouldBe Money.of(173)
		slot[0].dateTime shouldNotBe null
		
		slot[1].clientId shouldBe id2
		slot[1].commission shouldBe Money.of(189)
		slot[1].dateTime shouldNotBe null
		
		slot[2].clientId shouldBe id3
		slot[2].commission shouldBe Money.ZERO
		slot[2].dateTime shouldNotBe null
	}
	
	test("get transactions info for some clients") {
		// given
		val id1 = ClientId(1)
		val id2 = ClientId(2)
		
		val t1 = Transaction(id1, Money.of(1_000), now())
		val t2 = Transaction(id2, Money.of(2_000), now())
		
		// when
		slot.clear()
		val transactionsInfo = sut(listOf(t1, t2)).getTransactionsInfo(listOf(id1))
		
		// then
		transactionsInfo shouldContainExactly listOf(
			TransactionsInfo(
				id1,
				transactionsNumber = 1,
				totalValue = Money.of(1_000),
				fee = Money.of(35),
				lastTransactionDate = t1.dateTime
			),
		)
		
		slot[0].clientId shouldBe id1
		slot[0].commission shouldBe Money.of(35)
		slot[0].dateTime shouldNotBe null
	}
})
