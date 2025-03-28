package com.balajiseeds.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balajiseeds.R;


public class CustomImageViewDashed extends RelativeLayout {
    private ImageView dottedBorderImageView;
    private TextView uploadTextView;

    public CustomImageViewDashed(Context context) {
        super(context);
        init(context);
    }

    public CustomImageViewDashed(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImageViewDashed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.custom_image_view, this);
        dottedBorderImageView = findViewById(R.id.dottedBorderImageView);
        uploadTextView = findViewById(R.id.uploadTextView);
        if (dottedBorderImageView.getDrawable() != null) {
            uploadTextView.setVisibility(GONE);
        }
    }

    public ImageView getImageView() {
        return dottedBorderImageView;
    }

    public TextView getUploadTextView() {
        return uploadTextView;
    }


}
