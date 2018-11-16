package es.source.code.activity2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class SCOSEntry extends AppCompatActivity {
    private RelativeLayout rl_entry;
    private float mPosX = 0,mCurPosX = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        rl_entry = findViewById(R.id.RL_Entry);
        rl_entry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mCurPosX-mPosX<-5){
                            //action: slide left
                            Intent intent = new Intent(SCOSEntry.this,MainScreen.class);
                            intent.putExtra("From","FromEntry");
                            startActivity(intent);
                        }
                        break;

                }
                return true;
            }
        });
    }
}
