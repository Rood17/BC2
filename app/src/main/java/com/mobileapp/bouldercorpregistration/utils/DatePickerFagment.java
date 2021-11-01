package com.mobileapp.bouldercorpregistration.utils;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.mobileapp.bouldercorpregistration.R;
import com.mobileapp.bouldercorpregistration.RegistrationForm;

import java.util.Calendar;

public class DatePickerFagment  {

    private static int Day, Month, Year;
    private static Resources Resources;

    public static void passResources(Resources resources){
        Resources = resources;
    }

    public static void datePickerDialog(View v) {
        DatePickerFagment newFragment = new DatePickerFagment();
        //newFragment.show(getSupportFragmentManager(), "date");

        TextView date = FormValidations.getDate();

        final Calendar cldr = Calendar.getInstance();
        Day = cldr.get(Calendar.DAY_OF_MONTH);
        Month = cldr.get(Calendar.MONTH);
        Year = cldr.get(Calendar.YEAR);
        String of = Resources.getString(R.string.of_string);

        date.setInputType(InputType.TYPE_NULL);

        final int[] year1 = {0};

        DatePickerDialog dialog = new DatePickerDialog(v.getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //Todo your work here
                FormValidations.setDateString(day + " " + of + " " + getMonthName(month) + " " + of + " " + year );
                date.setText(FormValidations.getDateString());


                //Remove Error
                if (date != null){
                    date.setError(null);
                    RegistrationForm.btnChangeColor(true, "birth");
                }

            }

            //Default date
        }, 1980, Month, Day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();

    }

    public static String getMonthName(int month){
        String setMonth;

        switch (month){
            case 1: setMonth = Resources.getString(R.string.M01);
                break;
            case 2: setMonth = Resources.getString(R.string.M02);
                break;
            case 3: setMonth = Resources.getString(R.string.M03);
                break;
            case 4: setMonth = Resources.getString(R.string.M04);
                break;
            case 5: setMonth = Resources.getString(R.string.M05);
                break;
            case 6: setMonth = Resources.getString(R.string.M06);
                break;
            case 7: setMonth = Resources.getString(R.string.M07);
                break;
            case 8: setMonth = Resources.getString(R.string.M08);
                break;
            case 9: setMonth = Resources.getString(R.string.M09);
                break;
            case 10: setMonth = Resources.getString(R.string.M10);
                break;
            case 11: setMonth = Resources.getString(R.string.M11);
                break;
            case 12: setMonth = Resources.getString(R.string.M12);
                break;
            default: setMonth = "Mes Invalido";
                break;
        }

        return setMonth;
    }




}