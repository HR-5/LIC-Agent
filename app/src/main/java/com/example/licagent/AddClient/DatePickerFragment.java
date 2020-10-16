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
import java.util.Locale;

import static com.example.trichypoliceapp.NewComplain.NewComplain.firObject;
import static com.example.trichypoliceapp.NewComplain.Page2.dateOfOccurence;
import static com.example.trichypoliceapp.NewComplain.Page2.dateOfReport;
import static com.example.trichypoliceapp.NewComplain.Page2.dateVal;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),this,year,month,day);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());




        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);



        if (dateVal == 0){
            dateOfOccurence.setText(sdf.format(c.getTime()));
            firObject.setDateOfOccurrence(sdf.format(c.getTime()));
        }
        else{
            dateOfReport.setText(sdf.format(c.getTime()));
            firObject.setDateOfReport(sdf.format(c.getTime()));
        }



    }
}
