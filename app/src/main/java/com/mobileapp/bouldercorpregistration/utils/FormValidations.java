package com.mobileapp.bouldercorpregistration.utils;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.mobileapp.bouldercorpregistration.R;
import com.mobileapp.bouldercorpregistration.RegistrationForm;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class  FormValidations {

    private static String fieldIsRequired = RegistrationForm.errorMessage;
    private static EditText Name, LastName, Email, Country, RespName1, RespName2, RespPhone1, RespPhone2;
    private static TextInputLayout NameLy, LastName_ly, Mail_ly, Country_ly, Resp_Ly, RespPhone_Ly;
    private static ArrayList MinorsArray;
    private static TextView Date;
    private static String DateString;
    private static View view;
    private static Context context;
    private static int registerCount;


    //Obligatory Fields
    public static boolean validateForm(View v){

        view = v;
        boolean valid = true;

        if ( isEmptyField(getName(), getNameLy())){
            valid = false;
        }
        if ( isEmptyField(getLastName(), getLastNameLy())){
            valid = false;
        }
        if ( isEmptyField(getEmail(), getEmailLy())){
            valid = false;
        } else if (!emailVerifier(getEmail().getText().toString())) {
            valid = false;
        }
        if(isEmptyField(getDate())){
            valid = false;
        }
        /**if(isEmptyField(getCountry(), getCountryLy())){
            System.out.println("Esta Vacio getCountry 639");
            valid = false;
        }**/

        if ( valid ) {
            RegistrationForm.btnChangeColor(valid, "bouth");
        }

        //Resp
        if ( isEmptyField(getRespName1(), getPersonResLy())){
            valid = false;
        }


        if( isEmptyField(getRespPhone1(), getPersonRespPhoneLy()))
            valid = false;

        //Minors Array Validation
        if (getMinorsArray() != null ){
            if ( isEmptyminorArrayValidation() )
                valid = false;
        }



        return valid;
    }

    public static Boolean isEmptyminorArrayValidation(){
        Boolean result = false;
        ArrayList <EditText> minorArray = getMinorsArray();

        if ( minorArray != null) {
            for ( EditText field : minorArray){

                if ( isEmptyField(field) )
                    result = true;

            }
        }

        return result;
    }

    public static void minorNamesFieldsArray(ArrayList<String> minorsNameArray){

        ArrayList <EditText> minorsNamesArray = new ArrayList();

        int i=0;
        for ( String name : minorsNameArray){
            i++;
            EditText nameA = (EditText) view.findViewById(i);
            minorsNamesArray.add(nameA);
        }

        setMinorsArray(minorsNamesArray);
    }



    //Check if a field is empty
    public static boolean isEmptyField(EditText field){
        String data = field.getText().toString().trim();
        if(TextUtils.isEmpty(data)){
            field.setError(fieldIsRequired);

            return true;
        }
        else{
            //field.setBackgroundColor(Color.rgb(135, 254, 21));
            //validateForm(view, Name, LastName, Email, Country);
            return false;
        }
    }

    //Check if a field is empty
    public static boolean isEmptyField(EditText field, TextInputLayout fieldLy){
        String data = field.getText().toString().trim();


        if(TextUtils.isEmpty(data)){
            //field.setError(fieldIsRequired);
            fieldLy.setError(fieldIsRequired);

            return true;
        }
        else{
            fieldLy.setError(null);
            //field.setBackgroundColor(Color.rgb(135, 254, 21));
            //validateForm(view, Name, LastName, Email, Country);
            return false;
        }
    }


    //Check if a field is empty *** DATE
    public static boolean isEmptyField(TextView field){
        String data = field.getText().toString().trim();
        if(field.getText().length() == 0){

            //field.setError(fieldIsRequired);
            RegistrationForm.btnChangeColor(false, "");

            return true;
        }
        else{
            //field.setError(null);
            field.setVisibility(View.VISIBLE);
            //field.setBackgroundColor(Color.rgb(135, 254, 21));
            //validateForm(view, Name, LastName, Email, Country);
            RegistrationForm.btnChangeColor(true, "birth");
            return false;
        }
    }

    //Check is a valid email
    public static Boolean emailVerifier(String emailString){

        //Definir si es un email válido
        if ( emailString.contains("@") && emailString.contains(".com") )
            return true;

        Toast.makeText(getContext(), R.string.form_email_correct, Toast.LENGTH_LONG).show();
        getEmail().setError(fieldIsRequired);
        return false;
    }

    public static void dateFormat(Context context, String Date ){

        // listener y al escribir 2 datos poner in / y así

    }

    public static void formListeners(View view, Button btnContinue){

        //name
        getName().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateForm(v);
                }
            }
        });
        //lastname
        getLastName().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateForm(v);
                }
            }
        });
        //Country
        /**getCountry().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                validateForm(v);
            }
        });**/
        //email
        getEmail().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(!isEmptyField(getEmail()))
                        emailVerifier(getEmail().getText().toString());
                }
            }
        });

        //datepicker
        getDate().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validateForm(v);
                }
            }
        });

        //respName
        getRespName1().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                validateForm(v);
            }
        });
        //respPhone
        getRespPhone1().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                validateForm(v);
            }
        });
    }


    //GETTERS AND SETTERS

    public static EditText getName() {
        return Name;
    }

    public static void setName(EditText name) {
        Name = name;
    }

    public static EditText getLastName() {
        return LastName;
    }

    public static void setLastName(EditText lastName) {
        LastName = lastName;
    }

    public static TextView getDate() {
        return Date;
    }

    public static void setDate(TextView date) {
        Date = date;
    }

    public static EditText getCountry() {
        return Country;
    }

    public static void setCountry(EditText country) {
        Country = country;
    }

    public static EditText getEmail() {
        return Email;
    }

    public static void setEmail(EditText email) {
        Email = email;
    }


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        FormValidations.context = context;
    }

    public static String getDateString() {
        return DateString;
    }

    public static void setDateString(String dateString) {
        DateString = dateString;
    }

    public static String getCompleteNameString() { return getName().getText().toString() + "_" +
    getLastName().getText().toString();}

    //Layaouts
    public static TextInputLayout getNameLy() {
        return NameLy;
    }

    public static void setNameLy(TextInputLayout nameLy) {
        NameLy = nameLy;
    }

    public static TextInputLayout getLastNameLy() {
        return LastName_ly;
    }

    public static void setLastNameLy(TextInputLayout lastNameLy) {
        LastName_ly = lastNameLy;
    }

    public static TextInputLayout getCountryLy() {
        return Country_ly;
    }

    public static void setCountryLy(TextInputLayout countryLy) {
        Country_ly = countryLy;
    }

    public static TextInputLayout getEmailLy() {
        return Mail_ly;
    }

    public static void setEmailLy(TextInputLayout emailLy) {
        Mail_ly = emailLy;
    }

    public static TextInputLayout getPersonResLy() {
        return Resp_Ly;
    }

    public static void setPersonRespLy(TextInputLayout respLy) {
        Resp_Ly = respLy;
    }

    public static TextInputLayout getPersonRespPhoneLy() {
        return RespPhone_Ly;
    }

    public static void setPersonRespPhoneLy(TextInputLayout respPhoneLy) {
        RespPhone_Ly = respPhoneLy;
    }

    //Minors
    public static ArrayList<EditText> getMinorsArray() {
        return MinorsArray;
    }

    public static void setMinorsArray(ArrayList<EditText> minorsArray) {
        MinorsArray = minorsArray;
    }

    //RESP
    public static EditText getRespName1() {
        return RespName1;
    }

    public static void setRespName1(EditText respName1) {
        RespName1 = respName1;
    }

    public static EditText getRespPhone1() {
        return RespPhone1;
    }

    public static void setRespPhone1(EditText respPhone1) {
        RespPhone1 = respPhone1;
    }

    public static EditText getRespName2() {
        return RespName2;
    }

    public static void setRespName2(EditText respName2) {
        RespName2 = respName2;
    }

    public static EditText getRespPhone2() {
        return RespPhone2;
    }

    public static void setRespPhone2(EditText respPhone2) {
        RespPhone2 = respPhone2;
    }






    //Create hash map of registration data
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList getRegistrationDataArray(){
        ArrayList<String> regDataArray = new ArrayList<String>();

        regDataArray.add(" ");
        regDataArray.add(" DATOS DEL CLIENTE ");
        regDataArray.add(" ");
        regDataArray.add("Nombre : " + getName().getText().toString());
        regDataArray.add("Apellidos : " + getLastName().getText().toString());
        regDataArray.add("Edad : " + getAge());
        regDataArray.add("Fecha de Nacimiento : " + getDate().getText().toString());
        //regDataArray.add("País de Origen : " + getCountry().getText().toString());
        regDataArray.add("Email : " + getEmail().getText().toString());
        regDataArray.add(" ");
        regDataArray.add(" DATOS CONTACTO DE EMERGENCIA ");
        regDataArray.add(" ");
        regDataArray.add("1er Contacto : " + getRespName1().getText().toString());
        regDataArray.add("Teléfono : " + getRespPhone1().getText().toString());

        if ( getRespName2() != null ){
            if ( getRespName2().getText().toString() != ""){
                regDataArray.add(" ");
                regDataArray.add("2ndo Contacto : " + getRespName2().getText().toString());
                regDataArray.add("Teléfono : " + getRespPhone2().getText().toString());
            }
        }
        if ( getMinorsArray() != null){
            regDataArray.add(" ");
            regDataArray.add("MENORES DE EDAD REGISTRADOS : " + getMinorsArray().size());
            regDataArray.add(" ");
            int index1 = 0;
            for ( EditText field : getMinorsArray()){
                index1++;
                regDataArray.add(index1 + ".- Nombre : " + field.getText().toString());
            }
        }


        return regDataArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getAge(){
        int age;
        int dateYear = 0;

        if ( getDate().length() != 0)
            dateYear = Integer.parseInt(getDate().getText().toString().substring(getDate().getText().toString().length() - 4));
        age = LocalDateTime.now().getYear() - dateYear;

        return age;
    }

    public static void setRegisterCount(){
        registerCount ++;
    }
    public static int getRegisterCount(){
        return registerCount;
    }

}
