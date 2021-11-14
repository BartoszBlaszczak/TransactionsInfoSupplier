package hex.transactionsinfosupplier.domain.port

import hex.transactionsinfosupplier.domain.GetTransactionsInfoLoggingDetails

interface GetTransactionsInfoLogger {
	fun log(details: GetTransactionsInfoLoggingDetails)
}