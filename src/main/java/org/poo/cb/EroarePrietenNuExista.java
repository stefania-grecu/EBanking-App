package org.poo.cb;

public class EroarePrietenNuExista extends Throwable {
    public EroarePrietenNuExista(String email) {
        System.out.println("You are not allowed to transfer money to " + email);
    }
}
