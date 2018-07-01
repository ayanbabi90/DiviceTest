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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import sensors.device.divicetest.R;

public class Mp3Adapter extends RecyclerView.Adapter<Mp3Adapter.Mp3AdapterViewHolder> {

    List<String> mp3FileList;
    List<String> mp3Path;
    Context context;

    public Mp3Adapter(List<String> mp3FileList, List<String> mp3Path, Context context) {
        this.mp3FileList = mp3FileList;
        this.mp3Path = mp3Path;
        this.context = context;

    }

    @NonNull
    @Override
    public Mp3AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.mp3_holder_lay, viewGroup,false);
        return new Mp3AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Mp3AdapterViewHolder holder, final int position) {
        final String mp3Title = mp3FileList.get(position);

        holder.mp3Text.setText(mp3Title);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(mp3Path.get(position));

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"audio/mp3");
                (context).startActivity(intent);


            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Caution !!");
                builder.setMessage("Deleting files could not be reversed");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        File file = new File(mp3Path.get(position));

                        boolean bf = file.delete();
                        if (!bf){
                            Toast.makeText(context,"error deleting file"+mp3FileList.get(position),Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(context,"success deleting file"+mp3FileList.get(position),Toast.LENGTH_LONG).show();

                        }

                    }
                });

                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
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
        return mp3FileList.size();
    }

    public class Mp3AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView mp3Text;
        LinearLayout linearLayout;

        public Mp3AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mp3Text = itemView.findViewById(R.id.mp3TextViewID);
            linearLayout = itemView.findViewById(R.id.mp3LayoutId);


        }
    }

}
