package citaapp.citapprenticeshipapp;


import android.app.Activity;
import android.util.Log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestDatabaseConnection extends AppCompatActivity {

    Button btnConnectToDb;
    Button btnTestLocalDB;

    Spinner spnList;

    TextView txtOutput;

    LocalDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database_connection);
        txtOutput = (TextView) findViewById(R.id.txtOutput);

        btnConnectToDb = (Button) findViewById(R.id.btnConnectToDb);
        btnConnectToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SyncDepartmentDB(TestDatabaseConnection.this).execute();
            }
        });

        final Activity me = this;
        db = new LocalDBHandler(this);
        spnList = (Spinner) findViewById(R.id.spnList);

        btnTestLocalDB = (Button) findViewById(R.id.btnTestLocalDB);
        btnTestLocalDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Department", "Getting results...");
                List<String> lstName = new ArrayList<String>();
                List<CITDepartment> depts = db.getAllDepartments();

                for (CITDepartment dept : depts)
                {
                    lstName.add(dept.getName());

                    String log = "Id: " + dept.getId() + ", Name: " + dept.getName()
                            + ", Phone: " + dept.getPhone() + ", Email: " + dept.getEmail();

                    Log.d("Department", log);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(me,
                        android.R.layout.simple_spinner_item, lstName);

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnList.setAdapter(dataAdapter);
            }
        });





    }
}


    /*
    void printOutput(String inStrJson)
    {
        String data = "";
        try {
            jsonRootObject = new JSONObject(inStrJson);
            //Get the instance of JSONArray that contains JSONObjects
            //JSONArray jsonArray1 = jsonRootObject.optJSONArray("Employee");
            jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String name = jsonObject.optString("name").toString();
                float salary = Float.parseFloat(jsonObject.optString("salary").toString());

                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n ";
            }
            txtOutput.setText(data);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    */