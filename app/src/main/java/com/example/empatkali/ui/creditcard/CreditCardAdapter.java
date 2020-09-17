package com.example.empatkali.ui.creditcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empatkali.R;
import com.example.empatkali.data.Item;
import com.example.empatkali.databinding.ItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.H> {
    private Context pContext;
    private List<Item> pItems;
    private CreditCardViewModel viewModel;

    public CreditCardAdapter(List<Item> items, Context context) {
        this.pItems = items;
        this.pContext = context;
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(CreditCardViewModel.class);
    }

    public void setList(List<Item> list) {
        if (pItems == null) {
            pItems = new ArrayList<>();
        }
        pItems.clear();
        pItems.addAll(list);
        notifyDataSetChanged();
    }

    public class H extends RecyclerView.ViewHolder {
        private Item item;
        ItemLayoutBinding b;

        public H(@NonNull View view) {
            super(view);
            b = ItemLayoutBinding.bind(view);
        }
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new H(view);
    }

    @Override
    public void onBindViewHolder(@NonNull H h, int position) {
        h.item = pItems.get(position);
        h.b.TVItemNumber.setText(h.item.getNumber());
        h.b.TVItemCcv.setText(h.item.getCcv());
        h.b.TVItemYearmonth.setText(h.item.getMonthYear());
    }

    @Override
    public int getItemCount() {
        if (pItems != null) {
            return pItems.size();
        } else return 0;
    }
}
