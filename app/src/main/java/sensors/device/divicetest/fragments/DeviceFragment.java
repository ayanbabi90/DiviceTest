package sensors.device.divicetest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import github.nisrulz.easydeviceinfo.base.EasyCpuMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import sensors.device.divicetest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {


    public DeviceFragment() {
        // Required empty public constructor
    }


    public static int GetNumberOfCores() {
        return (new File("/sys/devices/system/cpu/"))
                .listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return Pattern.matches("cpu[0-9]+", f.getName());
                    }
                }).length;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device, container, false);

        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getContext());
        EasyCpuMod easyCpuMod = new EasyCpuMod();

        TextView cpu, boadr, model, brand, sdk, serialno, boot_loader, buildid, code_name, hard_ware, user, radiov, host, buildvr, displayv, cpuCore;


        String serialNo = easyDeviceMod.getSerial();
        String board = easyDeviceMod.getBoard();
        String bootloader = easyDeviceMod.getBootloader();
        String brandName = easyDeviceMod.getBuildBrand();
        String buildId = easyDeviceMod.getBuildID();
        String codeName = easyDeviceMod.getBuildVersionCodename();
        String buildSdk = String.valueOf(easyDeviceMod.getBuildVersionSDK());
        String modelNAme = easyDeviceMod.getModel();
        boolean rootAcess = easyDeviceMod.isDeviceRooted();
        String hardware = easyDeviceMod.getHardware();
        String displayVersion = easyDeviceMod.getDisplayVersion();
        //String phno = easyDeviceMod.getPhoneNo();
        String radioV = easyDeviceMod.getRadioVer();
        String buildHostN = easyDeviceMod.getBuildHost();
        String buildVR = easyDeviceMod.getBuildVersionRelease();

        StringBuilder supportABI = new StringBuilder();
        for (String abis : easyCpuMod.getSupportedABIS()) {
            supportABI.append(abis).append("\n");
        }

        String supportedABI = supportABI.toString();

        serialno = view.findViewById(R.id.serialnoT);
        boadr = view.findViewById(R.id.boardT);
        boot_loader = view.findViewById(R.id.bootloader);
        brand = view.findViewById(R.id.brandnameT);
        buildid = view.findViewById(R.id.buildidT);
        code_name = view.findViewById(R.id.codenameT);
        sdk = view.findViewById(R.id.buuildsdkT);
        model = view.findViewById(R.id.modelNameT);
        hard_ware = view.findViewById(R.id.hadrwareT);
        displayv = view.findViewById(R.id.displayvT);
        radiov = view.findViewById(R.id.radiovT);
        host = view.findViewById(R.id.hostnameT);
        buildvr = view.findViewById(R.id.buildvrT);
        user = view.findViewById(R.id.userT);
        cpuCore = view.findViewById(R.id.cpuCore);

        cpu = view.findViewById(R.id.cpuT);


        cpu.setText(supportedABI);
        serialno.setText(serialNo);
        boadr.setText(board);
        boot_loader.setText(bootloader);
        brand.setText(brandName);
        buildid.setText(buildId);
        code_name.setText(codeName);
        sdk.setText(buildSdk);
        model.setText(modelNAme);
        hard_ware.setText(hardware);
        displayv.setText(displayVersion);
        radiov.setText(radioV);
        host.setText(buildHostN);
        buildvr.setText(buildVR);
        if (!rootAcess) {
            user.setText("Not Rooted !");
        } else {
            user.setText("Device Rooted !");
        }


        String noCPUOfCores = String.valueOf(GetNumberOfCores());

        cpuCore.setText(noCPUOfCores);




        return view;
    }


}
