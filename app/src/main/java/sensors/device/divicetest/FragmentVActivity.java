package sensors.device.divicetest;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import sensors.device.divicetest.adapters.TabAdapter;
import sensors.device.divicetest.fragments.BatteryFragment;
import sensors.device.divicetest.fragments.DashBoardFragment;
import sensors.device.divicetest.fragments.DeviceFragment;
import sensors.device.divicetest.fragments.DisplayDFragment;

public class FragmentVActivity extends AppCompatActivity {


    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_v);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashBoardFragment(), "Dash Board");
        adapter.addFragment(new BatteryFragment(), "Battery");
        adapter.addFragment(new DeviceFragment(), "Device");
        adapter.addFragment(new DisplayDFragment(), "Display");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
