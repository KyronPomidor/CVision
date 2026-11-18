package com.pbl.back.service;

import com.pbl.back.exception.FileStorageException;
import com.pbl.back.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageStorageServiceTest {

    @TempDir
    Path baseDirectory;

    private ImageStorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new ImageStorageService(baseDirectory.toString());
    }

    @Test
    void storesValidPngUnderRequestedSubdirectory() throws IOException {
        MockMultipartFile file = image("photo.png", "image/png",
                new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A});

        ImageStorageService.StoredImage stored = storageService.store(file, "postings/15");

        assertTrue(stored.relativePath().matches("postings/15/[0-9a-f-]{36}\\.png"));
        assertTrue(Files.exists(baseDirectory.resolve(stored.relativePath())));
        assertEquals("image/png", stored.contentType());
        assertEquals("photo.png", stored.originalFileName());
    }

    @Test
    void rejectsTextContentDeclaredAsPng() {
        assertThrows(FileStorageException.class, () -> storageService.store(
                image("file.png", "image/png", "not an image".getBytes()), "postings/15"));
    }

    @Test
    void rejectsSvgAndHtmlContentTypes() {
        assertThrows(FileStorageException.class, () -> storageService.store(
                image("image.svg", "image/svg+xml", new byte[]{1, 2, 3}), "postings/15"));
        assertThrows(FileStorageException.class, () -> storageService.store(
                image("page.html", "text/html", new byte[]{1, 2, 3}), "postings/15"));
    }

    @Test
    void rejectsFilesLargerThanFiveMegabytes() {
        byte[] content = new byte[5 * 1024 * 1024 + 1];
        assertThrows(FileStorageException.class, () -> storageService.store(
                image("large.png", "image/png", content), "postings/15"));
    }

    @Test
    void rejectsPathsOutsideTheBaseDirectory() {
        assertThrows(FileStorageException.class, () -> storageService.load("../../etc/passwd"));
    }

    @Test
    void deletesDirectoryAndItsContentsQuietly() throws IOException {
        Path directory = baseDirectory.resolve("postings").resolve("15");
        Files.createDirectories(directory);
        Files.writeString(directory.resolve("image.png"), "data");

        storageService.deleteDirectoryQuietly("postings/15");

        assertFalse(Files.exists(directory));
    }

    private MockMultipartFile image(String name, String contentType, byte[] content) {
        return new MockMultipartFile("file", name, contentType, content);
    }
}
