package sensors.device.divicetest.activity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        /*Toolbar toolbar = findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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


        new allAppRunningAsyncTask().execute();

        FloatingActionButton floatingActionButton = findViewById(R.id.clenerBT);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(phoneBoosterActivity.this);

                dialog.setContentView(R.layout.custome_dialoge);

                final ImageView img = dialog.findViewById(R.id.ImgV);

                Button button = dialog.findViewById(R.id.okDID);

                Glide.with(getApplicationContext()).asGif().load(R.drawable.pokapok).into(img);
                img.setVisibility(View.VISIBLE);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });

                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        img.setVisibility(View.GONE);
                    }
                };
                handler.postDelayed(runnable, 10000);


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


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


            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            recyclerView = findViewById(R.id.showAppRecylerID);
            recyclerView.setLayoutManager(new LinearLayoutManager(phoneBoosterActivity.this));
            recyclerView.setAdapter(adapterPhoneBooster);
            progressDialog.dismiss();

            super.onPostExecute(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(phoneBoosterActivity.this, "Searching", "App Loading");
            super.onPreExecute();
        }

    }


}
