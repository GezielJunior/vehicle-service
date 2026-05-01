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
@Table(name = "vehicle")
class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @Column(name = "model", nullable = false, length = 50)
    String model

    @Column(name = "brand", nullable = false, length = 50)
    String brand

    @Column(name = "license_plate", nullable = false, unique = true, length = 10)
    String licensePlate
}
