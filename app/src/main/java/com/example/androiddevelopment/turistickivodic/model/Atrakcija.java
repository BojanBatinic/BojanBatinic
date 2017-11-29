package com.example.androiddevelopment.turistickivodic.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.androiddevelopment.turistickivodic.model.Atrakcija.FIELD_NAME_PICTURES;

/**
 * Created by androiddevelopment on 29.11.17..
 */
@DatabaseTable(tableName = Atrakcija.TABLE_NAME_ATRAKCIJA)
public class Atrakcija {
    public static final String TABLE_NAME_ATRAKCIJA = "atrakcije";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_DESCRIPTION = "description";
    public static final String FIELD_NAME_ADDRESS = "address";
    public static final String FIELD_NAME_PHONE_NUMBER = "phone_number";
    public static final String FIELD_NAME_WEB_ADRESA = "web";
    public static final String FIELD_NAME_RADNO_VREME = "radno_vreme";
    public static final String FIELD_NAME_ULAZNICA = "ulaznica";
    public static final String FIELD_NAME_PICTURES = "pictures";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = FIELD_NAME_ADDRESS)
    private String address;

    @DatabaseField(columnName = FIELD_NAME_PHONE_NUMBER)
    private int phoneNumber;

    @DatabaseField(columnName = FIELD_NAME_WEB_ADRESA)
    private String webAdresa;

    @DatabaseField(columnName = FIELD_NAME_RADNO_VREME)
    private String radnoVreme;

    @DatabaseField(columnName = FIELD_NAME_ULAZNICA)
    private double ulaznica;

    @DatabaseField(columnName = FIELD_NAME_PICTURES)
    private String pictures;

    public Atrakcija() {
    }

    public static String getTableNameAtrakcija() {
        return TABLE_NAME_ATRAKCIJA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebAdresa() {
        return webAdresa;
    }

    public void setWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
    }

    public String getRadnoVreme() {
        return radnoVreme;
    }

    public void setRadnoVreme(String radnoVreme) {
        this.radnoVreme = radnoVreme;
    }

    public double getUlaznica() {
        return ulaznica;
    }

    public void setUlaznica(double ulaznica) {
        this.ulaznica = ulaznica;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return name;
    }
}
