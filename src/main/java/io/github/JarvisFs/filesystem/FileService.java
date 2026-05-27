package io.github.JarvisFs.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

@Service
public class FileService {

    static String BASE_URL = "C:/JARVIS_SANDBOX";

<<<<<<< HEAD
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public Path resolveSafePath(String input){
=======
    public Path resolveSafePath(String input){

        Path base = Paths.get(BASE_URL).normalize().toAbsolutePath();

        Path targetPath = base
                .resolve(input)
                .normalize()
                .toAbsolutePath();

        if (!targetPath.startsWith(base)){
            System.out.println("[-] Access outside default sandbox requested.");
            throw new SecurityException("Access outside sandbox requested");
        }

        return targetPath;

    };

    public void createFolder(String name) throws IOException{

        Path targetPath = resolveSafePath(name);

        Files.createDirectories(targetPath);
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5

        Path base = Paths.get(BASE_URL).normalize().toAbsolutePath();

        Path targetPath = base
                .resolve(input)
                .normalize()
                .toAbsolutePath();

<<<<<<< HEAD
        if (!targetPath.startsWith(base)){
            System.out.println("[-] Access outside default sandbox requested.");
            logger.warn("Access outside default sandbox requested.");
            throw new SecurityException("Access outside sandbox requested");
        }

        return targetPath;
=======
    public void createFile(String name, String content) throws IOException{

        Path targetPath = resolveSafePath(name);

        Files.writeString(targetPath, content, StandardOpenOption.CREATE);

        System.out.println("[+] File " + name + " created successfully!\n");

    }

    public void delete(String name) throws IOException{

        Path targetPath = resolveSafePath(name);

        if(Files.notExists(targetPath)){
            throw new RuntimeException("Path not found: " + targetPath);
        }

        if(Files.isDirectory(targetPath)){
            Files.walk(targetPath)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                    });
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5

    }

    public void createFolder(String name){

        logger.info("Creating folder: {}", name);

        Path targetPath = resolveSafePath(name);

        try
        {
            Files.createDirectories(targetPath);

            System.out.println("[+] Folder " + name + " created successfully!\n");
            logger.info("Folder created successfully: {}", name);

        } catch (IOException e){

            System.out.println("Error creating folder: " + e.getMessage());
            logger.error("Error creating folder: {}", name, e);

        }
    }

    public void createFile(String name, String content){

        logger.info("Creating file: {}", name);

        Path targetPath = resolveSafePath(name);

        try
        {
            Files.writeString(targetPath, content, StandardOpenOption.CREATE);

            System.out.println("[+] File " + name + " created successfully!\n");
            logger.info("File created successfully: {}", name);

        } catch (IOException e){

            System.out.println("Error creating file: " + e.getMessage());
            logger.error("Error creating file: {}", name, e);

        }


    }

    public void delete(String name){

        logger.info("Deleting file/folder: {}", name);

        Path targetPath = resolveSafePath(name);

        if(Files.notExists(targetPath)){
            logger.error("Path not found : {}", targetPath);
            throw new RuntimeException("Path not found: " + targetPath);
        }

        if(Files.isDirectory(targetPath)) {

            try (var paths = Files.walk(targetPath)){

                    paths
                        .sorted(Comparator.reverseOrder())
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

                System.out.println("Error deleting file: " + e.getMessage());
                logger.error("Error deleting file: {}", name, e);

            }

            return;
        }

<<<<<<< HEAD
        try
        {
            Files.delete(targetPath);
=======
        Files.delete(targetPath);
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5

            System.out.println("[+] File " + name + " deleted successfully!\n");
            logger.info("File deleted successfully: {}", name);

        } catch (IOException e){

            System.out.println("Error deleting file: " + e.getMessage());
            logger.info("Error deleting file: {}", e.getMessage());

        }

    }

    public void rename(String name, String newName){

<<<<<<< HEAD
        logger.info("Renaming file/folder: {}", name);

=======
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5
        Path currentPath = resolveSafePath(name);

        Path newPath = resolveSafePath(newName);

        if(Files.notExists(currentPath)){
            logger.error("Path not found : {}", currentPath);
            throw new RuntimeException("Path not found: " + currentPath);
        }

        try
        {
            Files.move(currentPath, newPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[+] File " + name + " renamed to " + newName + " successfully!\n");
            logger.info("File {} renamed successfully to: {}", name, newName);

        } catch (IOException e){

            System.out.println("Error renaming file: " + e.getMessage());
            logger.error("Error renaming file: {}", e.getMessage());

        }
    }

    public void list(String name){

<<<<<<< HEAD
        logger.info("Listing files: {}", name);

=======
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5
        Path targetPath = resolveSafePath(name);

        if(Files.isRegularFile(targetPath)) {
            System.out.println(targetPath.getFileName());
<<<<<<< HEAD
            logger.info("File {} listed successfully!\n", name);
            return;
        }

        try (var paths = Files.walk(targetPath)){

                paths
=======
            return;
        }

        try {

            Files.walk(targetPath)
>>>>>>> b145a293c2c69805308563256a570d6573a9c0e5
                    .forEach(path -> {
                        System.out.println(path.getFileName());
                    });
            logger.info("Folder {} listed successfully!\n", name);

        } catch (IOException e){

            System.out.println(e.getMessage());
            logger.error("Error in listing files : {}", e.getMessage());

        }
    }
}