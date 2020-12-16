package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ListView listViewSensor;
    private SensorManager sensorManger;
    private List<Sensor> sensorList;
    private List<String> listInfo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        listViewSensor = (ListView) findViewById(R.id.listView_id);
        //TODO:Sensor 設定
        sensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //取出sensor資料
        sensorList = sensorManger.getSensorList(Sensor.TYPE_ALL);
        listInfo = new ArrayList<String>();
        //不知道有多少sensor 所以用foreach
        for(Sensor sensor : sensorList){
            String msg = sensor.getType()+" - "+sensor.getName()+" : "+sensor.getVendor()+"\n";
            Log.d("main","semsor msg = "+msg);
            listInfo.add(sensor.getType()+" - "+sensor.getName()+" : \n"+sensor.getVendor());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listInfo);
        listViewSensor.setAdapter(adapter);
        //查看手機sensor數量
        setTitle("Sensor number : "+listInfo.size());
        //TODO: listView監聽
        listViewSensor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sensor sensorItem = sensorList.get(position);
                int seneorType = sensorItem.getType();
                Log.d("main","sensorType = "+seneorType);

                switch (seneorType){
                    //光線
                    case Sensor.TYPE_LIGHT:
                        Sensor sensor = sensorManger.getDefaultSensor(Sensor.TYPE_LIGHT);
                        if(sensor == null){
                            Toast.makeText(context,"There is no light sensor.",Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(context,LightActivity.class);
                            startActivity(intent);
                        }

                        break;
                    //距離
                    case  Sensor.TYPE_PROXIMITY:
                        intent = new Intent(context,ProximityActivity.class);
                        startActivity(intent);
                        break;
                    //加速器
                    case Sensor.TYPE_ACCELEROMETER:
                        intent = new Intent(context,AccActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


    }
}