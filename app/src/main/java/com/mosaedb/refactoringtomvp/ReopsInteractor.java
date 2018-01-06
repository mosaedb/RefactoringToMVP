package com.mosaedb.refactoringtomvp;

import java.util.List;

import retrofit2.Call;

public interface ReopsInteractor {
    Call<List<Repo>> listRepos(String githubUsername);
}
