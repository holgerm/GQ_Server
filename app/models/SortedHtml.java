package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;



@Entity
public class SortedHtml extends Model {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    private Long id;


    @Lob
    private String wort;

    private Integer zahl;


   @ManyToMany
   @Column(name="htmlparameter")
   private Set<TemplateParameter> parameterlist;


    public SortedHtml(String s, Integer i, String parameters){
        wort = s;
        zahl = i;
       computeParameters(parameters);


    }


    public void computeParameters(String parameters){

        String search = parameters;



        String[] split = search.split("%");

        for(String astring: split){


            if(astring.contains(":=")){


            String[] newstrings = astring.split(":=");

                String nametoadd = newstrings[0].replaceAll(" ","");
                       nametoadd = nametoadd.replaceAll("\n", "");

                String valueadd = newstrings[1].split(";;")[0];

                if(valueadd.contains("!!\"")){

                    valueadd = valueadd.split("!!\"")[1];

                    valueadd = valueadd.split("\"!!")[0];


                } else {


                	
                	  if(valueadd.contains("!!&#8221;")){

                          valueadd = valueadd.split("!!&#8221;")[1];

                          valueadd = valueadd.split("&#8221;!!")[0];


                      } else {
                	
                	
                	
                    valueadd = valueadd.replaceAll(" ","");
                    valueadd = valueadd.replaceAll("\n", "");

                      }

                }



                TemplateParameter padd = new TemplateParameter(nametoadd,valueadd);


                padd.save();

                parameterlist.add(padd);




            }

        }







    }

    public Integer getZahl(){

        return zahl;
    }


    public String getWort(){


        if(wort.contains("%%_GEOQUEST_HEADER_FUNCTIONS_%%")){ return "%%_GEOQUEST_HEADER_FUNCTIONS_%%"; } else {
        return wort;
        }
    }


    public Long getId(){
        return id;
    }

    public void removeMe(){

        Set<TemplateParameter> x = new HashSet<TemplateParameter>();
        x.addAll(parameterlist);

        for(TemplateParameter atp: x){

            if(parameterlist.contains(atp)){

                parameterlist.remove(atp);
                this.update();
                atp.delete();

            }

        }


    }

    public Set<TemplateParameter> getParameters(){

        return parameterlist;

    }






    public static final Finder<Long, SortedHtml> find = new Finder<Long, SortedHtml>(
            Long.class, SortedHtml.class);




}
