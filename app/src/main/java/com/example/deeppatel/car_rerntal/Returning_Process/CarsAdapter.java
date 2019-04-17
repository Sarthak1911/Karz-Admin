package com.example.deeppatel.car_rerntal.Returning_Process;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Customer.Customer;
import com.example.deeppatel.car_rerntal.R;

import java.util.ArrayList;
import java.util.List;


public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> implements Filterable {

    private OnCarItemClickedListener onCarItemClickedListener;
    private CarsEngine carsEngine;
    List<Car> bookedCarsListFull;


    public CarsAdapter(OnCarItemClickedListener onCarItemClickedListener, CarsEngine carsEngine) {
        this.onCarItemClickedListener = onCarItemClickedListener;
        this.carsEngine = carsEngine;
        this.bookedCarsListFull = carsEngine.getCarList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View listItem = LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.lisy_item_booked_car, viewGroup, false);

        return new ViewHolder(listItem, onCarItemClickedListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Car car = carsEngine.getCar(i);
        viewHolder.car_name.setText(car.getCar_name());

        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle_ele.getBackground();
        bgShape.setColor(car.getColor());

//        viewHolder.model_name.setText(car.getCar_model());
//        viewHolder.booked_on.setText(car.getBooked_on());
//        viewHolder.booked_by.setText(car.getBooked_by());
//        viewHolder.available_on.setText(car.getAvailable_on());

    }

    @Override
    public int getItemCount() {
        return carsEngine.getCount();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Car> bookedCarsListNew = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                //No query in the search box
                bookedCarsListNew.addAll(bookedCarsListFull);

            } else {

                String pattern = constraint.toString().toLowerCase().trim();

                for (Car car : bookedCarsListFull) {

                    if (car.getCar_name().toLowerCase().contains(pattern)) {

                        bookedCarsListNew.add(car);

                    }

                }

            }

            FilterResults results = new FilterResults();

            results.values = bookedCarsListNew;

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            carsEngine.setCarList((List) results.values);
            notifyDataSetChanged();

        }

    };


    public interface OnCarItemClickedListener{

        void onCarItemClicked(int position, View view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView car_name, model_name, booked_by, booked_on, available_on;
        View circle_ele;
        //Gotta declare image for the car once available
        OnCarItemClickedListener onCarItemClickedListener;


        public ViewHolder(@NonNull View itemView, OnCarItemClickedListener onCarItemClickedListener) {
            super(itemView);

            car_name = itemView.findViewById(R.id.car_name);
            circle_ele = itemView.findViewById(R.id.circle_ele);
//            model_name = itemView.findViewById(R.id.model);
//            booked_by = itemView.findViewById(R.id.booked_by);
//            booked_on = itemView.findViewById(R.id.booked_on);
//            available_on = itemView.findViewById(R.id.available_from);
            this.onCarItemClickedListener = onCarItemClickedListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            onCarItemClickedListener.onCarItemClicked(getAdapterPosition(), v);

        }
    }


}
