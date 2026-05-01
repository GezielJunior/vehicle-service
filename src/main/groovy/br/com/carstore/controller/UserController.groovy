package br.com.carstore.controller

import br.com.carstore.domain.User
import br.com.carstore.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/users")
class UserController {

    private final UserService userService

    UserController(UserService userService) {
        this.userService = userService
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/")
    HttpResponse create(@Body User user) {
        return HttpResponse.created(userService.create(user))
    }
}
