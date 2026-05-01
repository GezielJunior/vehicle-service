package br.com.carstore.controller

import br.com.carstore.domain.Vehicle
import br.com.carstore.service.VehicleService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/vehicles")
class VehicleController {

    private final VehicleService vehicleService

    VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService
    }

    @Post(uri="/")
    HttpResponse create(@Body Vehicle vehicle) {
        return HttpResponse.created(vehicleService.create(vehicle))
    }

    @Get("/{id}")
    HttpResponse<Vehicle> findById(@PathVariable Long id) {
        return HttpResponse.ok(vehicleService.findById(id))
    }

}