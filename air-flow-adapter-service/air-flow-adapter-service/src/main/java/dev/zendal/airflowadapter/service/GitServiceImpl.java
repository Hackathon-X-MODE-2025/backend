package dev.zendal.airflowadapter.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class GitServiceImpl implements GitService {
    @Override
    public Git getGit() {
        final File dir;
        try {
            dir = Files.createTempDirectory("dag").toFile();
            CredentialsProvider cp =  new UsernamePasswordCredentialsProvider("token", System.getenv("DAGS_REPO_ACCESS_TOKEN"));
            CredentialsProvider.setDefault(cp);
            return Git.cloneRepository()
                    .setURI("https://gitlab.zendal.dev/hack-2025/backend/dags.git")
                    .setCredentialsProvider(
                            new UsernamePasswordCredentialsProvider("token", System.getenv("DAGS_REPO_ACCESS_TOKEN"))
                    )
                    .setDirectory(dir)
                    .setBranch("main")
                    .call();
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
