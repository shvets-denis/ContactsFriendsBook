package com.valerityoss.contactsfriendsbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.valerityoss.contactsfriendsbook.R;
import com.valerityoss.contactsfriendsbook.interfaces.FriendsListFragmentCallback;
import com.valerityoss.contactsfriendsbook.models.Friend;
import com.valerityoss.contactsfriendsbook.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by denis on 28.04.2016.
 */
public class FriendsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Friend> friendList;
    private FriendsListFragmentCallback friendsListCallback;

    public FriendsListAdapter(Context context, ArrayList<Friend> friendList, FriendsListFragmentCallback friendsListCallback) {
        this.context = context;
        this.friendList = friendList;
        this.friendsListCallback = friendsListCallback;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @BindView(R.id.imageViewLogoFriend)
        ImageView imageViewLogoFriend;
        @BindView(R.id.textViewNameFriend)
        TextView textViewNameFriend;
        @BindView(R.id.textViewPhoneFriend)
        TextView textViewPhoneFriend;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;
        @BindView(R.id.imageViewOpen)
        ImageView imageViewOpen;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.friend_item_layout, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Friend friend = friendList.get(position);
        if (Utils.checkString(friend.urlPhoto)) {
            Glide.with(context).load(friend.urlPhoto).into(holder.imageViewLogoFriend);
        }
        holder.textViewNameFriend.setText(Utils.getString(context, friend.firstName)+" "+Utils.getString(context, friend.lastName));
        holder.textViewPhoneFriend.setText(Utils.getString(context, friend.phone));
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsListCallback != null)
                    friendsListCallback.clickItemFriendsListFragment(position);
            }
        });
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friendsListCallback != null)
                    friendsListCallback.removeItemFriendsListFragment(position);
            }
        });
        return view;
    }
}