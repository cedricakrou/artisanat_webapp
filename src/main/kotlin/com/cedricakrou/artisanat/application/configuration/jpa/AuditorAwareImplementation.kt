package com.cedricakrou.artisanat.application.configuration.jpa

import org.springframework.data.domain.AuditorAware
import java.util.*


open class AuditorAwareImplementation : AuditorAware<String>  {

    override fun getCurrentAuditor(): Optional<String> = Optional.of("")

}