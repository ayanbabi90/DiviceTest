package sensors.device.divicetest.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import java.util.List;

import sensors.device.divicetest.R;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListAdapterViewHolder> {


    private List<String> fileList;
    private List<String> filePath;
    List<String> size;
    Context context;


    public FileListAdapter(Context context,List<String> fileList, List<String> filePath,List<String> size){
        this.fileList = fileList;
        this.context = context;
        this.filePath = filePath;
        this.size = size;
    }



    @NonNull
    @Override
    public FileListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.files_xtra_layout, parent,false);
        return new FileListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileListAdapterViewHolder holder, final int position) {
        final String title = String.valueOf(fileList.get(position));
        holder.fileNameEID.setText(title);
        Log.isLoggable("title",position);



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File fget = new File(filePath.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //To send the activity by type
                intent.setDataAndType(Uri.fromFile(fget),"application/vnd.android.package-archive");
                (context).startActivity(intent);

            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Caution !!");
                builder.setMessage("Remember deleting files can't be reversed");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        File fileTobeUsed = new File(filePath.get(position));
                        boolean b = fileTobeUsed.delete();
                        if (!b){

                            Toast.makeText(context, "error  "  + fileList.get(position), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context, "success  "  + fileList.get(position), Toast.LENGTH_LONG).show();
                            fileList.remove(position);
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
        return fileList.size();
    }

    public class FileListAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView fileNameEID, size;
        ImageView imageView2;
        LinearLayout linearLayout;


        public FileListAdapterViewHolder(View itemView) {
            super(itemView);

            fileNameEID = itemView.findViewById(R.id.fileNameEID);
            imageView2 = itemView.findViewById(R.id.imageView2);
            linearLayout = itemView.findViewById(R.id.linearShFID);

        }


    }



}
