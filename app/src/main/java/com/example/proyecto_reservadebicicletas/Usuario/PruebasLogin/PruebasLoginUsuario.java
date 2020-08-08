package com.example.proyecto_reservadebicicletas.Usuario.PruebasLogin;

public class PruebasLoginUsuario {
    private final String login;
    private final String pass;
    public PruebasLoginUsuario(String login, String pass){
        this.login = login;
        this.pass = pass;
    }
    public String getLogin(){
        return login;
    }
    public String getPass(){
        if(pass.length() < 5){
            String s = "###";
            return "###";
        }
        return pass;
    }
}
