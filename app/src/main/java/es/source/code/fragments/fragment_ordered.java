package es.source.code.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

import es.source.code.activity2.R;
import es.source.code.adapter.FoodOrderedItemAdapter;
import es.source.code.data_control.FoodDataControl;
import es.source.code.model.FoodInfo;
import es.source.code.activity2.SCOS_Global_Application;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link fragment_ordered#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_ordered extends android.support.v4.app.Fragment {
    private ListView lv_frag_ordered;
    private FoodOrderedItemAdapter foodOrderedItemAdapter;
    private TextView tv_price,tv_quant;
    private Button btn_submit;
    private ArrayList<FoodInfo> foodOrderedList;
    private SCOS_Global_Application myApp;
    public fragment_ordered() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static fragment_ordered newInstance(String param1, String param2) {
        fragment_ordered fragment = new fragment_ordered();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodOrderedItemAdapter = new FoodOrderedItemAdapter(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try{lv_frag_ordered.setAdapter(foodOrderedItemAdapter);}catch (Exception e){}
        try{updatePriceTag();}catch (Exception e){}
        if(isVisibleToUser)Log.v("ordered","setUserVisible");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_ordered, container, false);
        myApp = (SCOS_Global_Application) getActivity().getApplication();
        lv_frag_ordered = view.findViewById(R.id.ordered_lv);
        lv_frag_ordered.setAdapter(foodOrderedItemAdapter);
        //get initial data
        foodOrderedList = myApp.getFoodUnOrderedList();
        //update price tag
        tv_price= view.findViewById(R.id.tv_price_frag_ordered);
        tv_quant = view.findViewById(R.id.tv_frag_ordered_quant);
        btn_submit = view.findViewById(R.id.btn_ordered_submit);
        btn_submit.setOnClickListener(new submitClick());
        updatePriceTag();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("ordered","onPause");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("ordered","ActivityCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("ordered","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("ordered","onStart");
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.v("ordered","onInflate");
    }

    // TODO: Rename method, update argument and hook method into UI event

    private void updatePriceTag(){
        foodOrderedList = myApp.getFoodOrderList();
        float sumPrice = FoodDataControl.calPriceSum(foodOrderedList);
        DecimalFormat df = new DecimalFormat("#0.00");
        String sumPriceText = df.format(sumPrice)+" 元";
        tv_price.setText(sumPriceText);
        updateBadge();
    }
    private void updateBadge(){
        if(foodOrderedList.size()==0) tv_quant.setVisibility(View.INVISIBLE);
        else{
            tv_quant.setVisibility(View.VISIBLE);
            tv_quant.setText(String.valueOf(foodOrderedList.size()));
        }
    }
    private class submitClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for(FoodInfo foodinfo:foodOrderedList){
                if(foodinfo.getStock()>0) {
                    if (foodinfo.getSubmitNum() == 0) foodinfo.setSubmitNum(foodinfo.getSubmitNum() + 1);
                    if (foodinfo.getOrderedNum() == 1) foodinfo.setOrderedNum(0);
                    foodinfo.setStock(foodinfo.getStock()-1);
                }
                else{
                    Toast.makeText(getActivity(),foodinfo.getFoodName()+"out of stock",Toast.LENGTH_SHORT).show();
                }
            }

            foodOrderedList = myApp.getFoodOrderList();
            lv_frag_ordered.setAdapter(foodOrderedItemAdapter);
            updatePriceTag();
        }
    }
}
