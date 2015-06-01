package com.example.lorik.foodlistfromxml;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class FoodsListActivity extends ListActivity {

    private static final String TAG = "MainActivity";
    ArrayList<Food> foodsList = new ArrayList<Food>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            XmlPullParser foodsParser = getResources().getXml(R.xml.foods);
        HandleFoodXML handleFoodXML = new HandleFoodXML();
        foodsList = handleFoodXML.getFoodList(foodsParser);

        setListAdapter(new StateAdapter(foodsList.toArray(new Food[foodsList.size()])));
        final FoodsListActivity activity = this;
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                // selectedState = (State)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, FoodInfo.class);

                Log.v(TAG,"id" + foodsList.get(position).getId());
                intent.putExtra("foodSendID",foodsList.get(position).getId());

                startActivity(intent);

            }
        };
        getListView().setOnItemClickListener(itemListener);
    }


    private Food getModel(int position) {
        return (((StateAdapter) getListAdapter()).getItem(position));
    }

    class StateAdapter extends ArrayAdapter<Food> {

        private LayoutInflater mInflater;

        StateAdapter(Food[] list) {
            super(FoodsListActivity.this, R.layout.activity_foods_list, list);
            mInflater = LayoutInflater.from(FoodsListActivity.this);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            if (row == null) {

                row = mInflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.nameView = (TextView) row.findViewById(R.id.textName);
                holder.priceView = (TextView) row.findViewById(R.id.textPrice);
                row.setTag(holder);
            } else {

                holder = (ViewHolder) row.getTag();
            }

            Food food = getModel(position);

            holder.nameView.setText(food.getName());
            holder.priceView.setText(food.getPrice());

            return row;
        }

        class ViewHolder {
            public TextView nameView, priceView;
        }

    }
}