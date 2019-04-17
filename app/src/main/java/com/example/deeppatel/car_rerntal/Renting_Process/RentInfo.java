package com.example.deeppatel.car_rerntal.Renting_Process;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.R;

public class RentInfo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue_rentinfo:
                Toast.makeText(this, "Continue Button", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button
                                Toast.makeText(getBaseContext() , "OK", Toast.LENGTH_SHORT).show();
                                /******************Redirect To Payment Activity *************/

                                /******For debit or credit****/
                                Intent intent = new Intent(getBaseContext(), Payment.class);
                                intent.putExtra("selected_car_customer_rent", String.valueOf("Dummy"));
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // User clicked the No button
                                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                break;

                            /*****  If cash is selected *****/
                            case DialogInterface.BUTTON_NEUTRAL:
                                Intent recieptIntent = new Intent(getBaseContext(), Receipt.class);
                                recieptIntent.putExtra("selected_car", String.valueOf("Receipt"));
                                startActivity(recieptIntent);
                                break;

                        }
                    }
                };

                alert.setTitle("PAYMENT");
                alert.setMessage("Please select hte payment method");
                alert.setNeutralButton("Cash",dialogClickListener);
                alert.setPositiveButton("Debit/Credit", dialogClickListener);
                alert.setNegativeButton("Cancel",dialogClickListener);
                AlertDialog dialog = alert.create();
                dialog.show();
                break;

            case R.id.btn_cancel_rentinfo:
                Toast.makeText(getBaseContext(), "Cancel Button", Toast.LENGTH_SHORT).show();

                /*******     Back to All Available Car Fragment  ********/
                Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
                serachForCustomer.putExtra("selected_car", String.valueOf("To be Solved "));
                startActivity(serachForCustomer);
                break;

        }
    }
}
