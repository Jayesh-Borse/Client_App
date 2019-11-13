package com.jayesh.clientapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class User extends AppCompatActivity {
    DatabaseHelper myDb;
    Button btnviewData,btnUpdateData;
    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        myDb = new DatabaseHelper(this);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        btnviewData = findViewById(R.id.viewbutton);
        btnUpdateData = findViewById(R.id.editbutton);
        viewAll();
       // updateData();
    }

    public void LogOut(View view) {

        preferenceConfig.writeLoginStatus(false);
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }

    public void NewUser(View view) {

        startActivity(new Intent(this,Info.class));


    }

    public void viewAll(){

        btnviewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","Data not found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n\n");
                }
                showMessage("Report",buffer.toString());
            }
        });

    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void folder(View view) {
        Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/"+"Client App");
        myIntent.setDataAndType(uri,"application/pdf");
        //String[] mimeTypes = {"image/*", "application/pdf"};
        myIntent.addCategory(Intent.CATEGORY_OPENABLE);
        //myIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        try {
            startActivityForResult(
                    Intent.createChooser(myIntent, "Select file"),
                    0);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog

        }
       // startActivity(Intent.createChooser(myIntent,"Open File"));
    }

    public void updateData(View view9)
    {
        startActivity(new Intent(User.this,SearchActivity.class));

   }

//    public void Exit(View view) {
//
//
//    }
}
