package hex.transactionsinfosupplier.domain

import java.math.BigDecimal

data class TransactionFeeWage(val transactionValueLimit: Money, val feePercentage: BigDecimal)
