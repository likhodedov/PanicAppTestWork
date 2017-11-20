package com.withyou.apps.panicapp.VacancyData;

import java.util.List;

public class Company {
    private final String title;
    private final String url;
    private final List<CompanyPhoto> photo;

    /**
     * @param title - название компании
     * @param url - ссылка на логотип компании
     * @param photo - список фотографий, представленный компанией
     */
    public Company(String title, String url, List<CompanyPhoto> photo) {
        if (isCompanyNameEmpty(title)) title = "Организация не указана";
        this.title = title;
        this.url = url;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<CompanyPhoto> getPhoto() {
        return photo;
    }

    /**
     * @param title - название компании
     * @return true - некорректное название компании
     *         false - с названием все ОК
     */
    public boolean isCompanyNameEmpty(String title){
        return (title == null | title.equals("") | title.equals("null"));
    }

}
