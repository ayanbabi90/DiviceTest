package sensors.device.divicetest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import github.nisrulz.easydeviceinfo.base.EasyDisplayMod;
import sensors.device.divicetest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayDFragment extends Fragment {


    public DisplayDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        EasyDisplayMod easyDisplayMod = new EasyDisplayMod(getActivity());

        TextView resulation = view.findViewById(R.id.DisplayResulationID);
        TextView density = view.findViewById(R.id.DisplayDensityID);
        TextView DisplaySize = view.findViewById(R.id.displaySizeID);
        TextView densityRefresh = view.findViewById(R.id.DisplayRefreshRate);


        String resu = easyDisplayMod.getResolution();
        String ppi = easyDisplayMod.getDensity();
        String PhysicalSize = String.valueOf(easyDisplayMod.getPhysicalSize());
        String ps = PhysicalSize.substring(0,PhysicalSize.length()-6)+"Inch";

        String refreashRate = String.valueOf(easyDisplayMod.getRefreshRate());
        String rr = refreashRate.substring(0,refreashRate.length()-7) +"Hz";

        resulation.setText(resu);
        density.setText(ppi);
        DisplaySize.setText(ps);
        densityRefresh.setText(rr);





        return view;
    }

}
