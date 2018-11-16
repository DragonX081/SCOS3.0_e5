package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.model.FoodInfo;
import es.source.code.model.FoodStockInfo;

public class ServerObserverService extends Service {
    private ArrayList<FoodInfo>foodInfos;
    private GetStockThread getStockThread;
    private int mainPid;
    private String mainPackageName;
    private SCOS_Global_Application myApp;
    private Messenger msger_client;
    private Messenger mMessenger;
    private boolean isDataUpdated=true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("onCreate","oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        myApp = (SCOS_Global_Application) getApplication();
        mHandler cMessageHandler = new mHandler();
        mMessenger = new Messenger(cMessageHandler);
        Log.v("onbind","onbind");
        return mMessenger.getBinder();
    }
    /*
    private ArrayList<FoodStockInfo> getFoodStockInfos(){
        //todo this is simulation
        ArrayList<FoodStockInfo> foodStockInfoList = new ArrayList<FoodStockInfo>();
        SCOS_Global_Application myapp = (SCOS_Global_Application) getApplication();
        ArrayList<FoodInfo> foodInfos = myapp.getFoodInfos();
        for(FoodInfo foodInfo:foodInfos){
            //number control
            int baseStock = 5;
            FoodStockInfo mfoodStockInfo = new FoodStockInfo(foodInfo.getFoodName(),baseStock);
            foodStockInfoList.add(mfoodStockInfo);
        }
        return foodStockInfoList;
    }
    */
    private class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msger_client = msg.replyTo;
            switch (msg.what){
                case 1:
                    isDataUpdated=true;
                    getStockThread = new GetStockThread(this);
                    getStockThread.start();
                    Log.e("case1","case1");
                    break;
                case 0:
                    //close thread
                    if(getStockThread!=null) getStockThread.interrupt = true;
                    break;



            }
        }
    }

    private class GetStockThread extends Thread{
        mHandler mhandler;
        public boolean interrupt = false;
        public GetStockThread(mHandler mhandler){
            this.mhandler = mhandler;
        }
        @Override
        public void run() {
            Log.e("run","run");
            super.run();
            while(!interrupt) {//to close
                Message msg = new Message();
                msg.replyTo = new Messenger(mhandler);
                msg.what = 10;
                if(myApp.getFoodInfos().size()==0){
                    myApp.generateTestData();
                };
                foodInfos = myApp.getFoodInfos();
                //check updated
                isDataUpdated = checkDataUpdated(foodInfos);

                Bundle bundle = new Bundle();
                bundle.putSerializable("foodInfos",foodInfos);
                msg.setData(bundle);
                //todo send msg
                Log.e("isRunning",isAppRunning(myApp.getApplicationContext())?"true":"false");
                if(isAppRunning(myApp.getApplicationContext())) {
                    try {
                        if(isDataUpdated){
                            //start update notification service

                            Intent UpdateNotiIntent = new Intent(ServerObserverService.this,UpdateService.class);
                            UpdateNotiIntent.putExtra("action","PROVIDE_NEW_FOOD");
                            UpdateNotiIntent.putExtra("newFoodInfoList",foodInfos);
                            startService(UpdateNotiIntent);

                            //send message to mainScreen
                            msger_client.send(msg);
                            isDataUpdated = false;
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } } }

    }
    private boolean isAppRunning(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if(processInfos!=null){
            if(processInfos.get(0).processName.contains(packageName)) return true;
        }
        return false;
    }
    private boolean checkDataUpdated(ArrayList<FoodInfo> foodInfos){
        //to modify
        return isDataUpdated;
    }

}
