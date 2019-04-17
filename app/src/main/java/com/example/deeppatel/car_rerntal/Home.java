package com.example.deeppatel.car_rerntal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.fragments.CarFragment;
import com.example.deeppatel.car_rerntal.Customer.CustomerFragment;
import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.OCR.TextRecognition;
import com.example.deeppatel.car_rerntal.Renting_Process.AvailableCarsFragment;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.TransactionInfo;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Available_Car;
import com.example.deeppatel.car_rerntal.Returning_Process.ReturnACarFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Stack;

public class Home extends AppCompatActivity
        implements
        AvailableCarsFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    private android.support.v7.app.ActionBar actionBar;
    private Stack fragmentStack;
    private BottomNavigationView navigation;
    private SearchView searchView;
    //Get the fragment manipulators ready for the transaction
    FragmentManager fragmentManager = getSupportFragmentManager();

    //Based on the button clicks, the fragments must be added to the activity

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_customer:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_car:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_rent:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_return_car:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_home_search_info_menu, menu);

        MenuItem actionOrderSearch = menu.findItem(R.id.item_search);
        searchView = (SearchView) actionOrderSearch.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            //For real time search results
            @Override
            public boolean onQueryTextChange(String s) {

                EventBus.getDefault().post(new SearchCustomer(s));

                return false;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();;

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initialize the stack
        fragmentStack = new Stack();
        //Add HomeFragment object to it
        replaceFragment(R.id.navigation_rent);
        getPermission();

    }



    public void setActionBarTitle(String title){

        actionBar.setTitle(title);

    }

    public void setBottomNavigationItemChecked(int navId){

        switch (navId) {

            case R.id.navigation_customer:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_rent:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_return_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

        }

    }

    public void closeSearchView(){

        if(searchView != null){

            if(!searchView.isIconified()){

                searchView.setIconified(true);

            }

        }

    }

    public  void replaceFragment(int fragmentId){

        FragmentTransaction fragmentTransaction;

        CustomerFragment customerFragment;
        AvailableCarsFragment rentACarFragment;
        ReturnACarFragment returnACarFragment;
        CarFragment carFragment;

        //Based on the navigation button clicked, the fragments must be added to the activity

        switch (fragmentId){

            case R.id.navigation_customer:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                customerFragment = new CustomerFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, customerFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added Cust");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                carFragment = new CarFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, carFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added Car");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_rent:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                rentACarFragment = new AvailableCarsFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, rentACarFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added rent");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_return_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                returnACarFragment = new ReturnACarFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, returnACarFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added return");
                fragmentTransaction.commit();

                closeSearchView();

                break;

        }

    }

    @Override
    public void onListFragmentInteraction(Data_Available_Car.DummyItem item) {
        TransactionInfo.car=item;
//        Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
//        serachForCustomer.putExtra("selected_car", String.valueOf(item));
//        startActivity(serachForCustomer);
        Intent text = new Intent(this, TextRecognition.class);
        text.putExtra("selected_car", String.valueOf(item));
        startActivity(text);
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
        }
    }
}
