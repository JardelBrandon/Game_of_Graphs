package com.example.robotica.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button buttonCrossfadeDrawer = (Button) findViewById(R.id.button_crossfade_drawer);
        buttonCrossfadeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCrossfadeDrawer = new Intent(getApplicationContext(), CrossfadeDrawerLayoutActvitiy.class);
                startActivity(intentCrossfadeDrawer);
            }
        });

        Button buttonMiniDrawer = (Button) findViewById(R.id.button_mini_drawer);
        buttonMiniDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMiniDrawer = new Intent(getApplicationContext(), MiniDrawerActivity.class);
                startActivity(intentMiniDrawer);
            }
        });
    }
}
