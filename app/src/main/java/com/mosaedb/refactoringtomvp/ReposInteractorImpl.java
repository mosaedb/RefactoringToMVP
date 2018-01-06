package com.mosaedb.refactoringtomvp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReposInteractorImpl implements ReposInteractor {

    private GitHubService service;

    public ReposInteractorImpl() {
        // Configure Retrofit
        // (A proper way of doing this would be via Dependency Injection)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);
    }

    @Override
    public Call<List<Repo>> listRepos(String githubUsername) {
        return service.listRepos(githubUsername);
    }
}
