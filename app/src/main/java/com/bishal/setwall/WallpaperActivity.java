package com.bishal.setwall;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {
    private ImageView wallpaperIV;
    private Button setWallpaperBtn;
    private Button setWallpaperBtn2;
    private Button setWallpaperBtn3;
    private String imgUrl;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        wallpaperIV = findViewById(R.id.idIVWallpaper);
        setWallpaperBtn = findViewById(R.id.idBtnSetWallpaperHomeScreen);
        setWallpaperBtn2 = findViewById(R.id.idBtnSetWallpaperBoth);
        setWallpaperBtn3 = findViewById(R.id.idBtnSetWallpaperLockScreen);
        imgUrl = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(wallpaperIV);
        wallpaperManager= WallpaperManager.getInstance(getApplicationContext());
        setWallpaperBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Failed To Load Image...." ,Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);//for HomeScreen
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Failed To Set Wallpaper...." ,Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this,"Wallpaper Set To Homescreen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });
        setWallpaperBtn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Failed To Load Image...." ,Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);//for HomeScreen
                            wallpaperManager.setBitmap(resource,null,true,WallpaperManager.FLAG_LOCK);//for LockScreen
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Failed To Set Wallpaper...." ,Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this,"Wallpaper Set To Both Homescreen And Lockscreen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });
        setWallpaperBtn3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Failed To Load Image...." ,Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource,null,true,WallpaperManager.FLAG_LOCK);//for LockScreen
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Failed To Set Wallpaper...." ,Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this,"Wallpaper Set To Lockscreen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });



    }
}