package com.mosaedb.refactoringtomvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageButton imageButton;
    ReposAdapter adapter;
    ListView listView;
    TextView textNoDataFound;
    GitHubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageButton = findViewById(R.id.imageButton);
        textNoDataFound = findViewById(R.id.text_no_data_found);
        listView = findViewById(R.id.listView);

        // Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GitHubService.class);

        adapter = new ReposAdapter(this);

        listView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String githubUsername = getUserInput().trim().replaceAll("\\s+", "");
        // Just call the method on the GitHubService
        service.listRepos(githubUsername)
                // enqueue runs the request on a separate thread
                .enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> repos) {
                        updateUi(repos.body());
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void updateUi(List<Repo> repos) {
        if (repos == null || repos.isEmpty()) {
            textNoDataFound.setVisibility(View.VISIBLE);
        } else {
            textNoDataFound.setVisibility(View.GONE);
        }

        adapter.clear();
        if (repos != null) {
            adapter.addAll(repos);
        }
    }

    private String getUserInput() {
        return editText.getText().toString();
    }
}