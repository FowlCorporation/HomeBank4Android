package com.fowlcorp.homebank4android.gui;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Martin on 23/01/2015.
 */
public class CustomTableRow extends TableRow{

    private TextView label;
    private AutoCompleteTextView  edit;

    public CustomTableRow(Context context){
        super(context);
        label = new TextView(context);
        edit = new AutoCompleteTextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams Editparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        label.setLayoutParams(params);
        edit.setLayoutParams(Editparams);
        this.addView(label);
        this.addView(edit);


    }

    public TextView getLabel() {
        return label;
    }

    public void setLabelText(String text){
        label.setText(text);
    }

    public AutoCompleteTextView getEdit() {
        return edit;
    }

    public void setEditText(String text){
        edit.setText(text);
    }


}
