package sensors.device.divicetest.activitys;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.ApkAdapter;

public class ApkActivity extends AppCompatActivity {
    private PackageManager packageManager = null;
    private ApkAdapter apkAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk);

        new LoadApplications().execute();


    }


    //The AsyncTask*********************************************************
    private class LoadApplications extends AsyncTask<Void,Void,Void>{
        private ProgressDialog progress=null;

        @Override
        protected Void doInBackground(Void... params) {


            List<String> apkList = new ArrayList<>();
            List<String> apkPAth = new ArrayList<>();

            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            String [] extension = {"apk"};

            Collection<File>  Allfiles = FileUtils.listFiles(new File(String.valueOf(filePath)),extension,true);

            for (File f1 : Allfiles){
                apkList.add(f1.getName());
                apkPAth.add(f1.getAbsolutePath());

            }

           apkAdapter =  new ApkAdapter(apkList,apkPAth,ApkActivity.this);

            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            RecyclerView recyclerView = findViewById(R.id.apkRecylerViewID);
            recyclerView.setLayoutManager(new LinearLayoutManager(ApkActivity.this));
            recyclerView.setAdapter(apkAdapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress =ProgressDialog.show(ApkActivity.this,"Searching","App Loading");
            super.onPreExecute();
        }


    }

}
