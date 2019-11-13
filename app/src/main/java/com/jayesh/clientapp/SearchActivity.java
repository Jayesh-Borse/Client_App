package com.jayesh.clientapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    EditText editText,id;
    TextView textView;
    DatabaseHelper myDb;
    Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText=findViewById(R.id.editText4);
        textView=findViewById(R.id.textView2);

        myDb = new DatabaseHelper(this);
    }

    public void find(View view)
    {
        String str1=editText.getText().toString();
        String str2=myDb.searchData(str1);
        if(str2!="null"){
            textView.setText(str2);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("Please remember the ID of the client you want to edit or delete");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog ad = builder.create();
            ad.show();
        }

    }

    public void del (View view ){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete");
        builder.setMessage("Enter the ID of the Client you want to delete");
        id=new EditText(this);
        builder.setView(id);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               final String delid=id.getText().toString();

                    int delrow= myDb.deleteData(delid);
                    if(delrow>0){
                        Toast.makeText(SearchActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SearchActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    public void edit (View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit");
        builder.setMessage("Enter the ID of the Client you want to edit");
        id=new EditText(this);
        builder.setView(id);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(SearchActivity.this,Info.class));

            }
        });
        AlertDialog ad =builder.create();
        ad.show();
    }


}
