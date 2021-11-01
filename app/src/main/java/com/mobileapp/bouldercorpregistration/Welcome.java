package com.mobileapp.bouldercorpregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.codesgood.views.JustifiedTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mobileapp.bouldercorpregistration.utils.CreateTextLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Welcome extends AppCompatActivity {

    private CheckBox chPoliticas;
    private CheckBox chReglamento;
    private Boolean isChPoliticas = false;
    private Boolean isChReglamento = false;
    public static ProgressDialog dialogWelcome;
    private LinearLayout RecomenLy, WelcomeTxtLy;
    private Resources Res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        //RES
        Res = this.getResources();

        chPoliticas = findViewById(R.id.checkPoliticas);
        chReglamento = findViewById(R.id.checkReglamento);

        //Layouts
        RecomenLy = (LinearLayout) findViewById(R.id.recomendations_ly);
        WelcomeTxtLy = (LinearLayout) findViewById(R.id.welcome_txt);
        loadRecomendations();

        findViewById(R.id.ActionButtonContinue).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Call The next activityx
                dialogWelcome = ProgressDialog.show(Welcome.this, "",
                        getResources().getText(R.string.loading), true);

                dialogWelcome.show();

                //Go to Register
                Intent goToRegister = new Intent();
                goToRegister.setClass(view.getContext(), RegistrationForm.class);

                if ( isChPoliticas && isChReglamento){

                    startActivity(goToRegister);
                    //dialogWelcome.dismiss();
                } else {
                    dialogWelcome.dismiss();
                    Toast.makeText(view.getContext(), R.string.obligatory_field, Toast.LENGTH_LONG).show();
                    if (!isChPoliticas)
                        chPoliticas.setError( getResources().getText(R.string.error_valid_field));
                    if(!isChReglamento)
                        chReglamento.setError(getResources().getText(R.string.error_valid_field));
                }

                //startActivity(goToRegister);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });
    }

    public void loadRecomendations(){


        final Typeface font = ResourcesCompat.getFont(this, R.font.signatrue);

        String[] recomen_part_1;
        String[] recomen_part_2;
        String[] recomen_part_3;

        /**if ( MainActivity.isInEnglish){
            recomen_part_1 = this.getResources().getStringArray(R.array.recomendaciones_en);
            recomen_part_2 = this.getResources().getStringArray(R.array.recomendaciones_caida_en);
            recomen_part_3 = this.getResources().getStringArray(R.array.objetos_perdidos_en);
        } else {
            recomen_part_1 = this.getResources().getStringArray(R.array.recomendaciones);
            recomen_part_2 = this.getResources().getStringArray(R.array.recomendaciones_caida);
            recomen_part_3 = this.getResources().getStringArray(R.array.objetos_perdidos);
        }**/
        recomen_part_1 = this.getResources().getStringArray(R.array.recomendaciones);
        recomen_part_2 = this.getResources().getStringArray(R.array.recomendaciones_caida);
        recomen_part_3 = this.getResources().getStringArray(R.array.objetos_perdidos);

        ArrayList<String[]> fullRecomendations = new <String[]> ArrayList();
        fullRecomendations.add(recomen_part_1);
        fullRecomendations.add(recomen_part_2);
        fullRecomendations.add(recomen_part_3);

        //Set Intro
        JustifiedTextView welcome = new JustifiedTextView(this);

        ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        /**welcome.setText(Html.fromHtml("" +  this.getResources().getString(R.string.txt_bienvenida)));
        welcome.setGravity(Gravity.START);
        //titles.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //Finish txt titles
        welcome.setLayoutParams(paramsExample);
        welcome.setPadding(0,30,0,15);
        WelcomeTxtLy.addView(welcome,RecomenLy.getLayoutParams());**/

        CreateTextLayout newText = new CreateTextLayout(this);
        newText.createParagraph(WelcomeTxtLy,"normal", paramsExample, this.getResources().getString(R.string.txt_bienvenida), "center");



        //Iterators
        int i = 0;
        final int titleIndex = 1;

        for ( String[] itemArray : fullRecomendations) {


            //Print
            for (String item : itemArray) {
                i++;
                try {
                    JustifiedTextView txtJustify = new JustifiedTextView(this);
                    TextView titles = new TextView(this);


                    if ( i == titleIndex) {
                        titles.setText(Html.fromHtml("<b>" + item + "</b>"));
                        titles.setGravity(Gravity.START);
                        //titles.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        //Finish txt titles
                        titles.setLayoutParams(paramsExample);
                        titles.setPadding(0,20,0,0);

                        View line = new View(this);
                        line.setLayoutParams(paramsExample);
                        line.setBackgroundColor(Color.BLACK);
                        line.setMinimumHeight(1);
                        //line.setPadding(0,2,0,20);

                        line.setLayoutParams(paramsExample);


                        RecomenLy.addView(titles,RecomenLy.getLayoutParams());
                        RecomenLy.addView(line,RecomenLy.getLayoutParams());
                    } else {
                        txtJustify.setText(Html.fromHtml("<b>" + item + "</b>"));
                        //txtJustify.setGravity(Gravity.);
                        //txtJustify.setTextAlignment(View.);
                        //Finish txt titles
                        txtJustify.setTextSize(14);
                        //txtJustify.setTypeface(font);
                        //el primer texto  20
                        if ( i == 2)
                            txtJustify.setPadding(0,25,0,0);
                        else if ( i == itemArray.length)
                            txtJustify.setPadding(0,0,0,25);
                        else
                            txtJustify.setPadding(0,0,0,15 );


                        //txtJustify.setPadding(0,0,0,5);
                        txtJustify.setLayoutParams(paramsExample);
                        RecomenLy.addView(txtJustify,RecomenLy.getLayoutParams());
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }


            }
            //clear iterator
            i=0;

        }
        //Final line
        View line2 = new View(this);

        line2.setLayoutParams(paramsExample);
        line2.setBackgroundColor(Color.BLACK);
        line2.setMinimumHeight(1);
        line2.setLayoutParams(paramsExample);
        //RecomenLy.addView(line2,RecomenLy.getLayoutParams());


    }

    public void goToPoliticas(View view){

        //Go to Register
        Intent goToPoliticas = new Intent();
        goToPoliticas.setClass(view.getContext(), DataNotice.class);

        startActivity(goToPoliticas);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkPoliticas:
                if (checked)
                    isChPoliticas = true;
                // Put some meat on the sandwich
                else
                    isChPoliticas = false;
                // Remove the meat}
                break;
            case R.id.checkReglamento:
                if (checked)
                    isChReglamento = true;
                // Cheese me
                else
                    isChReglamento = false;
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
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