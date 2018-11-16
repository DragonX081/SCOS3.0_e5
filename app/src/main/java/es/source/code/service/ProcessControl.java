package es.source.code.service;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class ProcessControl {
    public static boolean isAppRunning(Context context,String packageName,int pid){

        //todo maybe deprecated
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo runningAppProcessInfo:list){
            Log.v("pid+Name",String.valueOf(runningAppProcessInfo.pid)+" "+runningAppProcessInfo.processName);
            if(runningAppProcessInfo.pid==pid&&runningAppProcessInfo.processName.equals(packageName)){
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
