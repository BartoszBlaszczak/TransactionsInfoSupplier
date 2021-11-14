package hex.transactionsinfosupplier.domain

import java.time.LocalDateTime

data class TransactionsInfo(
	val clientId: ClientId,
	val transactionsNumber: Int,
	val totalValue: Money,
	val fee: Money,
	val lastTransactionDate: LocalDateTime?
)

@JvmInline value class ClientId(val value: Int)

