package com.mosaedb.refactoringtomvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ReposView {

    EditText editText;
    ImageButton imageButton;
    ReposAdapter adapter;
    ListView listView;
    TextView textNoDataFound;
    ReposPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        imageButton = findViewById(R.id.imageButton);
        textNoDataFound = findViewById(R.id.text_no_data_found);
        listView = findViewById(R.id.listView);

        adapter = new ReposAdapter(this);

        listView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.performSearch(getUserInput());
            }
        });

        // Note: Don't do this on production code, use Dependency Injection instead
        // to provide the BooksInteractor and the BooksPresenter to the View
        // Learn how to use Dagger 2 here:
        // https://medium.com/@Miqubel/understanding-dagger-2-367ff1bd184f#.s2jza32df
        ReposInteractor interactor = new ReposInteractorImpl();
        presenter = new ReposPresenter(interactor);
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void updateUi(List<Repo> repos) {
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