package com.desuzed.expencomes.util;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class CustomTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;

    public CustomTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 0) {
            textInputLayout.setError("Поле не должно быть пустым");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 0) {
            textInputLayout.setError("Поле не должно быть пустым");
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
