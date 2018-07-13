package sensors.device.divicetest.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sensors.device.divicetest.R;

public class AdapterCpuCooler extends RecyclerView.Adapter<AdapterCpuCooler.AdapterCpuCoolerViewHolder> {

    List<String> appname;
    List<Drawable> icons;

    Context context;


    public AdapterCpuCooler(List<String> appname, List<Drawable> icons, Context context) {
        this.appname = appname;
        this.icons = icons;
        this.context = context;
        //  mChecked = new boolean[appname.fileSize()];
    }

    @NonNull
    @Override
    public AdapterCpuCoolerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cpu_cooler_extends, viewGroup, false);
        return new AdapterCpuCoolerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCpuCoolerViewHolder holder, final int position) {

        String Aname = appname.get(position);
        Drawable Aico = icons.get(position);


        holder.icon.setImageDrawable(Aico);
        holder.nameT.setText(Aname);

    }

    @Override
    public int getItemCount() {
        return appname.size();
    }

    class AdapterCpuCoolerViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView nameT;


        public AdapterCpuCoolerViewHolder(@NonNull View itemView) {
            super(itemView);

            nameT = itemView.findViewById(R.id.cpuNameText);
            icon = itemView.findViewById(R.id.cpuAppImg);


        }


    }


}
