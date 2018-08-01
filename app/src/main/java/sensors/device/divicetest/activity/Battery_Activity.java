package sensors.device.divicetest.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.race604.drawable.wave.WaveDrawable;

import java.math.BigDecimal;
import java.util.ArrayList;

import github.nisrulz.easydeviceinfo.base.BatteryHealth;
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import sensors.device.divicetest.R;

public class Battery_Activity extends AppCompatActivity {
    ContentResolver contentResolver;
    AdView adView;
    private ImageView mImageView;
    private WaveDrawable mWaveDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_);

        Toolbar toolbar = findViewById(R.id.toolbarBtS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CPU cooler");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(Battery_Activity.this);
        String bttryTechnology = easyBatteryMod.getBatteryTechnology();
        Button bt = findViewById(R.id.button22962);

        @BatteryHealth int batteryHealth = easyBatteryMod.getBatteryHealth();

        String t1, t2, t3;

        TextView batteryHTxtV = findViewById(R.id.bttryHelthTxtV);

        switch (batteryHealth) {
            case BatteryHealth.GOOD:
                t1 = "Battery Health : Good";
                batteryHTxtV.setText(t1);
                break;
            case BatteryHealth.HAVING_ISSUES:
                t2 = "Battery Health : Having Issue";
                batteryHTxtV.setText(t2);
                break;
            default:
                t3 = "Battery Health : Medium";
                batteryHTxtV.setText(t3);
                break;
        }
        mImageView = findViewById(R.id.image);
        mWaveDrawable = new WaveDrawable(Battery_Activity.this, R.drawable.ic_battery);
        mImageView.setImageDrawable(mWaveDrawable);
        mWaveDrawable.setWaveAmplitude(5);
        mWaveDrawable.setWaveLength(70);
        mWaveDrawable.setWaveSpeed(2);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean canWrite = Settings.System.canWrite(Battery_Activity.this);
            AndroidPermissionChck();

            if (!canWrite) {
                //send a intent to direct app permission
                AlertDialog alertDialog = new AlertDialog.Builder(Battery_Activity.this).create();
                alertDialog.setMessage("Q Battery Booster can write settings.");
                alertDialog.show();

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else {


        }
        contentResolver = getContentResolver();
        final WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final boolean wifiState = wifiManager.isWifiEnabled(); //check wifi on or off
        final int brightnes = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, -1);
        //check display brightness
        LocationManager lm = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsState = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final ArrayList<String> list = new ArrayList<>();
        try {
            int brightnessMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
            long screenTimeOut = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT);

            if (wifiState != false) {
                list.add("wifi enabled");
            }
            if (brightnessMode == 0) {
                if (brightnes >= 5) {
                    list.add("brightness greater");
                }
            }
            if (gpsState != false) {
                list.add("gps enabled");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        String p = String.valueOf(list.size()) + " " + "problem found";
        if (list.size() > 0) {
            TextView tv = findViewById(R.id.textView38);
            if (list.size() >= 1) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(p);
            }
            bt.setVisibility(View.VISIBLE);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 5);
//                Settings.System.putInt(contentResolver,Settings.System.SCREEN_OFF_TIMEOUT,-1);
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Battery_Activity.this);
                builder.setTitle("Attention Required");
                builder.setMessage("Turn of gps as it can save more battery");
                builder.setPositiveButton("Turn Off", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                EasyBatteryMod easyBatteryMod = new EasyBatteryMod(Battery_Activity.this);
                int p = easyBatteryMod.getBatteryPercentage();
                float p2 = p * 8650 / 100;
                int ty = Math.round(p2);
                if (easyBatteryMod.isDeviceCharging()) {
                    mWaveDrawable.setIndeterminate(true);
                } else if (!easyBatteryMod.isDeviceCharging()) {
                    mWaveDrawable.setIndeterminate(false);
                    mWaveDrawable.setLevel(ty);
                } else {
                    mWaveDrawable.setLevel(ty);
                }
                handler.postDelayed(this, 10000);
            }
        });

        final Handler handler1 = new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                TextView level = findViewById(R.id.levelTXTV);
                TextView temp = findViewById(R.id.tempTXTV);
                TextView volt = findViewById(R.id.voltageTXV);

                EasyBatteryMod easyBatteryMod = new EasyBatteryMod(Battery_Activity.this);
                String percentageLevel = String.valueOf(easyBatteryMod.getBatteryPercentage()) + "%";
                String temperature = String.valueOf(easyBatteryMod.getBatteryTemperature()) + ((char) 0x00B0) + "C";
                int voltt = easyBatteryMod.getBatteryVoltage();
                int xd = 3;

                BigDecimal unscaled = new BigDecimal(voltt);
                BigDecimal scaled = unscaled.scaleByPowerOfTen(-xd);
                String voltage = String.valueOf(scaled) + ((char) 0x00B0) + "C";

                level.setText(percentageLevel);
                volt.setText(String.valueOf(voltage));
                temp.setText(temperature);

                TextView chrgrConnected = findViewById(R.id.chargercTV);
                if (easyBatteryMod.isDeviceCharging()) {
                    chrgrConnected.setVisibility(View.VISIBLE);
                } else if (!easyBatteryMod.isDeviceCharging()) {
                    chrgrConnected.setVisibility(View.INVISIBLE);
                }
                handler1.postDelayed(this, 1000);


            }
        });
        adView = new AdView(this, "681947592150931_682399402105750", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

    }

    private void AndroidPermissionChck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                                {
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                }
                        , 1);

            }
        }

    }







}
