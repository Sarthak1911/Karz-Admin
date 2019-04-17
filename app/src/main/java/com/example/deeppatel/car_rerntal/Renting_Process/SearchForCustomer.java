package com.example.deeppatel.car_rerntal.Renting_Process;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.TransactionInfo;

public class SearchForCustomer extends AppCompatActivity

        implements  SearchedCustomerFragment.OnListFragmentInteractionListener

{
    int flag=0;
    private  String car_selected; //From First Activity
    private EditText et_custoerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_for_customer);
        et_custoerName=findViewById(R.id.et_search_for_customer);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                car_selected= null;
            } else {
                car_selected= extras.getString("selected_car");
            }
        } else {
            car_selected= (String) savedInstanceState.getSerializable("selected_car");
        }
    }

    public void searchCustomer(View view) {
        Data_Searched_Customer.CreateList(et_custoerName.getText().toString());

        et_custoerName= findViewById(R.id.et_search_for_customer);
        if(et_custoerName.getText().toString().equals(""))
        {
            //Toast.makeText(this, "Please Enter the Customer Name", Toast.LENGTH_SHORT).show();
            et_custoerName.setHint("Please Enter the Customer Name");
            int color= Color.parseColor("#FF0000");
            et_custoerName.setHintTextColor( color);
        }
        else {

            if(flag==0)
            {
                SearchedCustomerFragment cutomersFragment = new SearchedCustomerFragment(et_custoerName.getText().toString());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.searched_customer_fragment, cutomersFragment,"SearchedCustomer")
                        .commit();
                flag=1;
            }
            else{

//            boolean fragmentA = getSupportFragmentManager().findFragmentByTag("SearchedCustomer").isInLayout();
//            //If user clicks button more than one time fragment won't be updated. To overcome that check backstack
//            if (fragmentA == false) {
//                //not exist
//                SearchedCustomerFragment cutomersFragment = new SearchedCustomerFragment(et_custoerName.getText().toString());
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.searched_customer_fragment, cutomersFragment)
//                        .addToBackStack("SearchedCustomer")
//                        .commit();
//                Toast.makeText(this, "If Null ", Toast.LENGTH_SHORT).show();
//
//            }
//            else
                {
                //fragment exist

                Toast.makeText(this, "Click "+car_selected, Toast.LENGTH_SHORT).show();

               Fragment fragment = getSupportFragmentManager().findFragmentByTag("SearchedCustomer");
                getSupportFragmentManager().beginTransaction().remove(fragment);


                SearchedCustomerFragment cutomersFragment = new SearchedCustomerFragment(et_custoerName.getText().toString());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.searched_customer_fragment, cutomersFragment,"SearchedCustomer")
                            .commit();


            }

        }}
    }

    @Override
    public void onListFragmentInteraction(Data_Searched_Customer.DummyItem2 item, int count) {

        Toast.makeText(this, "Customer: "+item.details, Toast.LENGTH_SHORT).show();
        TransactionInfo.customer=item;
        Intent intent = new Intent(this, RentInfo.class);
        intent.putExtra("selected_customer", String.valueOf(item));
        startActivity(intent);


    }
}
