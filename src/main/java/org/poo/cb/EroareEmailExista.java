package org.poo.cb;

public class EroareEmailExista extends Throwable{
    public EroareEmailExista(String email) {
        System.out.println("User with " + email + " already exists");
    }
}

