package citaapp.citapprenticeshipapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewInformation extends AppCompatActivity
{
    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;

    EditText txtUsi, txtCitNum, txtAnpName, txtAnpPhone, txtAnpEmail, txtDeptName, txtDeptPhone, txtDeptEmail, txtLlnDate;

    Button editBtn;
    TextView RPLlbl, TPClbl;

    void loadPrefs()
    {
        preferenceSettings = getSharedPreferences("myInfo", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        try
        {
            txtUsi.setText(preferenceSettings.getString("USI", "Not Found"));
            txtCitNum.setText(preferenceSettings.getString("CIT Number", "Not Found"));
            txtAnpName.setText(preferenceSettings.getString("ANP Name", "Not Found"));
            txtAnpPhone.setText(preferenceSettings.getString("ANP Phone", "Not Found"));
            txtAnpEmail.setText(preferenceSettings.getString("ANP Email", "Not Found"));
            txtDeptName.setText(preferenceSettings.getString("Department Name", "Not Found"));
            txtDeptPhone.setText(preferenceSettings.getString("Department Phone", "Not Found"));
            txtDeptEmail.setText(preferenceSettings.getString("Department Email", "Not Found"));
            txtLlnDate.setText(preferenceSettings.getString("LLN Date", "Not Found"));
            RPLlbl.setText(preferenceSettings.getString("RPL", "No"));
            TPClbl.setText(preferenceSettings.getString("Training Plan", "No"));
            //these ones aren't done yet
           /*
            RPLlbl.setText(preferenceSettings.getString("RPL", "Not Found"));
            TPClbl.setText(preferenceSettings.getString("Training Plan", "Not Found"));
            ClassStartTxt.setText(preferenceSettings.getString("Class Start Date", "Not Found"));
            */
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
        setContentView(R.layout.activity_view_information);

        txtUsi = (EditText) findViewById(R.id.txtUsiNumber);
        txtCitNum = (EditText) findViewById(R.id.txtCitNumber);
        txtAnpName = (EditText) findViewById(R.id.txtAnpName);
        txtAnpPhone = (EditText) findViewById(R.id.txtAnpPhone);
        txtAnpEmail = (EditText) findViewById(R.id.txtAnpEmail);
        txtDeptName = (EditText) findViewById(R.id.txtDeptName);
        txtDeptPhone = (EditText) findViewById(R.id.txtDeptPhone);
        txtDeptEmail = (EditText) findViewById(R.id.txtDeptEmail);
        txtLlnDate = (EditText) findViewById(R.id.txtLlnDate);
        RPLlbl = (TextView) findViewById(R.id.lblRPL);
        TPClbl = (TextView) findViewById(R.id.lblTPC);
        //not yet implemented

        //EditText ClassStartTxt = (EditText) findViewById(R.id.txtClassStart);

        editBtn = (Button) findViewById(R.id.btnEdit);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInformation.this, EditInformation.class));
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
