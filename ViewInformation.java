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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ViewInformation extends AppCompatActivity {

    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);

        EditText txtUsi = (EditText) findViewById(R.id.txtUsiNumber);

        EditText txtCitNum = (EditText) findViewById(R.id.txtCitNumber);

        EditText txtAnpName = (EditText) findViewById(R.id.txtAnpName);

        EditText anpPhoneTxt = (EditText) findViewById(R.id.txtAnpPhone);

        EditText txtAnpEmail = (EditText) findViewById(R.id.txtAnpEmail);

        EditText txtDeptName = (EditText) findViewById(R.id.txtDeptName);

        EditText txtDeptPhone = (EditText) findViewById(R.id.txtDeptPhone);

        EditText txtDeptEmail = (EditText) findViewById(R.id.txtDeptEmail);

        EditText LLNTxt = (EditText) findViewById(R.id.txtLLN);

        TextView RPLlbl = (TextView) findViewById(R.id.lblRPL);

        TextView TPClbl = (TextView) findViewById(R.id.lblTPC);

        EditText ClassStartTxt = (EditText) findViewById(R.id.txtClassStart);


        //String filename = "informationFile";

        //FileInputStream inputStream;

        preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        final Button editBtn = (Button) findViewById(R.id.btnEdit);




        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInformation.this, EditInformation.class));
            }
        });


        try
        {
            /*inputStream = openFileInput(filename);
            if(inputStream !=null){
                InputStreamReader info = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(info);

                String str = "";

                StringBuilder buf=new StringBuilder();
                while ((str = reader.readLine()) != null) {

                    buf.append(str);

            }

            inputStream.close();*/


            txtUsi.setText(preferenceSettings.getString("USI", "Not Found"));
            txtCitNum.setText(preferenceSettings.getString("CIT Number", "Not Found"));

            txtAnpName.setText(preferenceSettings.getString("ANP Name", "Not Found"));
            anpPhoneTxt.setText(preferenceSettings.getString("ANP Phone", "Not Found"));
            txtAnpEmail.setText(preferenceSettings.getString("ANP Email", "Not Found"));

            txtDeptName.setText(preferenceSettings.getString("Department Name", "Not Found"));
            txtDeptPhone.setText(preferenceSettings.getString("Department Phone", "Not Found"));
            txtDeptEmail.setText(preferenceSettings.getString("Department Email", "Not Found"));


            //these ones aren't done yet
            LLNTxt.setText(preferenceSettings.getString("LLN Completion", "Not Found"));
            RPLlbl.setText(preferenceSettings.getString("RPL", "Not Found"));
            TPClbl.setText(preferenceSettings.getString("Training Plan", "Not Found"));
            ClassStartTxt.setText(preferenceSettings.getString("Class Start Date", "Not Found"));



            // txtUsi.setText(buf.toString(), TextView.BufferType.EDITABLE);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
