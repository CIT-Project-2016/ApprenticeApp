package citaapp.citapprenticeshipapp;


import android.app.Activity;
import android.util.Log;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestDatabaseConnection extends AppCompatActivity {

    // Progress Dialog
    //private ProgressDialog pDialog;

    // Creating JSON Parser object
    //JSONParser jParser = new JSONParser();

    //ArrayList<HashMap<String, String>> productsList;

    Button btnViewDepartments;
    Button btnTestLocalDB;
    TextView txtOutput;

    LocalDBHandler db;
    //JSONArray jsonArray;
    //JSONObject jsonRootObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database_connection);
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        String strJson = "{\"Employee\" :[" +
                "{\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"}," +
                "{\"id\":\"02\",\"name\":\"Sairamkrishna\",\"salary\":\"500000\"}," +
                "{\"id\":\"03\",\"name\":\"Sathish kallakuri\",\"salary\":\"600000\"}" +
                "]}";

        //printOutput(strJson);
        //new GetDetails(this).execute();

        btnViewDepartments = (Button) findViewById(R.id.btnViewDepartments);
        btnViewDepartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetAllDetails(TestDatabaseConnection.this).execute();
            }
        });

        db = new LocalDBHandler(this);
        Log.d("ok: ", "did it work?");

        btnTestLocalDB = (Button) findViewById(R.id.btnTestLocalDB);
        btnTestLocalDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Insert: ", "Inserting ..");
                //db.addDept(new CITDepartment("hello", "02954544", "23423@gtrni.com"));

                List<CITDepartment> depts = db.getAllDepartments();
                for (CITDepartment dept : depts)
                {
                    String log = "Id: " + dept.getId() + ", Name: " + dept.getName()
                            + ", Phone: " + dept.getPhone() + ", Email: " + dept.getEmail();

                    Log.d("Dept: : ", log);
                }
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