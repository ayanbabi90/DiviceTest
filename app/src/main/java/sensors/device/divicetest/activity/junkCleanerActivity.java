package sensors.device.divicetest.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
    Dialog dialog;

    String[] extensions =
            {/*"mp4",*/"crypt12", "backup", "crypt1", "dat", "ulog", "log", "httpggimg", "ini", "html", "js", "tmp", "exo", "svg", "json", "css", "chck", "aspx"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junk_cleaner);

        //******************************Async Task**********************
        new JunkCleanerAsyncTask().execute();
        SharedPreferences spf = getApplicationContext().getSharedPreferences("aPPData", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbarJK);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button fAB = findViewById(R.id.bt123);


        allfilesizeTextV = findViewById(R.id.textView7);
        tagTextV = findViewById(R.id.junkfileTextv);

        allfilesizeTextV.setVisibility(View.VISIBLE);
        tagTextV.setVisibility(View.VISIBLE);


        //*******************************************************************************


        dialog = new Dialog(junkCleanerActivity.this, R.style.Theme_AppCompat_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custome_junk_cleaner_end);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        AdView mainFbAddV = new AdView(junkCleanerActivity.this, "681947592150931_684198228592534", AdSize.BANNER_HEIGHT_50);
        // Find the Ad Container
        LinearLayout fbAdd = dialog.findViewById(R.id.banner_end);
        // Add the ad view to your activity layout
        fbAdd.addView(mainFbAddV);
        // Request an ad
        mainFbAddV.loadAd();




        fAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeletAsyncTask(junkCleanerActivity.this).execute();
                dialog.show();
                final TextView tv = dialog.findViewById(R.id.tsdTXTV);
                SharedPreferences spf = getApplicationContext().getSharedPreferences("aPPData", MODE_PRIVATE);
                String str = spf.getString("filesDs", null);
                if (str != null) {
                    tv.setText(str);
                }
                final LottieAnimationView la2 = dialog.findViewById(R.id.animS);
                la2.setAnimation(R.raw.junk_file);
                la2.playAnimation();

                Handler h2 = new Handler();
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        la2.setVisibility(View.INVISIBLE);
                        LottieAnimationView la = dialog.findViewById(R.id.animM);
                        la.setAnimation(R.raw.pb2);
                        la.playAnimation();
                    }
                }, 7000);

                Handler h3 = new Handler();
                h3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LottieAnimationView la = dialog.findViewById(R.id.animM);
                        la.setVisibility(View.INVISIBLE);

                        TextView tr = dialog.findViewById(R.id.textView221);
                        tr.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);

                    }
                }, 8000);


            }
        });


        String result = spf.getString("filesD", "");


        // allfilesizeTextV.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/AmaticaSC-Regular.ttf"));
        allfilesizeTextV.setAnimateType(HTextViewType.RAINBOW);
        allfilesizeTextV.animateText(result);

        //  tagTextV.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/AmaticaSC-Regular.ttf"));
        tagTextV.setAnimateType(HTextViewType.TYPER);
        tagTextV.animateText("junk files found");


        AndroidPermissionChck();


    }


    private void AndroidPermissionChck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                                {
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_EXTERNAL_STORAGE
                                }
                        , 1);

            }
        }

    }


    public class JunkCleanerAsyncTask extends AsyncTask<Void, Void, Void> {

        Dialog dialog = null;


        @Override
        protected Void doInBackground(Void... voids) {

            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            List<String> fileList = new ArrayList<>();
            List<String> filePathT = new ArrayList<>();
            List<String> size = new ArrayList<>();
            List<Long> sizeT = new ArrayList<>();

            // File [] files =  filePath.listFiles();
            Collection<File> allFiles = FileUtils.listFiles(new File(String.valueOf(filePath)), extensions, true);

            for (File file : allFiles) {
                fileList.add(file.getName());
                filePathT.add(file.getAbsolutePath());
                long v = file.getAbsoluteFile().length();
                //file size converter
                String vsize = android.text.format.Formatter.formatFileSize(junkCleanerActivity.this, v);
                size.add(vsize);
                sizeT.add(v);


            }

            adapter_junkCleaner = new Adapter_junkCleaner(junkCleanerActivity.this, fileList, filePathT, size);


            SharedPreferences spf = getApplicationContext().getSharedPreferences("aPPData", MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            String fL = String.valueOf(fileList.size());

            long sum = 0;
            for (long i : sizeT) {
                sum += i;
            }

            String vs = android.text.format.Formatter.formatFileSize(junkCleanerActivity.this, sum);


            editor.putString("filesD", fL);
            editor.apply();


            editor.putString("filesDs", vs);
            editor.apply();



            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView = findViewById(R.id.recylerViewFileFolderID);
            recyclerView.setLayoutManager(new LinearLayoutManager(junkCleanerActivity.this));
            recyclerView.setAdapter(adapter_junkCleaner);
            dialog.dismiss();
            super.onPostExecute(aVoid);
        }


        @Override
        protected void onPreExecute() {


            dialog = new Dialog(junkCleanerActivity.this, R.style.Theme_AppCompat_NoActionBar);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custome_junk_cleaner_start);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            AdView fbAddV = new AdView(junkCleanerActivity.this, "681947592150931_684195985259425", AdSize.BANNER_HEIGHT_50);
            // Find the Ad Container
            LinearLayout adContainer = dialog.findViewById(R.id.junk_bnnr_start);
            // Add the ad view to your activity layout
            adContainer.addView(fbAddV);
            // Request an ad
            fbAddV.loadAd();


            final LottieAnimationView la2 = dialog.findViewById(R.id.anim_second);
            la2.setAnimation(R.raw.verify_phone);
            la2.playAnimation();
//
//            Handler h2 = new Handler();
//            h2.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    la2.setVisibility(View.INVISIBLE);
//                    LottieAnimationView la = dialog.findViewById(R.id.anim_main);
//                    la.setAnimation(R.raw.pb2);
//                    la.playAnimation();
//                }
//            },8000);

            super.onPreExecute();
        }
    }


    public class DeletAsyncTask extends AsyncTask<Void, Void, Void> {


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
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}