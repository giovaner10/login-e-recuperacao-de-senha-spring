package br.com.cwi.crescer.usuarios.factories.login;

import java.util.Random;

public class SimpleFactory {

    public static Long getRandomLong() {
        return new Random().nextLong();
    }

    public static String getEmail() {
        return "teste@cwi.com.br";
    }


}
