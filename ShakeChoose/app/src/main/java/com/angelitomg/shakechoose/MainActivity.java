package com.angelitomg.shakechoose;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {

                EditText editText = (EditText) findViewById(R.id.edit_message);
                String content = editText.getText().toString();
                String[] rows = content.split("\\n");
                Random rand = new Random();
                int index = rand.nextInt(rows.length);

                if (rows.length > 1) {
                    Toast.makeText(getApplicationContext(), rows[index], Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Você deve informar pelo menos 2 itens", Toast.LENGTH_SHORT).show();
                }

            }

        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Uri uri = Uri.parse( "http://www.angelitomg.com");
            startActivity(new Intent( Intent.ACTION_VIEW, uri ));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
