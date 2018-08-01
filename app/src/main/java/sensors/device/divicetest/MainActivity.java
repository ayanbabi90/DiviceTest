package sensors.device.divicetest;


import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import sensors.device.divicetest.activity.Battery_Activity;
import sensors.device.divicetest.activity.CpuCooler;
import sensors.device.divicetest.activity.SettingsActivity;
import sensors.device.divicetest.activity.apkManagerActivity;
import sensors.device.divicetest.activity.deviceInfoActivity;
import sensors.device.divicetest.activity.junkCleanerActivity;
import sensors.device.divicetest.activity.phoneBoosterActivity;
import sensors.device.divicetest.services.TheService;

public class MainActivity extends AppCompatActivity {
    private ArcProgress arcProgress;
    ImageButton s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /************************************** Services *************************************/

        Intent intent = new Intent(this, TheService.class);
        startService(intent);


        /********************** Menu *****************************************************/
        s1 = findViewById(R.id.s1);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



        /*********************  View Binders ********************************************/
        ImageView mpr3img = findViewById(R.id.dashMp3Id);
        ArcProgress arc_progress2 = findViewById(R.id.arc_progress2);
        ImageView deviceDash = findViewById(R.id.deviceInfoID);
        ImageView ramBooster = findViewById(R.id.processorImageID);
        ImageButton apkImage = findViewById(R.id.apkImageID);
        ImageView junkClenerImageView = findViewById(R.id.junkClenerImageView);
        ImageView cpuCooler = findViewById(R.id.temperatureImageID);

        //*********************  RAM  *************************************
        arcProgress = findViewById(R.id.arc_progress);

        arcProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Flubber.with()
            .animation(Flubber.AnimationPreset.FLASH)         // Slide up animation
            .repeatCount(1)                                 // Repeat once
            .duration(1000)                               // Last for 1000 milliseconds(1 second)
            .createFor(v)                                // Apply it to the view
            .start();                                   // Start it now
            }
        });


        arcProgress.setMax(100);
        arcProgress.setArcAngle(270);
        arcProgress.setStrokeWidth(18);
        arcProgress.setBottomTextSize(28);

        /************************ RealTime Ram Value ****************************/

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            int ramPercentage = getRamPercentage();
            arcProgress.setProgress(ramPercentage);

            int ramF = getFreeRam();
            int ramT = getTotalRam();
            String valu2 = String.valueOf(ramT);
            String value1 = String.valueOf(ramF);
            arcProgress.setBottomText(value1 + " MB /" + valu2 + " MB");

            handler.postDelayed(this, 100);

            }
        });


        /**********************   SD CARD  *********************************************/
        EasyMemoryMod easyMemoryMod = new EasyMemoryMod(MainActivity.this);

        String iA = String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getAvailableInternalMemorySize()));
        String iT = String.valueOf(easyMemoryMod.convertToGb(easyMemoryMod.getTotalInternalMemorySize()));

        String iA2 = iA.substring(0, iA.length() - 5);
        String iT2 = iT.substring(0, iT.length() - 5);

        int total = totalsd(iT2);
        int per = sdpercentage(iA2, iT2);

        arc_progress2.setMax(100);
        arc_progress2.setProgress(per);
        arc_progress2.setArcAngle(270);
        arc_progress2.setStrokeWidth(12);
        arc_progress2.setBottomTextSize(20);
        arc_progress2.setBottomText(iA2 + "GB" + "/ " + iT2 + "GB");

        /**************************  APK Manager ****************************************************/
        apkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, apkManagerActivity.class);
                startActivity(intent);
            }
        });

        /******************************  Junk Cleaner ************************************************/
        junkClenerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, junkCleanerActivity.class);
                startActivity(intent);
            }
        });

        /******************************************** Device info *************************************/
        deviceDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, deviceInfoActivity.class);
                startActivity(intent);

            }
        });

        /*********************************** RAM Booster **********************************************/
        ramBooster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, phoneBoosterActivity.class);
                startActivity(intent);
            }
        });

        /****************************************** Mp3 *********************************************/
        mpr3img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Battery_Activity.class);
                startActivity(intent);

            }
        });

        /****************************************  CPU Cooler  **************************************/
        cpuCooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CpuCooler.class);
                startActivity(intent);
            }
        });

        /**************************** Android Permission ********************************************/
        AndroidPermissionChck();
    }

    private void AndroidPermissionChck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                {
                  Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.KILL_BACKGROUND_PROCESSES
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                }
        , 1);

            }
        }

    }


    int getFreeRam() {

        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memInfo);
        String freeRam = String.valueOf(memInfo.availMem);
        int r1 = Integer.parseInt(freeRam);
        int dfr = r1 / 1000000;
        return dfr;
    }

    int getTotalRam() {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memInfo);
        String TotalRam = String.valueOf(memInfo.totalMem);
        int r1 = Integer.parseInt(TotalRam);
        int dfr = r1 / 1000000;
        return dfr;

    }

    int getRamPercentage() {
        double vp1 = getFreeRam();
        double vp2 = getTotalRam();

        Integer ramo = Integer.valueOf((int) (vp1 / vp2 * 100));
        return ramo;
    }

    int sdpercentage(String iA2, String iT2) {


        Double iAA = 100 * Double.parseDouble(iA2);
        Double iTT = 100 * Double.parseDouble(iT2);

        double per = (iAA / iTT * 100);
        int perr = Integer.parseInt(String.valueOf(Math.round(per)));

        return perr;
    }

    int totalsd(String iT2) {
        Double t = Double.parseDouble(iT2);
        double t1 = 100 * t;
        String st = String.valueOf(t1);
        String st1 = st.substring(0, st.length() - 2);
        int i = Integer.parseInt(st1);
        return i;


    }


}

