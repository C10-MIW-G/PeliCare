package com.makeitworkch10.pacemakers.pelicare.service;

import com.makeitworkch10.pacemakers.pelicare.exception.StorageException;
import com.makeitworkch10.pacemakers.pelicare.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * saving and retrieving files from disk
 */
@Service
public class FileStorageService {

    @Value("${filestoragelocation}")
    private String PHOTO_STORAGE_LOCATION;

    public String saveImage(List<MultipartFile> multipartFiles, Long circleId) {

        String newFilename = "no file selected";

        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {

                // look for the file extension
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                int fileExtensionStart = filename.lastIndexOf(".");
                String fileExtension = filename.substring(fileExtensionStart);

                // make proper filename: CareCircle-id + file extension
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(circleId);
                stringBuilder.append(fileExtension);
                newFilename = stringBuilder.toString();

                if (!filename.equals("no file selected")) {
                    store(file, newFilename);
                }
            }
        }
        return newFilename;
    }

    private void store(MultipartFile file, String newFilename) {
        Path rootLocation = Paths.get(PHOTO_STORAGE_LOCATION);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = rootLocation.resolve(
                            Paths.get(newFilename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    // deleting Care Circle requires deleting the corresponding image as well
    public void deleteImage(String filename) {

        if (!filename.equals("geen plaatje geselecteerd")) {
            try {
                Path file = load(filename);

                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (MalformedURLException e) {
                throw new StorageFileNotFoundException("Could not read file: " + filename, e);
            }
        }
    }

    public Path load(String filename) {
        return Paths.get(PHOTO_STORAGE_LOCATION).resolve(filename);
    }

    public String updateImage(Long carecircleId,
                              String oldImageFilename,
                              String noImage,
                              List<MultipartFile> multipartFiles) {
        if (noImage.compareTo("true") == 0) { // no more user provided image for this Care Circle
            deleteImage(oldImageFilename);
            return "no file selected";
        } else if (multipartFiles.get(0).isEmpty() ) {
                // no file selected
                return oldImageFilename;

        } else {
                // delete old file, save new file
                deleteImage(oldImageFilename);
                return saveImage(multipartFiles, carecircleId);
        }
    }
}
