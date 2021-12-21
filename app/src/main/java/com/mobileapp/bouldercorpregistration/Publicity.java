package com.mobileapp.bouldercorpregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.codesgood.views.JustifiedTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.pdf.TextField;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Publicity extends AppCompatActivity {

    TextInputLayout til_conocer;
    AutoCompleteTextView act_conocer;
    String conocer_answer;
    TextInputLayout otherField;
    LinearLayout p_publi;
    Boolean otherFieldFlag = false;
    EditText otherFieldText;
    ArrayList<String> mediosArray;
    CheckBox checkFb, checkInsta, checkRef, checkAnuncioCalle;

    ArrayList<String> arraylist_publi;
    ArrayAdapter<String> arrayadapter_publi;
    public static ProgressDialog dialogForm;
    public static String errorMessage;
    private static Button btnContinue;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);

        btnContinue = (Button) findViewById(R.id.ActionButtonContinue);

        til_conocer = (TextInputLayout)findViewById(R.id.til_conocer_redes);
        act_conocer = (AutoCompleteTextView)findViewById(R.id.act_conocer_redes);

        otherField = (TextInputLayout) findViewById(R.id.publi_otro);
        otherFieldText = (EditText) findViewById(R.id.publi_otro_txt);
        p_publi = (LinearLayout) findViewById(R.id.p_publi);

        checkFb = (CheckBox) findViewById(R.id.checkFb);
        checkInsta = (CheckBox) findViewById(R.id.checkInsta);
        checkRef = (CheckBox) findViewById(R.id.checkRef);
        checkAnuncioCalle = (CheckBox) findViewById(R.id.checkAnuncioCalle);

        mediosArray = new ArrayList<String>();


        // Set NONVisibility of Other
        otherField.setVisibility(View.GONE);

        // Set question list
        arraylist_publi = new ArrayList<>();
        arraylist_publi.add("Facebook");
        arraylist_publi.add("Instagram");
        arraylist_publi.add(this.getResources().getString(R.string.publi_calle));
        arraylist_publi.add(this.getResources().getString(R.string.publi_refer));
        arraylist_publi.add(this.getResources().getString(R.string.publi_pasando));
        arraylist_publi.add(this.getResources().getString(R.string.publi_otro));

        // Adapat spinner
        arrayadapter_publi = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arraylist_publi);
        act_conocer.setAdapter(arrayadapter_publi);
        // Set Styles
        act_conocer.setThreshold(1);
        act_conocer.setPadding(25, 25, 25, 25);

        // Set end paragraph
        setParagraph();


        // Get Answer
        act_conocer.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conocer_answer =  act_conocer.getText().toString();
                // Handler Input Text
                otherHandler(view);
            }
        });


        //go to next activity
        btnContinue.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //Go to next activity
            continueToDoc(view);
            }
        });

    }

    public void onCheckboxPublicityClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkFb:
                if (checked)
                    mediosArray.add("Facebook");
                    // Put some meat on the sandwich
                else
                    quitItemArray(mediosArray, "Facebook");
                // Remove the meat}
                break;
            case R.id.checkInsta:
                if (checked)
                    mediosArray.add("Instagram");
                    // Put some meat on the sandwich
                else
                    quitItemArray(mediosArray, "Instagram");
                // I'm lactose intolerant
                break;
            case R.id.checkRef:
                if (checked)
                    mediosArray.add("Amigos o familiares");
                    // Put some meat on the sandwich
                else
                    quitItemArray(mediosArray, "Amigos o familiares");
                // I'm lactose intolerant
                break;
            case R.id.checkAnuncioCalle:
                if (checked)
                    mediosArray.add("Publi calle");
                    // Put some meat on the sandwich
                else
                    quitItemArray(mediosArray, "Publi calle");
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
    }

    public void quitItemArray (ArrayList array, String word) {

        for ( int i = 0; i < array.size(); i++ ) {
            if ( array.get(i).toString().indexOf(word) != -1 ) {
                array.set(i, "");
            }
        }
    }

    public void sendData () {

        // Set First Answer
        FormValidations.setPConocer(conocer_answer);

        if ( mediosArray.size() > 0)
            FormValidations.setMediosArray(mediosArray);

    }

    // Display Text Box
    public void otherHandler (View view){
        // Display TextInput
        if ( conocer_answer.indexOf("Otro") != -1 ) {
            otherField.setVisibility(View.VISIBLE);
            otherFieldFlag = true;
        } else {
            otherField.setVisibility(View.GONE);
            otherFieldFlag = false;
        }
    }

    public void setParagraph () {
        ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        String p_publi_txt = this.getResources().getString(R.string.publi_end_p);

        JustifiedTextView txtJustify = new JustifiedTextView(this);
        txtJustify.setText(Html.fromHtml("<b>" + p_publi_txt + "</b>"));
        txtJustify.setPadding(0,25,0,25);
        txtJustify.setLayoutParams(paramsExample);
        p_publi.addView(txtJustify,p_publi.getLayoutParams());
    }



    //Go to Forms
    public void continueToDoc(View view){

        dialogForm = ProgressDialog.show(this, "",
                getResources().getText(R.string.loading), true);

        dialogForm.show();
        String errorMessage = getResources().getString(R.string.error_valid_field);

        //RegistrationFormBean.setArrayEnfants();

        //Call The next activity
        Intent goToReviewDoc = new Intent();
        goToReviewDoc.setClass(this, ReviewDoc.class);

        if ( !FormValidations.isEmptyField(act_conocer, til_conocer) ) {
            if (otherFieldFlag) {
                if (!FormValidations.isEmptyField(otherFieldText, otherField))
                {
                    sendData();
                    startActivity(goToReviewDoc);
                }
            } else {
                sendData();
                startActivity(goToReviewDoc);

            }
            dialogForm.dismiss();

        } else {
            til_conocer.setError(errorMessage);
            dialogForm.dismiss();
            btnContinue.setTextColor(Color.RED);
            //btnContinue.setBackgroundColor(Color.rgb(213, 212, 212));
            Toast.makeText(this, R.string.form_error_correct, Toast.LENGTH_LONG).show();
        }

    }
}