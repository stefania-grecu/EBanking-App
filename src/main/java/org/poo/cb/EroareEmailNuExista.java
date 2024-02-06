package org.poo.cb;

public class EroareEmailNuExista extends Throwable {
    public EroareEmailNuExista(String email) {
        System.out.println("User with " + email + " doesn't exist");
    }
}
