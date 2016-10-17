package citaapp.citapprenticeshipapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TimelineMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_main);


        //Apprenticeship Button
        Button btnApprenticeship = (Button) findViewById(R.id.btnApprenticeship);
        btnApprenticeship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, ApprenticeshipBegin.class));
            }
        });




        //Enrolment Button
        Button btnEnrolment = (Button) findViewById(R.id.btnEnrolment);
        btnEnrolment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, EnrolmentUSI.class));
            }
        });

        //Home Button
        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, MainActivity.class));
            }
        });

    }
}

