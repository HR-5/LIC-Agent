package com.example.licagent.AddClient;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.licagent.AddClient.AddDetailFragment.clientClass;
import static com.example.licagent.AddClient.Page2.dateVal;
import static com.example.licagent.AddClient.Page2.dobset;
import static com.example.licagent.AddClient.Page2.docset;
import static com.example.licagent.AddClient.Page2.domset;
import static com.example.licagent.AddClient.Page2.dueset;
import static com.example.licagent.AddClient.Page2.lpdset;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if(dateVal==0 ||dateVal==1) {
            dialog.getDatePicker().setMaxDate(new Date().getTime());
        }
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


        if (dateVal == 0) {
            dobset.setText(sdf.format(c.getTime()));
            clientClass.setDob(c.getTime());
        } else if (dateVal == 1) {
            docset.setText(sdf.format(c.getTime()));
            clientClass.setDatecomm(c.getTime());
        } else if (dateVal == 2) {
            domset.setText(sdf.format(c.getTime()));
            clientClass.setDatemat(c.getTime());
        } else if (dateVal == 3) {
            lpdset.setText(sdf.format(c.getTime()));
            clientClass.setLastDate(c.getTime());
        } else if (dateVal == 4) {
            dueset.setText(sdf.format(c.getTime()));
            clientClass.setDueDate(c.getTime());
        }


    }
}
