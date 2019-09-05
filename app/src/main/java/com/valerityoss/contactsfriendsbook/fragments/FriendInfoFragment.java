package com.valerityoss.contactsfriendsbook.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.valerityoss.contactsfriendsbook.R;
import com.valerityoss.contactsfriendsbook.interfaces.FriendInfoFragmentCallback;
import com.valerityoss.contactsfriendsbook.models.Friend;
import com.valerityoss.contactsfriendsbook.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by denis on 28.04.2016.
 */
public class FriendInfoFragment extends Fragment {

    private static final int REQUEST_PICK_PHOTO = 1;

    @BindView(R.id.imageViewPhotoFriend)
    ImageView imageViewPhotoFriend;
    @BindView(R.id.editTextFirstName)
    EditText editTextFirstName;
    @BindView(R.id.editTextLastName)
    EditText editTextLastName;
    @BindView(R.id.buttonViewDelete)
    ImageView buttonViewDelete;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPhone)
    EditText editTextPhone;
    @BindView(R.id.textViewGender)
    TextView textViewGender;
    @BindView(R.id.switchGender)
    Switch switchGender;

    private Unbinder unbinder;

    private String urlPhoto;
    private Friend currentFriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_info_fragment_layout, container, false);
        unbinder=ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            currentFriend = getArguments().getParcelable(Friend.class.getSimpleName());
            if (currentFriend != null) {
                if (currentFriend.id != 0)
                    setFriendInfo();
                else
                    clearFields();
            }

        }
        return rootView;
    }

    @OnClick(R.id.buttonViewDelete)
    public void removeCurrentFriend() {
        ((FriendInfoFragmentCallback) getActivity()).removeFriend();
    }

    @OnClick(R.id.imageViewPhotoFriend)
    public void clickChooseGalleryPhoto() {
        chooseGalleryPhoto();
    }

    @OnClick(R.id.switchGender)
    public void switchGenderFriend(Switch switchGender) {
        String gender = (switchGender.isChecked()) ? getActivity().getString(R.string.gender_male_string) : getActivity().getString(R.string.gender_female_string);
        textViewGender.setText(gender);
    }

    private void chooseGalleryPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                urlPhoto = selectedImage.toString();
                if (Utils.checkString(urlPhoto)) {
                    Glide.with(getActivity()).load(urlPhoto).into(imageViewPhotoFriend);
                }
            }
        }
    }

    public Friend getFriendModel() {
        Friend friend;
        if (currentFriend == null)
            friend = new Friend();
        else
            friend = currentFriend;
        friend.firstName = editTextFirstName.getText().toString();
        friend.lastName = editTextLastName.getText().toString();
        friend.phone = editTextPhone.getText().toString();
        friend.email = editTextEmail.getText().toString();
        friend.gender = Utils.getGenderInt(switchGender.isChecked());
        friend.urlPhoto = urlPhoto;
        return friend;
    }

    private void clearFields() {
        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        switchGender.setChecked(false);
        textViewGender.setText(getActivity().getString(R.string.gender_female_string));
        urlPhoto = null;
        imageViewPhotoFriend.setImageResource(R.mipmap.def_user_ic);
        imageViewPhotoFriend.setClickable(true);
        buttonViewDelete.setVisibility(View.GONE);
    }

    private void setFriendInfo() {
        urlPhoto = currentFriend.urlPhoto;
        if (Utils.checkString(currentFriend.urlPhoto)) {
            Glide.with(getActivity()).load(currentFriend.urlPhoto).into(imageViewPhotoFriend);
        }
        editTextFirstName.setText(Utils.getString(getActivity(), currentFriend.firstName));
        editTextLastName.setText(Utils.getString(getActivity(), currentFriend.lastName));
        editTextEmail.setText(Utils.getString(getActivity(), currentFriend.email));
        editTextPhone.setText(Utils.getString(getActivity(), currentFriend.phone));
        textViewGender.setText(Utils.getGenderString(getActivity(), currentFriend.gender));

        editTextFirstName.setEnabled(false);
        editTextLastName.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextPhone.setEnabled(false);
        imageViewPhotoFriend.setClickable(false);
        switchGender.setVisibility(View.GONE);
        buttonViewDelete.setVisibility(View.VISIBLE);

    }

    public void editFriendInfo() {
        if (Utils.checkString(currentFriend.urlPhoto)) {
            Glide.with(getActivity()).load(currentFriend.urlPhoto).into(imageViewPhotoFriend);
        }
        editTextFirstName.setText(Utils.getString(getActivity(), currentFriend.firstName));
        editTextLastName.setText(Utils.getString(getActivity(), currentFriend.lastName));
        editTextEmail.setText(Utils.getString(getActivity(), currentFriend.email));
        editTextPhone.setText(Utils.getString(getActivity(), currentFriend.phone));
        textViewGender.setText(Utils.getGenderString(getActivity(), currentFriend.gender));

        editTextFirstName.setEnabled(true);
        editTextLastName.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextPhone.setEnabled(true);
        imageViewPhotoFriend.setClickable(true);
        switchGender.setVisibility(View.VISIBLE);
        switchGender.setChecked(Utils.getGenderBoolean(currentFriend.gender));
        buttonViewDelete.setVisibility(View.GONE);

        editTextFirstName.requestFocus();

    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
