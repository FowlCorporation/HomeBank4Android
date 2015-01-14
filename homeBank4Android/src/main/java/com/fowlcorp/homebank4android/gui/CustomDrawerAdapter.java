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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fowlcorp.homebank4android.R;

import java.util.List;
 
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
 
      Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
 
      public CustomDrawerAdapter(Context context, int layoutResourceID,
                  List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
 
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
 
            DrawerItemHolder drawerHolder;
            View view = convertView;
 
            if (view == null) {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
 
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.ItemName = (TextView) view
                              .findViewById(R.id.drawer_itemName);
                  drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
                  
                  drawerHolder.iconHeader = (ImageView) view.findViewById(R.id.drawer_icon_header);
 
                  drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);
 
                  drawerHolder.headerLayout = (LinearLayout) view
                              .findViewById(R.id.headerLayout);
                  drawerHolder.itemLayout = (LinearLayout) view
                              .findViewById(R.id.itemLayout);
 
                  view.setTag(drawerHolder);
 
            } else {
                  drawerHolder = (DrawerItemHolder) view.getTag();
 
            }
 
            DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
            

 
            /*if (dItem.isOverview()) {
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
                  drawerHolder.ItemName.setText(dItem.getItemName());
 
            }else*/ if(dItem.isHeader()){
            	
            	drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
                drawerHolder.title.setText(dItem.getItemName());
                try {
					drawerHolder.iconHeader.setImageDrawable(view.getResources().getDrawable(
					              dItem.getImgResID()));
				} catch (NotFoundException e) {
					drawerHolder.iconHeader.setImageDrawable(null);
				}
            } else {
            
 
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
 
                  try {
					drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					              dItem.getImgResID()));
				} catch (NotFoundException e) {
					drawerHolder.icon.setImageDrawable(null);
				}
                  drawerHolder.ItemName.setText(dItem.getItemName());
 
            }
            
           /* drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getItemName());*/
            
            return view;
      }
      
      @Override
    public boolean areAllItemsEnabled() {
    	// TODO Auto-generated method stub
    	return false;
    }
      
    @Override
    public boolean isEnabled(int position) {
    	if(drawerItemList.get(position).isHeader()){
    		return false;
    	} else {
    		return true;
    	}
    }
 
      private static class DrawerItemHolder {
            TextView ItemName, title;
            ImageView icon, iconHeader;
            LinearLayout headerLayout, itemLayout;
      }

}
