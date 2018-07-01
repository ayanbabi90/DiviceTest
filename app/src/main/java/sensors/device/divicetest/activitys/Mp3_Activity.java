package sensors.device.divicetest.activitys;

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
import sensors.device.divicetest.adapters.Mp3Adapter;

public class Mp3_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_);

        RecyclerView recyclerView = findViewById(R.id.mp3RecylerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> mp3FileList = new ArrayList<>();
        List<String> mprPath = new ArrayList<>();

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        String [] extension = {"mp3"};
        Collection<File> allFiles = FileUtils.listFiles(new File(String.valueOf(file)),extension,true);


        for (File file1 : allFiles){

            mp3FileList.add(file1.getName());
            mprPath.add(file1.getAbsolutePath());

        }


        recyclerView.setAdapter(new Mp3Adapter(mp3FileList,mprPath,this));


    }
}
