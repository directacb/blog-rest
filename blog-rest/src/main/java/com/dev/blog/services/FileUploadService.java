package com.dev.blog.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.blog.FileStorageProperties;
import com.dev.blog.exceptions.StorageException;
import com.sun.xml.bind.api.impl.NameConverter.Standard;

@Service
public class FileUploadService {
		
	private final Path rootLocation;
	@Autowired
	public FileUploadService(FileStorageProperties properties) {
		this.rootLocation =Paths.get(properties.getUploadDir());
	try {
		Files.createDirectories(rootLocation);
	} catch (IOException e) {
		throw new StorageException("Could not initialize directory");
	}
	}
	//When file comes it will be mulipart file
	public String storeFile(MultipartFile file) {
		try {
			//checking if file is empty or not 
			if(file.isEmpty()) {
				throw new StorageException("Can not store empty file");
			}
			//not empty than getting the origanal name and than combining with rootlocation to destinationfile
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename())
					);
			//once we have destination file we can "try with resource" below can use to creaste inputstream and copy the file or existing file will be replaced 
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				
			}
			return file.getOriginalFilename();
		} catch(IOException e) {
			throw new StorageException("Could not store file");
		}
	}
	
	public Resource loadAsResource(String filename) {
		
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageException("Could not read/find file");
			}
		} catch (MalformedURLException e) {
			throw new StorageException("Could not read file");
		}
	}
	
}
