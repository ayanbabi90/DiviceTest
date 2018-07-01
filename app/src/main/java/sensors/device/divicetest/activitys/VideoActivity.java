package sensors.device.divicetest.activitys;

import android.content.Context;
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
import sensors.device.divicetest.adapters.FileListAdapter;

public class VideoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_and_folder);
        recyclerView = findViewById(R.id.recylerViewFileFolderID);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        List<String> fileList = new ArrayList<>();
        List<String> filePathT = new ArrayList<>();
        List<String>  size = new ArrayList<>();
        // File [] files =  filePath.listFiles();
        String[] extensions = {"apk"};
        Collection<File> allFiles = FileUtils.listFiles(new File(String.valueOf(filePath)), extensions, true);

       for (File file: allFiles) {
            fileList.add(file.getName());
            filePathT.add(file.getAbsolutePath());
        }

        recyclerView.setAdapter(new FileListAdapter(this, fileList,filePathT,size));





    }



}