package com.mobileapp.bouldercorpregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobileapp.bouldercorpregistration.utils.CreateTextLayout;

import java.util.ArrayList;

public class DataNotice extends AppCompatActivity {

    private Context context;
    private LinearLayout DataLy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_data_notice);

        //Set Data
        DataLy = (LinearLayout) findViewById(R.id.data_notice);
        setDataNotice();

        //Close the window
        FloatingActionButton cerrar = (FloatingActionButton) findViewById(R.id.closeDataNot);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

    }

    //Set data notice
    public void setDataNotice(){
        //Create a text
        JustifiedTextView txtJustify = new JustifiedTextView(context);

        ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        String[] data_part_1;
        data_part_1 = this.getResources().getStringArray(R.array.aviso_datos);

        /**if ( MainActivity.isInEnglish){
            data_part_1 = this.getResources().getStringArray(R.array.aviso_datos_en);
        } else {

        }**/



        ArrayList<String[]> fullDataNotice = new <String[]> ArrayList();
        fullDataNotice.add(data_part_1);

        CreateTextLayout newTxt = new CreateTextLayout(this);
        newTxt.createParagraphFromArray(DataLy, paramsExample, fullDataNotice,"");



        /**
         * //Iterators
         *         int i = 0;
         *         final int titleIndex = 1;
        for ( String[] itemArray : fullDataNotice) {
            //Print
            for (String item : itemArray) {
                i++;
                CreateTextLayout newTxt = new CreateTextLayout(this);
                newTxt.createParagraph(DataLy, paramsExample, item,"");
            }
        }**/

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