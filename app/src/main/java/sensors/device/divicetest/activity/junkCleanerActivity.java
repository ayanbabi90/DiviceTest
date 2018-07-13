package sensors.device.divicetest.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.Adapter_junkCleaner;

public class junkCleanerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter_junkCleaner adapter_junkCleaner;
    HTextView allfilesizeTextV;
    HTextView tagTextV;

    String[] extensions =
            {/*"mp4",*/"crypt12", "backup", "crypt1", "dat", "ulog", "log", "httpggimg", "ini", "html", "js", "tmp", "exo", "svg", "json", "css", "chck", "aspx"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junk_cleaner);

        final Button fAB = findViewById(R.id.bt123);

        fAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeletAsyncTask(junkCleanerActivity.this).execute();
            }
        });


        new JunkCleanerAsyncTask().execute();
        new NoOfRunningAppAsync(junkCleanerActivity.this).execute();


    }

    public class JunkCleanerAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;


        @Override
        protected Void doInBackground(Void... voids) {

            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            List<String> fileList = new ArrayList<>();
            List<String> filePathT = new ArrayList<>();
            List<String> size = new ArrayList<>();

            // File [] files =  filePath.listFiles();
            Collection<File> allFiles = FileUtils.listFiles(new File(String.valueOf(filePath)), extensions, true);

            for (File file : allFiles) {
                fileList.add(file.getName());
                filePathT.add(file.getAbsolutePath());
                long v = file.getAbsoluteFile().length();
                //file size converter
                String vsize = android.text.format.Formatter.formatFileSize(junkCleanerActivity.this, v);
                size.add(vsize);

            }

            adapter_junkCleaner = new Adapter_junkCleaner(junkCleanerActivity.this, fileList, filePathT, size);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView = findViewById(R.id.recylerViewFileFolderID);
            recyclerView.setLayoutManager(new LinearLayoutManager(junkCleanerActivity.this));
            recyclerView.setAdapter(adapter_junkCleaner);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(junkCleanerActivity.this, "Searching", "Searching junk files initiated");
            super.onPreExecute();
        }
    }

    public class NoOfRunningAppAsync extends AsyncTask<Void, String, String> {
        Activity mActivity;

        public NoOfRunningAppAsync(Activity mActivity) {
            this.mActivity = mActivity;
        }


        @Override
        protected String doInBackground(Void... voids) {
            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            List<String> fileList = new ArrayList<>();
            List<String> size = new ArrayList<>();

            // File [] files =  filePath.listFiles();
            Collection<File> allFiles = FileUtils.listFiles(new File(String.valueOf(filePath)), extensions, true);

            for (File file : allFiles) {
                fileList.add(file.getName());
                long v = file.getAbsoluteFile().length();
                //file size converter
                String vsize = android.text.format.Formatter.formatFileSize(junkCleanerActivity.this, v);
                size.add(vsize);
            }
            String rto = String.valueOf(fileList.size());
            return rto;
        }


        @Override
        protected void onPostExecute(String result) {
            allfilesizeTextV = mActivity.findViewById(R.id.textView7);
            tagTextV = mActivity.findViewById(R.id.junkfileTextv);

            allfilesizeTextV.setVisibility(View.VISIBLE);
            tagTextV.setVisibility(View.VISIBLE);


            // allfilesizeTextV.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/AmaticaSC-Regular.ttf"));
            allfilesizeTextV.setAnimateType(HTextViewType.RAINBOW);
            allfilesizeTextV.animateText(result);

            //  tagTextV.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/AmaticaSC-Regular.ttf"));
            tagTextV.setAnimateType(HTextViewType.TYPER);
            tagTextV.animateText("junk files found");


            super.onPostExecute(result);
        }
    }

    public class DeletAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        Activity activity;

        public DeletAsyncTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            Collection<File> allfile = FileUtils.listFiles(new File(String.valueOf(file)), extensions, true);
            for (File f : allfile) {
                File file1 = new File(f.getAbsolutePath());
                file1.delete();
            }

            List<String> s1 = new ArrayList<>();
            List<String> s2 = new ArrayList<>();
            List<String> s3 = new ArrayList<>();
            adapter_junkCleaner = new Adapter_junkCleaner(activity, s1, s2, s3);

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activity, "Process Initiated", "Deleting junk files");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            allfilesizeTextV.setVisibility(View.INVISIBLE);
            tagTextV.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }


}