package hex.transactionsinfosupplier.domain

class FeeCalculator(feeWages: List<TransactionFeeWage>) {
	private val feeWages: List<TransactionFeeWage> = feeWages.sortedBy { it.transactionValueLimit }
	
	/**
	 * I wasn't sure how it should be calculated.
	 * I took this solution as the most likely but normally this should be reconciled with the Product Owner.
	 */
	fun calculate(transaction: Transaction): Money =
		feeWages.fold(Money.ZERO to Money.ZERO) { (used: Money, fee: Money), wage: TransactionFeeWage ->
			val toCalculate = minOf(transaction.amount - used, wage.transactionValueLimit - used)
			(used + toCalculate) to (fee + toCalculate * wage.feePercentage)
		}.second
}