package es.source.code.activity2;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.adapter.Food_Detail_VP_Adapter;
import es.source.code.model.FoodInfo;

public class FoodDetailed extends AppCompatActivity {
    private ViewPager vp_food_detail;
    private ArrayList<FoodInfo>foodInfos;
    private SCOS_Global_Application myApp;
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (SCOS_Global_Application) getApplication();
        foodInfos = myApp.getFoodInfos();
        try{position = getIntent().getIntExtra("position",0);}catch (Exception e){};
        setContentView(R.layout.activity_food_detailed);
        vp_food_detail = findViewById(R.id.vp_detail);
        vp_food_detail.setAdapter(new Food_Detail_VP_Adapter(getSupportFragmentManager(),foodInfos));
        vp_food_detail.setCurrentItem(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,MainScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("From","FromFoodDetail");
        switch (item.getItemId()){
            case R.id.item_orderedFood:
                intent.putExtra("position",2);
                startActivity(intent);
                break;
            case R.id.item_command:
                intent.putExtra("position",1);
                startActivity(intent);
                break;
            case R.id.item_service:
                Toast.makeText(this,"服务员~~~", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
