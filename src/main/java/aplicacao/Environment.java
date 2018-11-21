package main.java.aplicacao;

import java.util.Locale;
import java.util.Map;

public class Environment {

    public static Environment instance;
    private static String CRIPTO_KEY;
    private static String LOGIN_KEY;
    private static String PASSWORD_KEY;

    public static Environment getInstance() {
        if(instance==null){
            instance = new Environment();
        }
        return instance;
    }

    public Environment(){
        CRIPTO_KEY = System.getenv().get("CriptoKey");
        LOGIN_KEY = System.getenv().get("LoginDbKey");
        PASSWORD_KEY = System.getenv().get("PasswordDbKey");
    }

    // if in debug mode the system methods are going to return null
    public String getCriptoKey(){
        if(CRIPTO_KEY == null) {
            return "marflksdomnhtklq";
        }
        System.out.println("GetCripto: "+CRIPTO_KEY);
        return CRIPTO_KEY;
    }

    public String getLoginDbKey(){
        if(LOGIN_KEY == null) {
            return "postgres";
        }
        System.out.println("GetLogin: "+LOGIN_KEY);
        return LOGIN_KEY;
    }

    public String getPasswordDbKey(){
        if(PASSWORD_KEY == null) {
            return "admin";
        }
        System.out.println("GetPass: "+PASSWORD_KEY);
        return PASSWORD_KEY;
    }

}
