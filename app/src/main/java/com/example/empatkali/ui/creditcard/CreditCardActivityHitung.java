package com.example.empatkali.ui.creditcard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empatkali.databinding.ActivityOrderBinding;

public class CreditCardActivityHitung extends AppCompatActivity {
//    CreditCardFragmentBinding b;//pake layoutnya fragmen
    ActivityOrderBinding b;
    static int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityOrderBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);

        b.incrementButton.setOnClickListener(v->{
            number++;
            increment();
        });

        b.decrementButton.setOnClickListener(v->{
            number--;
            decrement();
        });
    }

    private void increment(){
        b.quantityTextView.setText(String.valueOf(number));
    }

    private void decrement(){
        b.quantityTextView.setText(String.valueOf(number));
    }
}