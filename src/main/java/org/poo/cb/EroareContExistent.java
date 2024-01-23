package org.poo.cb;

public class EroareContExistent extends Throwable {
    public EroareContExistent(String tipValuta) {
        System.out.println("User with " + tipValuta + " is already a friend");
    }
}
