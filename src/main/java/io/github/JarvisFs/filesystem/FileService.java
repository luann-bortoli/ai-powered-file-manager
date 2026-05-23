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

        System.out.println("[+] Folder " + name + " created successfully!\n");

    }

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

            System.out.println("[+] Folder " + name + " deleted successfully!\n");

            return;
        }

        Files.delete(targetPath);

        System.out.println("[+] File " + name + " deleted successfully!\n");

    }

    public void rename(String name, String newName) throws IOException{

        Path currentPath = resolveSafePath(name);

        Path newPath = resolveSafePath(newName);

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

        Path targetPath = resolveSafePath(name);

        if(Files.isRegularFile(targetPath)) {
            System.out.println(targetPath.getFileName());
            return;
        }

        try {

            Files.walk(targetPath)
                    .forEach(path -> {
                        System.out.println(path.getFileName());
                    });

        } catch (IOException e){

            System.out.println(e.getMessage());

        }
    }
}
