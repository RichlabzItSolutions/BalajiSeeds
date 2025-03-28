package com.balajiseeds;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.balajiseeds.databinding.DialogImageViewerBinding;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class ImageViewerDialogFragment extends DialogFragment {

    private static final String ARG_IMAGE_URLS = "imageUrls";
    private ArrayList<String> imageUrls;
    int pos=0;

    public static ImageViewerDialogFragment newInstance(ArrayList<String> imageUrls,int pos) {
        ImageViewerDialogFragment fragment = new ImageViewerDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_IMAGE_URLS, imageUrls);
        args.putInt("pos", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogImageViewerBinding binding = DialogImageViewerBinding.inflate(getLayoutInflater());
        Bundle args = getArguments();
        imageUrls = args != null ? args.getStringArrayList(ARG_IMAGE_URLS) : new ArrayList<>();
        pos = args != null ? args.getInt("pos") : 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());

        ViewPager viewPager = binding.viewPager;
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        return builder.create();
    }

    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView imageView = new PhotoView(container.getContext());
            Glide.with(requireContext())
                    .load(imageUrls.get(position))
                    .placeholder(R.drawable.loading)
                    .placeholder(R.drawable.loading).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }
}
