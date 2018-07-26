package sensors.device.divicetest.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyNetworkMod;
import github.nisrulz.easydeviceinfo.base.EasySimMod;
import github.nisrulz.easydeviceinfo.base.NetworkType;
import github.nisrulz.easydeviceinfo.base.PhoneType;
import sensors.device.divicetest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    TextView imei, imsi, sim_serial, carrer_Name, sim_Type, network_type, wifi_state, wifi_ssid, wifi_bssid, wifi_link_speed, ipv4, ipv6, wifiMac;


    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Infiltrate with views
        final View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        EasySimMod easySimMod = new EasySimMod(getContext());
        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        String imsiNo = easySimMod.getIMSI();
        String carrierName = easySimMod.getCarrier();
        String country = easySimMod.getCountry();
        String simSerialNo = easySimMod.getSIMSerial();
        boolean multiSim = easySimMod.isMultiSim();
        boolean simNetworkLocked = easySimMod.isSimNetworkLocked();
        String imeiNo = easyDeviceMod.getIMEI();

        imei = view.findViewById(R.id.imeiT);
        imsi = view.findViewById(R.id.imsiT);
        sim_serial = view.findViewById(R.id.simSerialNoT);
        carrer_Name = view.findViewById(R.id.carrierNameT);
        sim_Type = view.findViewById(R.id.simTypeT);
        network_type = view.findViewById(R.id.networkTypeT);
        wifi_state = view.findViewById(R.id.wifiStateT);
        wifi_bssid = view.findViewById(R.id.wifiBSSIDT);
        wifi_ssid = view.findViewById(R.id.wifiSSIDT);
        wifi_link_speed = view.findViewById(R.id.wifiSpeed);
        ipv4 = view.findViewById(R.id.ipvAT);
        ipv6 = view.findViewById(R.id.ipv6T);
        wifiMac = view.findViewById(R.id.wifiMacAT);


        @PhoneType
        int phoneType = easyDeviceMod.getPhoneType();

        switch (phoneType) {
            case PhoneType.CDMA:
                sim_Type.setText("CDMA");
                break;
            case PhoneType.GSM:
                sim_Type.setText("GSM");
                break;
            case PhoneType.NONE:
                sim_Type.setText("Un Known");
                break;
        }

        EasyNetworkMod easyNetworkMod = new EasyNetworkMod(getContext());

        @NetworkType
        int networkType = easyNetworkMod.getNetworkType();


        switch (networkType) {
            case NetworkType.CELLULAR_2G:
                network_type.setText("2G");
                break;
            case NetworkType.CELLULAR_3G:
                network_type.setText("3G");
                break;
            case NetworkType.CELLULAR_4G:
                network_type.setText("4G");
                break;
            case NetworkType.CELLULAR_UNIDENTIFIED_GEN:
                network_type.setText("Unidentified Generation");
                break;
            case NetworkType.CELLULAR_UNKNOWN:
                network_type.setText("UnKnown");
                break;
            case NetworkType.WIFI_WIFIMAX:
                network_type.setText("Wifi Max");
                break;
        }


        String ivp4Addrs = easyNetworkMod.getIPv4Address();
        String ipv6Addrs = easyNetworkMod.getIPv6Address();
        String wifiMacAddrs = easyNetworkMod.getWifiMAC();

        String wifiBssid = easyNetworkMod.getWifiBSSID();
        String wifiSSid = easyNetworkMod.getWifiSSID();
        String wifiLinkSpeed = easyNetworkMod.getWifiLinkSpeed();
        boolean wifiStateA = easyNetworkMod.isWifiEnabled();

        if (!wifiStateA) {
            wifi_state.setText("Disabled");
        } else {
            wifi_state.setText("Enabled");
        }


        imei.setText(imeiNo);
        imsi.setText(imsiNo);
        sim_serial.setText(simSerialNo);
        carrer_Name.setText(carrierName);
        wifiMac.setText(wifiMacAddrs);
        ipv4.setText(ivp4Addrs);
        ipv6.setText(ipv6Addrs);

        wifi_bssid.setText(wifiBssid);
        wifi_ssid.setText(wifiSSid);
        wifi_link_speed.setText(wifiLinkSpeed);




        return view;

    }



}