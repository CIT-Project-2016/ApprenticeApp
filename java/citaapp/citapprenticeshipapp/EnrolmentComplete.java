package citaapp.citapprenticeshipapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EnrolmentComplete extends AppCompatActivity {

    Button btnBack, btnHome;
    ImageButton btnLinkToElucian, btnLinkToCitsa;

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

        btnLinkToElucian = (ImageButton) findViewById(R.id.btnLinkToElucian);
        btnLinkToElucian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String APP_PACKAGE_NAME = "com.ellucian.elluciango";
                openInPlayStore(APP_PACKAGE_NAME);
            }
        });

        btnLinkToCitsa = (ImageButton) findViewById(R.id.btnLinkToCitsa);
        btnLinkToCitsa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String APP_PACKAGE_NAME = "pokit.campus.citsa";
                openInPlayStore(APP_PACKAGE_NAME);
            }
        });
    }

    private void openInPlayStore(final String APP_PACKAGE_NAME)
    {
        try {
            //Open in Google Play store
            Log.d("trying open", "in Play Store");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PACKAGE_NAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
            //If cannot open Google Play store, open in browser.
            Log.d("no play store", "opening in browser");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + APP_PACKAGE_NAME)));
        }
    }
}


