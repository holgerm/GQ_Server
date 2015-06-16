package models.GameParts;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import controllers.Application;
import flexjson.JSON;
import models.Game;
import models.NewsstreamItem;
import models.TemplateParameter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



@Entity
public class MenuItem extends Model {

    @Id
    private Long id;
    
    private String title;
    
    private String show;
    
    @OneToOne
    private  Rule onSelect;
    
    private boolean activity;
    
    private int priority;
    
    private boolean showText;
    
    private String icon;
    
    
    public MenuItem(String n){

    	setTitle(n);
    	show = "if_room";
    	activity = true;
    	priority = 1;
    	showText = true;
    	icon = "";
    	onSelect = new Rule();
    	onSelect.save();

    }

    @JSON(include=true)
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@JSON(include=true)
	public String getShow() {
		return show;
	}


	public void setShow(String show) {
		this.show = show;
	}

	@JSON(include=true)
	public boolean getActivity() {
		return activity;
	}


	public void setActivity(boolean activity) {
		this.activity = activity;
	}


	@JSON(include=true)
	public boolean getShowText() {
		return showText;
	}


	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	@JSON(include=true)
	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JSON(include=true)
	public Long getId() {
		return id;
	}

	@JSON(include=true)
	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}



    public static final Finder<Long, MenuItem> find = new Finder<Long, MenuItem>(
            Long.class, MenuItem.class);

    @JSON(include=true)
	public Rule getOnSelect() {
		return onSelect;
	}


	public void setOnSelect(Rule onSelect) {
		this.onSelect = onSelect;
	}


	public Element createXML(Document doc, Game game, ZipOutputStream zout) {

		
		

		
		
		Element menuitem = doc.createElement("menuItem");
		
		
		
		
		/// MISSION ATTRIBUTES
		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		menuitem.setAttributeNode(attr);
		
		
		Attr attr2 = doc.createAttribute("title");
		attr2.setValue(title);
		menuitem.setAttributeNode(attr2);
		

		Attr attr3 = doc.createAttribute("show");
		attr3.setValue(show);
		menuitem.setAttributeNode(attr3);
		
		

		Attr attr4 = doc.createAttribute("priority");
		attr4.setValue(String.valueOf(priority));
		menuitem.setAttributeNode(attr4);
		
		

		Attr attr5 = doc.createAttribute("activity");
		if(activity){
		attr5.setValue("1");
		} else {
			
			attr5.setValue("0");
		}
		menuitem.setAttributeNode(attr5);
		


		Attr attr6 = doc.createAttribute("showText");
		if(showText){
		attr6.setValue("1");
		} else {
			
			attr6.setValue("0");
		}
		menuitem.setAttributeNode(attr6);
		
		

		Attr attr7 = doc.createAttribute("icon");
		attr7.setValue(icon);
		menuitem.setAttributeNode(attr7);
		
		
		
		
		
		URL url;
		try {
			url = new URL(icon);
		
			
			String path = icon;
			String[] splitResult = path.split("/");
			
			path = splitResult[splitResult.length-1];
		
		File file = new File("public/uploads/"+ Application.getLocalPortal().getId() + "/editor/"+game.getId()+"/files",path);
		file.deleteOnExit();
	
			FileUtils.copyURLToFile(url, file);
			
			
			attr7.setValue("files/"+path);
			
			
			
			
			
			
			

			  final int BUFFER = 2048;
			   
			    byte data[] = new byte[BUFFER];
			    
			FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis,BUFFER);
            
            
            ZipEntry ze = new ZipEntry("files/"+path);
    	    zout.putNextEntry(ze);
            
            int size = -1 ;
            while(( size = bis.read(data,0,BUFFER)) != -1)
            {
                zout.write(data, 0, size);
            }
			
			
    	    zout.closeEntry();
    
			
			
			
			
		
		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		

		
		
		
		Element rule = onSelect.createXML(doc,0,this,game,zout);
		if(rule != null){
		menuitem.appendChild(rule);
		}
		
		
		
		
		
		
		
		return menuitem;
		
		
	}




}
