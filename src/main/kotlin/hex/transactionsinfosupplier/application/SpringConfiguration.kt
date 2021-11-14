package hex.transactionsinfosupplier.application

import hex.transactionsinfosupplier.adapter.CsvLoadedTransactionsRepository
import hex.transactionsinfosupplier.adapter.FeeWagesLoader
import hex.transactionsinfosupplier.adapter.GetTransactionsInfoMongoLogger
import hex.transactionsinfosupplier.adapter.TransactionsInfoController
import hex.transactionsinfosupplier.domain.FeeCalculator
import hex.transactionsinfosupplier.domain.GetTransactionsInfoUseCase
import hex.transactionsinfosupplier.domain.port.GetTransactionsInfoLogger
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class SpringConfiguration (
	private val properties: Properties
) {
	@Bean fun transactionsRepository() = CsvLoadedTransactionsRepository(properties)
	@Bean fun feeWagesLoader() = FeeWagesLoader(properties)
	@Bean fun feeCalculator() = FeeCalculator(FeeWagesLoader(properties).load())
	@Bean fun logger(): GetTransactionsInfoLogger = GetTransactionsInfoMongoLogger(properties)
	@Bean fun useCase() = GetTransactionsInfoUseCase(transactionsRepository(), feeCalculator(), logger())
	@Bean fun controller() = TransactionsInfoController(useCase())
}