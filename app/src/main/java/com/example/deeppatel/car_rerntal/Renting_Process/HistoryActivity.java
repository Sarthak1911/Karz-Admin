package com.example.deeppatel.car_rerntal.Renting_Process;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.HistoryFragment;
import com.example.deeppatel.car_rerntal.Renting_Process.dummy.DummyContent;

public class HistoryActivity extends AppCompatActivity
implements  HistoryFragment.OnListFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("History");

        HistoryFragment historyFragment = new HistoryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.history_framelayout,historyFragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(getBaseContext(), com.example.deeppatel.car_rerntal.Renting_Process.Individual_Transaction_History.class);
        intent.putExtra("transaction_object_car",item.content);
        intent.putExtra("transaction_object_customer",item.details);
        startActivity(intent);
    }
}
