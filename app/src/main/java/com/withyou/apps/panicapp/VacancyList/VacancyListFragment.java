package com.withyou.apps.panicapp.VacancyList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.withyou.apps.panicapp.MainActivity;
import com.withyou.apps.panicapp.R;
import com.withyou.apps.panicapp.VacancyData.DataParser;
import com.withyou.apps.panicapp.VacancyData.HttpHandler;
import com.withyou.apps.panicapp.VacancyData.VacancyModel;
import com.withyou.apps.panicapp.VacancyDetails.VacancyDetailFragment;
import org.json.JSONException;
import java.util.List;
import static com.withyou.apps.panicapp.MainActivity.vacancyModels;

public class VacancyListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    VacancyAdapter vacancyAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<VacancyModel> tempModelVacancies;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_vacancy_fragment,
                container, false);
        final Context context = getActivity().getApplicationContext();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        vacancyAdapter = new VacancyAdapter(vacancyModels);
        recyclerView.setAdapter(vacancyAdapter);
        MainActivity.toolbarBackButton.setVisibility(View.INVISIBLE);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        FragmentTransaction fragmentTransaction=getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction();
                        VacancyDetailFragment myFragment = new VacancyDetailFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("id",position );

                        myFragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(
                                 R.anim.slide_right,
                                 R.anim.slide_left,
                                 R.anim.slide_left_reverse,
                                 R.anim.slide_right_reverse);
                        fragmentTransaction.replace(R.id.lin,myFragment);
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack("fr2");
                    }
                })
        );
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setRefreshing(true);
        GetVacancies getVacanciesAsync = new GetVacancies();
        getVacanciesAsync.execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    private class GetVacancies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler handler = new HttpHandler();
            String inputData =handler.makeServiceCall(MainActivity.VACANCY_URL);
            try {
                tempModelVacancies = DataParser.getDataFromWeb(inputData);
            } catch (JSONException | IllegalArgumentException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Ошибка получения данных", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (tempModelVacancies!=null){
                        vacancyModels.clear();
                        vacancyModels.addAll(tempModelVacancies);
                        vacancyAdapter.notifyDataSetChanged();
                        Log.e("TAG","SUCCESS");
            }
        }
    }
}

