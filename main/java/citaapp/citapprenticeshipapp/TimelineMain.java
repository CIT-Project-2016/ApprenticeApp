package citaapp.citapprenticeshipapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TimelineMain extends AppCompatActivity {

    Button btnApprenticeship, btnContract, btnEnrolment, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_main);


        //Apprenticeship Button
        btnApprenticeship = (Button) findViewById(R.id.btnApprenticeship);
        btnApprenticeship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, ApprenticeshipBegin.class));
            }
        });

        //Contract Button
        btnContract = (Button) findViewById(R.id.btnContract);
        btnContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, ContractSignContract.class));
            }
        });


        //Enrolment Button
        btnEnrolment = (Button) findViewById(R.id.btnEnrolment);
        btnEnrolment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, EnrolmentUSI.class));
            }
        });

        //Home Button
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineMain.this, MainActivity.class));
            }
        });

    }
}

