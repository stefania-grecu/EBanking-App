package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static Utilizator cautaUtilizatorul (ArrayList<Utilizator> utilizatori, String email) {
        for (Utilizator u : utilizatori)
            if (u.email.equals(email))
                return u;
        return null;
    }
    public static void main(String[] args){
        if(args == null) {
            System.out.println("Running Main");
        } else {
            ArrayList<Utilizator> utilizatori = new ArrayList<>();

            String fileExchangeRates = "src/main/resources/" + args[0];
            String[][] valoare = new String[6][6];
            int i = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(fileExchangeRates))) {
                String line;
                while ((line = br.readLine()) != null) {
                    valoare[i] = line.split(",");
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String fileStockValues = "src/main/resources/" + args[1];
            //String[][] actData = new String[][];
            i = 0;

//            try (BufferedReader br = new BufferedReader(new FileReader(fileStockValues))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    valoare[i] = line.split(",");
//                    for(int x = 0; x < 6; x++)
//                        System.out.print(valoare[i][x] + " ");
//                    i++;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            String fileCommans = "src/main/resources/" + args[2];
            String[] comanda = new String[100];

            try (BufferedReader br = new BufferedReader(new FileReader(fileCommans))) {
                String line;
                while ((line = br.readLine()) != null) {
                    comanda = line.split(" ");

                    if (comanda[0].equals("CREATE") && comanda[1].equals("USER")) {
                        String adresa = "";
                        for (int j = 5; j < comanda.length - 1; j++) {
                            adresa = adresa + comanda[j] + " ";
                        }
                        adresa = adresa + comanda[comanda.length - 1];

                        Utilizator util = cautaUtilizatorul(utilizatori, comanda[2]);
                        if (util != null)
                            throw new EroareEmailExista(comanda[2]);

                        Utilizator u = new Utilizator(comanda[2], comanda[3], comanda[4], adresa);
                        utilizatori.add(u);

                    } else if (comanda[0].equals("ADD") && comanda[1].equals("FRIEND")) {
                        Utilizator util = cautaUtilizatorul(utilizatori, comanda[3]);
                        if (util == null)
                            throw new EroareEmailNuExista(comanda[3]);

                        util = cautaUtilizatorul(utilizatori, comanda[2]);
                        if (util == null)
                            throw new EroareEmailNuExista(comanda[2]);

                        Utilizator prieten = cautaUtilizatorul(util.prieteni, comanda[3]);
                        if (prieten != null)
                            throw new EroarePrietenExistent(comanda[3]);

                        //adaugare prieten
                        prieten = cautaUtilizatorul(utilizatori, comanda[3]);
                        util.prieteni.add(prieten);
                        prieten.prieteni.add(util);

                    } else if (comanda[0].equals("ADD") && comanda[1].equals("ACCOUNT")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        u.adaugareCont(comanda[3]);

                    } else if (comanda[0].equals("ADD") && comanda[1].equals("MONEY")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        double suma = Double.parseDouble(comanda[4]);

                        u.adaugareBani(comanda[3], (float) suma);

                    } else if (comanda[0].equals("EXCHANGE") && comanda[1].equals("MONEY")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        double suma = Double.parseDouble(comanda[5]);

                        u.adaugareBani(comanda[3], (float) suma);

                    }
                    else if (comanda[0].equals("LIST") && comanda[1].equals("USER")) {
                        for (Utilizator u : utilizatori)
                            if (u.email.equals(comanda[2])) {
                                System.out.print("{\"email\":\"" + u.email + "\",\"firstname\":\"" + u.nume + "\",\"lastname\":\"" + u.prenume + "\",\"address\":\"" + u.adresa + "\",\"friends\":[");
                                if (!u.prieteni.isEmpty()) {
                                    i = 0;
                                    System.out.print("\"" + u.prieteni.get(i).email + "\"");
                                    for (i = 1; i < u.prieteni.size(); i++)
                                        System.out.print(",\"" + u.prieteni.get(i).email + "\"");
                                }
                                System.out.println("]}");
                            }
                    } else if (comanda[0].equals("LIST") && comanda[1].equals("PORTFOLIO")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        System.out.print("{\"stocks\":[],");
                        System.out.print("\"accounts\":[");
                        if (!u.cont.isEmpty()) {
                            i = 0;
                            String tipCont = u.cont.get(0).getClass().getSimpleName().substring(0,3);
                            System.out.print("{\"currencyName\":\"" + tipCont + "\",\"amount\":\"" + u.cont.get(0).getSuma() + "\"}");

                            for (i = 1; i < u.cont.size(); i++) {
                                tipCont = u.cont.get(i).getClass().getSimpleName().substring(0,3);
                                System.out.print(",{\"currencyName\":\"" + tipCont + "\",\"amount\":\"" + u.cont.get(i).getSuma() + "\"}");
                            }
                        }
                        System.out.println("]}");

                    }
                }
            } catch (IOException | EroareEmailExista e) {
                e.printStackTrace();
            } catch (EroareEmailNuExista e) {
                throw new RuntimeException(e);
            } catch (EroarePrietenExistent e) {
                throw new RuntimeException(e);
            } catch (EroareContExistent e) {
                throw new RuntimeException(e);
            }

        }
    }
}