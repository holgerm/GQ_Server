package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "newsstreamitems")
public class NewsstreamItem extends Model {

    @Id
    private Long id;


    @Lob
    private String title;

    @Lob
    private String text;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datum;

    private String visibility;

    private String posterclass;

    private Long posterid;

    private Long repost;




    public NewsstreamItem(String t,String c, String visible, String classy, Long id){

        title = t;

        text = c;

        visibility = visible;

        datum = new Date();

        posterclass = classy;

        posterid = id;

        repost= 0L;


    }

    public NewsstreamItem(NewsstreamItem n){

        this.title = n.getTitle();
        this.text = n.getText();
        this.datum = n.getDatum();
        this.visibility = n.getVisibility();
        this.posterclass = n.getPosterClass();
        this.posterid = n.getPosterid();
        this.repost = n.getId();

    }


    public Long getId(){
        return id;
    }

    public String getTitle(){ return title; }

    public String getText(){ return text; }

    public Date getDatum(){ return datum; }

    public String getVisibility(){ return visibility; }

    public String getPosterClass(){
        return posterclass;

        }

    public Long getPosterid(){

        return posterid;
    }





    public static final Finder<Long, NewsstreamItem> find = new Finder<Long, NewsstreamItem>(
            Long.class, NewsstreamItem.class);




}
