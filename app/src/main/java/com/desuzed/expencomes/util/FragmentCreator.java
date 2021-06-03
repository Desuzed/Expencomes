package com.desuzed.expencomes.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.desuzed.expencomes.R;
import com.desuzed.expencomes.db.DbApp;
import com.desuzed.expencomes.fragments.EditItemFragment;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.Item;

public class FragmentCreator {
    private Context context;
    private FragmentManager fManager;

    public FragmentCreator(Context context, FragmentManager fManager) {
        this.context = context;
        this.fManager = fManager;
    }

    public void startFragment(Fragment f) {
        fManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFrame, f)
                .commit();
    }
}
