package com.example.empatkali.ui.creditcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.empatkali.constants.CardType;
import com.example.empatkali.data.Item;
import com.example.empatkali.databinding.ActivityCreditCardBinding;

public class CreditCardActivity extends AppCompatActivity {
    ActivityCreditCardBinding b;
    private TextWatcher ccvTextWatcher,
            monthTextWatcher, yearTextWatcher;
    String numberStr;
    static int number = 0;
    private CreditCardViewModel viewModel;

    private void setItem(Item item) {
        if (item == null) {
            item = new Item();
            item.setNumber("24680");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCreditCardBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);


        viewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);
        viewModel.getItemLiveData().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                if (item != null) {
                    setItem(item);
                    b.TVNumberViewmodel.setText(item.getNumber());
                }
            }
        });

        b.EDITNumber.addTextChangedListener(numberTextWatcher);

        b.BUTTONCekValiditas.setOnClickListener(v->{
//            b.TVNumber.setText(numberStr);
            b.TVNumber.setText("12345");

            if (b.TVNumber.getText().toString().matches("[0-9]+")) {
                //* PASSED TEST
                Toast.makeText(this, "tvNumber valid regex", Toast.LENGTH_SHORT).show();
            } else {
                //* PASSED TEST
                Toast.makeText(this, "tvNumber invalid", Toast.LENGTH_SHORT).show();
            }

            /*CardType.detect(numberStr);

            if (isValid(numberStr)) {
                Toast.makeText(this, "number valid", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "number SALAH", Toast.LENGTH_SHORT).show();
            }*/
        });
    }

    private TextWatcher numberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                numberStr = charSequence.toString();
        }
        @Override
        public void afterTextChanged(Editable editable) {
            numberStr = editable.toString();
            if (!numberStr.isEmpty())
                b.TVNumber.setText(numberStr);//ng bisa
        }
    };

    public boolean isValid(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}