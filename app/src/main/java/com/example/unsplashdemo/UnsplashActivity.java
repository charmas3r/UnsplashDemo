package com.example.unsplashdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.List;

public class UnsplashActivity extends AppCompatActivity {

    private final String CLIENT_ID = "33f7e73196bbdd863240a46ff2aabdd01ecdc8101b62a679185ab5182474c712";
    private Unsplash unsplash;
    private PhotoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsplash);

        unsplash = new Unsplash(CLIENT_ID);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(UnsplashActivity.this, 2));

        adapter = new PhotoRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        unsplash.getPhotos(2, 20, Order.POPULAR, new Unsplash.OnPhotosLoadedListener() {
            @Override
            public void onComplete(List<Photo> photos) {
                adapter.setPhotos(photos);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void search(View view) {
        EditText editText = findViewById(R.id.editText);
        String query = editText.getText().toString();

        unsplash.searchPhotos(query,1,20,null, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                List<Photo> photos = results.getResults();
                adapter.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });


    }
}
