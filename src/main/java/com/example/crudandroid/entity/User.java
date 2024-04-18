package com.example.crudandroid.entity;

public class User {
    private String document;
    private String names;
    private String lastnames;
    private String user;
    private String password;

    public User(String document, String names, String lastnames, String user, String password) {
        this.document = document;
        this.names = names;
        this.lastnames = lastnames;
        this.user = user;
        this.password = password;
    }

    public User() {
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario= " + user + ", " + "\r\n" +
                "NroDocumento= " + document + ", " + "\r\n" +
                "Nombres= " + names + ", " + "\r\n" +
                "Apellidos= " + lastnames + ", " + "\r\n" +
                "Contraseña= " + password;
    }
}
