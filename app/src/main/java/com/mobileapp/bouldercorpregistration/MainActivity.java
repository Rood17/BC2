package com.mobileapp.bouldercorpregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobileapp.bouldercorpregistration.utils.LocaleHelper;

public class MainActivity extends AppCompatActivity {

    private final String mLanguageCodeEn = "en";
    private final String mLanguageCodeEs = "es";
    public static Boolean isInEnglish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change Language in Runtime
        //French
        /**
         findViewById(R.id.btn_fr).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        System.out.println("Jaaaa francès en teorìa perro");
        LocaleHelper.setLocale(MainActivity.this, "fr");
        recreate();
        }
        });**/

        //Buttons
        TextView enBtnTxt = (TextView) findViewById(R.id.en_btn_txt);
        TextView esBtnTxt = (TextView) findViewById(R.id.es_btn_txt);


        //English
        findViewById(R.id.btn_en).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enBtnTxt.setTextColor(Color.parseColor("#ff0000"));
                esBtnTxt.setTextColor(Color.WHITE);
                LocaleHelper.setLocale(MainActivity.this, mLanguageCodeEn);
                recreate();

                //setGlobal
                isInEnglish = true;

            }
        });
        //Spanish
        findViewById(R.id.btn_es).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esBtnTxt.setTextColor(Color.parseColor("#ff0000"));
                enBtnTxt.setTextColor(Color.WHITE);
                LocaleHelper.setLocale(MainActivity.this, mLanguageCodeEs);
                recreate();

            }
        });

    }

    //posiblemetne fuera
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(255, 187, 115));


        paint.setTextSize(10);
        //paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getColor();

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    //Go to Forms
    public void goToWelcome(View v){

        //Call The next activityx
        Intent goToWelcome = new Intent();
        goToWelcome.setClass(this, Welcome.class);

        startActivity(goToWelcome);

    }


    //Go to Forms
    public void dataNotice(View v){

        //Call The next activity
        Intent goToDataNotice = new Intent();
        goToDataNotice.setClass(this, DataNotice.class);

        startActivity(goToDataNotice);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        RelativeLayout mainImage = (RelativeLayout) findViewById(R.id.main_layout);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mainImage.setBackground(getResources().getDrawable(R.drawable.main_image));
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mainImage.setBackground(getResources().getDrawable(R.drawable.background1));
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



    //Hide navs
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}