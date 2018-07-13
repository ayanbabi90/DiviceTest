package sensors.device.divicetest.activity;

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
import android.view.View;
import android.widget.Button;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.ArrayList;
import java.util.List;

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
        new cpuCoolerAsyn().execute();
        Button coolerBt = findViewById(R.id.cpuCoolerBT);


        coolerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new cleanCpuAsync().execute();

                tno = findViewById(R.id.coolerNo);
                tText = findViewById(R.id.coolerText);
                tno.setVisibility(View.INVISIBLE);
                tText.setVisibility(View.INVISIBLE);

            }
        });

        new noOfRunningApp(CpuCooler.this).execute();


    }


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


    public class noOfRunningApp extends AsyncTask<Void, String, String> {

        Activity activity;

        public noOfRunningApp(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(Void... voids) {

            List<String> noApp = new ArrayList<>();

            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            //   PackageManager pm = activity.getPackageManager();

            List<ActivityManager.RunningAppProcessInfo> allProcess = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo ar : allProcess) {
                noApp.add(ar.processName);
            }

            return String.valueOf(noApp.size());
        }

        @Override
        protected void onPostExecute(String result) {
            HTextView tno = activity.findViewById(R.id.coolerNo);
            HTextView tText = activity.findViewById(R.id.coolerText);

            tno.setVisibility(View.VISIBLE);
            tText.setVisibility(View.VISIBLE);

            tno.setAnimateType(HTextViewType.RAINBOW);
            tno.animateText(String.valueOf(result));


            tText.setAnimateType(HTextViewType.TYPER);
            tText.animateText("process need to kill");


            super.onPostExecute(result);
        }
    }
}
