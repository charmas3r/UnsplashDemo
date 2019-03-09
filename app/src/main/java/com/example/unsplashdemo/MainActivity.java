package com.example.unsplashdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button searchBtn, randomBtn, dynamicBtn;
    private ImageView unsplashIv;
    private TextView dynamicTv;
    private static Photo photoUrl;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.mainBackground);

        unsplashIv = findViewById(R.id.unsplashIv);
        dynamicTv = findViewById(R.id.dynamicTv);

        searchBtn = findViewById(R.id.btn1);
        randomBtn = findViewById(R.id.btn2);
        dynamicBtn = findViewById(R.id.btn3);

        searchBtn.setOnClickListener(this);
        randomBtn.setOnClickListener(this);
        dynamicBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn1:
                Intent intent = new Intent(this, UnsplashActivity.class);
                startActivity(intent);
                break;

            case R.id.btn2:
                String CLIENT_ID = "33f7e73196bbdd863240a46ff2aabdd01ecdc8101b62a679185ab5182474c712";
                Unsplash unsplash;
                unsplash = new Unsplash(CLIENT_ID);

                unsplash.getRandomPhoto(null, true, null, null, null,null, null, new Unsplash.OnPhotoLoadedListener() {
                    @Override
                    public void onComplete(Photo photo) {
                        photoUrl = photo;
                        Picasso.get().load(photo.getUrls().getSmall()).into(unsplashIv);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

                break;

            case R.id.btn3:

                Bitmap bitmap = ((BitmapDrawable)unsplashIv.getDrawable()).getBitmap();

                Palette.from(bitmap)
                        .generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                if (textSwatch == null) {
                                    Toast.makeText(MainActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                ll.setBackgroundColor(textSwatch.getRgb());
                                dynamicTv.setTextColor(textSwatch.getTitleTextColor());
                                //bodyColorText.setTextColor(textSwatch.getBodyTextColor());
                            }
                        });

                break;
        }

    }

}
