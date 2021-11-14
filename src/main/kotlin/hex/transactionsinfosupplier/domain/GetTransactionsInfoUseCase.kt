package hex.transactionsinfosupplier.domain

import hex.transactionsinfosupplier.domain.port.GetTransactionsInfoLogger
import hex.transactionsinfosupplier.domain.port.TransactionsRepository

class GetTransactionsInfoUseCase(
	private val repository: TransactionsRepository,
	private val feeCalculator: FeeCalculator,
	private val logger: GetTransactionsInfoLogger
) {
	
	fun getTransactionsInfo(clientIds: List<ClientId>): List<TransactionsInfo> =
		clientIds.map { clientId ->
			val transactions = repository.getTransactionsFor(clientId)
			val sum = transactions.map(Transaction::amount).reduceOrNull(Money::plus)?: Money.ZERO
			val fee = transactions.map(feeCalculator::calculate).reduceOrNull(Money::plus)?: Money.ZERO
			val lastTransactionDate = transactions.map(Transaction::dateTime).lastOrNull()
			
			TransactionsInfo(clientId, transactions.size, sum, fee, lastTransactionDate)
		}.also(::log)
	
	private fun log(infos: List<TransactionsInfo>) {
		infos.map(::GetTransactionsInfoLoggingDetails).map(logger::log)
	}
}