package br.com.carstore.auth

import br.com.carstore.repository.UserRepository
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton
import org.mindrot.jbcrypt.BCrypt

@Singleton
class AuthenticationProviderUserPassword<B> implements HttpRequestAuthenticationProvider<B> {

    private final UserRepository userRepository

    AuthenticationProviderUserPassword(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Override
    AuthenticationResponse authenticate(
            @Nullable HttpRequest<B> httpRequest,
            @NonNull AuthenticationRequest<String, String> authenticationRequest
    ) {
        def user = userRepository.findByUsername(authenticationRequest.identity as String)

        if (user.isPresent() && BCrypt.checkpw(authenticationRequest.secret as String, user.get().password)) {
            return AuthenticationResponse.success(authenticationRequest.identity as String)
        }

        return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
    }
}
