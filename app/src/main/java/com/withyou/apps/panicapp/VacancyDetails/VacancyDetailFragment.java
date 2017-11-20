package com.withyou.apps.panicapp.VacancyDetails;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.withyou.apps.panicapp.MainActivity;
import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.VacancyModel;
import com.withyou.apps.panicapp.VacancyList.RecyclerItemClickListener;
import com.withyou.apps.panicapp.VacancyPhotoPreview.PhotoPreviewFragment;
import org.sufficientlysecure.htmltextview.HtmlTextView;


public class VacancyDetailFragment extends Fragment {

    RecyclerView photoRecyclerView;
    ImageView companyLogoImage;
    VacancyModel model;
    int position_item;
    Bundle bundle;
    TextView vacancyNameText;
    TextView vacancyDateText;
    TextView vacancyViewsText;
    TextView vacancySalaryText;
    TextView vacancyCityText;
    TextView vacancyExperienceText;
    TextView vacancyEmploymentText;
    TextView vacancyCompanyNameText;
    TextView vacancyCompanyAddressText;
    TextView vacancyContactName;
    TextView vacancyContactSite;
    TextView vacancyContactEmail;
    TextView vacancyContactICQ;
    HtmlTextView vacancyDescriptionHTMLText;
    PhotoRecyclerAdapter photoRecyclerAdapter;
    BroadcastReceiver networkStateReceiver;

    /*
        Отображаем всю имеющуюся информацию по вакансии, если нет списка фотографий от вакансии, то
        скрываем вьюху, так же и с контактными данными, если полностью отсутствуют какие либо
        контактные данные, то не показываем вообще, иначе показываем что есть.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vacancy_detail_fragment,
                container, false);

        bundle = this.getArguments();
        if (bundle != null) position_item = bundle.getInt("id");

        final Context context = getActivity().getApplicationContext();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        photoRecyclerView = (RecyclerView) view.findViewById(R.id.details_horizontal_photo);
        photoRecyclerView.setLayoutManager(layoutManager);
        companyLogoImage = (ImageView) view.findViewById(R.id.details_image_company_logo);
        vacancyNameText = (TextView) view.findViewById(R.id.details_text_vacancy_name);
        vacancyDateText = (TextView) view.findViewById(R.id.details_text_date);
        vacancyViewsText = (TextView) view.findViewById(R.id.details_text_views);
        vacancySalaryText = (TextView) view.findViewById(R.id.details_text_salary);
        vacancyCityText = (TextView) view.findViewById(R.id.details_text_city);
        vacancyExperienceText = (TextView) view.findViewById(R.id.details_text_experience);
        vacancyEmploymentText = (TextView) view.findViewById(R.id.details_text_employment);
        vacancyCompanyNameText = (TextView) view.findViewById(R.id.details_text_company_name);
        vacancyCompanyAddressText = (TextView) view.findViewById(R.id.details_text_company_location);
        vacancyDescriptionHTMLText = (HtmlTextView) view.findViewById(R.id.details_text_descr);
        vacancyContactName = (TextView) view.findViewById(R.id.details_contact_person);
        vacancyContactSite = (TextView) view.findViewById(R.id.details_contact_site);
        vacancyContactEmail = (TextView) view.findViewById(R.id.details_contact_mail);
        vacancyContactICQ = (TextView) view.findViewById(R.id.details_contact_icq);

        MainActivity.toolbarBackButton.setVisibility(View.VISIBLE);
        model = MainActivity.vacancyModels.get(position_item);
        vacancyNameText.setText(model.getVacancyName());
        vacancyDateText.setText(model.getDate());
        vacancyViewsText.setText(model.getViews());
        vacancySalaryText.setText(model.getSalary());
        vacancyCityText.setText(model.getContacts().getAdress());
        vacancyExperienceText.setText(model.getExperienceTitle());
        vacancyEmploymentText.setText(model.getWorkingTypeTitle());
        vacancyCompanyNameText.setText(model.getCompany().getTitle());
        vacancyCompanyAddressText.setText(model.getContacts().getCityTitle());
        vacancyDescriptionHTMLText.setHtml(model.getDescription());

        if (model.getCompany().getPhoto().size() == 0)
            view.findViewById(R.id.photo_list_layout).setVisibility(View.GONE);

        if (model.getContacts().isContactEmpty()) {
            view.findViewById(R.id.details_contact_layout).setVisibility(View.GONE);
        } else {
            if (!model.getContacts().isNameEmpty())
                vacancyContactName.setText(model.getContacts().getName());
            else view.findViewById(R.id.details_contact_person_layout).setVisibility(View.GONE);
            if (!model.getContacts().isURLEmpty())
                vacancyContactSite.setText(model.getContacts().getUrl());
            else view.findViewById(R.id.details_contact_site_layout).setVisibility(View.GONE);
            if (!model.getContacts().isEmailEmpty())
                vacancyContactEmail.setText(model.getContacts().getEmail());
            else view.findViewById(R.id.details_contact_mail_layout).setVisibility(View.GONE);
            if (!model.getContacts().isICQEmpty())
                vacancyContactICQ.setText(model.getContacts().getIcq());
            else view.findViewById(R.id.details_contact_icq_layout).setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(model.getCompany().getUrl())
                .apply(RequestOptions.placeholderOf(R.drawable.company_placeholder_logo))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(companyLogoImage);

        photoRecyclerAdapter = new PhotoRecyclerAdapter(model.getCompany().getPhoto(),getContext());
        photoRecyclerView.setAdapter(photoRecyclerAdapter);

        photoRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                        PhotoPreviewFragment photoPreviewFragment = new PhotoPreviewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("photo_position",position);
                        bundle.putInt("photo_item",position_item);
                        photoPreviewFragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(
                                R.anim.slide_in_up,
                                R.anim.slide_out_up,
                                R.anim.slide_out_up_reverse,
                                R.anim.slide_in_up_reverse);
                        fragmentTransaction.add(R.id.lin,photoPreviewFragment);
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack("fr3");
                    }
                })
        );
        return view;
    }

    /*
        Так как есть вероятность что пользователь смог прогрузить список вакансий и они у него
        отобразились, но при переходе к этому фрагменту интернета - нет. Так как фотографии
        для подгрузки есть, лайаут для ресайклера отобразится, но будут висеть плейсхолдеры вместо
        фотографий, поэтому при появлении сети обновляем данные ресайклера получая интент.
     */
    @Override
    public void onResume(){
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        networkStateReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                 if (intent.getExtras() != null) {
                     final ConnectivityManager connectivityManager
                             = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                     final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                     if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                         photoRecyclerAdapter.notifyDataSetChanged();
                     }
                 }
            }
        };
        getActivity().registerReceiver(networkStateReceiver, filter);
        super.onResume();
    }

    @Override
    public void onPause(){
        getActivity().unregisterReceiver(networkStateReceiver);
        super.onPause();
    }
}


