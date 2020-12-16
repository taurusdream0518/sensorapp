package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ProximityActivity extends AppCompatActivity {

    private TextView textViewResult;
    private ImageView imageViewPic;
    private Context context;
    private SensorManager sensorManger;
    private MyProximityListener myListener;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        setTitle("Proximity sensor");
        context = this;

        textViewResult = (TextView) findViewById(R.id.textView_proResult);
        textViewResult.setText("");
        imageViewPic = (ImageView) findViewById(R.id.imageView_proPic);
        sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManger.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        myListener = new MyProximityListener();
        sensorManger.registerListener(myListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private class MyProximityListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuilder sb = new StringBuilder();
            sb.append("sensor : "+event.sensor.getName()+"\n");
            float proValue = event.values[0];
            Log.d("pro","proValue = "+proValue);
            sb.append("pro value = "+proValue+"\n");
            textViewResult.setText(sb.toString());

            if(proValue <= 1){
                textViewResult.append("\nToo close");
                imageViewPic.setImageResource(R.drawable.p2);
            } else {
                imageViewPic.setImageResource(R.drawable.p1);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManger.unregisterListener(myListener);
    }
}