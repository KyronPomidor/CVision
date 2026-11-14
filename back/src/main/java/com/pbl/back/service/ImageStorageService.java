package com.pbl.back.service;

import com.pbl.back.exception.FileStorageException;
import com.pbl.back.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class ImageStorageService {

    private static final long MAX_IMAGE_SIZE = 5L * 1024 * 1024;
    private final Path baseDirectory;

    public ImageStorageService(
            @Value("${app.storage.images-directory:uploads/images}") String baseDirectory) {
        this.baseDirectory = Path.of(baseDirectory).toAbsolutePath().normalize();
    }

    public StoredImage store(MultipartFile file, String subDirectory) {
        if (file.isEmpty()) {
            throw new FileStorageException("An image file is required.");
        }
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new FileStorageException("Image files must be 5 MB or smaller.");
        }

        String contentType = file.getContentType() == null
                ? ""
                : file.getContentType().trim().toLowerCase(Locale.ROOT);
        ImageType imageType = ImageType.fromContentType(contentType);
        if (imageType == null) {
            throw new FileStorageException("Only JPEG, PNG, and WebP images are allowed.");
        }

        byte[] content;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            throw new FileStorageException("Could not read the image file.", ex);
        }
        if (!imageType.matches(content)) {
            throw new FileStorageException("Image content does not match its declared type.");
        }

        String originalFileName = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        if (originalFileName.length() > 255) {
            originalFileName = originalFileName.substring(0, 255);
        }

        String storedFileName = UUID.randomUUID() + "." + imageType.extension;
        Path directory = resolveSubDirectory(subDirectory);
        Path target = directory.resolve(storedFileName).normalize();
        if (!target.startsWith(baseDirectory)) {
            throw new FileStorageException("The image path is invalid.");
        }

        try {
            Files.createDirectories(directory);
            Files.write(target, content);
        } catch (IOException ex) {
            throw new FileStorageException("Could not save the image file.", ex);
        }

        String relativePath = baseDirectory.relativize(target).toString().replace('\\', '/');
        return new StoredImage(relativePath, imageType.contentType, originalFileName);
    }

    public Resource load(String relativePath) {
        Path target = resolveRelativePath(relativePath);
        if (!Files.exists(target)) {
            throw new ResourceNotFoundException("Image file not found.");
        }
        return new FileSystemResource(target);
    }

    public void deleteQuietly(String relativePath) {
        try {
            Files.deleteIfExists(resolveRelativePath(relativePath));
        } catch (IOException | RuntimeException ex) {
            log.warn("Could not delete stored image '{}'", relativePath, ex);
        }
    }

    public void deleteDirectoryQuietly(String subDirectory) {
        try {
            Path directory = resolveSubDirectory(subDirectory);
            if (!Files.exists(directory)) {
                return;
            }
            try (Stream<Path> paths = Files.walk(directory)) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException ex) {
                        log.warn("Could not delete image storage path '{}'", path, ex);
                    }
                });
            }
        } catch (IOException | RuntimeException ex) {
            log.warn("Could not delete image storage directory '{}'", subDirectory, ex);
        }
    }

    private Path resolveSubDirectory(String subDirectory) {
        try {
            Path relativeDirectory = Path.of(subDirectory);
            if (relativeDirectory.isAbsolute()) {
                throw new FileStorageException("The image path is invalid.");
            }
            Path directory = baseDirectory.resolve(relativeDirectory).normalize();
            if (!directory.startsWith(baseDirectory) || directory.equals(baseDirectory)) {
                throw new FileStorageException("The image path is invalid.");
            }
            return directory;
        } catch (InvalidPathException ex) {
            throw new FileStorageException("The image path is invalid.", ex);
        }
    }

    private Path resolveRelativePath(String relativePath) {
        try {
            Path relative = Path.of(relativePath);
            if (relative.isAbsolute()) {
                throw new FileStorageException("The image path is invalid.");
            }
            Path target = baseDirectory.resolve(relative).normalize();
            if (!target.startsWith(baseDirectory)) {
                throw new FileStorageException("The image path is invalid.");
            }
            return target;
        } catch (InvalidPathException | NullPointerException ex) {
            throw new FileStorageException("The image path is invalid.", ex);
        }
    }

    public record StoredImage(String relativePath, String contentType, String originalFileName) {
    }

    private enum ImageType {
        JPEG("image/jpeg", "jpg"),
        PNG("image/png", "png"),
        WEBP("image/webp", "webp");

        private final String contentType;
        private final String extension;

        ImageType(String contentType, String extension) {
            this.contentType = contentType;
            this.extension = extension;
        }

        private static ImageType fromContentType(String contentType) {
            for (ImageType type : values()) {
                if (type.contentType.equals(contentType)) {
                    return type;
                }
            }
            return null;
        }

        private boolean matches(byte[] content) {
            return switch (this) {
                case JPEG -> content.length >= 3
                        && (content[0] & 0xFF) == 0xFF
                        && (content[1] & 0xFF) == 0xD8
                        && (content[2] & 0xFF) == 0xFF;
                case PNG -> content.length >= 8
                        && (content[0] & 0xFF) == 0x89
                        && content[1] == 0x50
                        && content[2] == 0x4E
                        && content[3] == 0x47
                        && content[4] == 0x0D
                        && content[5] == 0x0A
                        && content[6] == 0x1A
                        && content[7] == 0x0A;
                case WEBP -> content.length >= 12
                        && content[0] == 'R'
                        && content[1] == 'I'
                        && content[2] == 'F'
                        && content[3] == 'F'
                        && content[8] == 'W'
                        && content[9] == 'E'
                        && content[10] == 'B'
                        && content[11] == 'P';
            };
        }
    }
}
