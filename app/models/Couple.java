package models;


/**
 * Created with IntelliJ IDEA.
 * User: kevinglaap
 * Date: 03.07.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */


public class Couple  {

    private String wort;
    private Integer zahl;


    public Couple(String s, Integer i){
        wort = s;
        zahl = i;

    }


    public Integer getZahl(){

        return zahl;
    }


    public String getWort(){

        return wort;
    }


}
