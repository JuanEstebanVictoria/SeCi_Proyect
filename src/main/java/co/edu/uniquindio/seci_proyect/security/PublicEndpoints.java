package co.edu.uniquindio.seci_proyect.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class PublicEndpoints {

    private final RequestMatcher[] matchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/public/**"),
            new AntPathRequestMatcher("/login", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/users", HttpMethod.POST.name())
    };
}
