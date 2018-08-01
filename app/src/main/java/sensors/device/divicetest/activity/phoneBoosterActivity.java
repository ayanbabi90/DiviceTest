package sensors.device.divicetest.activity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.Adapter_phoneBooster;

public class phoneBoosterActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Adapter_phoneBooster adapterPhoneBooster = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_booster);
        Toolbar toolbar = findViewById(R.id.toolbarphoneB);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*

        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(phoneBoosterActivity.this);

        String temperature = String.valueOf(easyBatteryMod.getBatteryTemperature());

        TextView tempV = findViewById(R.id.textTemperatureView);
        tempV.setText(temperature+((char)0x00B0)+"c");
        */


        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {


                EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getApplicationContext());
                TextView tempV = findViewById(R.id.textTemperatureView);
                String temperature = String.valueOf(easyBatteryMod.getBatteryTemperature());

                //int ft = Integer.parseInt(temperature);

                tempV.setText(temperature + ((char) 0x00B0) + "c");


                handler.postDelayed(this, 100);

            }
        });

        final Dialog dialog = new Dialog(phoneBoosterActivity.this, R.style.Theme_AppCompat_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_dialoge);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        AdView adview2 = new AdView(phoneBoosterActivity.this, "681947592150931_683755865303437", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = dialog.findViewById(R.id.banner_container993);
        // Add the ad view to your activity layout
        adContainer.addView(adview2);
        // Request an ad
        adview2.loadAd();


        new allAppRunningAsyncTask().execute();

        Button buttonClearApp = findViewById(R.id.clenerBT);

        buttonClearApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences spf = getApplicationContext().getSharedPreferences("phone_booster", MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("didStart", "yes");

                dialog.show();

                LottieAnimationView animationView = dialog.findViewById(R.id.animation_view2);
                animationView.setAnimation(R.raw.clear);
                animationView.playAnimation();

                Handler worker = new Handler();
                worker.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LottieAnimationView animationView = dialog.findViewById(R.id.animation_view2);
                        animationView.setAnimation(R.raw.particle_explosion);
                        animationView.playAnimation();
                    }
                }, 3000);

                Handler worker2 = new Handler();
                worker2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LottieAnimationView animationView = dialog.findViewById(R.id.animation_view2);
                        animationView.setAnimation(R.raw.done_clear);
                        animationView.playAnimation();

                        SharedPreferences spf = getSharedPreferences("totalApp", MODE_PRIVATE);
                        String get = spf.getString("app", "");

                        TextView textView = dialog.findViewById(R.id.textView28);
                        textView.setVisibility(View.VISIBLE);


                        TextView textView1 = dialog.findViewById(R.id.textView25);
                        textView1.setVisibility(View.VISIBLE);


                        TextView textView2 = dialog.findViewById(R.id.textView28);
                        textView.setVisibility(View.VISIBLE);
                        textView2.setText(get);
                    }


                }, 5000);

                Handler handlerGo = new Handler();
                handlerGo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();

                        Intent intent = new Intent(phoneBoosterActivity.this, Add_Activity.class);
                        startActivity(intent);

                    }
                }, 8000);






                ActivityManager actvityManager = (ActivityManager) phoneBoosterActivity.this.getSystemService(ACTIVITY_SERVICE);

                List<ActivityManager.RunningAppProcessInfo> appProcessInfos = actvityManager.getRunningAppProcesses();

                for (ActivityManager.RunningAppProcessInfo rp : appProcessInfos) {
                    if (!rp.processName.equalsIgnoreCase("sensors.device.divicetest")) {

                        if (rp.processName.equals(rp.processName)) {
                            android.os.Process.sendSignal(rp.pid, Process.SIGNAL_KILL);
                            android.os.Process.killProcess(rp.pid);
                            actvityManager.killBackgroundProcesses(rp.processName);
                            actvityManager.restartPackage(rp.processName);



                        }
                    }
                }

                List<String> appList = new ArrayList<>();
                List<Drawable> appListIcons = new ArrayList<>();
                List<String> pids = new ArrayList<>();

                recyclerView = findViewById(R.id.showAppRecylerID);
                recyclerView.setLayoutManager(new LinearLayoutManager(phoneBoosterActivity.this));
                recyclerView.setAdapter(new Adapter_phoneBooster(appList, appListIcons, pids));


            }
        });


    }


    public class allAppRunningAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog = null;
        Dialog dialog = null;


        @Override
        protected Void doInBackground(Void... parms) {
            Context context;


            ActivityManager am = (ActivityManager) phoneBoosterActivity.this.getSystemService(ACTIVITY_SERVICE);
            PackageManager pm = phoneBoosterActivity.this.getPackageManager();

            List<String> appList = new ArrayList<>();
            List<Drawable> appListIcons = new ArrayList<>();
            List<String> pids = new ArrayList<>();
            List<ActivityManager.RunningAppProcessInfo> allTasks = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo allTask : allTasks) {
                try {

                    ApplicationInfo aI = pm.getApplicationInfo(allTask.processName, 0);


                    //Only Installed App
                    if ((aI.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {


                        CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(allTask.processName, PackageManager.GET_META_DATA));
                        String packagename = allTask.processName;
                        Drawable ico = pm.getApplicationIcon(packagename);
                        String pid = String.valueOf(allTask.pid);

                        pids.add(pid);
                        appList.add(c.toString());
                        appListIcons.add(ico);


                    }

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }


            adapterPhoneBooster = new Adapter_phoneBooster(appList, appListIcons, pids);

            SharedPreferences spf = getApplicationContext().getSharedPreferences("totalApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            String st = String.valueOf(appList.size());
            editor.putString("app", st);
            editor.apply();

            Thread.currentThread();
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            recyclerView = findViewById(R.id.showAppRecylerID);
            recyclerView.setLayoutManager(new LinearLayoutManager(phoneBoosterActivity.this));
            recyclerView.setAdapter(adapterPhoneBooster);
            dialog.dismiss();

            super.onPostExecute(result);
        }


        @Override
        protected void onPreExecute() {


            dialog = new Dialog(phoneBoosterActivity.this, R.style.Theme_AppCompat_NoActionBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_phonebooster);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            AdView adview1 = new AdView(phoneBoosterActivity.this, "681947592150931_683755865303437", AdSize.BANNER_HEIGHT_50);
            // Find the Ad Container
            LinearLayout adContainer = dialog.findViewById(R.id.banner_container994);
            // Add the ad view to your activity layout
            adContainer.addView(adview1);
            // Request an ad
            adview1.loadAd();


            LottieAnimationView la2 = dialog.findViewById(R.id.animation_view);
            la2.setAnimation(R.raw.phone_scanning);
            la2.playAnimation();

            Handler h2 = new Handler();
            h2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LottieAnimationView la = dialog.findViewById(R.id.animation_view);
                    la.setAnimation(R.raw.pb2);
                    la.playAnimation();
                }
            }, 7000);





            super.onPreExecute();
        }


    }


}
