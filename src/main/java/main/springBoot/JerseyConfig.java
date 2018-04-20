package main.springBoot;


import main.rest.MovieService;
import main.rest.ReviewService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by dryzu on 19.04.2018.
 */
@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
// регистрация REST контроллеров
		register(MovieService.class);
		register(ReviewService.class);
	}
}
