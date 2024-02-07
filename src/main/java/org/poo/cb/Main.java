package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
            CoadaComenzi coada = new CoadaComenzi();

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

            //citire din fisier
            String fileStockValues = "src/main/resources/" + args[1];
            String[] x = new String[11];
            ArrayList<Actiuni> actiuni = new ArrayList<>();

            try (BufferedReader b = new BufferedReader(new FileReader(fileStockValues))) {
                String l;
                //citim prima linie pentru a o elimina;
                l = b.readLine();
                while ((l = b.readLine()) != null) {
                    x = l.split(",");

                    Actiuni a = new Actiuni(x[0], 0);
                    for(int j = 1; j <= 10; j++)
                        a.valori.add(Double.parseDouble(x[j]));

                    actiuni.add(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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

                        ArrayList<Actiuni> act = new ArrayList<>();
                        Utilizator u = new Utilizator(comanda[2], comanda[3], comanda[4], adresa, act);
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
                        assert prieten != null;
                        prieten.prieteni.add(util);

                    } else if (comanda[0].equals("ADD") && comanda[1].equals("ACCOUNT")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        Comanda c = new AdaugaContComanda(u,comanda[3]);
                        coada.adaugaCoada(c);

                    } else if (comanda[0].equals("ADD") && comanda[1].equals("MONEY")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        double suma = Double.parseDouble(comanda[4]);

                        Comanda c = new AdaugaBaniComanda(u, comanda[3], (float) suma);
                        coada.adaugaCoada(c);

                    } else if (comanda[0].equals("EXCHANGE") && comanda[1].equals("MONEY")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        double suma = Double.parseDouble(comanda[5]);

                        Comanda c = new AdaugaSchimbValutar(u, comanda[3], comanda[4], suma, valoare);
                        coada.adaugaCoada(c);

                    } else if (comanda[0].equals("TRANSFER") && comanda[1].equals("MONEY")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);
                        Utilizator p = cautaUtilizatorul(u.prieteni, comanda[3]);

                        if (p == null)
                            throw new EroarePrietenNuExista(comanda[3]);

                        double suma = Double.parseDouble(comanda[5]);

                        Comanda c = new AdaugaTransferBani(u, p, comanda[4], suma);
                        coada.adaugaCoada(c);

                    } else if (comanda[0].equals("BUY") && comanda[1].equals("STOCKS")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        for (Actiuni j : actiuni)
                            if (j.numeCompanie.equals(comanda[3])) {
                                double suma = j.valori.get(9);

                                Comanda c = new AdaugaCumparaActiuni(u, suma, Integer.parseInt(comanda[4]), comanda[3]);
                                coada.adaugaCoada(c);
                            }

                    } else if (comanda[0].equals("RECOMMEND") && comanda[1].equals("STOCKS")) {
                        System.out.print("{\"stocksToBuy\":[");
                        double m1, m2;
                        int k = 0, index = 0;

                        //pentru prima comparie pe care o afisam
                        while(k == 0) {
                            m1 = 0;
                            m2 = 0;

                            for (int r = 5; r < 10; r++)
                                m1 = m1 + actiuni.get(index).valori.get(r);
                            m1 = m1 / 5;

                            for (int r = 0; r < 10; r++)
                                m2 = m2 + actiuni.get(index).valori.get(r);
                            m2 = m2 / 10;

                            if (m1 > m2) {
                                System.out.print("\"" + actiuni.get(index).getNumeCompanie() + "\"");
                                k = 1;
                            }
                            index++;
                        }

                        for (k = index; k < actiuni.size(); k ++) {
                            m1 = 0;
                            m2 = 0;
                            for (int r = 5; r < 10; r++)
                                m1 = m1 + actiuni.get(k).valori.get(r);
                            m1 = m1 / 5;
                            //System.out.println(m1);
                            for (int r = 0; r < 10; r++)
                                m2 = m2 + actiuni.get(k).valori.get(r);
                            m2 = m2 / 10;

                            if (m1 > m2)
                                System.out.print(",\"" + actiuni.get(k).getNumeCompanie() + "\"");
                        }
                        System.out.println("]}");

                    }
                    else if (comanda[0].equals("LIST") && comanda[1].equals("USER")) {
                        int k = 0;

                        for (Utilizator u : utilizatori)
                            if (u.email.equals(comanda[2])) {
                                k = 1;
                                System.out.print("{\"email\":\"" + u.email + "\",\"firstname\":\"" + u.nume + "\",\"lastname\":\"" + u.prenume + "\",\"address\":\"" + u.adresa + "\",\"friends\":[");
                                if (!u.prieteni.isEmpty()) {
                                    i = 0;
                                    System.out.print("\"" + u.prieteni.get(i).email + "\"");
                                    for (i = 1; i < u.prieteni.size(); i++)
                                        System.out.print(",\"" + u.prieteni.get(i).email + "\"");
                                }
                                System.out.println("]}");
                            }

                        if (k == 0)
                            throw new EroareEmailNuExista(comanda[2]);

                    } else if (comanda[0].equals("LIST") && comanda[1].equals("PORTFOLIO")) {
                        Utilizator u = cautaUtilizatorul(utilizatori, comanda[2]);

                        DecimalFormat numar = new DecimalFormat("#0.00");


                        System.out.print("{\"stocks\":[");

                        Iterator<Actiuni> iterator = u.actiuni.getIterator();

                        if (iterator.hasNext()) {
                            Actiuni a = iterator.next();
                            System.out.print("{\"stockName\":\"" + a.getNumeCompanie() + "\",\"amount\":" + a.getNrActiuni() + "}");

                            while (iterator.hasNext()) {
                                a = iterator.next();
                                System.out.print(",{\"stockName\":\"" + a.getNumeCompanie() + "\",\"amount\":" + a.getNrActiuni() + "}");
                            }
                        }

                        System.out.print("],\"accounts\":[");
                        if (!u.cont.isEmpty()) {
                            i = 0;
                            String tipCont = u.cont.get(0).getClass().getSimpleName().substring(0,3);
                            System.out.print("{\"currencyName\":\"" + tipCont + "\",\"amount\":\"" + numar.format(u.cont.get(0).getSuma()) + "\"}");

                            for (i = 1; i < u.cont.size(); i++) {
                                tipCont = u.cont.get(i).getClass().getSimpleName().substring(0,3);
                                System.out.print(",{\"currencyName\":\"" + tipCont + "\",\"amount\":\"" + numar.format(u.cont.get(i).getSuma()) + "\"}");
                            }
                        }
                        System.out.println("]}");
                    }
                    coada.scosCoada();
                }
            } catch (IOException | EroareEmailExista e) {
                e.printStackTrace();
            } catch (EroarePrietenNuExista | EroarePrietenExistent | EroareContExistent | EroareEmailNuExista e) {
                System.out.println(e);
            }

        }
    }
}