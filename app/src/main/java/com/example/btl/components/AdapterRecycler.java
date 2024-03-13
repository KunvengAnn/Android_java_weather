package com.example.btl.components;

// Import RecyclerView from AndroidX
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import java.util.List;
import android.content.Context;
import com.example.btl.R;

import org.w3c.dom.Text;

// The adapter class which extends RecyclerView Adapter
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyView> {

    private List<String> list;
    private List<Integer> drawableList;

    private List<String> listTextTempt;
    private List<String> listHour;
    private List<String> listTempMix;
    private List<String> listTempMin;

    // Constructor for adapter class which takes a list of strings and a list of drawable resource IDs
// Constructor for adapter class which takes a list of strings, a list of integers, and a list of strings
    public AdapterRecycler(List<String> horizontalList, List<Integer> drawableList, List<String> tempList, List<String> listHour, List<String> listTempMix, List<String> listTempMin) {
        this.list = horizontalList;
        this.drawableList = drawableList;
        this.listTextTempt = tempList;
        this.listHour = listHour;
        this.listTempMin = listTempMin;
        this.listTempMix = listTempMix;
    }


    // ViewHolder class
    public class MyView extends RecyclerView.ViewHolder {
        TextView textViewDay;
        ImageView imageView_IconWeather;
        TextView textViewTemp;
        TextView textviewHour;
        TextView textViewTempMin;
        TextView textViewTempMix;

        public MyView(View view) {
            super(view);
            textViewDay = view.findViewById(R.id.id_TextviewDay);
            imageView_IconWeather = view.findViewById(R.id.id_imageView_IConWeather);
            textViewTemp = view.findViewById(R.id.id_textTemperature);
            textviewHour = view.findViewById(R.id.id_textview_HourRecycler);
            textViewTempMin = view.findViewById(R.id.id_textView_TempMin);
            textViewTempMix = view.findViewById(R.id.id_textView_TempMix);
        }
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        // Check if lists are empty or null
//        if (list.isEmpty() || drawableList.isEmpty() || listTextTempt.isEmpty()) {
//            return;
//        }
        holder.textViewDay.setText(list.get(position));
        int iconResourceId = drawableList.get(position);
        holder.imageView_IconWeather.setImageResource(iconResourceId);

        // Set temperature text
        holder.textViewTemp.setText(listTextTempt.get(position));

        holder.textViewTempMix.setText(listTempMix.get(position));
        holder.textViewTempMin.setText(listTempMin.get(position));

        holder.textviewHour.setText(listHour.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
