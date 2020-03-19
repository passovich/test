package com.example.test;

public class User {
    private String m_token;
    private String m_name;
    private String m_email;
    private String m_password;
    private String m_phone_country_code;
    private String m_phone;
    private String m_address;

    public User(String m_token, String m_name, String m_email, String m_password, String m_phone_country_code, String m_phone, String m_address) {
        this.m_token = m_token;
        this.m_name = m_name;
        this.m_email = m_email;
        this.m_password = m_password;
        this.m_phone_country_code = m_phone_country_code;
        this.m_phone = m_phone;
        this.m_address = m_address;
    }

    public String getM_token() {
        return m_token;
    }

    public void setM_token(String m_token) {
        this.m_token = m_token;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }

    public String getM_phone_country_code() {
        return m_phone_country_code;
    }

    public void setM_phone_country_code(String m_phone_country_code) {
        this.m_phone_country_code = m_phone_country_code;
    }

    public String getM_phone() {
        return m_phone;
    }

    public void setM_phone(String m_phone) {
        this.m_phone = m_phone;
    }

    public String getM_address() {
        return m_address;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }
}
