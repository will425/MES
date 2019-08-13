package com.rabbit.regularmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;

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
                Intent lineGraphIntent = new Intent(MainActivity.this, CircleGraphActivity.class);
                MainActivity.this.startActivity(lineGraphIntent);
            }
        });

        Button circleGraphButton = (Button) findViewById(R.id.circleGraphButton);
        circleGraphButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent circleGraphIntent = new Intent(MainActivity.this, CircleGraphActivity.class);
                MainActivity.this.startActivity(circleGraphIntent);
            }
        });

    }
}
