package es.source.code.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import es.source.code.activity2.MainScreen;
import es.source.code.activity2.R;
import es.source.code.activity2.SCOS_Global_Application;
import es.source.code.adapter.FoodItemAdapter;
import es.source.code.adapter.FoodLabelAdapter;
import es.source.code.data_control.FoodDataControl;
import es.source.code.model.FoodInfo;
import es.source.code.self_util.*;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link fragment_orderFood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_orderFood extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private ArrayList<FoodInfo> foodInfos,foodOrderList;
    private ArrayList<String> foodTypeLabels;
    private int[] foodLabelPositions;
    private String mParam2;
    private ListView lv_foodList;
    private RadioGroup rg_foodTypeLabel;
    private View view;
    private typeCheckChangeListener mtypeCheckedChangeListener;
    private FoodItemAdapter mFoodItemAdapter;
    private SCOS_Global_Application myApp;
    public fragment_orderFood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_orderFood.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_orderFood newInstance(String param1, String param2) {
        fragment_orderFood fragment = new fragment_orderFood();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            myApp = (SCOS_Global_Application) getActivity().getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //bound todo

        foodInfos = myApp.getFoodInfos();
        if(foodInfos.size()!=0) {
            List resultList = FoodDataControl.typeLabelize(foodInfos);
            foodTypeLabels = (ArrayList<String>) resultList.get(0);
            foodLabelPositions = FoodDataControl.toListInt((ArrayList<Integer>) resultList.get(1));
        }


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_order_food,container,false);
        lv_foodList = view.findViewById(R.id.frame_ord_lv);
        rg_foodTypeLabel = view.findViewById(R.id.frame_ord_radGroup);
        if(foodTypeLabels!=null)init_rg_foodtype(rg_foodTypeLabel,foodTypeLabels);
        mtypeCheckedChangeListener = new typeCheckChangeListener();
        rg_foodTypeLabel.setOnCheckedChangeListener(mtypeCheckedChangeListener);
        //inflate the listViews
        mFoodItemAdapter = new FoodItemAdapter(foodInfos,this,rg_foodTypeLabel);
        lv_foodList.setAdapter(mFoodItemAdapter);
        lv_foodList.setOnScrollListener(new foodListScrollListener());
        //lv_foodType.setAdapter(new FoodLabelAdapter(foodTypeLabels,this));
        //set submit button
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(mFoodItemAdapter!=null){
                mFoodItemAdapter.notifyDataSetChanged();
                if(mtypeCheckedChangeListener!=null) mtypeCheckedChangeListener.toScroll=true;
            }
        }else{
            if(mtypeCheckedChangeListener!=null) mtypeCheckedChangeListener.toScroll=false;
        }

    }

    private void init_rg_foodtype(RadioGroup radioGroup, ArrayList<String> foodTypeLabels){
        for(int i =0;i<foodTypeLabels.size();i++) {
            String foodtype = foodTypeLabels.get(i);
            RadioButton radioButton = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.food_order_type_radio_button,null);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,pixelTrans.DpToPix(getActivity(),75)));
            radioButton.setText(foodtype);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
        }
    }
    private class typeCheckChangeListener implements RadioGroup.OnCheckedChangeListener {
        //todo improve to be enclosed
        public boolean toScroll= true;
        @Override
        public void onCheckedChanged(RadioGroup group, final int checkedId) {
            if(toScroll){
            mFoodItemAdapter.toChangeTypeButton = false;
            //lv_foodList.smoothScrollToPosition(foodLabelPositions[checkedId]);
            lv_foodList.setSelection(foodLabelPositions[checkedId]);
            //rg_foodTypeLabel.check(checkedId);
            }
        }
    }

    private class foodListScrollListener implements AbsListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                mFoodItemAdapter.toChangeTypeButton=true;
                mtypeCheckedChangeListener.toScroll = false;
            }
            if(scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                mtypeCheckedChangeListener.toScroll= true;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
