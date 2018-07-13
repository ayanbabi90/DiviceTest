package sensors.device.divicetest.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.TabAdapter;
import sensors.device.divicetest.fragments.ApkManager_BackupApp;
import sensors.device.divicetest.fragments.ApkManager_InstalledApp;

public class apkManagerActivity extends AppCompatActivity {


    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_manager);

        toolbar = findViewById(R.id.toolbarApkManager);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

         viewPager = findViewById(R.id.ApkManagerViewPager);
         tabLayout = findViewById(R.id.appManagerTablayout);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new ApkManager_InstalledApp(),"Installed App");
        adapter.addFragment(new ApkManager_BackupApp(),"Backup App");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }



}
