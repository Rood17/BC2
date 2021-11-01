package com.mobileapp.bouldercorpregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobileapp.bouldercorpregistration.utils.CreateTextLayout;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;

public class Final extends AppCompatActivity {

    private LinearLayout goodbyeTxtLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //ly
        goodbyeTxtLy = findViewById(R.id.goodbye_txt);

        //set goodbye text
        ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        CreateTextLayout text = new CreateTextLayout(this);
        //text.createParagraph(goodbyeTxtLy, "normal", paramsExample, this.getResources().getString(R.string.goodbye_txt), "center");

    }

    public void finishReg(View v){
        Intent finishRegistration = new Intent();
        finishRegistration.setClass(this, MainActivity.class);

        //startActivity(finishRegistration);
        //Limpiar array CdT - 8888
        if ( FormValidations.getMinorsArray() != null)
            FormValidations.getMinorsArray().clear();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

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