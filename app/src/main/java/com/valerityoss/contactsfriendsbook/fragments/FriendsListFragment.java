package com.valerityoss.contactsfriendsbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.valerityoss.contactsfriendsbook.R;
import com.valerityoss.contactsfriendsbook.activity.ContactFriendsBookActivity;
import com.valerityoss.contactsfriendsbook.adapters.FriendsListAdapter;
import com.valerityoss.contactsfriendsbook.interfaces.FriendsListFragmentCallback;
import com.valerityoss.contactsfriendsbook.models.Friend;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by denis on 27.04.2016.
 */
public class FriendsListFragment extends Fragment {
    @BindView(R.id.listViewContact)
    ListView listViewContact;

    private FriendsListAdapter friendsListAdapter;
    private ArrayList<Friend> friendList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_list_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        listViewContact.setOnItemClickListener(new FriendListOnItemClickListener());
        if (getArguments() != null) {
            friendList = getArguments().getParcelableArrayList(ContactFriendsBookActivity.KEY_FRIENDS_LIST);
            setFriendsList(friendList);
        }
        return rootView;
    }

    public void setFriendsList(ArrayList<Friend> friendList) {
        friendsListAdapter = new FriendsListAdapter(getActivity(), friendList, (FriendsListFragmentCallback) getActivity());
        listViewContact.setAdapter(friendsListAdapter);
    }


    public void updateFriendsList() {
        friendsListAdapter.notifyDataSetChanged();
    }

    private class FriendListOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ((FriendsListFragmentCallback) getActivity()).clickItemFriendsListFragment(position);
        }
    }
}
