package io.github.JarvisFs.filesystem;

import io.github.JarvisFs.ai.dto.OllamaResponse;
import io.github.JarvisFs.command.ActionType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

@Service
public class FileService {

    static String BASE_URL = "C:/JARVIS_SANDBOX";

    public void createFolder(String name) throws IOException{

        Path folderPath = Paths.get(BASE_URL, name);
        Files.createDirectories(folderPath);

        System.out.println("[+] Folder " + name + " created successfully!\n");

    }

    public void createFile(String name, String content) throws IOException{

        Path filePath = Paths.get(BASE_URL, name);
        Files.writeString(filePath, content, StandardOpenOption.CREATE);

        System.out.println("[+] File " + name + " created successfully!\n");

    }

    public void delete(String name) throws IOException{

        Path filePath = Paths.get(BASE_URL, name);

        if(Files.notExists(filePath)){
            throw new RuntimeException("Path not found: " + filePath);
        }

        if(Files.isDirectory(filePath)){
            Files.walk(filePath)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                    });

            System.out.println("[+] Folder " + name + " deleted successfully!\n");

            return;
        }

        Files.delete(filePath);

        System.out.println("[+] File " + name + " deleted successfully!\n");

    }

    public void rename(String name, String newName) throws IOException{

        Path currentPath = Paths.get(BASE_URL, name);
        Path newPath = Paths.get(BASE_URL, newName);

        if(Files.notExists(currentPath)){
            throw new RuntimeException("Path not found: " + currentPath);
        }

        Files.move(currentPath, newPath, StandardCopyOption.REPLACE_EXISTING);

        if(Files.isRegularFile(newPath)){
            System.out.println("[+] File " + name + " renamed to " + newName + " successfully!\n");
            return;
        }

        System.out.println("[+] Folder " + name + " renamed to " + newName + " successfully!\n");

    }

    public void list(String name){

        if(Files.isRegularFile(Path.of(name))) {
            System.out.println(name);
            return;
        }

        Path folderPath = Paths.get(BASE_URL, name);

        try {

            Files.walk(folderPath)
                    .forEach(path -> {
                        System.out.println(path.getFileName());
                    });

        } catch (IOException e){

            System.out.println(e.getMessage());

        }
    }
}
