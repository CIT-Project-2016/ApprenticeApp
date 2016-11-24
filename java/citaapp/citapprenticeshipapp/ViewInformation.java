package citaapp.citapprenticeshipapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow;

public class ViewInformation extends AppCompatActivity
{
    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;

    EditText txtUsi, txtCitNum, txtAnpName, txtAnpPhone, txtAnpEmail, txtDeptName, txtDeptPhone, txtDeptEmail, txtLlnDate, txtClassDate, txtEditNote;

    TextView RPLlbl, TPClbl;

    Button editBtn, BackBtn, btnSaveNote, btnCancelNote;

    LayoutInflater layoutInflater;
    PopupWindow popupNotepad;

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
            txtClassDate.setText(preferenceSettings.getString("Class Start Date", "Not Found"));

            RPLlbl.setText(preferenceSettings.getString("RPL", "No"));
            TPClbl.setText(preferenceSettings.getString("Training Plan", "No"));

            txtEditNote.setText(preferenceSettings.getString("Notepad", "Notepad"));

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveToPrefs(String key, String value)
    {
        preferenceSettings = getSharedPreferences("myInfo",Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        preferenceEditor.putString(key, value);
        preferenceEditor.apply();
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
        txtClassDate = (EditText) findViewById(R.id.txtClassDate);

        RPLlbl = (TextView) findViewById(R.id.lblRPL);
        TPClbl = (TextView) findViewById(R.id.lblTPC);

        txtEditNote = (EditText) findViewById(R.id.txtEditNote);



        editBtn = (Button) findViewById(R.id.btnEdit);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInformation.this, EditInformation.class));
            }
        });

        BackBtn = (Button) findViewById(R.id.btnBack);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtEditNote.setOnClickListener( new View.OnClickListener(){
            @Override
             public void onClick(View v) {
                //creates a popup Window
                layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup)layoutInflater.inflate(R.layout.popup_notepad,null);
                popupNotepad = new PopupWindow(container, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT,true);

                popupNotepad.showAtLocation(v, Gravity.CENTER,0,0);
                //get contents from edit text

                final EditText notepadEditText;
                notepadEditText = ((EditText)popupNotepad.getContentView().findViewById(R.id.editTextNotepad));
                notepadEditText.setText(txtEditNote.getText());

                btnCancelNote = (Button)popupNotepad.getContentView().findViewById(R.id.btnCancelNote);
                btnSaveNote = (Button)popupNotepad.getContentView().findViewById(R.id.btnSaveNote);
                btnCancelNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        popupNotepad.dismiss();
                    }
                });

                btnSaveNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            saveToPrefs("Notepad", notepadEditText.getText().toString());
                            txtEditNote.setText(notepadEditText.getText());
                            popupNotepad.dismiss();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
