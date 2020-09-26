package com.greentower.seedApi.core.generic

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface GenericRepository<T : GenericClass> : JpaRepository<T, UUID>