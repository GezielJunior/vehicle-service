package br.com.carstore.service

import br.com.carstore.domain.Vehicle
import br.com.carstore.repository.VehicleRepository
import groovy.util.logging.Slf4j
import jakarta.inject.Singleton

@Slf4j
@Singleton
class VehicleService {

    private final VehicleRepository vehicleRepository

    VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository
    }

    Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle)
    }

    Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: ${id}"))
    }
}
