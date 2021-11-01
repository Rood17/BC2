package com.mobileapp.bouldercorpregistration.utils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.mobileapp.bouldercorpregistration.MainActivity;
import com.mobileapp.bouldercorpregistration.R;
import com.codesgood.views.JustifiedTextView;
import com.mobileapp.bouldercorpregistration.RegistrationForm;
import com.mobileapp.bouldercorpregistration.ReviewDoc;

import java.io.File;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CreatePDF {

    // Declarar variables de los controles.
    private Context Context;
    private Resources Res;
    private Boolean readyToSign;
    private LinearLayout ContractLayout;
    private LinearLayout SignLayout;
    private LinearLayout DigitalResLayout;
    private static Boolean finishProccess = false;
    private static Boolean firstTime;
    private static Boolean useChildHeader = false;
    private static String contractHeader;
    private static String ContractHeaderChilds;

    private int contractArray_1, contractArray_2, contractArray_3, contractArray_4, contractArray_5, contractArray_6,
            contractArray_7, contractArray_8, contractArray_9;


    private static String Name, LastName, Email, BirthDate, Country, Contract, CompleteName, CompleteNameString;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CreatePDF(Context context, LinearLayout contractLayout, LinearLayout signLayout, LinearLayout digitalResLayout, Boolean readyToSign) throws DocumentException {
        this.Context = context;
        Res = this.Context.getResources();
        this.ContractLayout = contractLayout;
        this.SignLayout = signLayout;
        this.readyToSign = readyToSign;
        this.DigitalResLayout = digitalResLayout;

        //clear
        firstTime = true;

        //Initializate
        setData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    public ArrayList <String[]> setData() {

        Name = FormValidations.getName().getText().toString();
        LastName = FormValidations.getLastName().getText().toString();
        Email = FormValidations.getEmail().getText().toString();
        BirthDate = FormValidations.getDate().getText().toString();
        //Country = FormValidations.getCountry().getText().toString();
        Contract = Context.getResources().getString(R.string.contract_title_2);


        //Set Strings
        CompleteName = Name + " " + LastName;
        CompleteNameString = Name+"_"+LastName;
        contractHeader = Context.getResources().getString(R.string.contract_header_1) + CompleteName + ", " + Context.getResources().getString(R.string.contract_header_2) + " " + BirthDate +", " +
                Context.getResources().getString(R.string.contract_header_3) + ", " ;
        ContractHeaderChilds = Context.getResources().getString(R.string.contract_header_4) + " ";

        //Create Title Array
        String[] titleArray = {};


        //titleArray = Context.getResources().getString(R.array.planets_array);
        //Contract
        //Deslinde
        //Contrct Traduction

        contractArray_1 = R.array.contract_1;
        contractArray_2 = R.array.contract_2;
        contractArray_3 = R.array.contract_3;
        contractArray_4 = R.array.contract_4;
        //contractArray_5 = R.array.contract_5;
        contractArray_6 = R.array.contract_6;
        contractArray_7 = R.array.contract_7;


        String[] contract_part_1 = Res.getStringArray(contractArray_1);
        String[] contract_part_2 = Res.getStringArray(contractArray_2);
        String[] contract_part_3 = Res.getStringArray(contractArray_3);
        String[] contract_part_4 = Res.getStringArray(contractArray_4);
        //String[] contract_part_5 = Res.getStringArray(R.array.contract_5);
        String[] contract_part_6 = Res.getStringArray(contractArray_6);
        String[] contract_part_7 = Res.getStringArray(contractArray_7);

        ArrayList <String[]> fullContract = new <String[]> ArrayList();
        fullContract.add(contract_part_1);
        fullContract.add(contract_part_2);
        fullContract.add(contract_part_3);
        fullContract.add(contract_part_4);
        //fullContract.add(contract_part_5);
        fullContract.add(contract_part_6);
        fullContract.add(contract_part_7);

        try {
            //Call Pint PDF
            if(!readyToSign)
                printPdf(fullContract);

            //Create PDF
            if (readyToSign)
                createSignedPdf(fullContract);

        } catch (Exception ex){
            ex.printStackTrace();
        }


        return fullContract;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    public void printPdf(ArrayList <String[]> contract){


        //String Header
        // 8888 String contractHeader = "Yo: " + CompleteName + " nacido el " + BirthDate + " en " + Country +", " +
                // "en mi carácter de cliente; mayor de edad ";
        // 8888 String contractHeaderChilds = " y/o como padre o tutor del/los menor(es) a mi a cargo: " ;

        //If there´re minors to register
        if ( FormValidations.getMinorsArray() != null )
            addMinorsNames();



        Boolean insertContractHeader = false;
        final int titleIndex = 1;

        //Print PDF for Review Doc Layout

        //Iterators
        int id = 0;
        int i = 0;
        int iTitle = 0;
        for ( String[] itemArray : contract){

            iTitle++;

            //Print
            for ( String item : itemArray){
                i++;
                id++;

                //Create a text
                JustifiedTextView txtJustify = new JustifiedTextView(Context);
                TextView titles = new TextView(Context);

                ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                //set text id
                titles.setId(id);


                try {
                    //Create PDF - if title
                    if ( i == titleIndex){
                        //Catch electronic firm module
                        if ( iTitle == contract.size()){
                            getDigitalFirm(paramsExample, id, null);

                            break;
                        }

                        //System.out.println(" Título : " +  " : " + item );
                        //setText
                        titles.setText(Html.fromHtml("<b>" + item + "</b>"));
                        titles.setGravity(Gravity.CENTER);
                        titles.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        //Catch first module
                        if ( iTitle == titleIndex){
                            //System.out.println(" SI A HUEVO : " +  " : " + item );
                            insertContractHeader = true;
                        }

                        //Finish txt titles
                        titles.setLayoutParams(paramsExample);
                        ContractLayout.addView(titles,ContractLayout.getLayoutParams());

                    //if paragraph
                    } else {

                        //If is the first paragraph in the contract
                        if ( insertContractHeader ){

                            //System.out.println(" Paragraph a hievooooo: " +  " : " + contractHeader + item );
                            //templatePDF.addParagraph(contractHeader + item);
                            //999 AQUÍ ONER LOS CHILDS
                            //lamar get array
                            //verificar si hay
                            //si hay crear un header con los nombres??
                            //hacer lo mismo en create

                            if (useChildHeader){
                                txtJustify.setId(id);
                                txtJustify.setText(Html.fromHtml(contractHeader + ContractHeaderChilds + item));
                            } else {
                                txtJustify.setId(id);
                                txtJustify.setText(Html.fromHtml(contractHeader + item));
                            }


                            //reset
                            insertContractHeader = false;

                        //rest of the paragraphs
                        } else {
                            //System.out.println(" Paragraph : " +  " : " + item );
                            //templatePDF.addParagraph(item);
                            txtJustify.setId(id);
                            txtJustify.setText(Html.fromHtml(item));
                            txtJustify.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);
                        }

                        //Finish txt justify paragraphs
                        txtJustify.setLayoutParams(paramsExample);
                        ContractLayout.addView(txtJustify,ContractLayout.getLayoutParams());

                    }
                    //styles for all
                    //templatePDF.addParagraph(createContract());
                    //templatePDF.addParagraph(getDigitalFirm());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //reset contract parts
            i=0;
        }
    }

    // no esta en global
    public void addMinorsNames(){
        ArrayList<EditText> arrayHeader = new ArrayList();
        arrayHeader = FormValidations.getMinorsArray();

        if ( arrayHeader.size() != 0){
            useChildHeader = true;
            int indexMinors = 0;
            for ( EditText field : arrayHeader){

                indexMinors++;

                //Primero
                if ( indexMinors == 1 )
                    if ( arrayHeader.size() > 1)
                        ContractHeaderChilds += field.getText().toString();
                    else
                        ContractHeaderChilds += field.getText().toString() + ", ";
                    //último
                else if ( arrayHeader.size() == (indexMinors) )
                    ContractHeaderChilds += " y " + field.getText().toString() + ",  ";
                else
                    ContractHeaderChilds += ", " + field.getText().toString();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void printDigitalResponsive(){

        if ( firstTime ){
            //Create a text
            JustifiedTextView txtJustify = new JustifiedTextView(Context);
            TextView title = new TextView(Context);

            ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            //set text id
            title.setText(Html.fromHtml("<b>" + Res.getText(R.string.contract_title_7 )+ "</b>"));
            title.setGravity(Gravity.CENTER);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //Finish txt titles
            title.setLayoutParams(paramsExample);
            DigitalResLayout.addView(title,DigitalResLayout.getLayoutParams());


            txtJustify.setText(Html.fromHtml(String.valueOf(Res.getText(R.string.p_7_a))));
            txtJustify.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);
            txtJustify.setLayoutParams(paramsExample);
            DigitalResLayout.addView(txtJustify,DigitalResLayout.getLayoutParams());

            //firstTime
            firstTime = false;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createSignedPdf(ArrayList <String[]> contract) throws DocumentException {


        final int titleIndex = 1;
        Boolean insertContractHeader = false;
        String dataClientTitle = "Información del Cliente";

        TemplatePDF templatePDF = new TemplatePDF(this.Context, CompleteNameString);

        //Open Document and Add Methas
        templatePDF.openDocument();
        templatePDF.addMethas("Deslinde BC", "contrato usuario", "BOULDER CORP");

        // 8888 String contractHeader = "Yo " + CompleteName + " nacido el " + BirthDate + " en " + Country + ", ";
        //If there´re minors to register
        if ( FormValidations.getMinorsArray() != null )
            addMinorsNames();

        //First Part -- Client Data
        ArrayList<String> clientData = new ArrayList<String>();
        clientData = FormValidations.getRegistrationDataArray();
        try {
            templatePDF.addTitle(dataClientTitle, "");
            for (String data : clientData) {
                templatePDF.addParagraph(data);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }



        //Iterators
        int i = 0;
        int iTitle = 0;
        for ( String[] itemArray : contract){

            iTitle++;

            for ( String item : itemArray){

                i++;
                try {
                    //Create PDF - if title
                    if ( i == titleIndex){

                        //Catch electronic firm module
                        if ( item.indexOf("ELECTRÓNICA") != -1 || item.indexOf("ELECTRONIC") != -1 ){
                            getDigitalFirm(null, -1, templatePDF);
                            Log.d("CreatePDF", "Se agrego firma exitosamente");
                        }

                        //Add title
                        templatePDF.addTitle( item, "");

                        //If first paragraph next
                        if ( item.contentEquals("DESLINDE") || item.contentEquals("DISCLAIMER") ){
                            insertContractHeader = true;
                        }

                    } else {

                        //If is the first paragraph in the contract
                        if ( insertContractHeader ){

                            //If minors registration
                            if (useChildHeader) {
                                templatePDF.addParagraph(contractHeader + ContractHeaderChilds + item);
                            } else {
                                templatePDF.addParagraph(contractHeader + item);
                            }


                            //si childs

                            //reset
                            insertContractHeader = false;
                        } else {
                            //System.out.println(" Paragraph : " +  " : " + item );
                            templatePDF.addParagraph(item);
                        }

                    }


                    //templatePDF.addParagraph(createContract());
                    //templatePDF.addParagraph(getDigitalFirm());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            //reset contract parts
            i=0;
        }

        //Close Document
        templatePDF.closeDocument();

        //Toast
        Toast.makeText(Context, R.string.pdfCreate, Toast.LENGTH_LONG).show();
        finishProccess = true;

    }

    //hacer getters y setters
   // @SuppressLint("ResourceType")
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    public void getDigitalFirm(ViewGroup.LayoutParams paramsExample, int id, TemplatePDF templatePDF){
        final int idNull = -1;
        //Create a text
        TextView nameSign = new TextView(Context);
        TextView txtSign = new TextView(Context);
        TextView date = new TextView(Context);
        final Typeface font = ResourcesCompat.getFont(Context, R.font.signatrue);

        //PRINT PDF
        if ( paramsExample != null && id != idNull){
            nameSign.setId(id);
            //nameSign.setText(Html.fromHtml("<p style='color:red'><i><u>  " + CompleteName + "  </u></i></p>"));
            nameSign.setGravity(Gravity.END);
            nameSign.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            nameSign.setTypeface(font);
            nameSign.setPadding(0, 30, 0, 0);
            //nameSign.set

            //Finish txt titles
            nameSign.setLayoutParams(paramsExample);
            //ContractLayout.addView(nameSign,ContractLayout.getLayoutParams());

            //text "sign"
            date.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM uuuu")));
            date.setGravity(Gravity.END);
            date.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            txtSign.setText(Html.fromHtml("Firma"));
            txtSign.setTextColor(Color.RED);
            txtSign.setGravity(Gravity.END);
            txtSign.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            //nameSign.set

            //Finish txt sign
            txtSign.setLayoutParams(paramsExample);
            date.setLayoutParams(paramsExample);
            SignLayout.addView(txtSign,SignLayout.getLayoutParams());
            SignLayout.addView(date,SignLayout.getLayoutParams());

        //Create PDF
        } else {
            templatePDF.addFoto(SignDialogFragment.getBitmapImage());
            //templatePDF.addDigitalSign(" hola aaaa a a aa a prueba");
        }



    }

    public static Boolean statusPdf(){
        return finishProccess;
    }

    ///////////////////////////////////////////////////////////////////////////

    // Abrir el archivo PDF una vez generado.
    public static void abrirPDFGenerado(Context context){
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try
            {
                context.startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(context , "No hay aplicación disponible para ver pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Nullable
    static File getAppSpecificAlbumStorageDir(Context context) {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        File file = new File(context.getExternalFilesDir(null), "BCRegisterApp");
        if (file == null || !file.mkdirs()) {
            Log.e("LOG_TAG", "Directory not created");
        }
        return file;
    }


}
