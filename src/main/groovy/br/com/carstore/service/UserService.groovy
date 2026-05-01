package br.com.carstore.service

import br.com.carstore.domain.User
import br.com.carstore.repository.UserRepository
import groovy.util.logging.Slf4j
import jakarta.inject.Singleton
import org.mindrot.jbcrypt.BCrypt

@Slf4j
@Singleton
class UserService {

    private final UserRepository userRepository

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    User create(User user) {
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
        return userRepository.save(user)
    }
}
