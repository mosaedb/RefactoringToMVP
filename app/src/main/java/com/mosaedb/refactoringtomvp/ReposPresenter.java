package com.mosaedb.refactoringtomvp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposPresenter {

    ReposView view;
    private ReposInteractor interactor;

    public ReposPresenter(ReposInteractor interactor) {
        this.interactor = interactor;
    }

    public void bind(ReposView view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public void performSearch(String userInput) {
        String githubUsername = userInput.trim().replaceAll("\\s+", "");
        // Just call the method on the GitHubService
        interactor.listRepos(githubUsername)
                // enqueue runs the request on a separate thread
                .enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> repos) {
                        if (view != null) {
                            view.updateUi(repos.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
