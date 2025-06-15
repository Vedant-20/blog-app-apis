package com.vedant.blog.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vedant.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();
		String randomID = UUID.randomUUID().toString();
		String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		String filePath = path + File.separator + filename1;

		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return filename1;

	}

	@Override
	public InputStream getResource(String path, String fileName) {
		String fullPath = path + File.separator + fileName;
		Path filePath = Paths.get(fullPath);

		if (!Files.exists(filePath)) {
			throw new RuntimeException("File not found at path: " + fullPath);
		}

		try {
			return Files.newInputStream(filePath);
		} catch (IOException e) {
			throw new RuntimeException("Error reading file at path: " + fullPath, e);
		}
	}

}
