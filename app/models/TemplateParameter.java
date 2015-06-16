package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created with IntelliJ IDEA.
 * User: kevinglaap
 * Date: 03.07.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class TemplateParameter extends Model {

    @Id
    private Long id;

    @Lob
    private String name;
     @Lob
    public String value;



    public TemplateParameter(String a, String b){
        name = a;
        value = b;

    }


    public String getValue(){

        return value;
    }


    public String getName(){

        return name;
    }


}
