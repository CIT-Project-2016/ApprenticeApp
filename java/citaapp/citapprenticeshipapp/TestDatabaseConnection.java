//package citaapp.citapprenticeshipapp;
//
//import android.util.Log;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestDatabaseConnection extends AppCompatActivity {
//
//    Button btnConnectToDb;
//    Button btnTestLocalDB;
//
//    Spinner spnList;
//
//    TextView lblDeptPhone;
//    TextView lblDeptEmail;
//
//    DBHandler dbHandler;
//
//    //List<CITDepartment> depts;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_database_connection);
//
//        dbHandler = new DBHandler(this);
//
//        spnList = (Spinner) findViewById(R.id.spnList);
//
//        dbHandler.initSpinner(spnList);
//
//        lblDeptEmail = (TextView) findViewById(R.id.lblDeptEmail);
//        lblDeptPhone = (TextView) findViewById(R.id.lblDeptPhone);
//
//        btnConnectToDb = (Button) findViewById(R.id.btnConnectToDb);
//        btnConnectToDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //new SyncDepartmentDB(TestDatabaseConnection.this).execute();
//                dbHandler.syncDB();
//            }
//        });
//
//        btnTestLocalDB = (Button) findViewById(R.id.btnTestLocalDB);
//        btnTestLocalDB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        spnList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // your code here
//                lblDeptPhone.setText(dbHandler.getDeptsList().get(position).getPhone());
//                lblDeptEmail.setText(dbHandler.getDeptsList().get(position).getEmail());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//        });
//    }
//}
//
//
//    /*
//    void printOutput(String inStrJson)
//    {
//        String data = "";
//        try {
//            jsonRootObject = new JSONObject(inStrJson);
//            //Get the instance of JSONArray that contains JSONObjects
//            //JSONArray jsonArray1 = jsonRootObject.optJSONArray("Employee");
//            jsonArray = jsonRootObject.optJSONArray("Employee");
//
//            //Iterate the jsonArray and print the info of JSONObjects
//            for(int i=0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                int id = Integer.parseInt(jsonObject.optString("id").toString());
//                String name = jsonObject.optString("name").toString();
//                float salary = Float.parseFloat(jsonObject.optString("salary").toString());
//
//                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n ";
//            }
//            txtOutput.setText(data);
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//    }
//    */