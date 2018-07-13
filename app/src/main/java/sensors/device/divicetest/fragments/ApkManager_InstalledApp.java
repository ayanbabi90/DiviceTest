package sensors.device.divicetest.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.Adapter_install_ApkManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApkManager_InstalledApp extends Fragment {
    RecyclerView recyclerView;
    Adapter_install_ApkManager adapter;


    public ApkManager_InstalledApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apk_manager__installed_app, container, false);
        recyclerView = view.findViewById(R.id.installedRecylerView);

        new AllAppRuning(recyclerView, getActivity()).execute();

        return view;

    }

    public class AllAppRuning extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        RecyclerView recyclerView;
        Activity activity;


        public AllAppRuning(RecyclerView recyclerView, Activity activity) {
            this.recyclerView = recyclerView;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PackageManager pm = activity.getPackageManager();
            List<String> appName = new ArrayList<>();
            List<Drawable> appIcon = new ArrayList<>();
            List<String> appPath = new ArrayList<>();
            List<String> packageName = new ArrayList<>();
            File backupDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/rdx");


            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo allTask : packages) {
                try {
                    ApplicationInfo aI = pm.getApplicationInfo(allTask.processName, 0);

                    if ((aI.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                        CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(allTask.packageName, PackageManager.GET_META_DATA));

                        appName.add(String.valueOf(c));
                        String appo = allTask.processName;

                        Drawable iconApp = pm.getApplicationIcon(appo);
                        appIcon.add(iconApp);

                        String apppath = allTask.publicSourceDir;
                        appPath.add(apppath);

                        String packgename = allTask.packageName;
                        packageName.add(packgename);


                    }

                    adapter = new Adapter_install_ApkManager(appName, appIcon, appPath, packageName, backupDir, activity);


                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            recyclerView = getView().findViewById(R.id.installedRecylerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
            super.onPostExecute(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activity, "Searching installed App", "searching initiated...");
            super.onPreExecute();
        }
    }


}
