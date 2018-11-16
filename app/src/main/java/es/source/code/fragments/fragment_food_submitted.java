package es.source.code.fragments;

import android.content.Context;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import es.source.code.activity2.R;
import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.adapter.FoodSubmittedAdapter;
import es.source.code.data_control.FoodDataControl;
import es.source.code.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link fragment_food_submitted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_food_submitted extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters

    private ListView listView;
    private TextView tv_price;
    private Button btn;
    private FoodSubmittedAdapter adapter;
    private SCOS_Global_Application myApp;
    private User userInfo=null;
    private ProgressBar progressBar;
    public fragment_food_submitted() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static fragment_food_submitted newInstance(String param1, String param2) {
        fragment_food_submitted fragment = new fragment_food_submitted();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (SCOS_Global_Application) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_food_submitted, container, false);
        listView = view.findViewById(R.id.submitted_lv);
        userInfo = myApp.getUserInfo();
        tv_price = view.findViewById(R.id.tv_price_frag_submitted);
        btn = view.findViewById(R.id.btn_frag_submitted_check);
        progressBar = view.findViewById(R.id.prgBar_frag_submitted_check);
        adapter = new FoodSubmittedAdapter(this,listView);
        listView.setAdapter(adapter);
        btn.setOnClickListener(new btnClick());
        updatePrice();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisible()) {
            myApp.getFoodSubmittedList();
            userInfo = myApp.getUserInfo();
            listView.setAdapter(adapter);
            updatePrice();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void updatePrice(){
        DecimalFormat df = new DecimalFormat("#0.00");
        float price = FoodDataControl.calPriceSum(myApp.getFoodSubmittedList());
        String sumPrice = df.format(price) + " 元";
        tv_price.setText(sumPrice);
    }
    private class btnClick implements View.OnClickListener{
        private boolean isClickable = true;
        private int duration = 6000;
        private int progress = 0;
        private int runTime = 0;
        private int sleepTime = 100;
        @Override
        public void onClick(View v) {
            if(isClickable) {
                final Handler mhandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.what==1) progressBar.setVisibility(View.INVISIBLE);
                        myApp.dataInit();
                        adapter.notifyDataSetChanged();
                        updatePrice();
                    }
                };
                progressBar.setVisibility(View.VISIBLE);
                AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        if (userInfo != null) {
                            if (userInfo.isOldUser())
                                Toast.makeText(getActivity(), "您好，老顾客，本次您可享受7折优惠！", Toast.LENGTH_SHORT).show();
                        }
                        while(progress<=100){
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                                runTime+=sleepTime;
                                progress = (int)((double)runTime/(double)duration*100.0);
                                progressBar.setProgress(progress);
                        }
                        Message msg = Message.obtain();
                        msg.what =1;
                        mhandler.sendMessage(msg);
                        isClickable = false;
                        return null;
                    }
                }.execute();
            }
        }

        public void setClickable(boolean clickable) {
            isClickable = clickable;
        }
    }
}
