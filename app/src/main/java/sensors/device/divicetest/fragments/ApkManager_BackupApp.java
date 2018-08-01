package sensors.device.divicetest.fragments;


import android.app.Activity;
import android.content.pm.PackageManager;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.Adapter_backup_apkManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApkManager_BackupApp extends Fragment {
    RecyclerView recyclerView;
    private PackageManager packageManager = null;
    private Adapter_backup_apkManager adapterBackupapkManager = null;

    public ApkManager_BackupApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apk_manager__backup_app, container, false);

        recyclerView = view.findViewById(R.id.apkRecylerViewID);

        new LoadApplications(recyclerView, getActivity()).execute();

        return view;
    }


    //*************************   The AsyncTask  ************************************
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        //To get the Activity
        Activity activity;
        //To bind the activity
        RecyclerView recyclerView;


        public LoadApplications(RecyclerView recyclerView, Activity activity) {
            this.recyclerView = recyclerView;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {


            List<String> apkList = new ArrayList<>();
            List<String> apkPAth = new ArrayList<>();

            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            String[] extension = {"apk"};

            Collection<File> Allfiles = FileUtils.listFiles(new File(String.valueOf(filePath)), extension, true);

            for (File f1 : Allfiles) {
                apkList.add(f1.getName());
                apkPAth.add(f1.getAbsolutePath());

            }

            adapterBackupapkManager = new Adapter_backup_apkManager(apkList, apkPAth, activity);

            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            recyclerView = getView().findViewById(R.id.apkRecylerViewID);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(adapterBackupapkManager);
            super.onPostExecute(result);
        }


    }

}
