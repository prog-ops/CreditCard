package com.example.empatkali.ui.creditcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.empatkali.constants.CardType;
import com.example.empatkali.constants.CreditCardUtils;
import com.example.empatkali.data.Item;
import com.example.empatkali.databinding.ActivityCreditCardBinding;

public class CreditCardActivity extends AppCompatActivity {
    ActivityCreditCardBinding b;
    String numberStr, ccvStr, validityStr;
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
        /*viewModel.getItemLiveData().observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                if (item != null) {
                    setItem(item);
                    b.TVNumberViewmodel.setText(item.getNumber());
                }
            }
        });*/

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

        String array[] = {
                "^4[0-9]{12}(?:[0-9]{3}){0,2}$",
                "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$",
                "^3[47][0-9]{13}$",
                "^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$",
                "^6(?:011|[45][0-9]{2})[0-9]{12}$",
                "^(?:2131|1800|35\\d{3})\\d{11}$",
                "^62[0-9]{14,17}$"
        };

        b.BUTTONCekValiditas.setOnClickListener(v->{
            String number = b.EDITNumber.getText().toString();
            String validity = b.EDITValidityMonthYear.getText().toString();
            if (CreditCardUtils.isValid(number)) {
                Toast.makeText(this, "invalid number (1)", Toast.LENGTH_SHORT).show();

            } else if (CreditCardUtils.isValidDate(validity)) {
                Toast.makeText(this, "invalid date (2)", Toast.LENGTH_SHORT).show();

            } else if (b.EDITCcv.getText()
                    .toString().length() < 3) {
                Toast.makeText(this, "invalid ccv (3)", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "invalid!!!", Toast.LENGTH_SHORT).show();
            }
        });

        b.BUTTONSave.setOnClickListener(v->{
            String number = b.EDITNumber.getText().toString();
//            String monthYear = b.EDITMonth.getText().toString()+
//                    b.EDITYear.getText().toString();
            String ccv = b.EDITCcv.getText().toString();

            Item item = new Item();
            item.setNumber(number);
//            item.setMonthYear(monthYear);
            item.setCcv(ccv);

            for (String a : array) {
                if (number.matches(a)) {
                    viewModel.insertItem(item);
                } else {
                    Toast.makeText(this, "INVALID "+number, Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }

    private TextWatcher numberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                numberStr = charSequence.toString();
        }
        @Override
        public void afterTextChanged(Editable editable) {
            numberStr = editable.toString();
            /*if (!numberStr.isEmpty())
                b.TVNumber.setText(numberStr);//ng bisa*/

            if (numberStr.length() >= 13 && numberStr.length() < 17) {

                b.EDITNumber.setBackgroundColor(Color.argb(0, 100, 255, 100));

                if (CreditCardUtils.isValid(numberStr)) {
//                    String get = CreditCardUtils.getName(numberStr);
                    int getInt = CreditCardUtils.getCardType(numberStr);

                    if (getInt == 1) {
                        b.TVNumber.setText(get);
                    }

                } else {
                    b.EDITNumber.setError("invalid");

                }
            }
        }
    };

    private TextWatcher ccvTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher validityTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void afterTextChanged(Editable editable) {

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