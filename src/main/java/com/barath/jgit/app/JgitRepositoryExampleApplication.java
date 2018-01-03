package com.barath.jgit.app;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class JgitRepositoryExampleApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JgitRepositoryExampleApplication.class, args);
		CredentialsProvider allowHosts = new CredentialsProvider() {

			@Override
			public boolean supports(CredentialItem... items) {
				for(CredentialItem item : items) {
					if((item instanceof CredentialItem.YesNoType)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
				for(CredentialItem item : items) {
					if(item instanceof CredentialItem.YesNoType) {
						((CredentialItem.YesNoType)item).setValue(true);
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean isInteractive() {
				return false;
			}
		};
		Git.cloneRepository()
				.setURI("ssh://git@github.com/BarathArivazhagan/cucumber-spring-integration.git")
				.setDirectory(new File("/Users/barath/Downloads/jgit-repository-example/test"))
				.setCredentialsProvider(allowHosts)
				.call();
	}
}
