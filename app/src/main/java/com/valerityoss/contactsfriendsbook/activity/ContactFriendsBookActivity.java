package com.valerityoss.contactsfriendsbook.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.valerityoss.contactsfriendsbook.R;
import com.valerityoss.contactsfriendsbook.components.TopToolBar;
import com.valerityoss.contactsfriendsbook.fragments.FriendInfoFragment;
import com.valerityoss.contactsfriendsbook.fragments.FriendsListFragment;
import com.valerityoss.contactsfriendsbook.interfaces.FriendInfoFragmentCallback;
import com.valerityoss.contactsfriendsbook.interfaces.FriendsListFragmentCallback;
import com.valerityoss.contactsfriendsbook.interfaces.TopToolBarCallback;
import com.valerityoss.contactsfriendsbook.models.Friend;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactFriendsBookActivity extends AppCompatActivity implements TopToolBarCallback, FriendsListFragmentCallback, FriendInfoFragmentCallback
{

    public static final String KEY_FRIENDS_LIST = "KEY_FRIENDS_LIST";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.containerFragment)
    FrameLayout containerFragment;
    @BindView(R.id.topToolBar)
    TopToolBar topToolBar;

    private ArrayList<Friend> listFriends;
    private FragmentManager fragmentManager;
    private FriendsListFragment friendsListFragment;
    private FriendInfoFragment friendInfoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_friends_book_activity_layout);
        ButterKnife.bind(this);
        topToolBar.setCallback(this);
        addFragmentToContainer();
        new LoadFriendsFromBaseTask().execute();

    }

    private void addFragmentToContainer()
    {
        friendsListFragment = new FriendsListFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerFragment, friendsListFragment, FriendsListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void setListFriends()
    {
        friendsListFragment.setFriendsList(listFriends);
    }

    private void goFriendsListFragment()
    {
        FriendsListFragment fragment = (FriendsListFragment) fragmentManager.findFragmentByTag(FriendsListFragment.class.getSimpleName());
        if(fragment == null)
        {
            Bundle args = new Bundle();
            args.putParcelableArrayList(KEY_FRIENDS_LIST, listFriends);
            friendsListFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendsListFragment, FriendsListFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_LIST_FRIENDS);
            topToolBar.setTitleText(getString(R.string.app_name));
        }
    }

    private void goEditFriend()
    {
        friendInfoFragment.editFriendInfo();
        topToolBar.setTypeItem(TopToolBar.TYPE_EDIT_FRIEND);
    }

    private void addFriendAndGoFriendsList()
    {
        friendInfoFragment.getFriendModel().save();
        listFriends.add(friendInfoFragment.getFriendModel());
        FriendsListFragment fragment = (FriendsListFragment) fragmentManager.findFragmentByTag(FriendsListFragment.class.getSimpleName());
        if(fragment == null)
        {
            Bundle args = new Bundle();
            args.putParcelableArrayList(KEY_FRIENDS_LIST, listFriends);
            friendsListFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendsListFragment, FriendsListFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_LIST_FRIENDS);
            topToolBar.setTitleText(getString(R.string.app_name));
        }
    }

    private void goAddFriend()
    {
        FriendInfoFragment fragment = (FriendInfoFragment) fragmentManager.findFragmentByTag(FriendInfoFragment.class.getSimpleName());
        if(fragment == null)
        {
            friendInfoFragment = new FriendInfoFragment();
            Bundle args = new Bundle();
            args.putParcelable(Friend.class.getSimpleName(), new Friend());
            friendInfoFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendInfoFragment, FriendInfoFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_ADD_FRIEND);
            topToolBar.setTitleText(getString(R.string.add_friend_string));
        }
    }

    private void goUpdateFriend()
    {
        friendInfoFragment.getFriendModel().save();
        FriendsListFragment fragment = (FriendsListFragment) fragmentManager.findFragmentByTag(FriendsListFragment.class.getSimpleName());
        if(fragment == null)
        {
            Bundle args = new Bundle();
            args.putParcelableArrayList(KEY_FRIENDS_LIST, listFriends);
            friendsListFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendsListFragment, FriendsListFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_LIST_FRIENDS);
            topToolBar.setTitleText(getString(R.string.app_name));
        }
    }

    private void removeFriendList(int positionItem)
    {
        listFriends.get(positionItem).delete();
        listFriends.remove(positionItem);
        friendsListFragment.updateFriendsList();
    }

    private void goFriendInfo(int positionItem)
    {
        FriendInfoFragment fragment = (FriendInfoFragment) fragmentManager.findFragmentByTag(FriendInfoFragment.class.getSimpleName());
        if(fragment == null)
        {
            friendInfoFragment = new FriendInfoFragment();
            Bundle args = new Bundle();
            args.putParcelable(Friend.class.getSimpleName(), listFriends.get(positionItem));
            friendInfoFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendInfoFragment, FriendInfoFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_VIEW_FRIEND);
            topToolBar.setTitleText(listFriends.get(positionItem).firstName);
        }
    }

    private void removeInfoFriend()
    {
        listFriends.remove(friendInfoFragment.getFriendModel());
        friendInfoFragment.getFriendModel().delete();
        FriendsListFragment fragment = (FriendsListFragment) fragmentManager.findFragmentByTag(FriendsListFragment.class.getSimpleName());
        if(fragment == null)
        {
            Bundle args = new Bundle();
            args.putParcelableArrayList(KEY_FRIENDS_LIST, listFriends);
            friendsListFragment.setArguments(args);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, friendsListFragment, FriendsListFragment.class.getSimpleName());
            fragmentTransaction.commit();
            topToolBar.setTypeItem(TopToolBar.TYPE_LIST_FRIENDS);
            topToolBar.setTitleText(getString(R.string.app_name));
        }
    }

    @Override
    public void clickItemTopToolBar(int typeTopToolBar, int typeItemTopToolBar)
    {
        Log.d("DENIS_LOG", "typeTopToolBar = " + typeTopToolBar);
        switch(typeItemTopToolBar)
        {
            case TopToolBar.TYPE_ITEM_BACK:
                goFriendsListFragment();
                break;
            case TopToolBar.TYPE_ITEM_RIGHT:

                switch(typeTopToolBar)
                {
                    case TopToolBar.TYPE_ADD_FRIEND:
                        addFriendAndGoFriendsList();
                        break;
                    case TopToolBar.TYPE_EDIT_FRIEND:
                        goUpdateFriend();
                        break;
                    case TopToolBar.TYPE_LIST_FRIENDS:
                        goAddFriend();
                        break;
                    case TopToolBar.TYPE_VIEW_FRIEND:
                        goEditFriend();
                        break;
                }
                break;
        }
    }

    @Override
    public void clickItemFriendsListFragment(int positionItem)
    {
        goFriendInfo(positionItem);
    }

    @Override
    public void removeItemFriendsListFragment(int positionItem)
    {
        removeFriendList(positionItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Fragment fragment = getFragmentManager().findFragmentByTag(FriendInfoFragment.class.getSimpleName());
        if(fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void removeFriend()
    {
        removeInfoFriend();
    }

    class LoadFriendsFromBaseTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            listFriends = (ArrayList<Friend>) SQLite.select().from(Friend.class).queryList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            setListFriends();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
