package hex.transactionsinfosupplier.adapter

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import hex.transactionsinfosupplier.application.Properties
import hex.transactionsinfosupplier.domain.Money
import hex.transactionsinfosupplier.domain.TransactionFeeWage
import java.io.File
import java.math.BigDecimal

class FeeWagesLoader(private val properties: Properties) {
	fun load(): List<TransactionFeeWage> =
		csvReader().readAllWithHeader(File(properties.feeWages.filePath)).mapNotNull {
			it[properties.feeWages.limitHeader]?.let(Money::of)?.let { limit ->
				it[properties.feeWages.feePercentageHeader]?.let(::toPercentage)?.let { feePercentage ->
					TransactionFeeWage(limit, feePercentage)
				}
			}
		}
	
	private fun toPercentage(value: String) =
		value
			.replace(",", ".")
			.toBigDecimal()
			.divide(BigDecimal(100))
}