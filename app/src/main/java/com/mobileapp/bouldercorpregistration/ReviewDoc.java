package com.mobileapp.bouldercorpregistration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.itextpdf.text.DocumentException;
import com.mobileapp.bouldercorpregistration.utils.CreatePDF;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;
import com.mobileapp.bouldercorpregistration.utils.LocaleHelper;
import com.mobileapp.bouldercorpregistration.utils.SignDialogFragment;
import com.mobileapp.bouldercorpregistration.utils.TemplatePDF;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class  ReviewDoc extends AppCompatActivity {


    //private LinearLayout lnLayout;
    //private RelativeLayout lnLayout;
    private Boolean readyToSign = false;
    private static ImageView imgSign;
    private LinearLayout contractLayout;
    private LinearLayout signLayout;
    private LinearLayout DigitalResLayout;
    private Boolean finish = false;
    private static TextView SignTxt;
    private static TextView signTxt2;
    private static CreatePDF printPdf;
    private static Button continueFinal;
    private static SignDialogFragment newFragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_doc);



        //prepare for contract print
        contractLayout = findViewById(R.id.contract);
        signLayout = findViewById(R.id.contractSign);
        DigitalResLayout = findViewById(R.id.contractDigitalResp);

        //Buttons
        continueFinal = (Button) findViewById(R.id.signBtn);


        //txt
        SignTxt = (TextView) findViewById(R.id.signTxt);
        signTxt2 = (TextView) findViewById(R.id.signTxt2);


        //Set iamge Sing
        imgSign = (ImageView) findViewById(R.id.imgSign);

        //Quit continue btn and txt
        continueFinal.setVisibility(View.GONE);
        signTxt2.setVisibility(View.GONE);

        //test
        try {
            createContract();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        RegistrationForm.dialogForm.dismiss();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createContract() throws DocumentException {
        readyToSign = false;
        //create solo al firmar
        printPdf = new CreatePDF(this.getApplicationContext(), contractLayout, signLayout, DigitalResLayout, readyToSign);

    }

    public void showSignPad(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new SignDialogFragment();

        if (true) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setImage(){
        imgSign.setImageBitmap(SignDialogFragment.getBitmapImage());

        //Quit the sign btn txt
        SignTxt.setVisibility(View.GONE);

        //Print las part
        printPdf.printDigitalResponsive();

        //Set continue btn and txt
        continueFinal.setVisibility(View.VISIBLE);
        signTxt2.setVisibility(View.VISIBLE);

        //quit dialog
        newFragment.dismiss();

    }


        
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signDoc(View v) throws DocumentException {
        //Crear Pdf
        new CreatePDF(this.getApplicationContext(), contractLayout, signLayout, DigitalResLayout, true);

        //If Finish
        if ( CreatePDF.statusPdf()){
            FormValidations.setRegisterCount();
            goToFinish();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToFinish(){
        //Call The next activity



        BackgroundMail.newBuilder(this)
                .withUsername("bc.regitro.backup@gmail.com")
                .withPassword("bcbackup2021")
                // bc.registro593
                .withMailto("bc.registro.backup@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Responsiva-" + FormValidations.getCompleteNameString())
                .withBody(String.valueOf(setBody()))
                .withAttachments(String.valueOf(TemplatePDF.pdfFile))
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();



        Intent goToFinish = new Intent();
        goToFinish.setClass(this.getApplicationContext(), Final.class);

        startActivity(goToFinish);
        //Limpiar array CdT - 8888
        if ( FormValidations.getMinorsArray() != null)
            FormValidations.getMinorsArray().clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String setBody(){

        String body;

        body = String.valueOf(Html.fromHtml("<p>" + "Se ha llevado acabo un registro con éxito.</p><p></p>" +

                "<h1>DATOS DEL CLIENTE</h1>" +
                "<ul>" +
                "<li>Nombre : " + FormValidations.getName().getText().toString()+ " </li>" +
                "<li>Apellidos : " + FormValidations.getLastName().getText().toString()+ " </li>" +
                "<li>Email : " + FormValidations.getEmail().getText().toString()+ " </li>" +
                "<li>Edad : " + FormValidations.getAge() + " </li>" +
                "<li>Fecha del registro : " + LocalDateTime.now() + "</li>"+
                "</ul>"));

        body += String.valueOf(Html.fromHtml("<br><br>" +

                "<h1>DATOS ENCUESTA</h1>" +
                "<p>Primer Contacto : " + FormValidations.getPConocer() + "</p>" ));
        if ( FormValidations.getMediosArray().size() > 0 ) {
            body += String.valueOf(Html.fromHtml("<br><p>Medios en que nos ha visto:</p><ul>"));
            for (int i = 0; i < FormValidations.getMediosArray().size(); i++) {
                body += String.valueOf(Html.fromHtml(
                        "<li> - " + FormValidations.getMediosArray().get(i) + "</li>"
                ));
            }
            body += String.valueOf(Html.fromHtml("</ul>"));
        }

        if ( FormValidations.getMinorsArray() != null){
            body += String.valueOf(Html.fromHtml("<br><br>"+
                "<h2> REGISTRO DE MENORES</h2>" +
                "<p>Registrados : " + FormValidations.getMinorsArray().size() + "</p>" +
                "<ul>"));
                for ( EditText minor : FormValidations.getMinorsArray()){
                    String minorName = minor.getText().toString();
                    body += String.valueOf(Html.fromHtml(
                                    "<li> - " + minorName + "</li>"
                    ));
                }
                body += String.valueOf(Html.fromHtml("</ul>"));
        }
        body += String.valueOf(Html.fromHtml("<br><br><p>Boulder Corp APP</p><p> Saludos y ¡buena vibra!</p>"));

        return body;
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