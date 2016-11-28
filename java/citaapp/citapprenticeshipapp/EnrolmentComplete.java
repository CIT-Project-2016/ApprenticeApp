package citaapp.citapprenticeshipapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EnrolmentComplete extends AppCompatActivity {

    Button btnBack, btnHome;
    Button btnLinkToElucianGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolment_complete);

        //Back Button
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ApprenticeshipBegin.this, TimelineMain.class));
                finish();
            }
        });

        //Home Button
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnrolmentComplete.this, MainActivity.class));
            }
        });

        btnLinkToElucianGo = (Button) findViewById(R.id.btnLinkToElucianGo);
        btnLinkToElucianGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openElucianGo();
            }
        });
    }

    private void openElucianGo()
    {
        final String APP_PACKAGE_NAME = "com.ellucian.elluciango"; // getPackageName() from Context or Activity object
        try {
            //Open in Google Play store
            Log.d("trying open: ", "in Play Store");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PACKAGE_NAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
            //If cannot open Google Play store, open in browser.
            Log.d("trying open: ", "in browser");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + APP_PACKAGE_NAME)));
        }
    }
}
