package es.source.code.activity2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.source.code.adapter.MainScreenVpAdapter;
import es.source.code.data_control.FoodDataControl;
import es.source.code.fragments.fragment_food_submitted;
import es.source.code.fragments.fragment_orderFood;
import es.source.code.fragments.fragment_ordered;
import es.source.code.fragments.fragment_setting;
import es.source.code.model.FoodInfo;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;

public class MainScreen extends AppCompatActivity {
    private BottomNavigationBar naviBar;
    private ViewPager vp;
    private boolean orderHide = true;
    private boolean initialized = false;
    private User userInfo;
    private String from;
    private ArrayList<FoodInfo>foodInfos;
    private ArrayList<FoodInfo>foodUnOrderedList;
    private ArrayList<FoodInfo>foodOrderList;
    private int[] foodLabelPosition;
    private ArrayList<String> foodTypeLabels;
    private MainScreenVpAdapter vpAdapter;
    private SCOS_Global_Application myApp;
    private SharedPreferences sharedPreferences;
    private int setPagePosition = 0;
    private int loginState;
    private Intent intentService;
    private ServiceConnection connection;
    private Messenger mServerMessenger;
    private IBinder iBinder;
    private myHandler mHandler;
    List<Fragment> fragmentList;
    private boolean dataLoaded = false;
    private Messenger mMessenger;
    private boolean serviceStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.v("create", "create");
        //get and manipulate data
        myApp = (SCOS_Global_Application)getApplication();
        sharedPreferences = getSharedPreferences("scos_shpref",Context.MODE_PRIVATE);
        mHandler = new myHandler();


        // get data


        /*
        if(myApp.getFoodInfos()==null)  {//initialize global data
            myApp.setFoodInfos(FoodInfo.getTestFoodInfo(20));
            myApp.setFoodUnOrderedList(myApp.getFoodInfos());
        }
        */


        if(myApp.getFoodInfos()!=null){
            foodInfos = myApp.getFoodInfos();
        }



        //bindRemoteServer();


        //

        //


        initialized = false;
        orderHide = true;
        Intent intent = getIntent();
        try {
            from = intent.getStringExtra("From");
            if(from==null) from = "default";
            setPagePosition = intent.getIntExtra("position",0);
        } catch (Exception except) {
            // TODO: 2018/9/19
        }
        //Log.v("Activity From", from);
        //navi Bar
        naviBar = findViewById(R.id.naviBar_Main);
        naviBar
                .setTabSelectedListener(new mySelect())
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setInActiveColor("#DDDDDD")
                .setBarBackgroundColor("#FFFFFF")
                .setActiveColor(R.color.colorAccent);
        vp = findViewById(R.id.main_viewPager);
        fragmentList=new ArrayList<Fragment>();//todo improve
        if (orderHide) {
            if (from.equals("FromEntry") || isSucceedLogin()||from.equals("FromFoodDetail")) {
                Log.v("add", "in");
                naviBar.addItem(new BottomNavigationItem(R.drawable.ic_restaurant_menu_white_48dp, "点菜"))
                        .addItem(new BottomNavigationItem(R.drawable.ic_add_shopping_cart_white_48dp, "查看订单"))
                        .addItem(new BottomNavigationItem(R.drawable.ic_content_paste_white_48dp,"已点菜单"));
                orderHide = false;
                //set default Fragment
                //
                Fragment frag_order_food = new fragment_orderFood();
                fragmentList.add(frag_order_food);
                //
                //todo add login help
                //todo: put paragm to ordered food fragment
                fragmentList.add(new fragment_ordered());
                //add submitted food frag
                fragmentList.add(new fragment_food_submitted());
            }
        }
        if (!initialized) {
            naviBar.addItem(new BottomNavigationItem(R.drawable.ic_settings_48pt, "通用设置"));
            fragmentList.add(new fragment_setting());
            vpAdapter = new MainScreenVpAdapter(getSupportFragmentManager(),fragmentList);
            if(dataLoaded)vp.setAdapter(vpAdapter);
            vp.addOnPageChangeListener(new myPagerChange());
            initialized = true;
        }
        naviBar.initialise();
        //get user info
        if(isSucceedLogin()){
            try{
                userInfo = (User) intent.getExtras().getSerializable("User");
            }catch (Exception exception){
                //todo
            }
        }
        //for new users
        if(userInfo!=null) {
            Log.v("old",userInfo.isOldUser()?"true":"false");
            if (!userInfo.isOldUser()) {
                Toast.makeText(MainScreen.this, "欢迎您成为SCOS新用户", Toast.LENGTH_LONG).show();
            }
        }
        //To Do
        if(foodInfos!=null)vp.setAdapter(vpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        if(!serviceStart){
            menu.findItem(R.id.item_st_service).setVisible(true);
            menu.findItem(R.id.item_stop_service).setVisible(false);
        }
        else{
            menu.findItem(R.id.item_st_service).setVisible(false);
            menu.findItem(R.id.item_stop_service).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_orderedFood:
                if(!orderHide) {
                    //todo jump to frag_orderfood
                    vp.setCurrentItem(2);
                }else{
                    Toast.makeText(this,"请登陆",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_command:
                if(!orderHide) {
                    //todo jump to commanded
                    vp.setCurrentItem(1);
                }else{
                    Toast.makeText(this,"请登陆",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_service:
                //todo call service
                Toast.makeText(this, "服务员~~~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_st_service:
                bindRemoteServer();
                invalidateOptionsMenu();
                Toast.makeText(this,"server binded",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_stop_service:
                unBindServer();
                invalidateOptionsMenu();
                Toast.makeText(this,"Server unbinded",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("MainScreen","onSaveInstance");
        if(dataLoaded)vp.setAdapter(vpAdapter);
    }

    @Override
    protected void onStart() {
        Log.v("start", "start");
        try{getIntent().getIntExtra("position",0);}catch (Exception e){}
        vp.setCurrentItem(setPagePosition);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null)unbindService(connection);
    }

    private class mySelect implements BottomNavigationBar.OnTabSelectedListener {
        @Override
        public void onTabSelected(int position) {
            if (orderHide) {
                //need to be improved
                switch (position) {
                    case 0:// select login
                        //Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        //startActivity(intent);
                        break;
                    case 1://select help
                        break;
                    default:
                        break;
                }
            } else {
                switch (position) {
                    case 0: //order
                        vp.setCurrentItem(0);
                        break;
                    case 1://command
                        vp.setCurrentItem(1);
                        break;
                    case 2:
                        vp.setCurrentItem(2);
                        //login
                        //Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        //startActivity(intent);
                        break;
                    case 3:
                        vp.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {
            if (orderHide) {
                switch (position) {
                    case 0:
                        //need to be improved
                        //Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        //startActivity(intent);
                        break;
                    case 1://help
                        break;
                    default:
                        break;
                }
            } else {
                switch (position) {
                    case 0: //command
                        break;
                    case 1://order
                        break;
                    case 2: //login
                        //Intent intent = new Intent(MainScreen.this, LoginOrRegister.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        //startActivity(intent);
                        break;
                    case 3://help
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private class myPagerChange implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            naviBar.selectTab(i);
            myApp.updateUnOrderedList();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
    private int getLoginState(){
        int loginState = sharedPreferences.getInt("loginState",0);
        return loginState;
    }
    private boolean isSucceedLogin(){
        int loginState = getLoginState();
        return loginState==1;
    }

    private void bindRemoteServer(){
        Intent intentService = new Intent(this,ServerObserverService.class);
        //intentService.setAction("com.future.scos.ServerObserverService");
        //intentService.setPackage("com.future.scos");
        mHandler = new myHandler();
        mMessenger = new Messenger(mHandler);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mServerMessenger = new Messenger(service);
                iBinder = service;
                Message msg = new Message();
                msg.what = 1;
                msg.replyTo = mMessenger;
                try {
                    mServerMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iBinder = null;
            }
        };
        bindService(intentService,connection,BIND_AUTO_CREATE);
        serviceStart = true;
    }
    private void unBindServer(){
        Message msg = new Message();
        msg.what = 0;
        msg.replyTo = mMessenger;
        try {
            mServerMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        serviceStart = false;

    }
    private class myHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                //todo
                case 10:
                    foodInfos = (ArrayList<FoodInfo>) msg.getData().getSerializable("foodInfos");
                    //Log.i("price",String.valueOf(foodInfos.get(0).getFoodPrice()));
                    myApp.setFoodInfos(foodInfos);
                    foodOrderList = myApp.getFoodOrderList();
                    foodUnOrderedList = myApp.getFoodUnOrderedList();
                    //todo improve
                    vp.setAdapter(vpAdapter);
                    dataLoaded = true;
                    break;
            }
        }
    }

}

