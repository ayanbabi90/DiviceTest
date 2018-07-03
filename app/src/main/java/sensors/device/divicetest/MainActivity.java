package sensors.device.divicetest;


import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.github.lzyzsd.circleprogress.ArcProgress;

import github.nisrulz.easydeviceinfo.base.EasyMemoryMod;
import sensors.device.divicetest.activitys.ApkActivity;
import sensors.device.divicetest.activitys.Mp3_Activity;
import sensors.device.divicetest.activitys.RunningAppActivity;

public class MainActivity extends AppCompatActivity{
    ImageView filesImageV, processorImageV,mpr3img;
     ArcProgress arcProgress;
     ArcProgress arc_progress2;
    ImageButton apkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpr3img = findViewById(R.id.dashMp3Id);
        arc_progress2 = findViewById(R.id.arc_progress2);
        filesImageV = findViewById(R.id.deviceInfoID);
        processorImageV = findViewById(R.id.processorImageID);
        apkImage = findViewById(R.id.apkImageID);

        //*********************RAM*************************************
        arcProgress = findViewById(R.id.arc_progress);

        int ramF = getFreeRam();
        int ramT = getTotalRam();
        int ramPercentage = getRamPercentage();
        String value1 = String.valueOf(ramF);
        String valu2 = String.valueOf(ramT);

        arcProgress.setProgress(ramPercentage);
        arcProgress.setBottomText(value1 + " MB /" + valu2 + " MB");
        arcProgress.setMax(100);
        arcProgress.setArcAngle(270);
        arcProgress.setStrokeWidth(18);
        arcProgress.setBottomTextSize(28);

        arcProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.MORPH)         // Slide up animation
                        .repeatCount(1)                                 // Repeat once
                        .duration(1000)                               // Last for 1000 milliseconds(1 second)
                        .createFor(v)                                // Apply it to the view
                        .start();                                   // Start it now
            }
        });




        //****************SD CARD***************************
        EasyMemoryMod easyMemoryMod = new EasyMemoryMod(MainActivity.this);

        String iA = String.valueOf( easyMemoryMod.convertToGb(easyMemoryMod.getAvailableInternalMemorySize()));
        String iT = String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getTotalInternalMemorySize()));

        String iA2 = iA.substring(0,iA.length()-5);
        String iT2 = iT.substring(0,iT.length()-5);

        int total = totalsd(iT2);
        int per = sdpercentage(iA2,iT2);

        arc_progress2.setMax(100);
        arc_progress2.setProgress(per);
        arc_progress2.setArcAngle(270);
        arc_progress2.setStrokeWidth(12);
        arc_progress2.setBottomTextSize(20);
        arc_progress2.setBottomText(iA2+"GB"+"/ "+iT2+"GB");







        apkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApkActivity.class);
                startActivity(intent);
            }
        });

        filesImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FragmentVActivity.class);
                startActivity(intent);

            }
        });
        processorImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RunningAppActivity.class);
                startActivity(intent);
            }
        });

        mpr3img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Mp3_Activity.class);
                startActivity(intent);

            }
        });



       AndroidPermissionChck();
    }

    private void AndroidPermissionChck() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                                {
                                        Manifest.permission.READ_PHONE_STATE
                                        ,Manifest.permission.KILL_BACKGROUND_PROCESSES
                                        ,Manifest.permission.ACCESS_WIFI_STATE
                                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                                }
                        , 1);

            }
        }

    }


    int getFreeRam(){

        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memInfo);
        String freeRam = String.valueOf(memInfo.availMem);
        int r1 =  Integer.parseInt(freeRam);
        int dfr = r1/1000000;
        return  dfr;


    }

    int getTotalRam(){
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memInfo);
        String TotalRam = String.valueOf(memInfo.totalMem);
        int r1 =  Integer.parseInt(TotalRam);
        int dfr = r1/1000000;
        return  dfr;

    }

    int getRamPercentage(){
        double vp1 = getFreeRam();
        double vp2 = getTotalRam();

        Integer ramo = Integer.valueOf((int) (vp1 / vp2 * 100));
        return ramo;
    }

    int sdpercentage(String iA2, String iT2){


        Double iAA = 100 * Double.parseDouble(iA2);
        Double iTT = 100 * Double.parseDouble(iT2);

        double per = (iAA/iTT * 100);
        int perr = Integer.parseInt(String.valueOf(Math.round(per)));

        return perr;
    }

    int totalsd(String iT2){
        Double t =  Double.parseDouble(iT2);
        double t1 = 100*t;
        String st = String.valueOf(t1);
        String st1 = st.substring(0,st.length()-2);
        int i = Integer.parseInt(st1);
        return i;


    }





}

