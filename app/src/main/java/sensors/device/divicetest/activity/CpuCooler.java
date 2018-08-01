package sensors.device.divicetest.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.ArrayList;
import java.util.List;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.AdapterCpuCooler;

public class CpuCooler extends AppCompatActivity {
    AdapterCpuCooler adapterCpuCooler;
    HTextView tno, tText;
//    LineGraphSeries<DataPoint> series;
//    Handler mHandler = new Handler();
//    float rx = 2;
//    //    GraphView graph;
//    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_cooler);

        Toolbar toolbar = findViewById(R.id.toolbar234);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CPU cooler");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EasyBatteryMod easyBatteryMod = new EasyBatteryMod(CpuCooler.this);

        AndroidPermissionChck();

//        //changing statusbar color
//        if (android.os.Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.orange));
//        }



        new cpuCoolerAsyn().execute();
        Button coolerBt = findViewById(R.id.cpuCoolerBT);


        final Dialog dialog = new Dialog(CpuCooler.this, R.style.Theme_AppCompat_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_cpu_cooler_end);
        AdView adView = new AdView(CpuCooler.this, "681947592150931_683707151974975", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout adContainer = dialog.findViewById(R.id.banner_container991);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));





        coolerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new cleanCpuAsync().execute();

                tno = findViewById(R.id.coolerNo);
                tText = findViewById(R.id.coolerText);

                float a = easyBatteryMod.getBatteryTemperature();
                float s1 = (float) (a - 2.3);
                tno.setAnimateType(HTextViewType.TYPER);
                tno.animateText(String.valueOf(s1));

                tno.setVisibility(View.VISIBLE);
                tText.setVisibility(View.INVISIBLE);
                String itn = String.valueOf(System.currentTimeMillis());

                long m = System.currentTimeMillis();


                dialog.show();


                final LottieAnimationView ltanv = dialog.findViewById(R.id.animation_view8);
                ltanv.setAnimation(R.raw.particle_explosion);
                ltanv.playAnimation();

                final TextView textView = dialog.findViewById(R.id.textView2211);
                final TextView textView1 = dialog.findViewById(R.id.textView36);

                SharedPreferences spf = getApplicationContext().getSharedPreferences("appRuning1", MODE_PRIVATE);
                final String s = spf.getString("appO", "");


                Handler h1 = new Handler();
                h1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.VISIBLE);
                        textView1.setVisibility(View.VISIBLE);
                        textView1.setText(s);
                        ltanv.setVisibility(View.INVISIBLE);
                        LottieAnimationView loti2 = dialog.findViewById(R.id.animation_view6);
                        loti2.setAnimation(R.raw.done_clear);
                        loti2.playAnimation();
                    }
                }, 5000);

                Handler h2 = new Handler();
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(CpuCooler.this, Add_Activity.class);
                        startActivity(intent);

                    }
                }, 8000);



            }
        });


        //   if (System.currentTimeMillis() == timeChk.get(1) || )
        new noOfRunningApp(CpuCooler.this).execute();


    }


    private void AndroidPermissionChck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                                {
                                        Manifest.permission.KILL_BACKGROUND_PROCESSES
                                }
                        , 1);

            }
        }

    }





    @SuppressLint("StaticFieldLeak")
    public class cpuCoolerAsyn extends AsyncTask<Void, Void, Void> {
        Dialog dialog = null;

        @Override
        protected Void doInBackground(Void... voids) {

            ActivityManager am = (ActivityManager) CpuCooler.this.getSystemService(Context.ACTIVITY_SERVICE);
            PackageManager pm = CpuCooler.this.getPackageManager();

            List<String> AllappRunning = new ArrayList<>();
            List<Drawable> appIcons = new ArrayList<>();


            List<ActivityManager.RunningAppProcessInfo> AllRunningProcess = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo ar : AllRunningProcess) {

                try {
                    // ApplicationInfo ai = pm.getApplicationInfo(ar.processName,0);
                    CharSequence sc = pm.getApplicationLabel(pm.getApplicationInfo(ar.processName, PackageManager.GET_META_DATA));

                    String appName = String.valueOf(sc);
                    Drawable appico = pm.getApplicationIcon(ar.processName);

                    AllappRunning.add(appName);
                    appIcons.add(appico);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
            adapterCpuCooler = new AdapterCpuCooler(AllappRunning, appIcons, CpuCooler.this);

            SharedPreferences spf = getApplicationContext().getSharedPreferences("appRuning1", MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();

            String s = String.valueOf(AllappRunning.size());
            editor.putString("appO", s);
            editor.apply();


            Thread.currentThread();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = findViewById(R.id.cpuRecylerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(CpuCooler.this));
            recyclerView.setAdapter(adapterCpuCooler);
            dialog.dismiss();

            super.onPostExecute(aVoid);
        }


        @Override
        protected void onPreExecute() {

            dialog = new Dialog(CpuCooler.this, R.style.Theme_AppCompat_NoActionBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_cpu_cooler);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            AdView adview1 = new AdView(CpuCooler.this, "681947592150931_683743018638055", AdSize.BANNER_HEIGHT_50);
            // Find the Ad Container
            LinearLayout adContainer = dialog.findViewById(R.id.banner_container992);
            // Add the ad view to your activity layout
            adContainer.addView(adview1);
            // Request an ad
            adview1.loadAd();

            final LottieAnimationView lav = dialog.findViewById(R.id.animation_view4);
            lav.setAnimation(R.raw.verify_phone);
            lav.playAnimation();

            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView textView = dialog.findViewById(R.id.textView221);
                    textView.setVisibility(View.INVISIBLE);
                    lav.setVisibility(View.INVISIBLE);

                    LottieAnimationView lav1 = dialog.findViewById(R.id.animation_view5);
                    lav1.setAnimation(R.raw.anim796_check);
                    lav1.playAnimation();

                }
            }, 3000);



            super.onPreExecute();
        }

    }


    @SuppressLint("StaticFieldLeak")
    public class cleanCpuAsync extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
//        AdapterCpuCooler aCooler;


        @Override
        protected Void doInBackground(Void... voids) {


            ActivityManager am = (ActivityManager) CpuCooler.this.getSystemService(Context.ACTIVITY_SERVICE);
            //PackageManager pm = CpuCooler.this.getPackageManager();


            List<ActivityManager.RunningAppProcessInfo> allp = am.getRunningAppProcesses();

            List<String> clossingApp = new ArrayList<>();
            List<Drawable> clossingAppIco = new ArrayList<>();

            for (ActivityManager.RunningAppProcessInfo appProcessInfo : allp) {

                if (!appProcessInfo.processName.equalsIgnoreCase("sensors.device.divicetest")) {

                    if (appProcessInfo.processName.equals(appProcessInfo.processName)) {
                        Process.sendSignal(appProcessInfo.pid, Process.SIGNAL_KILL);
                        Process.killProcess(appProcessInfo.pid);
                        am.killBackgroundProcesses(appProcessInfo.processName);
                        am.restartPackage(appProcessInfo.processName);


                    }
                }
            }


            adapterCpuCooler = new AdapterCpuCooler(clossingApp, clossingAppIco, CpuCooler.this);


            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CpuCooler.this, "Process Initiated", "closing all running cpu process");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = findViewById(R.id.cpuRecylerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(CpuCooler.this));
            recyclerView.setAdapter(adapterCpuCooler);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class noOfRunningApp extends AsyncTask<Void, Float, Float> {

        Activity activity;

        noOfRunningApp(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Float doInBackground(Void... voids) {
//
////            List<String> noApp = new ArrayList<>();
////
////            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
////            //   PackageManager pm = activity.getPackageManager();
////
////            List<ActivityManager.RunningAppProcessInfo> allProcess = am.getRunningAppProcesses();
////
////            for (ActivityManager.RunningAppProcessInfo ar : allProcess) {
////                noApp.add(ar.processName);
////            }
//            String.valueOf(noApp.size());

            EasyBatteryMod easyBatteryMod = new EasyBatteryMod(activity);

            return easyBatteryMod.getBatteryTemperature();
        }

        @Override
        protected void onPostExecute(Float result) {
            HTextView tno = activity.findViewById(R.id.coolerNo);
            HTextView tText = activity.findViewById(R.id.coolerText);

            tno.setVisibility(View.VISIBLE);
            tText.setVisibility(View.VISIBLE);

            tno.setAnimateType(HTextViewType.RAINBOW);
            tno.animateText(String.valueOf(result) + ((char) 0x00B0) + "c");


            tText.setAnimateType(HTextViewType.TYPER);
            tText.animateText("process");


            super.onPostExecute(result);
        }
    }
}
