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
import com.example.empatkali.databinding.FragmentHomeBinding;
import com.example.empatkali.ui.facedetection.FaceDetectionActivity;

import java.util.ArrayList;
import java.util.List;

public class CreditCardFragment extends Fragment {
    private CreditCardFragmentBinding b;

    public static CreditCardFragment newInstance() {
        return new CreditCardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_card_fragment, container, false);
        b = CreditCardFragmentBinding.bind(view);

        b.BUTTONDetectFace.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), FaceDetectionActivity.class);
            startActivity(intent);
        });

        b.BUTTONToActivity.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CreditCardActivity.class);
            startActivity(intent);
        });

        return view;
    }
}