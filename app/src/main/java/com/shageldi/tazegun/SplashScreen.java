package com.shageldi.tazegun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    private static  int SPLASH_SCREEN = 5000;
    AnimationDrawable anim;
    TextView name;
    int san=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);

//        anim=(AnimationDrawable)logo.getBackground();
//        anim.start();
        logo.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoom_out));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {





                //  logo.setImageResource(R.drawable.logo_two);





            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();





            }
        }, SPLASH_SCREEN);





        final TypeWriter tw = (TypeWriter) findViewById(R.id.tv);
        tw.setText("");
        tw.setCharacterDelay(150);
        tw.animateText("Täze Günüň Lezzeti");
    }
}