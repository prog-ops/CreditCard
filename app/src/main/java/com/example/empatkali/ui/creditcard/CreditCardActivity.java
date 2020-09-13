package com.example.empatkali.ui.creditcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.empatkali.constants.CardType;
import com.example.empatkali.databinding.ActivityCreditCardBinding;
import com.example.empatkali.databinding.ActivityOrderBinding;
import com.example.empatkali.databinding.CreditCardFragmentBinding;

public class CreditCardActivity extends AppCompatActivity {
    ActivityCreditCardBinding b;
    private CreditCardViewModel mViewModel;
    private TextWatcher ccvTextWatcher,
            monthTextWatcher, yearTextWatcher;
    String numberText;
    static int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCreditCardBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);


        b.EDITNumber.addTextChangedListener(numberTextWatcher);

        b.BUTTONCekValiditas.setOnClickListener(v->{
            b.TVNumber.setText(numberText);

            String isValidStr = b.EDITNumber.getText().toString();
            CardType.detect(isValidStr);

            if (isValid(isValidStr)) {
                Toast.makeText(this, "number valid", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "number SALAH", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TextWatcher numberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                numberText = charSequence.toString();
        }
        @Override
        public void afterTextChanged(Editable editable) {
            numberText = editable.toString();
            b.TVNumber.setText(numberText);//ng bisa
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