/*
 * Copyright © 2015 Fowl Corporation
 *
 * This file is part of HomeBank4Android.
 *
 * HomeBank4Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HomeBank4Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HomeBank4Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fowlcorp.homebank4android.gui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fowlcorp.homebank4android.R;



public class OverviewViewHolder extends RecyclerView.ViewHolder{
	
	private TextView title;
	private TextView balance;
	private TextView future;
	private TextView today;
	private ImageView icon;
	
	private View itemView;

	public OverviewViewHolder(View itemView) {
		super(itemView);
		this.itemView = itemView;
		
		title = (TextView) itemView.findViewById(R.id.overview_card_title);
		balance = (TextView) itemView.findViewById(R.id.overview_card_balance);
		future = (TextView) itemView.findViewById(R.id.overview_card_future);
		today = (TextView) itemView.findViewById(R.id.overview_card_today);
		icon = (ImageView) itemView.findViewById(R.id.overview_card_icon);
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public TextView getBalance() {
		return balance;
	}

	public void setBalance(TextView balance) {
		this.balance = balance;
	}

	public TextView getFuture() {
		return future;
	}

	public void setFuture(TextView future) {
		this.future = future;
	}

	public TextView getToday() {
		return today;
	}

	public void setToday(TextView today) {
		this.today = today;
	}

	public View getItemView() {
		return itemView;
	}

	public void setItemView(View itemView) {
		this.itemView = itemView;
	}

	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}
	
	
	
}
