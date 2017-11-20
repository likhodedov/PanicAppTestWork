package com.withyou.apps.panicapp.VacancyList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.VacancyModel;

import java.util.List;


public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.ViewHolder> {
    private List<VacancyModel> vacancyList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView vacancyName;
        public TextView vacancySalary;
        public TextView vacancyCompanyLocation;
        public TextView vacancyDate;

        public ViewHolder(View view) {
            super(view);
            vacancyName = (TextView) view.findViewById(R.id.item_vacancy_name);
            vacancySalary = (TextView) view.findViewById(R.id.item_salary);
            vacancyCompanyLocation = (TextView) view.findViewById(R.id.item_company_city);
            vacancyDate = (TextView) view.findViewById(R.id.item_date);
        }
    }

    public VacancyAdapter(List<VacancyModel> vacancyList){
        this.vacancyList = vacancyList;
    }

        @Override
    public void onBindViewHolder(VacancyAdapter.ViewHolder holder, int position) {
            VacancyModel vacancyModel = vacancyList.get(position);
            holder.vacancyDate.setText(vacancyModel.getDate());
            holder.vacancyName.setText(vacancyModel.getVacancyName());
            holder.vacancySalary.setText(vacancyModel.getSalary());
            holder.vacancyCompanyLocation.setText(vacancyModel.getCompany().getTitle() + ", "
                    + vacancyModel.getContacts().getCityTitle());
    }

    @Override
    public int getItemCount() {
        return vacancyList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item,parent, false);
        return new ViewHolder(v);
    }
}