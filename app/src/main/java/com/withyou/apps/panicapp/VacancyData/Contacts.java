package com.withyou.apps.panicapp.VacancyData;

public class Contacts {
    private final String icq;
    private final String skype;
    private final String email;
    private final String url;
    private final String street;
    private final String building;
    private final String room;
    private final String name;
    private final String cityTitle;
    private final String districtTitle;
    private final String microdistrictTitle;
    private final String adress;

    /**
     * @param icq - номер ICQ
     * @param skype - логин скайпа
     * @param email - адрес почты
     * @param url - сайт
     * @param street - улица
     * @param building - дом
     * @param room - номер офиса
     * @param name - контактное лицо
     * @param cityTitle - город
     * @param districtTitle - район
     * @param microdistrictTitle
     * @param adress  - полный адрес компании
     */
    public Contacts(String icq, String skype, String email, String url, String street,
                    String building, String room, String name, String cityTitle,
                    String districtTitle,String microdistrictTitle, String adress) {
        if (isCityTitleEmpty(cityTitle)) cityTitle = "Город не определен";
        this.icq = icq;
        this.skype = skype;
        this.email = email;
        this.url = url;
        this.street = street;
        this.building = building;
        this.room = room;
        this.name = name;
        this.cityTitle = cityTitle;
        this.districtTitle = districtTitle;
        this.microdistrictTitle = microdistrictTitle;
        this.adress = adress;
    }

    public String getIcq() {
        return icq;
    }

    public String getSkype() {
        return skype;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public String getMicrodistrictTitle() {
        return microdistrictTitle;
    }

    public String getAdress() {
        return adress;
    }

    public boolean isICQEmpty(){
        return (icq == null | icq.equals("0") | icq.equals(""));
    }

    /**
     * @return true - данные невалидны
     *         false - с данными все ОК
     */
    public boolean isEmailEmpty(){
        return (email == null | email.equals("") | email.equals("null"));
    }

    public boolean isURLEmpty(){
        return (url == null | url.equals(""));
    }

    public boolean isNameEmpty(){
        return (name == null | name.equals(""));
    }

    public boolean isContactEmpty(){
        return (isICQEmpty() && isEmailEmpty() && isURLEmpty() && isNameEmpty());
    }
    public boolean isCityTitleEmpty(String cityTitle){
        return (cityTitle == null | cityTitle.equals("") | cityTitle.equals("null"));
    }
}
