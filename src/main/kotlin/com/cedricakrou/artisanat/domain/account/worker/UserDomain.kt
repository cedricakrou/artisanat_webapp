package com.cedricakrou.artisanat.domain.account.worker

import com.cedricakrou.artisanat.domain.account.entity.User
import com.cedricakrou.artisanat.domain.account.port.IManageUser
import com.b2i.neo.domain.common.port.ReferencePort

interface UserDomain : IManageUser, ReferencePort<User>