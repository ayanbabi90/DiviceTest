package sensors.device.divicetest.adapters;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sensors.device.divicetest.R;

public class Adapter_phoneBooster extends RecyclerView.Adapter<Adapter_phoneBooster.ShowAppAdapterViewHolder> {

    private List<String> appProcessInfos;
    private List<Drawable> drawables;
    private List<String> pids;

    public Adapter_phoneBooster
            (List<String> appProcessInfos, List<Drawable> drawables, List<String> pids) {
        this.appProcessInfos = appProcessInfos;
        this.drawables = drawables;
        this.pids = pids;


    }

    @NonNull
    @Override
    public ShowAppAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.phone_bhooster_extra, parent, false);
        return new ShowAppAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAppAdapterViewHolder holder, int position) {
        String title = appProcessInfos.get(position);
        Drawable icons = drawables.get(position);
        final String pid = pids.get(position);


        holder.textView.setText(title);
        holder.pid.setText(pid);
        holder.imageView.setImageDrawable(icons);
        holder.appLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return appProcessInfos.size();
    }

    class ShowAppAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        TextView pid;
        ConstraintLayout appLayout;

        ShowAppAdapterViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            pid = itemView.findViewById(R.id.pidID);
            appLayout = itemView.findViewById(R.id.appShowLayoutID);

        }
    }


}
