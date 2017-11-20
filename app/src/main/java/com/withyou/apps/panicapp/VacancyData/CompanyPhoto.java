package com.withyou.apps.panicapp.VacancyData;

public class CompanyPhoto {
    private final String url;
    private final String title;
    private final String description;

    /**
     * @param url - ссылка на фотографию
     * @param title - заголовок фото
     * @param description - описание фото
     */
    public CompanyPhoto(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
