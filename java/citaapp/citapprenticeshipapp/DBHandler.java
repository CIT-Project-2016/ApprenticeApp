package citaapp.citapprenticeshipapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Donald on 7/11/2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "deptDB.db";
    private static final String TABLE_NAME = "departments";
    private static final String KEY_ID = "dept_id";
    private static final String KEY_NAME = "dept_name";
    private static final String KEY_PHONE = "dept_phone";
    private static final String KEY_EMAIL = "dept_email";

    private Context context; //will be the context that is calling this localdbhandler(eg. an activity)
    private Spinner spnDepts;

    private List<CITDepartment> allDepts = new ArrayList<CITDepartment>();

    private List<String> lstName = new ArrayList<String>();

    //private SQLiteDatabase db;

    public DBHandler(Context inContext) {
        super(inContext, DATABASE_NAME, null, DATABASE_VERSION);
        context = inContext;

        //clearLocalTable();
        //db = this.getWritableDatabase(); //local database
        syncDbIfEmpty(); // gets data from server db and puts in local db if local db is empty
        Log.d("DBHandler", "Constructor.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate","...");
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
                    + KEY_PHONE + " TEXT, " + KEY_EMAIL + " TEXT" + ")";
            Log.d("creating table", createTable);
            db.execSQL(createTable);

            //db.close();
            syncDbIfEmpty();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clearLocalTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("clear table", "clearing local table");
        db.delete(TABLE_NAME, null, null);

        db.close();
    }

    public void addRowToLocalDb(CITDepartment inDept)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, dept.getId());
        values.put(KEY_NAME, inDept.getName());
        values.put(KEY_PHONE, inDept.getPhone());
        values.put(KEY_EMAIL, inDept.getEmail());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void initSpinner(Spinner inSpinner)
    {
        //Setting the local private Spinner object to the spinner passed in to this method.

        Log.d("initSpinner", "...");
        spnDepts = inSpinner;

        if (hasTableData())
        {
            List<String> lstName = new ArrayList<String>();

            for (CITDepartment dept : getDeptsList())
            {
                lstName.add(dept.getName());

//            String log = "Id: " + dept.getId() + ", Name: " + dept.getName()
//                    + ", Phone: " + dept.getPhone() + ", Email: " + dept.getEmail();
//            Log.d("Department", log);
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, lstName);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spnDepts.setAdapter(dataAdapter);
            Log.d("initSpinner", "data adapter set.");
        }
        else
        {
            Log.d("initSpinner", "can not set data adapter yet...");
        }

    }

    public List<String> getDeptNames()
    {
        //TODO
        // return list of string department names

        return null;
    }

    public boolean hasTableData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);

        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount > 0)
        {
            //table has data
            Log.d("hasTableData result", "Table has data.");
            db.close();
            return true;
        }
        else
        {
            //table is empty
            Log.d("hasTableData result", "Table is empty.");
            db.close();
            return false;
        }
    }

    public List<CITDepartment> getDeptsList()
    {
        if (allDepts.isEmpty())
        {
            Log.d("allDepts is empty", "populating depts list");
            populateDeptsList();
        }

        return allDepts;
    }

    public void populateDeptsList()
    {
        List<CITDepartment> tempDeptsList = new ArrayList<CITDepartment>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CITDepartment dept = new CITDepartment();
                dept.setID(cursor.getInt(0));
                dept.setName(cursor.getString(1));
                dept.setPhone(cursor.getString(2));
                dept.setEmail(cursor.getString(3));

                tempDeptsList.add(dept);
            } while (cursor.moveToNext());
        }

        db.close();

        allDepts = tempDeptsList;
    }

//    public List<CITDepartment> getAllDepartments()
//    {
//        List<CITDepartment> deptList = new ArrayList<CITDepartment>();
//
//        String selectQuery = "SELECT * FROM " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                CITDepartment dept = new CITDepartment();
//                dept.setID(cursor.getInt(0));
//                dept.setName(cursor.getString(1));
//                dept.setPhone(cursor.getString(2));
//                dept.setEmail(cursor.getString(3));
//
//                deptList.add(dept);
//            } while (cursor.moveToNext());
//        }
//
//        db.close();
//        return deptList;
//    }

    public void syncDbIfEmpty()
    {
        Log.d("SyncDBIfEmpty", "...");
        if (!hasTableData())
        {
            Log.d("Calling SyncDB", "From syncDbIfEmpty");
            syncDb();
        }
        else
            Log.d("table has data", "From syncDbIfEmpty");

    }

    //calls the Asynchronous class below - does db connection stuff on separate thread
    public void syncDb()
    {
        Log.d("Calling new SyncDeptDB", ".execute");
        new SyncDeptDB().execute();
    }

    /**
     * Background Async Task to get database info by making HTTP Request to PHP file on server
     * */
    class SyncDeptDB extends AsyncTask<String, String, Boolean> { //<parameters, progress, result>

        //this is the URL for the PHP file that will get our SQL data for CIT departments from the server we've set up..


        //"http://192.168.1.8:80/android_connect/get_details.php" is for my home PC - Donald
        //"http://10.0.2.2:80/android_connect/get_details.php" should work with emulator in the lab
        private static final String URL_GET_DETAILS = "http://10.0.2.2:80/android_connect/get_details.php";

        private static final String TAG_SUCCESS = "success"; //this is used to check whether data was received correctly

        private static final String TAG_MESSAGE = "message"; //I haven't used this so far

        //this object will make the HTTP connection to the PHP file and parse the results in JSON format
        //  Java Script Object Notation
        JSONParser _jsonParser;

        //Context myActivity;    //this is a reference to the Activity that called SyncDepartmentDB
        ProgressDialog pDialog; //this is a progress dialog.

        public SyncDeptDB() {
            super();
            // do stuff
            _jsonParser = new JSONParser();
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading departments. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All details from url
         * */
        @Override
        protected Boolean doInBackground(String... args) {

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            //build parameters here if using them..

            //debug log
            Log.d("HTTP Request", "Starting");

            // getting JSON string from URL
            try {

                JSONObject jsonResult = _jsonParser.makeHttpRequest(URL_GET_DETAILS, "POST", params);

                if (jsonResult != null) {
                    Log.d("JSON SyncDepartmentDB: ", jsonResult.toString());

                    int success = jsonResult.getInt(TAG_SUCCESS);

                    if (success==1)
                    {
                        pDialog.setMessage("Department data received, saving to local database...");

                        clearLocalTable();

                        JSONArray jsonArray = jsonResult.getJSONArray("departments");

                        Log.d("Populating Local table", "...");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject c = jsonArray.getJSONObject(i);
                            //int id = c.getInt("dept_id");
                            String name = c.optString("dept_name");
                            String phone = c.optString("dept_phone");
                            String email = c.optString("dept_email");

                            addRowToLocalDb(new CITDepartment(name, phone, email));
                        }
                    }
                    return true;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return false; //if we didn't get a result
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Boolean success) {

            if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();

            if (success)
            {
                Toast.makeText(context, "Department info updated...", Toast.LENGTH_LONG).show();

                populateDeptsList();
                initSpinner(spnDepts);
            }
            else
                Toast.makeText(context, "Sorry, unable to connect to CIT Department database...", Toast.LENGTH_LONG).show();

        }
    }
}
