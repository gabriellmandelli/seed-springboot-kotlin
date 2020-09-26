package com.greentower.seedApi.rules.client.domain.entity

import com.greentower.seedApi.core.generic.GenericEntity
import com.greentower.seedApi.core.util.AuthUserUtil
import com.greentower.seedApi.rules.auth_user.domain.entity.AuthUser
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "todo")
class ToDo : GenericEntity() {

    @Column(name = "title")
    var title = ""

    @Column(name = "description")
    var description = ""

    @Column(name = "date_completed")
    var dateToEnd : LocalDateTime? = null

    @Column(name = "completed")
    var completed = false

    @ManyToOne
    @JoinColumn(name = "auth_user")
    var user : AuthUser = AuthUserUtil.getCurrentAuthUser()

    @ManyToMany(fetch = FetchType.LAZY ,cascade = [CascadeType.ALL])
    @JoinTable(name = "user_todo",
            joinColumns = [JoinColumn(name = "todo", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "auth_user", referencedColumnName = "id")]
    )
    lateinit var users:Collection<AuthUser>
}