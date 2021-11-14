package hex.transactionsinfosupplier.application

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("tis")
class Properties {
	var transactions: Transactions = Transactions()
	var feeWages: FeeWages = FeeWages()
	var mongo: Mongo = Mongo()
	var users: Map<String, String> = mapOf()
}

class Transactions {
	var filePath: String = ""
	var clientIdHeader: String = ""
	var amountHeader: String = ""
	var dateHeader: String = ""
	var datePattern: String = ""
}

class FeeWages {
	var filePath: String = ""
	var limitHeader: String = ""
	var feePercentageHeader: String = ""
}

class Mongo {
	var connectionString: String = ""
	var database: String = ""
}