package sensors.device.divicetest.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import sensors.device.divicetest.R;

public class Adapter_backup_apkManager extends RecyclerView.Adapter<Adapter_backup_apkManager.ApkAdapterViewHolder> {

    List<String> apkList;
    List<String> apkPath;
    Context context;

    public Adapter_backup_apkManager(List<String> apkList, List<String> apkPath, Context context) {

        this.apkList = apkList;
        this.apkPath = apkPath;
        this.context = context;
    }

    @NonNull
    @Override
    public ApkAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.apk_layout, parent, false);
        return new ApkAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApkAdapterViewHolder holder, final int position) {
        final String apkName = apkList.get(position);

        holder.apkText.setText(apkName);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(apkPath.get(position));

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                (context).startActivity(intent);
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Caution !!");
                builder.setMessage("Deleting files can't be reversed");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        File file2 = new File(apkPath.get(position));
                        boolean bf3 = file2.delete();
                        if (!bf3) {
                            Toast.makeText(context, "error deleting file" + apkList.get(position), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "success deleting file" + apkList.get(position), Toast.LENGTH_LONG).show();
                            apkList.remove(position);
                            notifyDataSetChanged();
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
        return apkList.size();
    }

    public class ApkAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView apkText;
        LinearLayout linearLayout;
        ImageView imageView;

        public ApkAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            apkText = itemView.findViewById(R.id.apkTextViewID);
            linearLayout = itemView.findViewById(R.id.apkLAyoutID);
            imageView = itemView.findViewById(R.id.apkIco);

        }
    }

}
