package citaapp.citapprenticeshipapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditInformation extends AppCompatActivity {

    EditText txtUsi, txtCitNum, txtAnpName, txtAnpPhone, txtAnpEmail,  txtLlnDate, txtClassDate;
    //EditText txtDeptName, txtDeptPhone, txtDeptEmail;

    RadioButton rbtRPLYes, rbtTrainingYes, rbtRPLNo, rbtTrainingNo;

    DatePickerDialog datePickerDialog;

    Button btnSave, btnCancel, btnClearDetails;

    Button btnClearLocalDb, btnForceConnect;

    Spinner spnDeptName;

    TextView lblDeptPhone, lblDeptEmail;

    DBHandler dbHandler;

    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;

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

        spnDeptName = (Spinner) findViewById(R.id.spnDeptName);
        lblDeptPhone = (TextView) findViewById(R.id.lblDeptPhone);
        lblDeptEmail = (TextView) findViewById(R.id.lblDeptEmail);

        txtLlnDate = (EditText) findViewById(R.id.txtLlnDate);
        txtClassDate = (EditText) findViewById(R.id.txtClassDate);
        rbtRPLYes = (RadioButton) findViewById(R.id.rbtRPLYes);
        rbtRPLNo = (RadioButton) findViewById(R.id.rbtRPLNo);
        rbtTrainingYes = (RadioButton) findViewById(R.id.rbtTrainingYes);
        rbtTrainingNo = (RadioButton) findViewById(R.id.rbtTrainingNo);
        btnClearDetails = (Button) findViewById(R.id.btnClearDetails);

        //
        dbHandler = new DBHandler(this);
        dbHandler.initSpinner(spnDeptName);


        spnDeptName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                lblDeptPhone.setText(dbHandler.getDeptsList().get(position).getPhone());
                lblDeptEmail.setText(dbHandler.getDeptsList().get(position).getEmail());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

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

        txtClassDate.setOnClickListener(new View.OnClickListener() {
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
                                txtClassDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnClearDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                SharedPreferences settings = getSharedPreferences("myInfo", Context.MODE_PRIVATE);
                settings.edit().clear().commit();

            }
        });
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                saveAllPrefs();
                //startActivity(new Intent(EditInformation.this, ViewInformation.class));
                finish(); //closing EditInformation instead of starting new ViewInformation Activity over the top.
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish(); //cancel storing information
            }
        });

        btnClearLocalDb = (Button) findViewById(R.id.btnClearLocalDb);
        btnClearLocalDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dbHandler.clearLocalTable();
            }
        });

        btnForceConnect = (Button) findViewById(R.id.btnForceConnect);
        btnForceConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dbHandler.syncDB();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart(); //Always call the superclass method first
        loadPrefs();
    }

    private void setSpinnerSelection()
    {
        for (CITDepartment tempDept : dbHandler.getDeptsList())
        {
            Log.d("testing department", tempDept.getName());
            if(tempDept.getName().equals(preferenceSettings.getString("Department Name", "Not Found")))
            {
                spnDeptName.setSelection(tempDept.getId()-1);
                Log.d("Found","This is the selection.");
                //break;
            }
        }
    }

    //separated the putString code into this separate method
    //takes the key and value strings and puts into Shared Preferences file
    private void saveToPrefs(String key, String value)
    {
        if (value != null && value != "")
        {
            preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
            preferenceEditor = preferenceSettings.edit();

            preferenceEditor.putString(key, value);
            preferenceEditor.apply();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbtTrainingYes:
                if (checked)

                    break;
            case R.id.rbtTrainingNo:
                if (checked)

                    break;
        }
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
            saveToPrefs("Department Name",  spnDeptName.getSelectedItem().toString());
            saveToPrefs("Department Phone",  lblDeptPhone.getText().toString());
            saveToPrefs("Department Email",  lblDeptEmail.getText().toString());
            saveToPrefs("LLN Date", txtLlnDate.getText().toString());
            saveToPrefs("Class Start Date", txtClassDate.getText().toString());

            if(rbtRPLYes.isChecked())
                saveToPrefs("RPL", "Yes");
            else
                saveToPrefs("RPL", "No");

            if(rbtTrainingYes.isChecked())
                saveToPrefs("Training Plan", "Yes");
            else
                saveToPrefs("Training Plan", "No");

            Toast.makeText(getApplicationContext(), "Your information has been saved!", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            //txtDeptName.setText(preferenceSettings.getString("Department Name", "Not Found"));
            //txtDeptPhone.setText(preferenceSettings.getString("Department Phone", "Not Found"));
            //txtDeptEmail.setText(preferenceSettings.getString("Department Email", "Not Found"));

            setSpinnerSelection();

            txtLlnDate.setText(preferenceSettings.getString("LLN Date", "Not Found"));
            txtClassDate.setText(preferenceSettings.getString("Class Start Date", "Not Found"));


            if("No".equals(preferenceSettings.getString("RPL","No")))
                rbtRPLNo.setChecked(true);
            else
                rbtRPLYes.setChecked(true);


            if("No".equals(preferenceSettings.getString("Training Plan","No")))
                rbtTrainingNo.setChecked(true);
            else
                rbtTrainingYes.setChecked(true);

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}