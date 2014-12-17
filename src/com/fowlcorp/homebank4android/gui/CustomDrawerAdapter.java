package com.fowlcorp.homebank4android.gui;

import java.util.List;

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
import com.fowlcorp.homebank4android.model.Account;
 
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
 
            if (dItem.isOverview()) {
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
                  drawerHolder.ItemName.setText(dItem.getItemName());
 
            }else if(dItem.isHeader()){
            	
            	drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
                drawerHolder.title.setText(dItem.getItemName());
            } else {
            
 
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
 
                  try {
					drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					              dItem.getImgResID()));
				} catch (NotFoundException e) {
				}
                  drawerHolder.ItemName.setText(dItem.getItemName());
 
            }
            
           /* drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getItemName());*/
            
            return view;
      }
 
      private static class DrawerItemHolder {
            TextView ItemName, title;
            ImageView icon;
            LinearLayout headerLayout, itemLayout;
      }

}
