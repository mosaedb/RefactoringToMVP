package com.mosaedb.refactoringtomvp;

import java.util.List;

import retrofit2.Call;

public interface ReposInteractor {
    Call<List<Repo>> listRepos(String githubUsername);
}
