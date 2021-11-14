package hex.transactionsinfosupplier.domain

import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class GetTransactionsInfoLoggingDetails(
	val clientId: ClientId,
	val commission: Money,
	val dateTime: LocalDateTime = now()
) {
	constructor(info: TransactionsInfo) : this(info.clientId, info.fee)
}