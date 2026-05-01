package br.com.carstore.repository

import br.com.carstore.domain.Vehicle
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface VehicleRepository extends CrudRepository<Vehicle,Long>{

}