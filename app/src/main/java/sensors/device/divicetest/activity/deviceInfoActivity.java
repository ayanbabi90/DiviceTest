package sensors.device.divicetest.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import sensors.device.divicetest.R;
import sensors.device.divicetest.adapters.TabAdapter;
import sensors.device.divicetest.fragments.DashBoardFragment;
import sensors.device.divicetest.fragments.DeviceFragment;
import sensors.device.divicetest.fragments.DisplayDFragment;
import sensors.device.divicetest.fragments.SensorsFragment;

public class deviceInfoActivity extends AppCompatActivity {


    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new DeviceFragment(), "Device");
        adapter.addFragment(new DashBoardFragment(), "Network Info");
        adapter.addFragment(new SensorsFragment(), "Sensors");
        adapter.addFragment(new DisplayDFragment(), "Display");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        AndroidPermissionChck();

    }

    private void AndroidPermissionChck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]
                                {
                                        Manifest.permission.READ_PHONE_STATE
                                        , Manifest.permission.ACCESS_WIFI_STATE
                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_EXTERNAL_STORAGE
                                }
                        , 1);

            }
        }

    }

}
