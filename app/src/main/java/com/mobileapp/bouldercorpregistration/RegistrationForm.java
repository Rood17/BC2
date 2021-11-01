package com.mobileapp.bouldercorpregistration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.mobileapp.bouldercorpregistration.bean.RegistrationFormBean;
import com.mobileapp.bouldercorpregistration.dao.RegistrationFormDao;
import com.mobileapp.bouldercorpregistration.utils.CreateTextLayout;
import com.mobileapp.bouldercorpregistration.utils.DatePickerFagment;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class RegistrationForm extends AppCompatActivity {

    private TextInputLayout name_ly, lastName_ly, mail_ly, country_ly, personResp_ly, personRespPhone_ly;
    private LinearLayout newEnfants_ly, num_childs_form_ly, title_1, title_2, title_3;

    private EditText name, lastName, email, country, personResp, personRespPhone, personResp2, personRespPhone2;
    private static TextView formText_1, date, num_childs_form_txt;
    public static String errorMessage;

    private static Button btnContinue, birthBtn;
    private SwitchMaterial form_switch;
    private Spinner numEnfants;
    private static Context context;


    private Boolean second_selection = false;
    private Boolean havePermissions=false;

    public static ProgressDialog dialogForm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);


        //Pass Context
        context = this.getApplicationContext();

        //Set Error Msg
        errorMessage = getResources().getString(R.string.error_valid_field);

        //Layouts
        name_ly = findViewById(R.id.form_name_ly);
        lastName_ly = findViewById(R.id.form_last_name_ly);
        mail_ly = findViewById(R.id.form_mail_ly);
        //country_ly = findViewById(R.id.form_country_ly);
        newEnfants_ly = findViewById(R.id.new_childs_form_ly);
        personResp_ly = findViewById(R.id.form_pers_resp);
        personRespPhone_ly = findViewById(R.id.form_phone_resp);
        //to set text
        title_1 = findViewById(R.id.form_title_1);
        title_2 = findViewById(R.id.form_title_2);
        title_3 = findViewById(R.id.form_title_3);



        //Material EditText
        name = (EditText) findViewById(R.id.form_name);
        lastName = (EditText) findViewById(R.id.form_last_name);
        email = (EditText) findViewById(R.id.form_mail);
        date = (TextView) findViewById(R.id.datePicker);
        //country = (EditText) findViewById(R.id.form_country);
        personResp = (EditText) findViewById(R.id.form_pers_resp_txt);
        personRespPhone = (EditText) findViewById(R.id.form_phone_resp_txt);
        personResp2 = (EditText) findViewById(R.id.form_pers_resp_txt_2);
        personRespPhone2 = (EditText) findViewById(R.id.form_phone_resp_txt_2);

        //Buttons
        birthBtn = (Button) findViewById(R.id.birth_date);
        btnContinue = (Button) findViewById(R.id.ActionButtonContinue);

        //Info Texts
        //formText_1 = (TextView) findViewById(R.id.form_txt_1);
        num_childs_form_txt = (TextView) findViewById(R.id.num_childs_form_txt);

        //Switch
        form_switch = (SwitchMaterial) findViewById(R.id.switch_form);
        //Num of Childs Resp
        numEnfants = (Spinner) findViewById(R.id.num_childs_form);


        //SET VISIBILITIES
        date.setVisibility(View.GONE);
        numEnfants.setVisibility(View.GONE);
        num_childs_form_txt.setVisibility(View.GONE);

        //Set Titles
        ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        CreateTextLayout setTexts = new CreateTextLayout(this);
        setTexts.createTtitle(title_1, paramsExample, this.getResources().getString(R.string.form_title_1));
        setTexts.createTtitle(title_2, paramsExample, this.getResources().getString(R.string.form_title_2));
        setTexts.createTtitle(title_3, paramsExample, this.getResources().getString(R.string.form_title_3));


        //Set Spinner
        setSpinnerChilds();

        //Date Picker
        DatePickerFagment.passResources(getResources());

        //Set FORMS (DAO)
        RegistrationFormDao.setRegistrationData(name, lastName, email, date, null, personResp, personRespPhone, personResp2, personRespPhone2);
        RegistrationFormDao.setRegistrationLayoutData(name_ly, lastName_ly, mail_ly, null, personResp_ly, personRespPhone_ly);

        //set autocomplete array
        //AutoCompleteTextView autocomplete = (AutoCompleteTextView) findViewById(R.id.form_country);
        //prepareAutocomplete(autocomplete);

        //setting context for validation form
        FormValidations.setEmail(email);
        FormValidations.setContext(this);




        //  *** LISTENERS ***

        //go to next activity
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Go to next activity
                if (checkPermissions())
                    continueToDoc(view);

            }
        });

        //Switch
        form_switch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(form_switch.isChecked()) {
                    numEnfants.setVisibility(View.VISIBLE);
                    num_childs_form_txt.setVisibility(View.VISIBLE);

                }else {
                    numEnfants.setVisibility(View.GONE);
                    num_childs_form_txt.setVisibility(View.GONE);
                    RegistrationFormBean.prepareEnfants(context, newEnfants_ly,"-");
                    numEnfants.setSelection(0);
                    //Limpiar array CdT - 8888
                    if ( FormValidations.getMinorsArray() != null)
                        FormValidations.getMinorsArray().clear();
                }
            }
        });


        //know num of enfants
        numEnfants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                RegistrationFormBean.prepareEnfants(context, newEnfants_ly,"-");
                RegistrationFormBean.prepareEnfants(context, newEnfants_ly, (String) numEnfants.getSelectedItem());

                //Fix Logic
                if ( second_selection && (numEnfants.getSelectedItem().toString().indexOf("-") != -1 )){
                    numEnfants.setVisibility(View.GONE);
                    num_childs_form_txt.setVisibility(View.GONE);
                    form_switch.setChecked(false);
                    //Limpiar array CdT - 8888
                    if ( FormValidations.getMinorsArray() != null)
                        FormValidations.getMinorsArray().clear();
                }


                second_selection = true;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //***********/
        Welcome.dialogWelcome.dismiss();


    }

    //Set spinenr data
    private void setSpinnerChilds(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.simple_numbers, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        numEnfants.setAdapter(adapter);
    }

    //Set Autocomplete
    private void prepareAutocomplete(AutoCompleteTextView autocomplete) {
        String[] arr = { "France", "México","Brazil",
                "Italy", "United States"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
    }

    //Go to Forms
    public void continueToDoc(View view){

        dialogForm = ProgressDialog.show(this, "",
                getResources().getText(R.string.loading), true);

        dialogForm.show();

        errorMessage = getResources().getString(R.string.error_valid_field);
        //RegistrationFormBean.setArrayEnfants();

        //Call The next activity
        Intent goToReviewDoc = new Intent();
        goToReviewDoc.setClass(this, ReviewDoc.class);

        if (FormValidations.validateForm(view) ) {
            startActivity(goToReviewDoc);
            //dialogForm.dismiss();
        } else {
            dialogForm.dismiss();
            btnContinue.setTextColor(Color.RED);
            FormValidations.formListeners(view, btnContinue);
            //btnContinue.setBackgroundColor(Color.rgb(213, 212, 212));
            Toast.makeText(this, R.string.form_error_correct, Toast.LENGTH_LONG).show();
        }

    }

    //Change btn color in validation
    public static void btnChangeColor(Boolean pass, String btn){
        if (pass && btn.indexOf("birth") != -1) {
            birthBtn.setTextColor(Color.WHITE);
            date.setVisibility(View.VISIBLE);
            return;
        }
        if (pass && btn.indexOf("continue") != -1){
            btnContinue.setTextColor(Color.WHITE);
            return;
        }
        if (pass && btn.indexOf("bouth") != -1){
            btnContinue.setTextColor(Color.WHITE);
            birthBtn.setTextColor(Color.WHITE);
            date.setVisibility(View.VISIBLE);
            return;
        }
        birthBtn.setTextColor(Color.RED);

    }


    //Set spinner datapicker
    public void showDatePickerDialog(View v) {
        DatePickerFagment.datePickerDialog(v);
    }



    //Hide navs **
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

    //Permissions Group
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkPermissions(){
        //Check for permissions
        if(ContextCompat.checkSelfPermission(context.getApplicationContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.d("TemplatePDF", "El almacenamiento externo esta disponible :)");
            havePermissions = true;
        } else {
            Log.e("TemplatePDF", "El almacenamiento externo no esta disponible :(");
            Log.e("TemplatePDF", "No hay permisos. Ejecutando solicitud de permisos...");
            getPermissions();
        }
        return havePermissions;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getPermissions(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //to simplify, call requestPermissions again
                Toast.makeText(this.getApplicationContext(),
                        "shouldShowRequestPermissionRationale",
                        Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }else{
            // permission granted
            havePermissions = true;
            Toast.makeText(this.getApplicationContext(),
                    "Gracias",
                    Toast.LENGTH_LONG).show();
            continueToDoc((View) getResources().getLayout(R.layout.activity_registration_form));
        }
    }

}