package com.mobileapp.bouldercorpregistration.bean;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobileapp.bouldercorpregistration.R;
import com.mobileapp.bouldercorpregistration.RegistrationForm;
import com.mobileapp.bouldercorpregistration.utils.FormValidations;

import java.util.ArrayList;

public class RegistrationFormBean {

    public static ArrayList <EditText> minorsNameArray;
    private static Context Context;

    //Prepare the auto enfants
    public static void prepareEnfants(Context context, LinearLayout newEnfants_ly, String stringEnfants) {
        Context = context;
        minorsNameArray  = new <EditText> ArrayList();

        if ( stringEnfants == "-" || stringEnfants.indexOf("-") != -1 ){
            newEnfants_ly.removeAllViews();
            return;
        }

        int numEnfants = Integer.parseInt((String) stringEnfants);

        if ( numEnfants != 0 ){

            int x = numEnfants;
            if ( x > 9) {
                newEnfants_ly.removeAllViews();
                Toast.makeText(context, R.string.lessthan9, Toast.LENGTH_LONG).show();
            } else {
                for ( int i =0; i < x; i++){
                    /**
                     TextInputLayout enfantParent_ly = new TextInputLayout(this);
                     TextInputEditText enfantChild = new TextInputEditText(this);

                     //Setting ly params
                     enfantParent_ly.setBottom(16);
                     enfantParent_ly.setStartIconDrawable(R.drawable.person);
                     enfantParent_ly.setHint(getResources().getString(R.string.complete_name));
                     enfantParent_ly.setBoxStrokeColor(Color.WHITE);
                     enfantParent_ly.setBoxBackgroundColor(Color.RED);
                     enfantParent_ly.setPadding(16,16,16,16);
                     //set text params
                     enfantChild.setPadding(16,16,16,16);


                     LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams (
                     LinearLayout.LayoutParams.MATCH_PARENT,
                     LinearLayout.LayoutParams.WRAP_CONTENT
                     );
                     enfantParent_ly.addView(enfantChild, 0, paramsExample);
                     newEnfants_ly.addView(enfantParent_ly);
                     **/
                    LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams (
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    EditText newPrueba = new EditText(context);
                    newPrueba.setPadding(16,16,16,16);
                    newPrueba.setLayoutParams(paramsExample);
                    newPrueba.setHint(R.string.childs_names);
                    newPrueba.setTag(i);
                    newPrueba.setId(R.id.form_name);
                    newPrueba.setTextSize(15);
                    newPrueba.setCompoundDrawablesWithIntrinsicBounds(R.drawable.person, 0, 0, 0);
                    newEnfants_ly.addView(newPrueba);

                    //Create Array
                    minorsNameArray.add(newPrueba);
                }
                FormValidations.setMinorsArray(minorsNameArray);
            }
        }

    }

    public static void setArrayEnfants(){
        //if ( minorsNameArray != null)
            //FormValidations.minorNamesFieldsArray(minorsNameArray);
    }



}
