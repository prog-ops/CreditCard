package com.example.empatkali.ui.creditcard;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.empatkali.R;
import com.example.empatkali.databinding.CreditCardFragmentBinding;

public class CreditCardFragment extends Fragment {
    private CreditCardViewModel mViewModel;
    private TextWatcher numberTextWatcher, ccvTextWatcher, monthTextWatcher, yearTextWatcher;
    private CreditCardFragmentBinding b;

    public static CreditCardFragment newInstance() {
        return new CreditCardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numberTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_card_fragment, container, false);
        b = CreditCardFragmentBinding.bind(view);

        b.BUTTONToActivity.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CreditCardActivity.class);
            startActivity(intent);
            //getActivity().finish();//ng
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);
        // TODO: Use the ViewModel

    }

}