package hex.transactionsinfosupplier.adapter

import hex.transactionsinfosupplier.domain.GetTransactionsInfoUseCase
import hex.transactionsinfosupplier.domain.ClientId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionsInfoController(private val useCase: GetTransactionsInfoUseCase) {
	
	@GetMapping
	fun transactionsInfo(@RequestParam clients: List<Int>) = useCase.getTransactionsInfo(clients.map(::ClientId))
}