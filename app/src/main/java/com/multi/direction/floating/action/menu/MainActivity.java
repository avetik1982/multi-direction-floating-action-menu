package com.multi.direction.floating.action.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RotatingFloatingActionButton rotatingFloatingActionButton = (RotatingFloatingActionButton) findViewById(R.id.main_fab);
        rotatingFloatingActionButton.addSubFloatingActionButtons(((SubFloatingActionButton)findViewById(R.id.sub_fab1)));
        rotatingFloatingActionButton.addSubFloatingActionButtons(((SubFloatingActionButton)findViewById(R.id.sub_fab2)));
        rotatingFloatingActionButton.addSubFloatingActionButtons(((SubFloatingActionButton)findViewById(R.id.sub_fab3)));
        rotatingFloatingActionButton.addSubFloatingActionButtons(((SubFloatingActionButton)findViewById(R.id.sub_fab4)));
    }
}
