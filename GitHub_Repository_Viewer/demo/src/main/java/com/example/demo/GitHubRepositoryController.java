package com.example.demo;

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

    public GitHubRepositoryController(GitHubRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRepositories(
            @PathVariable String username,
            @RequestHeader("Accept") String acceptHeader
    ) {
        ResponseEntity<String> response = repositoryService.getRepositories(username, acceptHeader);

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response.getBody());
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("status : 404, Message: User not found.");
        } else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("status: 406, Message: Not acceptable.");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
