package hex.transactionsinfosupplier.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SpringSecurityConfiguration(private val properties: Properties) : WebSecurityConfigurerAdapter() {
	
	override fun configure(http: HttpSecurity) {
		http
			.csrf().disable()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.httpBasic()
	}
	
	@Autowired
	fun configureGlobal(auth: AuthenticationManagerBuilder) {
		properties.users.entries.forEach { (username, password) ->
			auth.inMemoryAuthentication()
				.withUser(username)
				.password("{noop}$password")
				.roles("USER")
		}
	}
}