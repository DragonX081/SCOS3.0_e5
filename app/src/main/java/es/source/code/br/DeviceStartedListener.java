package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.source.code.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Intent updateServiceIntent = new Intent(context,UpdateService.class);
            updateServiceIntent.putExtra("action","BOOT_COMPLETED");

            context.startService(updateServiceIntent);
        }
    }
}
