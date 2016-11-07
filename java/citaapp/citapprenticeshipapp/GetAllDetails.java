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
class GetAllDetails extends AsyncTask<String, String, JSONObject> { //<parameters, progress, result>

    JSONParser jParser = new JSONParser();

    //"http://192.168.1.8:80/android_connect/get_details.php" is for my home PC - Donald
    //"http://10.0.2.2:80/android_connect/get_details.php" should work with emulator in the lab
    private static final String URL_GET_DETAILS = "http://10.0.2.2:80/android_connect/get_details.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";



    Activity myActivity;
    ProgressDialog pDialog;
    LocalDBHandler localDB;


    public GetAllDetails(Activity inActivity) {
        super();
        // do stuff
        myActivity = inActivity;
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

        Log.d("request", "starting");

        // getting JSON string from URL
        try {
            JSONObject json = jParser.makeHttpRequest(URL_GET_DETAILS, "GET", params);
            if (json != null) {
                Log.d("JSON result", json.toString());

                int success = json.getInt(TAG_SUCCESS);

                if (success==1)
                {
                    localDB.clearTable();
                    JSONArray jsonArray = json.getJSONArray("departments");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        //int id = c.getInt("dept_id");
                        String name = c.getString("dept_name");
                        String phone = c.getString("dept_phone");
                        String email = c.getString("dept_email");

                        localDB.addDept(new CITDepartment(name, phone, email));
                    }
                }
                return json;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(JSONObject json) {

        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();

        if (json != null) {
            Toast.makeText(myActivity, json.toString(),
                    Toast.LENGTH_LONG).show();

        }
    }
}
