package com.mobileapp.bouldercorpregistration.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.codesgood.views.JustifiedTextView;
import com.mobileapp.bouldercorpregistration.R;

import java.util.ArrayList;

public class CreateTextLayout {

    private Context context;
    private int Iterator = -1;
    private int ArrayLength = -1;
    private Typeface font;

    public CreateTextLayout(Context context) {
        this.context = context;
        font = ResourcesCompat.getFont(context, R.font.poppins_light);
    }


    public void createTtitle(LinearLayout titleLy, ViewGroup.LayoutParams paramsExample, String item){

        try {
            //JustifiedTextView txtJustify = new JustifiedTextView(context);
            TextView titles = new TextView(context);

            titles.setText(Html.fromHtml("<b>" + item + "</b>"));
            titles.setGravity(Gravity.START);
            //titles.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //Finish txt titles
            titles.setLayoutParams(paramsExample);
            titles.setPadding(0,20,0,0);

            View line = new View(context);
            line.setLayoutParams(paramsExample);
            line.setBackgroundColor(Color.BLACK);
            line.setMinimumHeight(1);
            //line.setPadding(0,2,0,20);

            line.setLayoutParams(paramsExample);


            titleLy.addView(titles,titleLy.getLayoutParams());
            //titleLy.addView(line,titleLy.getLayoutParams());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createParagraph(LinearLayout textLy, String typeOfTxt, ViewGroup.LayoutParams paramsExample, String item, String gravity){

        try {

            JustifiedTextView txtJustify = new JustifiedTextView(context);
            TextView normalTxt = new TextView(context);

            if( typeOfTxt.contentEquals("normal")){

                normalTxt.setText(Html.fromHtml("<b>" + item + "</b>"));

                if (gravity.contentEquals("center"))
                    normalTxt.setGravity(Gravity.CENTER);
                if (gravity.contentEquals("start"))
                    normalTxt.setGravity(Gravity.START);
                if (gravity.contentEquals("end"))
                    normalTxt.setGravity(Gravity.END);
                //txtJustify.setTextAlignment(View.);
                //Finish txt titles
                normalTxt.setTextSize(13);
                //normalTxt.setTextColor(Color.rgb(205,205,205));
                //el primer texto  20
                //If Array
                if ( Iterator > 0 ){
                    if ( Iterator == 1)
                        normalTxt.setPadding(0,30,0,0);
                    else if ( Iterator == ArrayLength)
                        normalTxt.setPadding(0,10,0,25);
                    else
                        normalTxt.setPadding(0,10,0,0 );
                } else {
                    normalTxt.setPadding(0,25,0,25 );
                }
                /** **/



                //txtJustify.setPadding(0,0,0,5);
                normalTxt.setLayoutParams(paramsExample);
                normalTxt.setTypeface(font);


                View line = new View(context);
                line.setLayoutParams(paramsExample);
                line.setBackgroundColor(Color.BLACK);
                line.setMinimumHeight(1);
                //line.setPadding(0,2,0,20);

                line.setLayoutParams(paramsExample);


                textLy.addView(normalTxt,textLy.getLayoutParams());
                //textLy.addView(line,textLy.getLayoutParams());

            }
            if (typeOfTxt.contentEquals("justify")){
                txtJustify.setText(Html.fromHtml("<b>" + item + "</b>"));

                if (gravity.contentEquals("center"))
                    txtJustify.setGravity(Gravity.CENTER);
                if (gravity.contentEquals("start"))
                    txtJustify.setGravity(Gravity.START);
                if (gravity.contentEquals("end"))
                    txtJustify.setGravity(Gravity.END);
                //txtJustify.setTextAlignment(View.);
                //Finish txt titles
                txtJustify.setTextSize(14);
                //el primer texto  20
                //If Array
                if ( Iterator > 0 ){
                    if ( Iterator == 1)
                        txtJustify.setPadding(0,30,0,0);
                    else if ( Iterator == ArrayLength)
                        txtJustify.setPadding(0,10,0,25);
                    else
                        txtJustify.setPadding(0,10,0,0 );
                } else {
                    txtJustify.setPadding(0,25,0,25 );
                }
                /** **/



                //txtJustify.setPadding(0,0,0,5);
                txtJustify.setLayoutParams(paramsExample);


                View line = new View(context);
                line.setLayoutParams(paramsExample);
                line.setBackgroundColor(Color.BLACK);
                line.setMinimumHeight(1);
                //line.setPadding(0,2,0,20);

                line.setLayoutParams(paramsExample);


                textLy.addView(txtJustify,textLy.getLayoutParams());
                //textLy.addView(line,textLy.getLayoutParams());
            }





        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createParagraphFromArray(LinearLayout textLy, ViewGroup.LayoutParams paramsExample, ArrayList<String[]> fullArray
            , String gravity){

        //Iterators
        int i = 0;
        final int titleIndex = 1;

        for ( String[] itemArray : fullArray) {
            //Print
            ArrayLength = fullArray.size();
            for (String item : itemArray) {
                i++;
                Iterator = i;
                try {
                    //TextView titles = new TextView(context);
                    createParagraph(textLy, "justify", paramsExample, item,  "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
