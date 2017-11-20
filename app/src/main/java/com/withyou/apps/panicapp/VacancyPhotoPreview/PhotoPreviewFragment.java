package com.withyou.apps.panicapp.VacancyPhotoPreview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.withyou.apps.panicapp.MainActivity;
import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.CompanyPhoto;

import java.util.List;

public class PhotoPreviewFragment extends Fragment {
    int position_photo;
    int position_item;
    CrutchViewPager viewPager;
    List<CompanyPhoto> photoList;
    PhotoViewPagerAdapter photoViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_preview_fragment,
                container, false);
        Context context = getActivity().getApplicationContext();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position_item = bundle.getInt("photo_item");
            position_photo = bundle.getInt("photo_position");
        }

        MainActivity.toolbarBackButton.setVisibility(View.VISIBLE);
        viewPager = (CrutchViewPager) view.findViewById(R.id.photo_preview_pager);

        photoList = MainActivity.vacancyModels.get(position_item).getCompany().getPhoto();
        photoViewPagerAdapter = new PhotoViewPagerAdapter(context, photoList);
        viewPager.setAdapter(photoViewPagerAdapter);
        viewPager.setCurrentItem(position_photo);

        return view;
    }
}
