package com.rabbit.regularmeeting;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lineGraphButton = (Button) findViewById(R.id.lineGraphButton);
        lineGraphButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent lineGraphIntent = new Intent(MainActivity.this, LineGraphActivity.class);
                //MainActivity.this.startActivity(lineGraphIntent);
                Intent intent = new Intent(getApplicationContext(), LineGraphActivity.class);
                startActivity(intent);
            }
        });

        Button circleGraphButton = (Button) findViewById(R.id.circleGraphButton);
        circleGraphButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent circleGraphIntent = new Intent(MainActivity.this, CircleGraphActivity.class);
                //MainActivity.this.startActivity(circleGraphIntent);
                Intent intent = new Intent(getApplicationContext(), CircleGraphActivity.class);
                startActivity(intent);
            }
        });
    }
}
