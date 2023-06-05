package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GitHubRepositoryController {
    private final GitHubRepositoryService repositoryService;

    @Autowired
    public GitHubRepositoryController(GitHubRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRepositories(
            @PathVariable String username,
            @RequestHeader("Accept") String acceptHeader
    ) {
        ResponseEntity<String> response = repositoryService.getRepositories(username, acceptHeader);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>("{\"status\": 404, \"Message\": \"User not found.\"}",
                    response.getHeaders(), HttpStatus.NOT_FOUND);
        } else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
            return new ResponseEntity<>("{\"status\": 406, \"Message\": \"Not acceptable.\"}",
                    response.getHeaders(), HttpStatus.NOT_ACCEPTABLE);
        }

        return response;
    }
}
