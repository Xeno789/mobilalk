package com.example.kotprog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CarInsuranceActivity extends AppCompatActivity {
    private static final String LOG_TAG = CarInsuranceActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<carInsurance> mItemList;
    private carInsuranceAdapter mAdapter;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_insurance);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.i(LOG_TAG,"Authenticated user");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();

        mAdapter = new carInsuranceAdapter(this, mItemList);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData() {
        String[] itemsNumberPlate = getResources().getStringArray(R.array.car_insurance_number_plates);
        String[] itemsBrand = getResources().getStringArray(R.array.car_insurance_brands);
        String[] itemsOwner = getResources().getStringArray(R.array.car_insurance_owners);
        String[] itemsSchedule = getResources().getStringArray(R.array.car_insurance_scheduled);

        mItemList.clear();
        for(int i = 0; i < itemsNumberPlate.length; i++){
            mItemList.add(new carInsurance(itemsNumberPlate[i], itemsBrand[i], itemsOwner[i], itemsSchedule[i]));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        Log.i(LOG_TAG, menu.toString());
        getMenuInflater().inflate(R.menu.car_insurance_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchMenuView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchMenuView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(LOG_TAG, newText);
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.i(LOG_TAG, "Searchbar clicked");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}