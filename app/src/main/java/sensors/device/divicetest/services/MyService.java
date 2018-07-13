//package sensors.device.divicetest.services;
//
//import android.app.ActivityManager;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.IBinder;
//import android.widget.Toast;
//
//public class MyService extends Service {
//    Wraper wraper;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Toast.makeText(this,"service created",Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this,"service Destoroy",Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//       // return super.onStartCommand(intent, flags, startId);
//        Toast.makeText(this,"on start command called",Toast.LENGTH_LONG).show();
//
//        new RAppMAsync().execute();
//
//        return START_REDELIVER_INTENT;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public class RAppMAsync extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            final Handler handler = new Handler();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    int rF = getFreeRam();
//                    int tF = getTotalRam();
//                    int pR = getRamPercentage();
//
//                  wraper = new Wraper(rF,tF,pR);
//
//                    handler.postDelayed(this, 100);
//                }
//
//            });
//        return null;
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }
//
//    public class Wraper{
//        int freeRAM,totalRAM,ramPercentage;
//
//        public Wraper(int freeRAM, int totalRAM, int ramPercentage) {
//            this.freeRAM = freeRAM;
//            this.totalRAM = totalRAM;
//            this.ramPercentage = ramPercentage;
//        }
//
//        public int getFreeRAM() {
//            return freeRAM;
//        }
//
//        public int getTotalRAM() {
//            return totalRAM;
//        }
//
//        public int getRamPercentage() {
//            return ramPercentage;
//        }
//    }
//
//
//    int getFreeRam() {
//
//        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(memInfo);
//        String freeRam = String.valueOf(memInfo.availMem);
//        int r1 = Integer.parseInt(freeRam);
//        int dfr = r1 / 1000000;
//        return dfr;
//    }
//
//    int getTotalRam() {
//        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(memInfo);
//        String TotalRam = String.valueOf(memInfo.totalMem);
//        int r1 = Integer.parseInt(TotalRam);
//        int dfr = r1 / 1000000;
//        return dfr;
//
//    }
//
//    int getRamPercentage() {
//        double vp1 = getFreeRam();
//        double vp2 = getTotalRam();
//
//        Integer ramo = Integer.valueOf((int) (vp1 / vp2 * 100));
//        return ramo;
//    }
//
//
//
//}
