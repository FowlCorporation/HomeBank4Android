package com.fowlcorp.homebank4android.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.fowlcorp.homebank4android.DetailedCardActivity;
import com.fowlcorp.homebank4android.R;
import com.fowlcorp.homebank4android.model.Operation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class AccountRecyclerAdapter extends RecyclerView.Adapter<OperationViewHolder> {
    private List<Operation> listOperation;
    private Calendar myDate;
    private Context context;
    
    public AccountRecyclerAdapter(List<Operation> listOperation ,Context context) {
    	this.listOperation = listOperation;
    	this.context = context;
	}

	@Override
	public int getItemCount() {
		return listOperation.size();
	}

	@Override
	public void onBindViewHolder(OperationViewHolder holder, int position) {
		final Operation operation = listOperation.get(position);
		
		myDate = Calendar.getInstance();
		myDate.setTime(operation.getDate().getTime());
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		holder.getView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context.getApplicationContext(), DetailedCardActivity.class);
				intent.putExtra("Date", df.format(myDate.getTime()));
				intent.putExtra("Category", operation.getCategory().getName());
				intent.putExtra("Payee", operation.getPayee().getName());
				intent.putExtra("Wording", operation.getWording());
				intent.putExtra("Amount", String.valueOf(operation.getAmount()));
				context.startActivity(intent);
			}
		});
		
		double montantdec = Math.round(operation.getBalanceAccount()*100);
		montantdec = montantdec/100;
		
		try {
			holder.getDate().setText(context.getString(R.string.cardLayout_date)+" "+df.format(myDate.getTime()));
		} catch (Exception e) {
		}
		try {
			holder.getCategory().setText(context.getString(R.string.cardLayout_category)+" "+operation.getCategory().getName());
		} catch (Exception e) {
		}
		try {
			holder.getTier().setText(context.getString(R.string.cardLayout_tier)+" "+operation.getPayee().getName());
		} catch (Exception e) {
		}
		try {
			holder.getMemo().setText(context.getString(R.string.cardLayout_memo)+" "+operation.getWording());
		} catch (Exception e) {
		}
		try {
			holder.getMontant().setText(colorText(context.getString(R.string.cardLayout_montant) + " ", String.valueOf(operation.getAmount())));
		} catch (Exception e) {
		}
        try {
        	holder.getSolde().setText(colorText(context.getString(R.string.cardLayout_solde) + " ", String.valueOf(montantdec)));
        } catch (Exception e) {
        }
				
	}

	@Override
	public OperationViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

		return new OperationViewHolder(v);
	}

	
	private Spannable colorText(String fieldName, String value) {
        Spannable span = new SpannableString(fieldName + value);
        span.setSpan(new ForegroundColorSpan((value.charAt(0) == '-' ? Color.rgb(206, 92, 0) : Color.rgb(78, 154, 54))), fieldName.length(), fieldName.length() + value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
  
}