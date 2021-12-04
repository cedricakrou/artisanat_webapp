package com.cedricakrou.artisanat

import com.cedricakrou.artisanat.application.bootstrap.RoleBootstrap
import com.cedricakrou.artisanat.application.bootstrap.UserBootstrap
import com.cedricakrou.artisanat.domain.account.worker.RoleDomain
import com.cedricakrou.artisanat.domain.account.worker.UserDomain
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@SpringBootApplication
class ArtisanatWebappApplication {


	@Bean
	fun initDatabase(
		roleDomain: RoleDomain,
		userDomain: UserDomain,
	) : CommandLineRunner {
		return CommandLineRunner {

			RoleBootstrap.seed(roleDomain)
			UserBootstrap.seed( userDomain, roleDomain)
		}
	}

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()


	@Bean
	fun emailTemplateResolver() : ClassLoaderTemplateResolver {
		val emailTemplateResolver = ClassLoaderTemplateResolver()
		emailTemplateResolver.prefix = "templates/";
		emailTemplateResolver.setTemplateMode("HTML5");
		emailTemplateResolver.suffix = ".html";
		emailTemplateResolver.setTemplateMode("XHTML");
		emailTemplateResolver.characterEncoding = "UTF-8";
		emailTemplateResolver.order = 1;
		return emailTemplateResolver
	}

}

fun main(args: Array<String>) {
	runApplication<ArtisanatWebappApplication>(*args)
}
