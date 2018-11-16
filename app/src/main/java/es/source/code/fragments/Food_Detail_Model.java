package es.source.code.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import es.source.code.activity2.R;
import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.model.FoodInfo;


public class Food_Detail_Model extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private FoodInfo mfoodInfo;
    private ArrayList<FoodInfo> foodOrderedList;
    private SCOS_Global_Application myApp;
    private View view;
    private TextView tv_price,tv_title,tv_descrip;
    private Button btn;
    private btnClick clickListener;
    public Food_Detail_Model() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Food_Detail_Model newInstance(String param1, String param2) {
        Food_Detail_Model fragment = new Food_Detail_Model();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food__detail__model, container, false);
        myApp = (SCOS_Global_Application) getActivity().getApplication();
        foodOrderedList = myApp.getFoodOrderList();
        tv_title = view.findViewById(R.id.tv_food_detail_title);
        tv_price = view.findViewById(R.id.tv_food_detail_price);
        tv_descrip = view.findViewById(R.id.tv_food_detail_descrip);
        btn = view.findViewById(R.id.btn_food_detail);
        clickListener = new btnClick();
        btn.setOnClickListener(clickListener);
        if(mfoodInfo!=null){
            tv_title.setText(mfoodInfo.getFoodName());
            DecimalFormat df = new DecimalFormat("#0.00");
            String price = df.format(mfoodInfo.getFoodPrice())+" 元";
            tv_price.setText(price);
            tv_descrip.setText(mfoodInfo.getFoodDescription());
            updateButton();
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //todo update button
            try{updateButton();} catch (Exception e){}
        }
    }

    public void setMfoodInfo(FoodInfo foodInfo){
        this.mfoodInfo = foodInfo;
    }

    private void updateButton(){
        if(mfoodInfo.getSubmitNum()>=1){
            btn.setBackgroundResource(R.drawable.btn_submitted_order);
            clickListener.setListenerType(clickListener.IS_SUBMITTED_LISTENER);
            btn.setText("已点单");
        }
        else if(mfoodInfo.getOrderedNum()>=1){
            btn.setBackgroundResource(R.drawable.btn_cancel_order);
            clickListener.setListenerType(clickListener.IS_CANCEL_LISTENER);
            btn.setText("取消");
        }
        else{
            btn.setBackgroundResource(R.drawable.btn_order);
            clickListener.setListenerType(clickListener.IS_ORDER_LISTENER);
            btn.setText("点菜");
        }
    }

    private class btnClick implements View.OnClickListener{
        public final int IS_ORDER_LISTENER = 0;
        public final int IS_CANCEL_LISTENER = 1;
        public final int IS_SUBMITTED_LISTENER = 2;
        private int listenerType = 0;
        @Override
        public void onClick(View v) {
            switch (listenerType){
                case IS_ORDER_LISTENER:
                    if(mfoodInfo.getStock()>0) {
                        if (mfoodInfo.getOrderedNum() <= 0) mfoodInfo.setOrderedNum(1);
                        //foodOrderedList.add(mfoodInfo);
                        updateButton();
                    }else{
                        Toast.makeText(getActivity(),"客官没库存啦~~",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case  IS_CANCEL_LISTENER:
                    if(mfoodInfo.getOrderedNum()>=1)mfoodInfo.setOrderedNum(0);
                    //foodOrderedList.remove(mfoodInfo);
                    updateButton();
                    break;
                case IS_SUBMITTED_LISTENER:
                    if(mfoodInfo.getSubmitNum()>=1){
                        //todo click Submitted btn
                    }
                    break;
            }
        }
        public void setListenerType(int listenerType){
            this.listenerType = listenerType;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event

}
