package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubRepositoryService {
    private static final String BASE_URL = "https://api.github.com";
    private static final String USER_AGENT = "MyGitHubApp";

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    public GitHubRepositoryService() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public ResponseEntity<String> getRepositories(String username, String acceptHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT);
        headers.set(HttpHeaders.ACCEPT, acceptHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_URL + "/users/" + username + "/repos?type=all";
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Check if the response is JSON
            if (MediaType.APPLICATION_JSON.isCompatibleWith(response.getHeaders().getContentType())) {
                // Format and display the JSON response
                String prettyJson = formatJson(response.getBody());
                System.out.println(prettyJson);
            }

            // Check if the response is XML
            if (MediaType.APPLICATION_XML.isCompatibleWith(response.getHeaders().getContentType())) {
                // Format and display the XML response
                String prettyXml = formatXml(response.getBody());
                System.out.println(prettyXml);
            }

            return response;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process the response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String formatJson(String json) throws JsonProcessingException {
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private String formatXml(String xml) throws JsonProcessingException {
        Object jsonObject = xmlMapper.readValue(xml, Object.class);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}
