package br.com.carstore.repository

import br.com.carstore.domain.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username)
}
