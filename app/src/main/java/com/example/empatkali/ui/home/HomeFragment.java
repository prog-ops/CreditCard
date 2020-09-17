package com.example.empatkali.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.empatkali.R;
import com.example.empatkali.data.Item;
import com.example.empatkali.databinding.CreditCardFragmentBinding;
import com.example.empatkali.databinding.FragmentHomeBinding;
import com.example.empatkali.ui.creditcard.CreditCardActivity;
import com.example.empatkali.ui.creditcard.CreditCardAdapter;
import com.example.empatkali.ui.creditcard.CreditCardViewModel;
import com.example.empatkali.ui.facedetection.FaceDetectionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private CreditCardViewModel pViewModel;
    private FragmentHomeBinding b;
    private CreditCardAdapter a;
    private List<Item> pItems;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        a = new CreditCardAdapter(pItems, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        b = FragmentHomeBinding.bind(view);

        b.RVItems.setLayoutManager(new LinearLayoutManager(getContext()));
        b.RVItems.setAdapter(a);


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
                    Toast.makeText(getActivity(), "Jumlah = "+a.getItemCount()+" item", Toast.LENGTH_SHORT).show();
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