package main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dryzu on 16.04.2018.
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration {

	@Bean
	public FilmLibrary getFilmLibrrary() {
		return FilmLibrary.createOracleStorageLibrary("jdbc:oracle:thin:@localhost:1521:xe");
	}
}
