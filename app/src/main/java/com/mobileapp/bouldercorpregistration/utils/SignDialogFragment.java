package com.mobileapp.bouldercorpregistration.utils;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.mobileapp.bouldercorpregistration.R;
import com.mobileapp.bouldercorpregistration.ReviewDoc;


public class SignDialogFragment extends DialogFragment {
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */

    private View viewR;
    //private static DrawSign newSign;
    private static android.graphics.Bitmap Bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout to use as dialog or embedded fragment
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
        viewR = layoutInflater.inflate(R.layout.activity_dialog, container, false);

        //New Draw && set controls
        DrawingView newSignView = new DrawingView(viewR.getContext());
        newSignView.setPenColor(Color.BLACK);
        newSignView.setPenSize(4);

        //get
        LinearLayout drawLayer = viewR.findViewById(R.id.drawLayout);
        drawLayer.addView(newSignView, drawLayer.getLayoutParams());

        Button clearBtn = viewR.findViewById(R.id.clearBtn33);
        clearBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Clean Draw
                newSignView.clear();
            }
        });

        Button saveBtn = viewR.findViewById(R.id.saveBtn33);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            public void onClick(View view) {
                //Set Bitmap Image
                setBitmapImage(newSignView.getBitmap());
                ReviewDoc.setImage();
            }
        });

        return viewR;

    }

    /** The system calls this only when creating the layout in a dialog.
     * @return*/
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the DialogWin.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setBitmapImage(Bitmap bitmap){
        this.Bitmap = bitmap;
    }
    public static android.graphics.Bitmap getBitmapImage(){
        return Bitmap;
    }
}
