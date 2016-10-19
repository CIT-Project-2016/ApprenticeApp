package citaapp.citapprenticeshipapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class EditInformation extends AppCompatActivity {

    FileOutputStream outputStream;
    EditText usiTxt, citTxt, anpNameTxt, anpPhoneTxt, anpEmailTxt, deptNameTxt, deptPhoneTxt, deptEmailTxt;
    //Need to get the last 4, date picker and radio buttons

    String filename = "informationFile";

    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        usiTxt = (EditText) findViewById(R.id.txtUsiNumber);

        preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();


        final Button saveBtn = (Button) findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                   // outputStream.write(usiTxt.getText().toString().getBytes());
                    //outputStream.close();

                    preferenceEditor.putString("USI",  usiTxt.getText().toString());
                    preferenceEditor.apply();
                    preferenceEditor.putString("CIT Number",  citTxt.getText().toString());
                    preferenceEditor.apply();

                    preferenceEditor.putString("ANP Name",  anpNameTxt.getText().toString());
                    preferenceEditor.apply();
                    preferenceEditor.putString("ANP Phone",  anpPhoneTxt.getText().toString());
                    preferenceEditor.apply();
                    preferenceEditor.putString("ANP Email",  anpEmailTxt.getText().toString());
                    preferenceEditor.apply();

                    preferenceEditor.putString("Department Name",  deptNameTxt.getText().toString());
                    preferenceEditor.apply();
                    preferenceEditor.putString("Department Phone",  deptPhoneTxt.getText().toString());
                    preferenceEditor.apply();
                    preferenceEditor.putString("Department Email",  deptEmailTxt.getText().toString());
                    preferenceEditor.apply();



                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Your information has been saved!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditInformation.this, ViewInformation.class));
            }
        });

    }
}
