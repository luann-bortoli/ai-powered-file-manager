package io.github.JarvisFs.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

@Service
public class FileService {

    private static final Path BASE_PATH = Paths.get("C:/JARVIS_SANDBOX")
            .toAbsolutePath()
            .normalize();

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private Path resolveSafePath(String input) {

        Path targetPath = BASE_PATH
                .resolve(input)
                .normalize()
                .toAbsolutePath();

        if (!targetPath.startsWith(BASE_PATH)) {

            System.out.println("[-] Access outside default sandbox requested.");
            logger.warn("Attempted access outside sandbox: {}", input);

            throw new SecurityException("Access outside sandbox requested");
        }

        return targetPath;
    }

    public void createFolder(String name) {

        logger.info("Creating folder: {}", name);

        Path targetPath = resolveSafePath(name);

        try {

            Files.createDirectories(targetPath);

            System.out.println("[+] Folder " + name + " created successfully!\n");
            logger.info("Folder created successfully: {}", name);

        } catch (IOException e) {

            System.out.println("Error creating folder: " + e.getMessage());
            logger.error("Error creating folder: {}", name, e);
        }
    }

    public void createFile(String name, String content) {

        logger.info("Creating file: {}", name);

        Path targetPath = resolveSafePath(name);

        try {
            Files.writeString(targetPath, content, StandardOpenOption.CREATE);

            System.out.println("[+] File " + name + " created successfully!\n");
            logger.info("File created successfully: {}", name);

        } catch (IOException e) {

            System.out.println("Error creating file: " + e.getMessage());
            logger.error("Error creating file: {}", name, e);
        }
    }

    public void delete(String name) {

        logger.info("Deleting file/folder: {}", name);

        Path targetPath = resolveSafePath(name);

        if (Files.notExists(targetPath)) {

            logger.error("Path not found: {}", targetPath);
            throw new RuntimeException("Path not found: " + targetPath);
        }

        if (Files.isDirectory(targetPath)) {

            try (var paths = Files.walk(targetPath)) {

                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {

                            try {

                                Files.delete(path);

                            } catch (IOException e) {

                                System.out.println(e.getMessage());
                                logger.error("Error deleting file: {}", path, e);
                            }
                        });

                System.out.println("[+] Folder " + name + " deleted successfully!\n");
                logger.info("Folder deleted successfully: {}", name);

            } catch (IOException e) {

                System.out.println("Error deleting folder: " + e.getMessage());
                logger.error("Error deleting folder: {}", name, e);
            }

            return;
        }

        try {

            Files.delete(targetPath);

            System.out.println("[+] File " + name + " deleted successfully!\n");
            logger.info("File deleted successfully: {}", name);

        } catch (IOException e) {

            System.out.println("Error deleting file: " + e.getMessage());
            logger.error("Error deleting file: {}", name, e);
        }
    }

    public void rename(String name, String newName) {

        logger.info("Renaming file/folder: {}", name);

        Path currentPath = resolveSafePath(name);
        Path newPath = resolveSafePath(newName);

        if (Files.notExists(currentPath)) {

            logger.error("Path not found: {}", currentPath);

            throw new RuntimeException("Path not found: " + currentPath);
        }

        try {

            Files.move(currentPath, newPath, StandardCopyOption.REPLACE_EXISTING);

            if (Files.isRegularFile(newPath)) {

                System.out.println("[+] File " + name + " renamed to " + newName + " successfully!\n");

                logger.info("File {} renamed successfully to {}", name, newName);

            } else {

                System.out.println("[+] Folder " + name + " renamed to " + newName + " successfully!\n");

                logger.info("Folder {} renamed successfully to {}", name, newName);
            }

        } catch (IOException e) {

            System.out.println("Error renaming file/folder: " + e.getMessage());

            logger.error("Error renaming file/folder: {}", name, e);
        }
    }

    public void list(String name) {

        logger.info("Listing files: {}", name);

        Path targetPath = resolveSafePath(name);

        if (Files.isRegularFile(targetPath)) {

            System.out.println(targetPath.getFileName());

            logger.info("File listed successfully: {}", name);

            return;
        }

        try (var paths = Files.walk(targetPath)) {

            paths.forEach(path -> {
                System.out.println(path.getFileName());
            });

            logger.info("Folder listed successfully: {}", name);

        } catch (IOException e) {

            System.out.println(e.getMessage());

            logger.error("Error listing files: {}", name, e);
        }
    }
}