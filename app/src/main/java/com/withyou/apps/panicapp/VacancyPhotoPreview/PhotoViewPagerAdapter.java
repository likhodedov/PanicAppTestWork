package com.withyou.apps.panicapp.VacancyPhotoPreview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.CompanyPhoto;
import java.util.List;

public class PhotoViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<CompanyPhoto> pageAdapterList;
    private LayoutInflater layoutInflater;

    public PhotoViewPagerAdapter(Context context, List<CompanyPhoto> pageAdapterList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        this.pageAdapterList = pageAdapterList;
    }

    @Override
    public int getCount() {
        return pageAdapterList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.layoutInflater.inflate(R.layout.photo_preview_pager_item, container, false);
        PhotoView displayImage = (PhotoView) view.findViewById(R.id.photo_preview);
        TextView descriptionText = (TextView) view.findViewById(R.id.photo_description);
        TextView titleText = (TextView) view.findViewById(R.id.photo_title);
        Glide.with(context)
                .load(pageAdapterList.get(position).getUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(displayImage);
        descriptionText.setText(pageAdapterList.get(position).getDescription());
        titleText.setText(pageAdapterList.get(position).getTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
