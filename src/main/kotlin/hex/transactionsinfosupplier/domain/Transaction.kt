package hex.transactionsinfosupplier.domain

import java.time.LocalDateTime

data class Transaction(val clientId: ClientId, val amount: Money, val dateTime: LocalDateTime)
