package io.github.JarvisFs.command;

import io.github.JarvisFs.filesystem.FileService;
import org.springframework.stereotype.Service;

@Service
public class CommandExecutor {

    private final FileService fs;
    public CommandExecutor(FileService fs) { this.fs = fs; }

    public void executeCommand(CommandRequest command) throws Exception{

        switch (command.getAction()){

            case CREATE_FOLDER -> fs.createFolder(command.getName());

            case CREATE_FILE -> fs.createFile(command.getName(), command.getContent());

            case DELETE -> fs.delete(command.getName());

            case RENAME -> fs.rename(command.getName(), command.getNewName());

            case LIST -> fs.list(command.getName());

            case ERROR -> System.out.println("[-] " + command.getMessage());

        }

    }

}
