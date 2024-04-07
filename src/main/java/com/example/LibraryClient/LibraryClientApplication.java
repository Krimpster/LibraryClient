package com.example.LibraryClient;

import com.example.LibraryClient.Panels.LibraryWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryClientApplication {

	public static void main(String[] args) {
		//SpringApplication.run(LibraryClientApplication.class, args);
		LibraryWindow libraryWindow = new LibraryWindow();
	}

}
