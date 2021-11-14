package hex.transactionsinfosupplier.adapter

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import hex.transactionsinfosupplier.application.Properties
import hex.transactionsinfosupplier.domain.Money
import hex.transactionsinfosupplier.domain.Transaction
import hex.transactionsinfosupplier.domain.ClientId
import hex.transactionsinfosupplier.domain.port.TransactionsRepository
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

class CsvLoadedTransactionsRepository(properties: Properties) : TransactionsRepository {
	private val transactions = csvReader()
		.readAllWithHeader(File(properties.transactions.filePath))
		.map { transaction ->
			Transaction(
				clientId = ClientId(transaction[properties.transactions.clientIdHeader]!!.toInt()),
				amount = Money.of(transaction[properties.transactions.amountHeader]!!),
				dateTime = LocalDateTime.parse(
					transaction[properties.transactions.dateHeader]!!,
					ofPattern(properties.transactions.datePattern)
				)
			)
		}
	
	override fun getTransactionsFor(clientId: ClientId): List<Transaction> =
		transactions.filter { it.clientId == clientId }
}