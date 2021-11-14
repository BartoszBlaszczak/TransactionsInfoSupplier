package hex.transactionsinfosupplier.adapter

import hex.transactionsinfosupplier.application.Properties
import hex.transactionsinfosupplier.domain.GetTransactionsInfoLoggingDetails
import hex.transactionsinfosupplier.domain.port.GetTransactionsInfoLogger
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class GetTransactionsInfoMongoLogger(properties: Properties) : GetTransactionsInfoLogger {
	private val collection = KMongo
		.createClient(properties.mongo.connectionString)
		.coroutine
		.getDatabase(properties.mongo.database)
		.getCollection<GetTransactionsInfoLoggingDetails>()
	
	
	override fun log(details: GetTransactionsInfoLoggingDetails) {
		runBlocking { collection.insertOne(details) }
	}
}