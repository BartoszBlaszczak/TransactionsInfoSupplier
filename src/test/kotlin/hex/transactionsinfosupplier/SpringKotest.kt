package hex.transactionsinfosupplier

import hex.transactionsinfosupplier.application.Properties
import hex.transactionsinfosupplier.application.SpringConfiguration
import hex.transactionsinfosupplier.application.SpringSecurityConfiguration
import hex.transactionsinfosupplier.domain.port.GetTransactionsInfoLogger
import io.kotest.core.spec.style.FunSpec
import io.kotest.spring.SpringListener
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringTestConfiguration::class, SpringSecurityConfiguration::class])
@ActiveProfiles("test")
abstract class SpringKotest(body: FunSpec.() -> Unit) : FunSpec() {
	override fun listeners() = listOf(SpringListener)
	init { body() }
}

@Configuration
class SpringTestConfiguration(properties: Properties): SpringConfiguration(properties) {
	override fun logger(): GetTransactionsInfoLogger =
		mockk<GetTransactionsInfoLogger>().also { every { it.log(any()) } just Runs }
}

