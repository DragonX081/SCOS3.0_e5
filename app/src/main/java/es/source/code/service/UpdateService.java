package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;

import es.source.code.activity2.FoodDetailed;
import es.source.code.activity2.MainScreen;
import es.source.code.activity2.R;
import es.source.code.model.FoodInfo;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "es.source.code.service.action.FOO";
    private static final String ACTION_BAZ = "es.source.code.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "es.source.code.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "es.source.code.service.extra.PARAM2";

    private ArrayList<FoodInfo> newFoodInfoList;
    private boolean toNotify = false;

    public UpdateService() {
        super("UpdateService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            toNotify = false;
            //todo reset action, maybe has a problem
            final String action = intent.getStringExtra("action");
            switch (action){
                case "PROVIDE_NEW_FOOD":
                    newFoodInfoList = (ArrayList<FoodInfo>) intent.getSerializableExtra("newFoodInfoList");
                    //for higher than 8.0 it should define a channel

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    //call Notification Channel

                    String channel_id = "scos_channel_newFood";
                    //channel name for user
                    CharSequence channelName = getString(R.string.channel_name_newFood);
                    //channel description for user
                    String channel_description = getString(R.string.channel_description_newFood);
                    //build Instance of channel
                    //todo reset importance
                    NotificationChannel notificationChannel = new NotificationChannel(channel_id,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                    //config channel properties
                    notificationChannel.setDescription(channel_description);
                    //config flashLight,vibration,
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100,200,300,500,300,200,100});
                    //build channel in notification manager
                    nm.createNotificationChannel(notificationChannel);

                    //to send notification for each new food

                    for(FoodInfo mfoodInfo:newFoodInfoList){
                        if(mfoodInfo.isNew()) {
                            //toNotify = true;
                            //create notification
                            Notification.Builder builder = new Notification.Builder(this, channel_id);

                            //notification content

                            builder.setContentTitle("客官别错过新品上架咯~~");
                            builder.setContentText(mfoodInfo.getFoodtype() + "新品上架：" + mfoodInfo.getFoodName() + ",仅需" + mfoodInfo.getFoodPrice());
                            builder.setSmallIcon(R.drawable.ic_fiber_new_black_24dp);


                            //notification intent action

                            //cancel
                            builder.setAutoCancel(true);
                            //audio

                            //other setting to default

                            //jump action
                            Intent notiJumpIntent = new Intent(UpdateService.this, FoodDetailed.class);
                            notiJumpIntent.putExtra("position", mfoodInfo.getFoodPos());
                            PendingIntent notiJumpPendingIntent = PendingIntent.getActivity(UpdateService.this,80,notiJumpIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(notiJumpPendingIntent);

                            Notification notification = builder.build();
                            nm.notify(0,notification);
                        }
                    }
                    break;
                case "BOOT_COMPLETED":
                    //todo startserver
                    Intent intent_server_observer = new Intent(UpdateService.this,ServerObserverService.class);
                    startService(intent_server_observer);
                    break;

            }


        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
