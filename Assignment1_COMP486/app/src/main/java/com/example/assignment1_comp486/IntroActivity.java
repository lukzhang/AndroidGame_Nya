package com.example.assignment1_comp486;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

//This is the intro screen to the game
public class IntroActivity extends Activity
        implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Get a reference to the button in our layout
        final Button buttonPlay=(Button)findViewById(R.id.btnContinue);

        //Listen for clicks
        buttonPlay.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //Must be the Play button
        //Create a new Intent object
        Intent i = new Intent(this, PlatformActivity.class);
        //Start our GameActivity class via the Intent
        startActivity(i);
        //Now shut this activity down
        finish();
    }

    //If the player hits the back button, quit the app
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }

}
