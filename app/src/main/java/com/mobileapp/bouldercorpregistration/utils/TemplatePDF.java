package com.mobileapp.bouldercorpregistration.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobileapp.bouldercorpregistration.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class TemplatePDF extends Activity {

    private Context context;
    public static File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Paragraph signature;
    private Date fecha = new Date();

    //Estilos
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.UNDERLINE);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.ITALIC);
    private Font fSign = new Font(Font.FontFamily.HELVETICA, 18, Font.ITALIC);


    @RequiresApi(api = Build.VERSION_CODES.O)
    public TemplatePDF(Context context, String completeName) {
        this.context = context;

        //Continue
        createFile(completeName);
    }

    //Escribir en el documento
    public void openDocument(){
        try {

            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            //Abrir doc
            document.open();


        } catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }
    //Crear Carpeta
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createFile(String completeName){

        //Create Folders /storage/emulated/0/Android/data/com.mobileapp.bouldercorpregistration/files/

        File responFile = new File("/storage/emulated/0/Android/data/com.mobileapp.bouldercorpregistration/files/", "Responsivas");
        File contractFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)), "BoluderCorp");
        contractFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/BoulderCorp/"), "Responsivas");

        //contractFile = new File(String.valueOf(contractFile)), "Responsivas");
        File BCFile = new File(context.getApplicationContext().getFilesDir(), "BoulderCorp");
        BCFile = new File(context.getApplicationContext().getFilesDir()+"/BoulderCorp/", "Responsivas2");


        if(!BCFile.exists())
            ///data/user/0/com/...
            if(!BCFile.mkdirs())
                Log.d("createFile", "Se creo directorio exitosamente - BCFile - :)");
            else
                Log.e("createFile", "Fail on create - BCFile -");
        if(!contractFile.exists()){
            //storage/emulated/0/Documents/...
            if(!contractFile.mkdirs())
                Log.d("createFile", "Se creo directorio exitosamente - contractFile - :)");
            else
                Log.e("createFile", "Fail on create - contractFile - ");
        }
        if(!responFile.exists()){
            //storage/emulated/0/Android/data/com.mobileapp.bouldercorpregistration/files/Responsivas
            if(!responFile.mkdirs())
                Log.d("createFile", "Se creo directorio exitosamente - responFile - :)");
            else
                Log.e("createFile", "Fail on create - responFile - ");
        }


        //System.out.println("hola 777 dirección BCFile  -  " + BCFile);
        //System.out.println("hola 777 dirección contractFile  -  " + contractFile);
        //System.out.println("hola 777 dirección responFile  -  " + responFile);
        //Create File
        //Create File

        //Verify and choose the best file path
        File filePath = null;


        if(contractFile.exists()){
            Log.e("TemplatePDF", "Existe contractFile");
            filePath = contractFile;
        } else {
            if(responFile.exists()) {
                Log.e("TemplatePDF", "Exise responFile ");
                filePath = responFile;
            } else {
                if(BCFile.exists()){
                    Log.e("TemplatePDF", "Existe BCFile");
                    filePath = BCFile;
                } else {
                    Log.e("TemplatePdf", "There´s no valid filepath forever.");
                }
            }
        }


        try {
            if (filePath != null)
                pdfFile = new File(filePath, FormValidations.getCompleteNameString() + ".pdf");
            else Log.e("TemplatePdf", "There´s no valid filepath.");

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    //addTitles
    public void addTitle(String title, String subtitle){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subtitle, fSubTitle));
            //addChildP(new Paragraph("Generado : " + date, fHighText));
            paragraph.setSpacingBefore(25);
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTitle", e.toString());
        }
    }

    //add paragraph
    public void addParagraph(String text) throws DocumentException {
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingBefore(5);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addParagraph", e.toString());
        }

    }

    //add Digital Sign
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDigitalSign() {
        try {


            String sTxt = context.getResources().getString(R.string.sign);
            signature = new Paragraph();

            //addChildSign(new Paragraph(sign, fText));
            addChildSign(new Paragraph(sTxt, fText));
            addChildSign(new Paragraph(LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM uuuu")), fText));

            signature.setSpacingBefore(30);
            signature.setSpacingAfter(0);

            //Add Sign
            document.add(signature);

        }catch(Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    //add a child of anything
    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    //add a child of anything
    private void addChildSign(Paragraph childSign){
        childSign.setAlignment(Element.ALIGN_RIGHT);
        signature.add(childSign);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addFoto(Bitmap u) {
        try {
            PdfPTable tabla = new PdfPTable(1);
            tabla.setWidthPercentage(60);
            tabla.setSpacingBefore(20);
            tabla.setSpacingAfter(0);

            Bitmap bmp = u;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(image);
            tabla.addCell(imageCell);
            document.add(tabla);
            addDigitalSign();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Agregar methas
    public void addMethas(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
        document.addCreationDate();
    }

    //Close Document
    public void closeDocument(){
        document.close();
    }
}
