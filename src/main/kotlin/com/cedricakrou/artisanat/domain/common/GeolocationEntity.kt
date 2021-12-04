package com.cedricakrou.artisanat.domain.common

import com.cedricakrou.artisanat.domain.account.entity.User
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class GeolocationEntity(var latitude : Double = 0.0, var longitude : Double = 0.0  ) : User()