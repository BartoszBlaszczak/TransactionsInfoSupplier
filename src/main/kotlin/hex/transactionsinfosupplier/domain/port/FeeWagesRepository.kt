package hex.transactionsinfosupplier.domain.port

import hex.transactionsinfosupplier.domain.TransactionFeeWage

interface FeeWagesRepository {
	fun getFeeWages(): List<TransactionFeeWage>
}