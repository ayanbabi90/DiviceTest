package sensors.device.divicetest;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sensors.device.divicetest.adapters.ShowAppAdapter;

public class ShowAllRunningAppActivity extends AppCompatActivity {

    private ShowAppAdapter showAppAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_running_app);
        recyclerView = findViewById(R.id.showAppRecylerID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        PackageManager pm = this.getPackageManager();

        List<String> appList=new ArrayList<>();
        List<Drawable> appListIcons = new ArrayList<>();
        List<String> pids = new ArrayList<>();


        List<ActivityManager.RunningAppProcessInfo> allTasks = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo allTask : allTasks) {
            try {
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(allTask.processName, PackageManager.GET_META_DATA));
                String packagename = allTask.processName;
                Drawable ico = pm.getApplicationIcon(packagename);
                String pid = String.valueOf(allTask.pid);

                pids.add(pid);
                appList.add(c.toString());
                appListIcons.add(ico);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        recyclerView.setAdapter(new ShowAppAdapter(appList,appListIcons,pids));





    }




}
