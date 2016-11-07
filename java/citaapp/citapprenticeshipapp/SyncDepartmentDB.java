package citaapp.citapprenticeshipapp;

/**
 * Created by Donald on 3/11/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Background Async Task to get database info by making HTTP Request to PHP file on server
 * */
class SyncDepartmentDB extends AsyncTask<String, String, JSONObject> { //<parameters, progress, result>

    //this is the URL for the PHP file that will get our SQL data for CIT departments from the server we've set up..

    //"http://192.168.1.8:80/android_connect/get_details.php" is for my home PC - Donald
    //"http://10.0.2.2:80/android_connect/get_details.php" should work with emulator in the lab
    private static final String URL_GET_DETAILS = "http://10.0.2.2:80/android_connect/get_details.php";


    private static final String TAG_SUCCESS = "success"; //this is used to check whether data was received correctly

    private static final String TAG_MESSAGE = "message"; //I haven't used this so far


    //this object will make the HTTP connection to the PHP file and parse the results in JSON format
    //  Java Script Object Notation
    JSONParser jParser;

    Activity myActivity;    //this is a reference to the Activity that called SyncDepartmentDB
    ProgressDialog pDialog; //this is a progress dialog.
    LocalDBHandler localDB; //this object will connect to the local DB to save the results

    public SyncDepartmentDB(Activity inActivity) {
        super();
        // do stuff
        myActivity = inActivity;
        jParser = new JSONParser();
        localDB = new LocalDBHandler(myActivity);
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(myActivity);
        pDialog.setMessage("Loading departments. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * getting All details from url
     * */
    @Override
    protected JSONObject doInBackground(String... args) {

        // Building Parameters
        HashMap<String, String> params = new HashMap<String, String>();

        //debug log
        Log.d("HTTP Request", "Starting");

        // getting JSON string from URL
        try {
            JSONObject jsonResult = jParser.makeHttpRequest(URL_GET_DETAILS, "GET", params);
            if (jsonResult != null) {
                Log.d("JSON SyncDepartmentDB: ", jsonResult.toString());

                int success = jsonResult.getInt(TAG_SUCCESS);

                if (success==1)
                {
                    pDialog.setMessage("Department data received, saving to local database...");

                    localDB.clearTable();

                    JSONArray jsonArray = jsonResult.getJSONArray("departments");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject c = jsonArray.getJSONObject(i);
                        //int id = c.getInt("dept_id");
                        String name = c.optString("dept_name");
                        String phone = c.optString("dept_phone");
                        String email = c.optString("dept_email");

                        localDB.addDept(new CITDepartment(name, phone, email));
                    }
                }
                return jsonResult;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null; //if we didn't get a result
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();

        if (json != null)
            Toast.makeText(myActivity, json.toString(), Toast.LENGTH_LONG).show();
    }
}
