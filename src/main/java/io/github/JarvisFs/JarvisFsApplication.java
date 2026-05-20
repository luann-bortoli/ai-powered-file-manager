package io.github.JarvisFs;

import io.github.JarvisFs.filesystem.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JarvisFsApplication {

	public static void main(String[] args) throws IOException
    {
		SpringApplication.run(JarvisFsApplication.class, args);
	}

}
