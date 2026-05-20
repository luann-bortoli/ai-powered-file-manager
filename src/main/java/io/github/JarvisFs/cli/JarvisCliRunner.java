package io.github.JarvisFs.cli;

import io.github.JarvisFs.ai.OllamaClient;
import io.github.JarvisFs.command.CommandExecutor;
import io.github.JarvisFs.command.CommandParser;
import io.github.JarvisFs.command.CommandRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class JarvisCliRunner implements CommandLineRunner {

    private final OllamaClient ollamaClient;
    private final CommandParser commandParser;
    private final CommandExecutor commandExecutor;

    public JarvisCliRunner(OllamaClient ollamaClient, CommandExecutor commandExecutor, CommandParser commandParser){
        this.ollamaClient = ollamaClient;
        this.commandExecutor = commandExecutor;
        this.commandParser = commandParser;
    }

    @Override
    public void run(String... args){

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                    ╔══════════════════════════════╗
                    ║         JarvisFs v0.1.0      ║
                    ║  AI Powered File Assistant   ║
                    ╚══════════════════════════════╝
                    
                    Type 'exit' to quit.
                    
                    """);

        while (true) {

            System.out.print("JarvisFs >> ");
            String input = scanner.nextLine();
            System.out.println("Working on it...");

            if (input.equalsIgnoreCase("exit")){
                System.out.println("JarvisFs terminated.");
                break;
            }

            if (input.isBlank()){
                continue;
            }

            try
            {
                String jsonCommand = ollamaClient.interpret(input);

                CommandRequest command = commandParser.parse(jsonCommand);

                commandExecutor.executeCommand(command);

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }

        }

    }

}
