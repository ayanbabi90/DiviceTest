package sensors.device.divicetest.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import sensors.device.divicetest.R;

public class Adapter_install_ApkManager extends RecyclerView.Adapter
        <Adapter_install_ApkManager.Adapter_ApkManager_Install_ViewHolder> {
    Context context;
    private List<String> appNameList;
    private List<Drawable> appIconsList;
    private List<String> appPath;
    private List<String> packageName;
    private File dirPath;

    public Adapter_install_ApkManager(List<String> appNameList, List<Drawable> appIconsList, List<String> appPath, List<String> packageName, File dirPath, Context context) {
        this.appNameList = appNameList;
        this.appIconsList = appIconsList;
        this.appPath = appPath;
        this.packageName = packageName;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_ApkManager_Install_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.install_app_adapter_layout, parent, false);
        return new Adapter_ApkManager_Install_ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ApkManager_Install_ViewHolder holder, final int position) {


        final String Appnames = appNameList.get(position);
        Drawable AppIcons = appIconsList.get(position);

        holder.appNameTextview.setText(Appnames);
        holder.appIconImageView.setImageDrawable(AppIcons);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String pkname = packageName.get(position);
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + pkname));
                (context).startActivity(intent);


            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Backup Apps");
                builder.setMessage("initiate app backup... ");
                builder.setPositiveButton("proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String pkgname = packageName.get(position);
                        String pth = appPath.get(position);
                        File f1 = new File(pth);
                        try {

                            String file_name = pkgname;
                            Log.d("file_name--", " " + file_name);


                            File f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Folder");
                            f2.mkdirs();
                            f2 = new File(f2.getPath() + "/" + file_name + ".apk");
                            f2.createNewFile();


                            InputStream in = new FileInputStream(f1);
                            OutputStream out = new FileOutputStream(f2);

                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            in.close();
                            out.close();

                            Toast.makeText(context, pkgname, Toast.LENGTH_LONG).show();

                        } catch (FileNotFoundException ex) {
                            System.out.println(ex.getMessage() + " in the specified directory.");
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }


                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return appNameList.size();
    }

    class Adapter_ApkManager_Install_ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextview;
        LinearLayout linearLayout;


        public Adapter_ApkManager_Install_ViewHolder(@NonNull View itemView) {
            super(itemView);

            appIconImageView = itemView.findViewById(R.id.apkManager_installApp_ImageV);
            appNameTextview = itemView.findViewById(R.id.apkManager_InstallApp_text);
            linearLayout = itemView.findViewById(R.id.apkManger_LinearLayout_InstallApp);


        }
    }


}
