package com.example.empatkali.ui.creditcard;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.empatkali.R;
import com.example.empatkali.data.Item;
import com.example.empatkali.databinding.CreditCardFragmentBinding;
import com.example.empatkali.ui.facedetection.FaceDetectionActivity;

import java.util.ArrayList;
import java.util.List;

public class CreditCardFragment extends Fragment {
    private CreditCardViewModel pViewModel;
    private CreditCardFragmentBinding b;
    private CreditCardAdapter a;
    private List<Item> pItems;

    public static CreditCardFragment newInstance() {
        return new CreditCardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        a = new CreditCardAdapter(pItems, getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_card_fragment, container, false);
        b = CreditCardFragmentBinding.bind(view);

        b.RVItems.setLayoutManager(new LinearLayoutManager(getContext()));
        b.RVItems.setAdapter(a);

        Toast.makeText(getActivity(), "size = "+a.getItemCount(), Toast.LENGTH_SHORT).show();

        b.BUTTONToActivity.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CreditCardActivity.class);
            startActivity(intent);
            //getActivity().finish();//ng
        });

        b.BUTTONDetectFace.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), FaceDetectionActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pViewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);
        // TODO: Use the ViewModel
        /*pViewModel.getItemLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item item) {

            }
        });*/
        pViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items != null) {
                    setListData(items);
                } else {
                    Toast.makeText(getContext(), "Items null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setListData(List<Item> items) {
        //if data changed, set new timezoneList to adapter of recyclerview
        if (pItems == null) {
            pItems = new ArrayList<>();
        }
        pItems.clear();
        pItems.addAll(items);

        if (a != null) {
            a.setList(items);
        }
    }

}