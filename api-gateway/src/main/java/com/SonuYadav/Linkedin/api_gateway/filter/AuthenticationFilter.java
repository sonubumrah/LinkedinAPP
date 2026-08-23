package com.SonuYadav.Linkedin.api_gateway.filter;

import com.SonuYadav.Linkedin.api_gateway.service.JWTService;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
	private final JWTService jwtService;

	public AuthenticationFilter(JWTService jwtService) {
		super(Config.class);
		this.jwtService = jwtService;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			log.info("login request started with {}", exchange.getRequest().getURI());
			final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
			if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
				log.error("Authorization header is missing or invalid");
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
			try{
				final String token = tokenHeader.split("Bearer ")[1];
				String userId=jwtService.getUserIdFromToken(token);
				ServerWebExchange modifiedExchange= exchange
						.mutate()
						.request(r->r.header("X-User-Id", userId))
						.build();
				return chain.filter(modifiedExchange);
			}catch (Exception e){
				log.error("Error while validating token: {}", e.getMessage());
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}




		};
	}

	public static class Config {
		// configuration properties for the filter (if any)
	}
}


