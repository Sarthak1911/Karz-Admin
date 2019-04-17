package com.example.deeppatel.car_rerntal.Renting_Process;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.TransactionInfo;

public class Receipt extends AppCompatActivity {

    TextView tv_customer_name;
    TextView tv_car_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        tv_car_name=findViewById(R.id.car_name);
        tv_customer_name=findViewById(R.id.customer_name);

        tv_car_name.setText(TransactionInfo.car.content);
        int color= Color.parseColor("#000000");
        tv_car_name.setTextColor(color);
        tv_customer_name.setTextColor(color);
        tv_customer_name.setText(TransactionInfo.customer.content);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /****After transaction is done remove all fragment from backstack and redirect to home page********/

        //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentManager fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
        Intent homePage = new Intent(this, Home.class);
        startActivity(homePage);
    }

    public void ok_receipt(View view) {
        Intent i = new Intent(getBaseContext(),Home.class);
        startActivity(i);
    }
}
