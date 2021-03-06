package org.carlspring.strongbox.security.authentication.suppliers;

import org.carlspring.strongbox.security.authentication.JwtTokenFetcher;
import org.carlspring.strongbox.users.security.SecurityTokenProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
class JWTAuthenticationSupplier
        implements AuthenticationSupplier, JwtTokenFetcher
{

    @Inject
    private SecurityTokenProvider securityTokenProvider;

    @CheckForNull
    @Override
    public Authentication supply(@Nonnull HttpServletRequest request)
    {
        final Optional<String> optToken = getToken(request);
        if (!optToken.isPresent())
        {
            return null;
        }

        final String token = optToken.get();
        String userName = securityTokenProvider.getSubject(token);
        String password = securityTokenProvider.getPassword(token);
        return new UsernamePasswordAuthenticationToken(userName, password);
    }
}
