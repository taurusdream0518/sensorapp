package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccActivity extends AppCompatActivity {

    private Context context;
    private TextView textViewResult;
    private ImageView imageViewTop,imageViewRight,imageViewLeft,imageViewDown;
    private SensorManager sensorManger;
    private MyAccListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);

        setTitle("Acc sensor");
        context = this;
        //設定螢幕是直立的

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textViewResult = (TextView) findViewById(R.id.textView_accResult);
        textViewResult.setText("");

        imageViewTop = (ImageView) findViewById(R.id.imageView_top);
        imageViewRight = (ImageView) findViewById(R.id.imageView_right);
        imageViewLeft = (ImageView) findViewById(R.id.imageView_left);
        imageViewDown = (ImageView) findViewById(R.id.imageView_down);

        imageViewTop.setVisibility(View.INVISIBLE);
        imageViewRight.setVisibility(View.INVISIBLE);
        imageViewLeft.setVisibility(View.INVISIBLE);
        imageViewDown.setVisibility(View.INVISIBLE);

        sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myListener = new MyAccListener();
        sensorManger.registerListener(myListener,sensor,SensorManager.SENSOR_DELAY_UI);

    }

    private class MyAccListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuilder sb = new StringBuilder();
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];
            sb.append("X value = "+xValue+"\n");
            sb.append("Y value = "+yValue+"\n");
            sb.append("Z value = "+zValue+"\n");

            textViewResult.setText(sb.toString());

            if(xValue <-4.0){
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.VISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
            } else if(xValue > 4.0) {
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.VISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
            } else {
                if(zValue >9){
                    imageViewTop.setVisibility(View.VISIBLE);
                    imageViewRight.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewDown.setVisibility(View.INVISIBLE);
                } else if(zValue <7){
                    imageViewTop.setVisibility(View.INVISIBLE);
                    imageViewRight.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewDown.setVisibility(View.VISIBLE);
                } else {

                    imageViewTop.setVisibility(View.INVISIBLE);
                    imageViewRight.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewDown.setVisibility(View.INVISIBLE);
                }
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