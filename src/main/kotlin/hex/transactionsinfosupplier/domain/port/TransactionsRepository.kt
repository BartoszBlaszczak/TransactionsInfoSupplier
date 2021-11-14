package hex.transactionsinfosupplier.domain.port

import hex.transactionsinfosupplier.domain.Transaction
import hex.transactionsinfosupplier.domain.ClientId

interface TransactionsRepository {
	fun getTransactionsFor(clientId: ClientId): List<Transaction>
}