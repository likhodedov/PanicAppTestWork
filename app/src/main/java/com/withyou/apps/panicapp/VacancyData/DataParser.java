package com.withyou.apps.panicapp.VacancyData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataParser {

/*
    Ищем ноду с наименованием vacancies, берем все элементы из нее и парсим.
    если данные пустые, то нет соединения с сервером и кидаем exception
    Для каждый подноды сначала проверяем не пуста ли она.
 */
    public static List<VacancyModel> getDataFromWeb(String data) throws JSONException,IllegalArgumentException {
        List<VacancyModel> vacancyModels = new ArrayList<VacancyModel>();
        if (data != null){
            JSONObject jsonObj = new JSONObject(data);
            JSONArray vacancyArray = jsonObj.getJSONArray("vacancies");
            for (int i = 0; i < vacancyArray.length(); i++) {
                JSONObject temp = vacancyArray.getJSONObject(i);
                Contacts tempContacts = getContact(temp);
                Company tempCompany = getCompany(temp);
                VacancyModel tempModel = getVacancyModel(temp,tempContacts,tempCompany);
                vacancyModels.add(tempModel);
            }
        } else throw new IllegalArgumentException("data empty");

        return vacancyModels;
    }

    private static Company getCompany(JSONObject jsonObject) throws JSONException {
        JSONObject tempObj = jsonObject.getJSONObject("company");
        JSONArray tempArray = tempObj.getJSONArray("photos");
        List<CompanyPhoto> photoArray = new ArrayList<CompanyPhoto>(0);
        String url = null;
        if (!tempObj.isNull("logo"))
            url = tempObj.getJSONObject("logo").optString("url","");
        if (tempArray.length() > 0) {
            for (int i = 0; i < tempArray.length(); i++) {
                JSONObject obj = tempArray.getJSONObject(i);
                String tempURL = obj.optString("url", "");
                String tempTitle = obj.optString("title", "");
                String tempDescr = obj.optString("description", "");
                CompanyPhoto tempCompanyPhoto = new CompanyPhoto(tempURL, tempTitle, tempDescr);
                photoArray.add(tempCompanyPhoto);
            }
        }
        return new Company(
                tempObj.optString("title", ""),
                url,
                photoArray
        );
    }


    private static Contacts getContact(JSONObject jsonObject) throws JSONException {
        JSONObject tempObj = jsonObject.getJSONObject("contact");
        String microdisctrict = null;
        String city = null;
        String dictrict = null;
        if (!tempObj.isNull("microdistrict"))
            microdisctrict = tempObj.getJSONObject("microdistrict").optString("title","");
        if (!tempObj.isNull("district"))
            dictrict = tempObj.getJSONObject("district").optString("title","");
        if (!tempObj.isNull("city"))
            city = tempObj.getJSONObject("city").optString("title","Город не указан");


        return new Contacts(
                tempObj.optString("icq",""),
                tempObj.optString("skype",""),
                tempObj.optString("email",""),
                tempObj.optString("url",""),
                tempObj.optString("street",""),
                tempObj.optString("building",""),
                tempObj.optString("room",""),
                tempObj.optString("name",""),
                city,
                dictrict,
                microdisctrict,
                tempObj.optString("address","")
                );
    }

    private static VacancyModel getVacancyModel(JSONObject object, Contacts contacts, Company company) throws JSONException {
        String education = null;
        String experience_length = null;
        String working_type = null;
        String schedule = null;
        if (!object.isNull("education"))
            education = object.getJSONObject("education").optString("title","");
        if (!object.isNull("experience_length"))
            experience_length = object.getJSONObject("experience_length").optString("title","");
        if (!object.isNull("working_type"))
            working_type = object.getJSONObject("working_type").optString("title","");
        if (!object.isNull("schedule"))
            schedule = object.getJSONObject("schedule").optString("title","");

        return new VacancyModel(
                getCorrectDate(object.optString("add_date","")),
                object.optString("header",""),
                education,
                experience_length,
                working_type,
                schedule,
                object.optString("description",""),
                contacts,
                company,
                object.optString("views",""),
                object.optString("requirements_short",""),
                object.optString("requirements",""),
                object.optString("salary","")
        );
    }

    /**
     * @param s - входная дата вида 2017-10-30T19:17:24+0700
     * @return дату в виде "1 января 2016"
     *          если год текущий, то сам год не указываем
     *          если дата будущая то возвращаем "Неизвестно"
     *          если прошлые года, то указывается и год
     */
    public static String getCorrectDate(String s){
        String[] monthName = {
                "янв.","февр.","марта","апр.","мая","июня","июля","авг.","сент.","окт.","нояб.","дек."
            };
        Pattern pattern = Pattern.compile("[0-9]{4}+[\\-]+[0-9]{2}+[\\-]+[0-9]{2}");
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()) {
            s = matcher.group(0);
            int[] dateFromServer = new int[3];
            String[] input = s.split("-");
            for (int i = 0;i < input.length;i++)
                dateFromServer[i] = Integer.parseInt(input[i]);
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);

            if (dateFromServer[0] > currentYear)
                return "Неизвестно";
            else if ((dateFromServer[0] == currentYear)&(dateFromServer[1] > currentMonth+1))
                return "Неизвестно";
            else if((dateFromServer[0] == currentYear)&(dateFromServer[1] == currentMonth+1)&(dateFromServer[2] > currentDay))
                return "Неизвестно";
            else if ((dateFromServer[2] == currentDay)&(dateFromServer[1] == currentMonth+1)&(dateFromServer[0] == currentYear))
                return "сегодня";
            else if (dateFromServer[0] != currentYear)
                return (input[2] + " " + monthName[dateFromServer[1]-1] + " " + input[0]+"г.");
            else return (input[2] + " " + monthName[dateFromServer[1]-1]);

        } else return "Неизвестно";
    }
}
