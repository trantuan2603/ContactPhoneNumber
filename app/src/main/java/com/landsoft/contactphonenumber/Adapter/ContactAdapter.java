package com.landsoft.contactphonenumber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.landsoft.contactphonenumber.Model.Contact;
import com.landsoft.contactphonenumber.R;

import java.util.List;

/**
 * Created by TRANTUAN on 15-Nov-17.
 */

public class ContactAdapter extends BaseAdapter{
    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvNumber;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = view.findViewById(R.id.img_avatar);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            viewHolder.tvNumber = view.findViewById(R.id.tv_number);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        Contact contact = contactList.get(i);
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvNumber.setText(contact.getNumber());
        if (contact.isMale()){
            viewHolder.imgAvatar.setImageResource(R.drawable.if_male3_403019);
        }
        else
        {
            viewHolder.imgAvatar.setImageResource(R.drawable.if_female1_403023);
        }
        return view;
    }
}
