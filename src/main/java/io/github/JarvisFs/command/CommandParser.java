package io.github.JarvisFs.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandParser
{
    private final ObjectMapper om;

    public CommandParser(ObjectMapper om){
        this.om = om;
    }

    public CommandRequest parse(String json){

        try{

            return om.readValue(json, CommandRequest.class);

        } catch (Exception e){

            throw new RuntimeException("Invalid json command: " + json);

        }
    }
}
