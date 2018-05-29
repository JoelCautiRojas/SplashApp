package com.programadoresperuanos.www.splashapp;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import static android.Manifest.permission.BODY_SENSORS;

public class Main2Activity extends AppCompatActivity implements SensorEventListener{

    TextView resultado,sensor1,sensor2,sensor3,sensor4;
    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resultado = findViewById(R.id.textView);
        sensor1 = findViewById(R.id.textView2);
        sensor2 = findViewById(R.id.textView3);
        sensor3 = findViewById(R.id.textView4);
        sensor4 = findViewById(R.id.textView5);
        if(ActivityCompat.checkSelfPermission(this,BODY_SENSORS)== PackageManager.PERMISSION_GRANTED){
            iniciarApp();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{BODY_SENSORS}, 100);
        }
    }

    private void iniciarApp() {
        SensorManager administradordesensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> lista = administradordesensores.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor:lista){
           mensaje+="Nombre: "+sensor.getName()+
                    "\r\nRango Maximo: "+String.valueOf(sensor.getMaximumRange())+
                    "\r\nRetraso Minimo: "+String.valueOf(sensor.getMinDelay())+
                    "\r\nPotencia en miliamperios: "+String.valueOf(sensor.getPower())+
                    "\r\nResolucion: "+String.valueOf(sensor.getResolution())+
                    "\r\nTipo Generico: "+String.valueOf(sensor.getType())+
                    "\r\nFabricante: "+sensor.getVendor()+
                    "\r\nVersion: "+String.valueOf(sensor.getVersion()+"\r\n\r\n");
        }
        mensaje+="Datos de los sensores de orientacion:\r\n\r\n";
        List<Sensor> lista2 = administradordesensores.getSensorList(Sensor.TYPE_ORIENTATION);
        if(!lista2.isEmpty()){
            Sensor orientacionsensor = lista2.get(0);
            administradordesensores.registerListener((SensorEventListener) this,orientacionsensor,SensorManager.SENSOR_DELAY_UI);
        }
        List<Sensor> lista3 = administradordesensores.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(!lista3.isEmpty()){
            Sensor acelerometerSensor = lista3.get(0);
            administradordesensores.registerListener((SensorEventListener) this,acelerometerSensor,SensorManager.SENSOR_DELAY_UI);
        }
        List<Sensor> lista4 = administradordesensores.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if(!lista4.isEmpty()){
            Sensor magneticField = lista4.get(0);
            administradordesensores.registerListener((SensorEventListener) this,magneticField,SensorManager.SENSOR_DELAY_UI);
        }
        List<Sensor> lista5 = administradordesensores.getSensorList(Sensor.TYPE_PROXIMITY);
        if(!lista5.isEmpty()){
            Sensor proximidad = lista5.get(0);
            administradordesensores.registerListener((SensorEventListener) this,proximidad,SensorManager.SENSOR_DELAY_UI);
        }
        resultado.setText(mensaje);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            iniciarApp();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ORIENTATION:
                String msj = "";
                for (int i = 0; i < 3 ; i++){
                    msj+="Dato de Orientacion"+i+": "+event.values[i]+"\r\n";
                }
                sensor1.setText(msj);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                String msj2 = "";
                for (int i = 0; i < 3 ; i++){
                    msj2+="Dato de Acelerometro"+i+": "+event.values[i]+"\r\n";
                }
                sensor2.setText(msj2);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                String msj3 = "";
                for (int i = 0; i < 3 ; i++){
                    msj3+="Dato de Campo Magnetico"+i+": "+event.values[i]+"\r\n";
                }
                sensor3.setText(msj3);
                break;
            case Sensor.TYPE_PROXIMITY:
                String msj4 = "";
                for (int i = 0; i < 3 ; i++){
                    msj4+="Dato de Proximidad"+i+": "+event.values[i]+"\r\n";
                }
                sensor4.setText(msj4);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
