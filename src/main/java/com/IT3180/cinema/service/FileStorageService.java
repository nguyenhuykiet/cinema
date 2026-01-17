package com.IT3180.cinema.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {
	private static final String UPLOAD_DIR = "uploads/posters/";

	public String storePoster(MultipartFile file) throws IOException {
		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/"))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image file!");

		Files.createDirectories(Paths.get(UPLOAD_DIR));

		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path path = Paths.get(UPLOAD_DIR).resolve(fileName);

		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		return "/uploads/posters/" + fileName;
	}

	public void deletePosterIfExists(String posterUrl) {
		if (posterUrl == null)
			return;

		Path path = Paths.get("uploads", posterUrl.replace("/posters/", ""));
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			// log warning
		}
	}
}
