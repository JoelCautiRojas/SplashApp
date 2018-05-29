package com.programadoresperuanos.www.splashapp;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import static android.Manifest.permission.BODY_SENSORS;

public class Main2Activity extends AppCompatActivity {

    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resultado = findViewById(R.id.textView);
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
        String mensaje = "";
        for (Sensor sensor:lista){
            mensaje = mensaje+sensor.getName()+"\r\n";
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
}
