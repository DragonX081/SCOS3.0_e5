package es.source.code.activity2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import es.source.code.adapter.HelperGridViewAdapter;

public class SCOSHelper extends AppCompatActivity {
    GridView gv;
    String[] helpList={"短信帮助","电话人工帮助","邮件帮助","关于系统","用户使用协议"};
    int[] iconList = {R.drawable.ic_sms_white_48dp,R.drawable.ic_phone_forwarded_white_48dp,R.drawable.ic_mail_outline_white_48dp,R.drawable.ic_settings_black_48dp,R.drawable.ic_insert_drive_file_white_48dp};
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);
        gv = findViewById(R.id.help_gv);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int index = msg.what;
                if(index==1) Toast.makeText(SCOSHelper.this,"邮件发送成功",Toast.LENGTH_SHORT).show();
            }
        };
        gv.setAdapter(new HelperGridViewAdapter(this,mHandler,helpList,iconList));
    }
}
