package citaapp.citapprenticeshipapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EditInformation extends AppCompatActivity {

    EditText txtUsi, txtCitNum, txtAnpName, txtAnpPhone, txtAnpEmail, txtDeptName, txtDeptPhone, txtDeptEmail, txtLlnDate;
    //Need to get the last 4, date picker and radio buttons

    DatePickerDialog datePickerDialog;

    Button saveBtn;

    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;

    //separated the putString code into this separate method
    //takes the key and value strings and puts into Shared Preferences file
    private void saveToPrefs(String key, String value)
    {
        preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        preferenceEditor.putString(key, value);
        preferenceEditor.apply();
    }

    private void saveAllPrefs()
    {
        try
        {
            saveToPrefs("USI",  txtUsi.getText().toString());
            saveToPrefs("CIT Number",  txtCitNum.getText().toString());
            saveToPrefs("ANP Name",  txtAnpName.getText().toString());
            saveToPrefs("ANP Phone",  txtAnpPhone.getText().toString());
            saveToPrefs("ANP Email",  txtAnpEmail.getText().toString());
            saveToPrefs("Department Name",  txtDeptName.getText().toString());
            saveToPrefs("Department Phone",  txtDeptPhone.getText().toString());
            saveToPrefs("Department Email",  txtDeptEmail.getText().toString());
            saveToPrefs("LLN Date", txtLlnDate.getText().toString());

            Toast.makeText(getApplicationContext(), "Your information has been saved!", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    //just separated these into this separate method for cleanliness..
    //just setting the text fields to the current values saved
    private void loadPrefs()
    {
        preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        try {
            txtUsi.setText(preferenceSettings.getString("USI", "Not Found"));
            txtCitNum.setText(preferenceSettings.getString("CIT Number", "Not Found"));
            txtAnpName.setText(preferenceSettings.getString("ANP Name", "Not Found"));
            txtAnpPhone.setText(preferenceSettings.getString("ANP Phone", "Not Found"));
            txtAnpEmail.setText(preferenceSettings.getString("ANP Email", "Not Found"));
            txtDeptName.setText(preferenceSettings.getString("Department Name", "Not Found"));
            txtDeptPhone.setText(preferenceSettings.getString("Department Phone", "Not Found"));
            txtDeptEmail.setText(preferenceSettings.getString("Department Email", "Not Found"));
            txtLlnDate.setText(preferenceSettings.getString("LLN Date", "Not Found"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        //referring the widgets
        txtUsi = (EditText) findViewById(R.id.txtUsi);
        txtCitNum = (EditText) findViewById(R.id.txtCitNum);
        txtAnpName = (EditText) findViewById(R.id.txtAnpName);
        txtAnpPhone = (EditText) findViewById(R.id.txtAnpPhone);
        txtAnpEmail = (EditText) findViewById(R.id.txtAnpEmail);
        txtDeptName = (EditText) findViewById(R.id.txtDeptName);
        txtDeptPhone = (EditText) findViewById(R.id.txtDeptPhone);
        txtDeptEmail = (EditText) findViewById(R.id.txtDeptEmail);
        txtLlnDate = (EditText) findViewById(R.id.txtLlnDate);

        txtLlnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditInformation.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txtLlnDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        saveBtn = (Button) findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                saveAllPrefs();
                //startActivity(new Intent(EditInformation.this, ViewInformation.class));
                finish(); //closing EditInformation instead of starting new ViewInformation Activity over the top.
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart(); //Always call the superclass method first
        loadPrefs();
    }
}