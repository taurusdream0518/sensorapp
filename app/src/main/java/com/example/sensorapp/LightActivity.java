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

public class LightActivity extends AppCompatActivity {

    private Context context;
    private TextView textViewResult;
    private ImageView imageViewPic;
    private SensorManager sensorManger;
    private MyLightener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        setTitle("Light sensor");
        context = this;

        textViewResult = (TextView) findViewById(R.id.textView_lightResult);
        textViewResult.setText("");
        imageViewPic = (ImageView) findViewById(R.id.imageView_lightPic);

        sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
        //把元件呼叫進來
        Sensor sensor = sensorManger.getDefaultSensor(Sensor.TYPE_LIGHT);
        //因為需要關閉 所以自己建一個監聽
        myListener = new MyLightener();
        sensorManger.registerListener(myListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private class MyLightener implements SensorEventListener {


        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuilder sb = new StringBuilder();
            sb.append("sensor : "+event.sensor.getName()+"\n");
            float luxValue = event.values[0];
            Log.d("light","luxValue = "+luxValue);
            sb.append("lux value = "+luxValue+"\n");
            textViewResult.setText(sb.toString());

            if(luxValue <= 40){
                imageViewPic.setImageResource(R.drawable.dark_light);
            } else {
                imageViewPic.setImageResource(R.drawable.imageslight);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    //關閉監聽
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManger.unregisterListener(myListener);
    }
}