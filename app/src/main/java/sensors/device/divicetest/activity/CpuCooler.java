package sensors.device.divicetest.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.AdapterCpuCooler;
import sensors.device.divicetest.dataBase.DataBaseHelper;

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
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EasyBatteryMod easyBatteryMod = new EasyBatteryMod(CpuCooler.this);


        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.orange));
        }



        new cpuCoolerAsyn().execute();
        Button coolerBt = findViewById(R.id.cpuCoolerBT);

        final DataBaseHelper db = new DataBaseHelper(CpuCooler.this);
        final HashMap<Integer, Long> timeChk = new HashMap<>();


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

                timeChk.put(1, m);

                boolean isInserted = db.insertValue(itn);
                if (isInserted = true) {
                    Toast.makeText(CpuCooler.this, "inserted data", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CpuCooler.this, "inserted data", Toast.LENGTH_LONG).show();

                }

            }
        });

        String rt = String.valueOf(timeChk.get(1));

        //   if (System.currentTimeMillis() == timeChk.get(1) || )
        new noOfRunningApp(CpuCooler.this).execute();


    }


    @SuppressLint("StaticFieldLeak")
    public class cpuCoolerAsyn extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = null;

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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RecyclerView recyclerView = findViewById(R.id.cpuRecylerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(CpuCooler.this));
            recyclerView.setAdapter(adapterCpuCooler);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CpuCooler.this, "Searching", "finding all app process");
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
