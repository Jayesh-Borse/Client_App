package com.jayesh.clientapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Info extends AppCompatActivity {
    private static final String TAG = "Info";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DatabaseHelper myDb;

    EditText  mEditText1, mEditText2, mEditText3, mEditText4, mEditText5, mEditText6, mEditText7,mEditText8;
    EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mDisplayDate = findViewById(R.id.editText3);
        textView = findViewById(R.id.serial);
        mEditText1 = findViewById(R.id.name);
        mEditText2 = findViewById(R.id.fhname);
        mEditText3 = findViewById(R.id.mname);
        mEditText4 = findViewById(R.id.email);
        mEditText5 = findViewById(R.id.contact);
        mEditText6 = findViewById(R.id.address);
        mEditText7 = findViewById(R.id.lastname);
        mEditText8 = findViewById(R.id.SendMail);

        myDb = new DatabaseHelper(this);
        //UpdateData();
        ActivityCompat.requestPermissions(Info.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Info.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet:dd/mm/yyyy:" + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


    }



    public void save(View view) {

        PdfDocument myPdfDocument= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myPdfDocument = new PdfDocument();
        }
        PdfDocument.PageInfo myPageInfo= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myPageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        }
        PdfDocument.Page myPage= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myPage = myPdfDocument.startPage(myPageInfo);
        }

        Paint myPaint=new Paint();

        String Message1 = "Serial No              : "+textView.getText().toString() + "\n";
        String Message2 = "Name                    : "+mEditText1.getText().toString()+"\n";
        String Message3 = "Middle Name       : "+mEditText2.getText().toString()+"\n";
        String Message4 = "Last Name           : "+mEditText7.getText().toString()+"\n";
        String Message5 = "Date of Birth        : "+mDisplayDate.getText().toString() + "\n";
        String Message6 = "Mother's Name  : "+mEditText3.getText().toString()+"\n";
        String Message7 = "Contact No.         : " + mEditText5.getText().toString() + "\n";
        String Message8 = "Email                     : "+mEditText4.getText().toString() + "\n";
        String Message9 = "Address                : "+mEditText6.getText().toString() + "\n";
        String Email = mEditText8.getText().toString();
        String Message=textView.getText().toString() +mEditText1.getText().toString();
        if(!Message1.isEmpty()&&!Message2.isEmpty()&&!Message3.isEmpty()&&!Message4.isEmpty()&&!Message5.isEmpty()&&!Message6.isEmpty()&&!Message7.isEmpty()&&!Message8.isEmpty()&&!Message9.isEmpty()&&!Email.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                myPage.getCanvas().drawText(Message1,10,25,myPaint);
                myPage.getCanvas().drawText(Message2,10,45,myPaint);
                myPage.getCanvas().drawText(Message3,10,65,myPaint);
                myPage.getCanvas().drawText(Message4,10,85,myPaint);
                myPage.getCanvas().drawText(Message5,10,105,myPaint);
                myPage.getCanvas().drawText(Message6,10,125,myPaint);
                myPage.getCanvas().drawText(Message7,10,145,myPaint);
                myPage.getCanvas().drawText(Message8,10,165,myPaint);
                myPage.getCanvas().drawText(Message9,10,185,myPaint);
                myPdfDocument.finishPage(myPage);
//                textView.getText().clear();
//                mEditText1.getText().clear();
//                mEditText2.getText().clear();
//                mEditText7.getText().clear();
//                mEditText3.getText().clear();
//                mEditText4.getText().clear();
//                mEditText5.getText().clear();
//                mEditText6.getText().clear();
//                mDisplayDate.getText().clear();


            }

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Client App");
            String myFilePath= Environment.getExternalStorageDirectory().getPath()+"/"+"Client App"+"/"+Message+".pdf";
            boolean success = true;
            if(!folder.exists()){
                success = folder.mkdirs();
            }
            if(success){
                File myFile=new File(myFilePath);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        myPdfDocument.writeTo(new FileOutputStream(myFile));
                    }
                    Toast.makeText(this, "PDF Saved", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    myPdfDocument.close();
                }
                boolean isInserted = myDb.insertData(mEditText1.getText().toString(),mEditText7.getText().toString(),mEditText2.getText().toString(),mDisplayDate.getText().toString(),mEditText3.getText().toString(),mEditText5.getText().toString(),mEditText4.getText().toString(),mEditText6.getText().toString());
                if(isInserted==true){
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }

                boolean isUpdated = myDb.updateData(textView.getText().toString(),mEditText1.getText().toString(),mEditText7.getText().toString(),mEditText2.getText().toString(),mDisplayDate.getText().toString(),mEditText3.getText().toString(),mEditText5.getText().toString(),mEditText4.getText().toString(),mEditText6.getText().toString());
                if (isUpdated==true){
                    Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Folder not created..", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Please enter Client Information", Toast.LENGTH_SHORT).show();
        }


    }








    public void SendMail(View view) {

        String recipientList = mEditText8.getText().toString();
        String[] recipient = recipientList.split(",");
        //String recipient = "jayborse2000@gmail.com";
        String serial = textView.getText().toString();
        String name = mEditText1.getText().toString();
        String Mname = mEditText2.getText().toString();
        String Lname = mEditText7.getText().toString();
        String Dob = mDisplayDate.getText().toString();
        String Moname = mEditText3.getText().toString();
        String CNo = mEditText5.getText().toString();
        String emailId = mEditText4.getText().toString();
        String address = mEditText6.getText().toString();

        String Messagex = "Serial No.                     :  "+serial+"\n\n"+"Name of Client           :  "+name+"\n\n"+"Middle Name              :  "+Mname+"\n\n"+"Last Name                  :  "+Lname+"\n\n"+"Date of Birth               :  "+Dob+"\n\n"+"Mothers Name           :  "+Moname+"\n\n"+"Contact No.                :  "+CNo+"\n\n"+"Email                            :  "+emailId+"\n\n"+"Residential Address  :  "+address;
        if(!serial.isEmpty()&&!recipientList.isEmpty()&&!name.isEmpty()&&!Mname.isEmpty()&&!Lname.isEmpty()&&!Dob.isEmpty()&&!Moname.isEmpty()&&!CNo.isEmpty()&&!emailId.isEmpty()&&!address.isEmpty()) {

            /*boolean isInserted = myDb.insertData(mEditText1.getText().toString(),mEditText7.getText().toString());
            if(isInserted==true){
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
            }*/


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT,serial+"_"+name+"_Report");
            intent.putExtra(Intent.EXTRA_EMAIL,recipient);
            intent.putExtra(Intent.EXTRA_TEXT,Messagex);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent,"Choose an email client" ));
            mEditText1.getText().clear();
            mEditText2.getText().clear();
            mEditText7.getText().clear();
            mEditText3.getText().clear();
            mEditText4.getText().clear();
            mEditText5.getText().clear();
            mEditText6.getText().clear();
            mDisplayDate.getText().clear();
            mEditText8.getText().clear();
        }
        else{
            Toast.makeText(this, "Please enter Client Information", Toast.LENGTH_SHORT).show();
        }

    }


}
