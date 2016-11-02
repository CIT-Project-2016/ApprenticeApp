package citaapp.citapprenticeshipapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TestDatabaseConnection extends AppCompatActivity {

    // Progress Dialog
    //private ProgressDialog pDialog;

    // Creating JSON Parser object
    //JSONParser jParser = new JSONParser();

    //ArrayList<HashMap<String, String>> productsList;

    TextView txtOutput;

    JSONArray jsonArray;
    JSONObject jsonRootObject;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database_connection);

        txtOutput = (TextView) findViewById(R.id.txtOutput);
        String strJson="{\"Employee\" :[" +
                "{\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"}," +
                "{\"id\":\"02\",\"name\":\"Sairamkrishna\",\"salary\":\"500000\"}," +
                "{\"id\":\"03\",\"name\":\"Sathish kallakuri\",\"salary\":\"600000\"}" +
                "]}";

        /*String strJson= "{\n"
                    +"  \"Employee\" :[ \n"
                    +"  {\n"
                    +"     \"id\":\"01\",\n"
                    +"     \"name\":\"Gopal Varma\",\n"
                    +"      \"salary\":\"500000\"\n"
                    +"  },\n"
                    +" {\n"
                    +"      \"id\":\"02\",\n"
                    +"     \"name\":\"Sairamkrishna\",\n"
                    +"     \"salary\":\"500000\"\n"
                    +" },\n"
                    +" {\n"
                    +"    \"id\":\"03\",\n"
                    +"   \"name\":\"Sathish kallakuri\",\n"
                    +"    \"salary\":\"600000\"\n"
                    +" }\n"
                    +" ]\n"
                    +"}";*/

        printOutput(strJson);

    }

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
}
