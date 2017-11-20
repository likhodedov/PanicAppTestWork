package com.withyou.apps.panicapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.withyou.apps.panicapp.VacancyData.DataParser;
import com.withyou.apps.panicapp.VacancyData.HttpHandler;
import com.withyou.apps.panicapp.VacancyData.VacancyModel;
import com.withyou.apps.panicapp.VacancyList.VacancyListFragment;
import org.json.JSONException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String VACANCY_URL = "http://rabota.ngs.ru/api/v1/vacancies/";
    private ProgressDialog pDialog;
    GetVacancies getVacanciesAsync;
    Toolbar toolbar;
    public static List<VacancyModel> vacancyModels;
    public static ImageView toolbarBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.toolbar_back).setVisibility(View.INVISIBLE);
        toolbarBackButton = (ImageView) toolbar.findViewById(R.id.toolbar_back);
        toolbarBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getVacanciesAsync = new GetVacancies();
        getVacanciesAsync.execute();
    }

    /**
     * Подгружаем все вакансии с сайта и далее передаем все во фрагмент, который их отобразит
     * Если нет интернета, или получаем некорректные данные, то появляется диалог предлагающий
     * повторить подгрузку вакансий.
     */
    private class GetVacancies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler handler = new HttpHandler();
            String inputData = handler.makeServiceCall(VACANCY_URL);

            try {
                vacancyModels = DataParser.getDataFromWeb(inputData);
            } catch (JSONException | IllegalArgumentException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Oops...");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Couldn't get json from server or we get empty data");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try again",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        getVacanciesAsync.cancel(true);
                                        getVacanciesAsync = new GetVacancies();
                                        getVacanciesAsync.execute();
                                    }
                                });
                        alertDialog.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.getProgress();
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (vacancyModels != null){
                VacancyListFragment myFragment = new VacancyListFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.lin,myFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack("fr1");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            moveTaskToBack(true);
            if (getVacanciesAsync != null)
                getVacanciesAsync.cancel(true);
        }
    }
}







