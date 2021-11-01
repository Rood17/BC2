package com.mobileapp.bouldercorpregistration.dao;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;

import java.util.HashMap;

public class RegistrationFormDao {

    public static void setRegistrationData(EditText name, EditText lastName, EditText email, TextView date, EditText country,
                                           EditText personResp, EditText personRespPhone, EditText personResp2, EditText personRespPhone2){

        FormValidations.setName(name);
        FormValidations.setLastName(lastName);
        FormValidations.setEmail(email);
        FormValidations.setDate(date);
        FormValidations.setCountry(country);
        FormValidations.setRespName1(personResp);
        FormValidations.setRespPhone1(personRespPhone);
        FormValidations.setRespName2(personResp2);
        FormValidations.setRespPhone2(personRespPhone2);

    }
    public static void setRegistrationLayoutData(TextInputLayout name_ly, TextInputLayout lastName_ly, TextInputLayout mail_ly
    , TextInputLayout comment_ly, TextInputLayout respLy, TextInputLayout respPhoneLy){

        FormValidations.setNameLy(name_ly);
        FormValidations.setLastNameLy(lastName_ly);
        FormValidations.setEmailLy(mail_ly);
        FormValidations.setCountryLy(comment_ly);
        FormValidations.setPersonRespLy(respLy);
        FormValidations.setPersonRespPhoneLy(respPhoneLy);

    }
}
