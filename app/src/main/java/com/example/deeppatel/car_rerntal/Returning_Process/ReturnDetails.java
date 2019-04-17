package com.example.deeppatel.car_rerntal.Returning_Process;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;

public class ReturnDetails extends AppCompatActivity {

    private View circle_ele;
    private TextView car_name;
    private View parentActivityLayout;
    private Intent toHomeAfterSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_return_activity);

        parentActivityLayout = findViewById(R.id.calculation_parent);
        circle_ele = findViewById(R.id.DETAILS_circle);
        car_name = findViewById(R.id.DETAILS_car_name);

        //Get the car details from the calling activity
        Car car = (Car) getIntent().getSerializableExtra("Car");

        if(car != null){

            GradientDrawable bgShape = (GradientDrawable) circle_ele.getBackground();
            bgShape.setColor(car.getColor());

            car_name.setText(car.getCar_name());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.return_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.payment){

            final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.payment_dialog, null);

            Button debit = dialogView.findViewById(R.id.btn_debit);
            Button credit = dialogView.findViewById(R.id.btn_credit);
            Button cash = dialogView.findViewById(R.id.btn_cash);

            debit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogBuilder.dismiss();
                    Snackbar.make(parentActivityLayout, R.string.payment_success, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.back, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                    startActivity(toHomeAfterSuccess);

                                }
                            }).show();

                }
            });

            credit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogBuilder.dismiss();
                    Snackbar.make(parentActivityLayout, R.string.payment_success, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.back, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                    startActivity(toHomeAfterSuccess);

                                }
                            }).show();

                }
            });

            cash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogBuilder.dismiss();
                    Snackbar.make(parentActivityLayout, R.string.payment_success, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.back, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                    startActivity(toHomeAfterSuccess);

                                }
                            }).show();

                }
            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();


        }

        return true;

    }
}
