package org.poo.cb;

public class EroarePrietenExistent extends Throwable {
    public EroarePrietenExistent(String email) {
        System.out.println("User with " + email + " is already a friend");
    }
}
