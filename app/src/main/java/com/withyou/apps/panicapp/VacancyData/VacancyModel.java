package com.withyou.apps.panicapp.VacancyData;


public class VacancyModel {
    private final String date;
    private final String vacancyName;
    private final String educationTitle;
    private final String experienceTitle;
    private final String workingTypeTitle;
    private final String sheduleTitle;
    private final String description;
    private final Contacts contacts;
    private final Company company;
    private final String views;
    private final String requirmentsShort;
    private final String requirments;
    private final String salary;

    /**
     * @param date - дата размещения вакансии
     * @param vacancyName - наименование вакансии
     * @param educationTitle - требования к образованию
     * @param experienceTitle - требования к опыту работы
     * @param workingTypeTitle - график работы
     * @param sheduleTitle - тип занятости
     * @param description - описание вакансии
     * @param contacts - контакты по данной вакансии
     * @param company - информация о компании
     * @param views - кол-во просмотров
     * @param requirmentsShort - требования к соискателю
     * @param requirments - требования к соискателю в более широкой форме
     * @param salary - зарплатная вилка
     */
    public VacancyModel(String date, String vacancyName, String educationTitle,
                        String experienceTitle, String workingTypeTitle, String sheduleTitle,
                        String description, Contacts contacts, Company company, String views,
                        String requirmentsShort, String requirments, String salary) {

        this.date = date;
        this.vacancyName = vacancyName;
        this.educationTitle = educationTitle;
        this.experienceTitle = experienceTitle;
        this.workingTypeTitle = workingTypeTitle;
        this.sheduleTitle = sheduleTitle;
        this.description = description;
        this.contacts = contacts;
        this.company = company;
        this.views = views;
        this.requirmentsShort = requirmentsShort;
        this.requirments = requirments;
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public String getEducationTitle() {
        return educationTitle;
    }

    public String getExperienceTitle() {
        return experienceTitle;
    }

    public String getWorkingTypeTitle() {
        return workingTypeTitle;
    }

    public String getSheduleTitle() {
        return sheduleTitle;
    }

    public String getDescription() {
        return description;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public Company getCompany() {
        return company;
    }

    public String getViews() {
        return views;
    }

    public String getRequirmentsShort() {
        return requirmentsShort;
    }

    public String getRequirments() {
        return requirments;
    }

    public String getSalary() {
        return salary;
    }

}
