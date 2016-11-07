package citaapp.citapprenticeshipapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manager on 7/11/2016.
 */

public class LocalDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "deptDB.db";
    private static final String TABLE_NAME = "departments";
    private static final String KEY_ID = "dept_id";
    private static final String KEY_NAME = "dept_name";
    private static final String KEY_PHONE = "dept_phone";
    private static final String KEY_EMAIL = "dept_email";

    public LocalDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
                    + KEY_PHONE + " TEXT, " + KEY_EMAIL + " TEXT" + ")";
            Log.d("creating table: ", createTable);
            db.execSQL(createTable);
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

    public void clearTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDept(CITDepartment dept) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, dept.getId());
        values.put(KEY_NAME, dept.getName());
        values.put(KEY_PHONE, dept.getPhone());
        values.put(KEY_EMAIL, dept.getEmail());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<CITDepartment> getAllDepartments() {
        List<CITDepartment> deptList = new ArrayList<CITDepartment>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        StringBuilder sbColumnOutput = new StringBuilder();
        String[] columns = cursor.getColumnNames();

        int a = 1;
        for( String column : columns)
        {
            sbColumnOutput.append(column);
            sbColumnOutput.append(": ");
            //sbColumnOutput.append(cursor.getType(2));
            sbColumnOutput.append(", ");
            a++;
        }

        String columnOutput = sbColumnOutput.toString();
         Log.d("Columns:", columnOutput);

        if (cursor.moveToFirst()) {
            do {
                CITDepartment dept = new CITDepartment();
                dept.setID(cursor.getInt(0));
                dept.setName(cursor.getString(1));
                dept.setPhone(cursor.getString(2));
                dept.setEmail(cursor.getString(3));

                deptList.add(dept);
            } while (cursor.moveToNext());
        }

        db.close();
        return deptList;
    }
}
