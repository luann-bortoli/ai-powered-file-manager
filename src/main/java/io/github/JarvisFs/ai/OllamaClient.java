package io.github.JarvisFs.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.JarvisFs.ai.dto.OllamaRequest;
import io.github.JarvisFs.ai.dto.OllamaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OllamaClient {

    private final ObjectMapper om;
    private final HttpClient httpClient;
    private final String systemPrompt;

    private String loadSystemPrompt() {
        try (var is = new org.springframework.core.io.ClassPathResource("SystemPrompt.txt").getInputStream()) {
            return new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load system prompt", e);
        }
    }

    public OllamaClient(ObjectMapper om, HttpClient httpClient) {
        this.om = om;
        this.httpClient = httpClient;
        this.systemPrompt = loadSystemPrompt();
    }

    @Value("${ollama.url}")
    private String ollamaUrl;

    @Value("${ollama.model}")
    private String ollamaModel;

    public String interpret(String userInput){

        try
        {

            String finalPrompt = systemPrompt + "\nUser: " + userInput;

            OllamaRequest body = new OllamaRequest(ollamaModel, finalPrompt, false);

            String jsonBody = om.writeValueAsString(body);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaUrl + "/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200){
                throw new RuntimeException("API error: " + res.statusCode() + res.body());
            }

            OllamaResponse ollamaResponse = om.readValue(res.body(), OllamaResponse.class);

            return ollamaResponse.getResponse();

        } catch (Exception e)
        {
            throw new RuntimeException("Failed to call API: " + e.getMessage());
        }

    }

}
