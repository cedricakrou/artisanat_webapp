package com.cedricakrou.artisanat.domain.account.entity

import com.cedricakrou.artisanat.domain.account.account.entity.Account
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.HashSet
import javax.persistence.*

@Entity
@Table( name = "user_account" )
@DiscriminatorColumn( name = "user_type", length = 32)
@Inheritance( strategy =  InheritanceType.SINGLE_TABLE)
open class User() : Person(), UserDetails {


    @Column(unique = true, nullable = false)
    private var username: String? = null

    @JsonIgnore
    private var password: String = ""

    @Column( columnDefinition = "boolean default false" )
    open var isActive : Boolean = false

    @Column(nullable = false)
    open var locked = false

    @Column(nullable = false)
    open var enabled = false

    @Column(nullable = false)
    open var expired = false

    @Column(nullable = false)
    open var credentialsExpired = false

    open var lang: String? = null

    open var pin : String = ""

    open var balance : Double = 0.0

    @Column( columnDefinition = "boolean default true" )
    open var firstConnection : Boolean = true

    @Column( columnDefinition = "boolean default true" )
    open var resetPassword : Boolean = true

    @JsonIgnore
    @ManyToMany(targetEntity = Role::class, fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "user_Role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    open var roles: Collection<Role>? = null

    @OneToMany( mappedBy = "user" )
    var accounts : MutableSet<Account> = mutableSetOf()


    @Transient
    @JsonIgnore
    private var authorities: MutableCollection<GrantedAuthority>? = null

    constructor(username: String?, password: String, roles: Collection<Role>?) : this() {
        this.username = username
        this.password = password
        this.roles = roles
    }


    fun setUsername( username : String ) {
        this.username = username
    }

    fun setPassword( password : String ) {
        this.password = password
    }


    override fun getUsername(): String {

        return username!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun isEnabled(): Boolean {
        return enabled
    }


    override fun isCredentialsNonExpired(): Boolean {
        return !credentialsExpired
    }


    override fun isAccountNonExpired(): Boolean {
        return !expired
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked
    }


    fun setAuthorities(
            authorities: MutableCollection<GrantedAuthority>?) {
        this.authorities = authorities
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (authorities == null || authorities!!.isEmpty()) {
            authorities = HashSet()
            for (role in roles!!) {
                authorities?.add(SimpleGrantedAuthority(String.format("ROLE_%s", role.name)))
            }
        }
        return authorities!!
    }


    @JsonGetter
    fun roleId(): Long? {
        return roles!!.stream().findFirst().get().id
    }

    @PrePersist
    fun onPrePersist() {
        lang = "fr"
        enabled = true
        password = BCryptPasswordEncoder().encode(password)
    }

}