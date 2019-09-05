package com.valerityoss.contactsfriendsbook.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valerityoss.contactsfriendsbook.R;
import com.valerityoss.contactsfriendsbook.interfaces.TopToolBarCallback;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by denis on 27.04.2016.
 */
public class TopToolBar extends LinearLayout {
    public static final int TYPE_LIST_FRIENDS = 1;
    public static final int TYPE_VIEW_FRIEND = 2;
    public static final int TYPE_ADD_FRIEND = 3;
    public static final int TYPE_EDIT_FRIEND = 4;

    public static final int TYPE_ITEM_BACK = 0;
    public static final int TYPE_ITEM_RIGHT = 1;

    @BindView(R.id.buttonViewRight)
    ImageView buttonViewRight;
    @BindView(R.id.buttonViewLogo)
    ImageView buttonViewLogo;
    @BindView(R.id.buttonViewBack)
    ImageView buttonViewBack;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @BindDrawable(R.mipmap.add_user_ic)
    Drawable addUserDrawable;
    @BindDrawable(R.mipmap.edit_user_ic)
    Drawable editUserDrawable;
    @BindDrawable(R.mipmap.apply_user_ic)
    Drawable applyUserDrawable;

    private String title = "";
    private int type = TYPE_LIST_FRIENDS;
    private TopToolBarCallback topToolBarCallback;

    public TopToolBar(Context context) {
        super(context);
        initViews(context);
    }

    public TopToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public TopToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopToolBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        ButterKnife.bind(this);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TopToolBar, 0, 0);
        try {
            type = a.getInt(R.styleable.TopToolBar_typeTopToolBar, TYPE_LIST_FRIENDS);
            title = a.getString(R.styleable.TopToolBar_titleTopToolBar);
        } finally {
            a.recycle();
        }
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        ButterKnife.bind(this);
        setTitleText(title);
        setTypeItem(type);
    }

    public void setTitleText(String title) {
        if (title != null) {
            if (!title.equalsIgnoreCase(this.title))
                this.title = title;

            textViewTitle.setText(title);
        }
    }

    public void setTypeItem(int type) {
        if (type != this.type)
            this.type = type;
        switch (type) {
            case TYPE_LIST_FRIENDS:
                buttonViewRight.setVisibility(View.VISIBLE);
                buttonViewBack.setVisibility(View.INVISIBLE);
                buttonViewRight.setImageDrawable(addUserDrawable);
                break;
            case TYPE_EDIT_FRIEND:
                buttonViewRight.setVisibility(View.VISIBLE);
                buttonViewBack.setVisibility(View.VISIBLE);
                buttonViewRight.setImageDrawable(applyUserDrawable);
                break;
            case TYPE_ADD_FRIEND:
                buttonViewRight.setVisibility(View.VISIBLE);
                buttonViewBack.setVisibility(View.VISIBLE);
                buttonViewRight.setImageDrawable(applyUserDrawable);
                break;
            case TYPE_VIEW_FRIEND:
                buttonViewRight.setVisibility(View.VISIBLE);
                buttonViewBack.setVisibility(View.VISIBLE);
                buttonViewRight.setImageDrawable(editUserDrawable);
                break;
            default:

        }
    }

    public void setCallback(TopToolBarCallback topToolBarCallback) {
        if (topToolBarCallback != null)
            this.topToolBarCallback = topToolBarCallback;
    }

    @OnClick(R.id.buttonViewRight)
    public void clickButtonViewRight() {
        Log.d("DENIS_LOG","clickButtonViewRight");
        if (topToolBarCallback != null)
            topToolBarCallback.clickItemTopToolBar(type, TYPE_ITEM_RIGHT);

    }

    @OnClick(R.id.buttonViewBack)
    public void clickButtonViewBack() {
        if (topToolBarCallback != null)
            topToolBarCallback.clickItemTopToolBar(type, TYPE_ITEM_BACK);

    }
}
