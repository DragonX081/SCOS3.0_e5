package es.source.code.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.security.Permission;

import es.source.code.activity2.R;
import es.source.code.self_util.MailSender;
import es.source.code.self_util.SendMailUtil;

public class HelperGridViewAdapter extends BaseAdapter {
    private String[] helpList;
    private int[] iconList;
    private Activity mActivity;
    private Handler mHandler;
    public HelperGridViewAdapter(Activity mActivity,Handler mHandler, String[] helpList, int[] iconList) {
        this.helpList = helpList;
        this.iconList = iconList;
        this.mActivity = mActivity;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount() {
        return helpList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mActivity.getLayoutInflater().inflate(R.layout.helper_gv_item, null);
        ImageView iv = convertView.findViewById(R.id.help_gv_item_iv);
        TextView tv = convertView.findViewById(R.id.help_gv_item_tv);
        iv.setImageResource(iconList[position]);
        tv.setText(helpList[position]);
        convertView.setOnClickListener(new mClickListener(position));
        return convertView;
    }

    private void sendEmail() {
        SendMailUtil.send(mHandler);
        Toast.makeText(mActivity, "Email has been sent", Toast.LENGTH_SHORT).show();
        //Log.v("start to send: ","srtSend");
    }

    private void callCustomerService() {
        String phoneNumber = "5554";
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri num = Uri.parse("tel:" + phoneNumber);
        intent.setData(num);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity,new String[]{"android.permission.CALL_PHONE"},1);
        }
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) mActivity.startActivity(intent);
    }
    private void sendSms(){
        String message = "test Sms";
        String destTelNum = "9872321";
        Intent sendIntent = new Intent("SENT_SMS_ACTION");
        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");
        PendingIntent sendPI = PendingIntent.getBroadcast(mActivity,0,sendIntent,0);
        PendingIntent deliverPI = PendingIntent.getBroadcast(mActivity,0,deliverIntent,0);
        mActivity.registerReceiver(new myBroadCastReceiver(),new IntentFilter("SENT_SMS_ACTION"));
        mActivity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(mActivity,"delivered Complete",Toast.LENGTH_SHORT).show();
            }
        },new IntentFilter("DELIVERED_SMS_ACTION"));
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(destTelNum,null,message,sendPI,deliverPI);
    }
    private void aboutSystem(){

    }
    private class myBroadCastReceiver extends BroadcastReceiver {
        public myBroadCastReceiver(){
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(mActivity,"求助短信发送成功",Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(mActivity,"Send SMS Failed",Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(mActivity,"Radio OFF",Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(mActivity,"NULL_PDU",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private class mClickListener implements View.OnClickListener{
        private int position;

        public mClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position){
                case 0://sms
                    sendSms();
                    break;
                case 1://phone
                    callCustomerService();
                    break;
                case 2://mail
                    sendEmail();
                    break;
                case 3://system
                    aboutSystem();
                    break;
                case 4://protocol
                    break;
            }
        }
    }
}
