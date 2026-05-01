package br.com.carstore.domain

import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@CompileStatic
@Serdeable
@Entity
@Table(name = "app_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @Column(name = "username", nullable = false, unique = true, length = 50)
    String username

    @Column(name = "password", nullable = false)
    String password
}
