package models.GameParts;

import models.help.GameCopyContext;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Global;
import util.XmlUtil;
import static util.XmlUtil.asList;
import static util.XmlUtil.allSubNodesAsList;
import static util.XmlUtil.mySubNodesAsList;


@Entity
public class Condition extends Model {

    @Id
    private Long id;
    
    @Lob
    private String fulltext;
    private String trigger;
    private String varA;
    private String opt;
    private String varB;
    private String enclosure;
    private boolean is_type;
    private boolean is_full;
    
    
   
    
    
    public Condition(String t, String a, String o, String b, String en){
     
    	trigger = t;
    	varA = a;
    	opt = o;
    	varB = b;
    	enclosure = en;
    	is_type = false;
    	is_full = false;

    }

    
    public Condition(String t){
    	
    	trigger = t;
    	is_type = true;
    	
    }
    
    public Condition(boolean willbefull,String x){
    	
    	if(willbefull == true){
    		is_full = true;
    		fulltext = x;
    		
    	} else {
    		
    		is_type = true;
    		trigger = x;
    		
    	}
    	
    }
    
    
    
    // GETTER
   
    public boolean isFull(){ return is_full; }
    public boolean isType(){ return is_type; }
    public String getTrigger(){ return trigger; }
    public String getA(){ return varA; }
    public String getB(){ return varB; }
    public String getOperation(){ return opt; }
    public String getEnclosure(){ return enclosure; }

    public String getFull(){ 
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	return fulltext; 
    
    
    
    
    
    
    
    }
    
    
    
    public Element getXML(Document doc, Game g,boolean newform){ 
    	
    	
		Element the_if = doc.createElement("if");
		
		if(newform){
			
			the_if = doc.createElement("condition");
			
		}
		the_if.setNodeValue(fulltext);

    	
    	if(fulltext.contains("<eq>") || fulltext.contains("<lt>") || fulltext.contains("<leq>") || fulltext.contains("<geq>") || fulltext.contains("<gt>") || fulltext.contains("<missionState") || fulltext.contains("<NoRuleFiredYet")){
    		
    	 the_if.setNodeValue("");

			InputStream inputStream = new ByteArrayInputStream(fulltext.getBytes());
			DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
		    newInstance.setNamespaceAware(true);
		    
		    
		    
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = null;
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	 
		
		    Document parse = docBuilder.newDocument();
			try {
				parse = newInstance.newDocumentBuilder().parse(inputStream);
				
				the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
				the_if.setNodeValue("");
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		
    	} else {
    		
    		
    		
    	
    		
    		

    			Element help2 = doc.createElement("helpbrackets");
    			help2.setTextContent(fulltext);
    			the_if.appendChild(help2);
    			
    			
    		

        		
        		the_if = searchForBrackets(the_if,doc);
    		
    		

    		//// OR
    			
    			for(Node z: allSubNodesAsList(the_if)){
    				    				
    				
					if(z.getNodeName().equals("help")){
						
	    				System.out.println("[BRACKETS] concluding element: "+z.getNodeName()+">"+z.getTextContent());

if(z.getTextContent().matches(("\\s*"))){
	    					
	    					z.getParentNode().removeChild(z);
	    					
	    				} else {
    					
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					boolean doit = false;
    					if(z.getTextContent().contains("OR")){
    						doit = true;
    						String[] help = z.getTextContent().split("OR");
    						for(String x:help){full2.add(x);}
    					} else if(z.getTextContent().contains("||")){
    						doit = true;
    						String[] help = z.getTextContent().split("||");
    						for(String x:help){full2.add(x);}
    					}
    					
    					if(doit == true){
		    			Element and = doc.createElement("or");

    					for(String x:full2){
    		    			
    						Element help = doc.createElement("help");
    		    			help.setTextContent(x);
    		    			and.appendChild(help);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(and);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					
	    				}
    					
    					
    				}
    				
    			}
    		
    		
    		
    		//// AND
    			
    			for(Node z: allSubNodesAsList(the_if)){
    				
					if(z.getNodeName().equals("help")){
    					
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					boolean doit = false;
    					if(z.getTextContent().contains("AND")){
    						doit = true;
    						String[] help = z.getTextContent().split("AND");
    						for(String x:help){full2.add(x);}
    					} else if(z.getTextContent().contains("&&")){
    						doit = true;
    						String[] help = z.getTextContent().split("&&");
    						for(String x:help){full2.add(x);}
    					}
    					
    					if(doit == true){
		    			Element and = doc.createElement("and");

    					for(String x:full2){
    		    			
    						Element help = doc.createElement("help");
    		    			help.setTextContent(x);
    		    			and.appendChild(help);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(and);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					
    				}
    				
    			}
    			
    			
    		
    		
    		//// OPERATORS: =,!=,<,<=,>,>=
    			
    			
    			
    //// LEQ			
	for(Node z: allSubNodesAsList(the_if)){
    				
    			
    					if(z.getNodeName().equals("help")){
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					
    					if(z.getTextContent().matches("(.*)<=(.*)")){
    						String[] help = z.getTextContent().split("<=");
    						for(String x:help){full2.add(x);}
    					
    					
    					
    					
		    			Element y = doc.createElement("leq");

    					for(String x:full2){
    		    			
    						Element help3 = doc.createElement("help");
    		    			help3.setTextContent(x);
    		    			y.appendChild(help3);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(y);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					}
    				
    				
    			}
		
//// GEQ			
for(Node z: allSubNodesAsList(the_if)){
			
		
				if(z.getNodeName().equals("help")){
				
	    		ArrayList<String> full2 =  new ArrayList<String>();

				
				if(z.getTextContent().matches("(.*)>=(.*)")){
					String[] help = z.getTextContent().split(">=");
					for(String x:help){full2.add(x);}
				
				
				
				
    			Element y = doc.createElement("geq");

				for(String x:full2){
	    			
					Element help4 = doc.createElement("help");
	    			help4.setTextContent(x);
	    			y.appendChild(help4);
	    			
	    			
	    		}
				
				z.getParentNode().appendChild(y);
    			z.getParentNode().removeChild(z);
				
				}
				}
			
			
		}
	
////NOT EQ		
			
for(Node z: allSubNodesAsList(the_if)){
		
	
			if(z.getNodeName().equals("help")){
			
    		ArrayList<String> full2 =  new ArrayList<String>();

			
			if(z.getTextContent().matches("(.*)!=(.*)")){
				String[] help = z.getTextContent().split("!=");
				for(String x:help){full2.add(x);}
			
			
			
			
			Element y = doc.createElement("not");
			Element y2 = doc.createElement("eq");
			y.appendChild(y2);

			for(String x:full2){
    			
				Element help5 = doc.createElement("help");
    			help5.setTextContent(x);
    			y2.appendChild(help5);
    			
    			
    		}
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			}
			}
		
		
	}
	
	
////LT			
			
for(Node z: allSubNodesAsList(the_if)){
		
	
			if(z.getNodeName().equals("help")){
			
    		ArrayList<String> full2 =  new ArrayList<String>();

			
			if(z.getTextContent().matches("(.*)<(.*)")){
				String[] help = z.getTextContent().split("<");
				for(String x:help){full2.add(x);}
			
			
			
			
			Element y = doc.createElement("lt");

			for(String x:full2){
    			
				Element help6 = doc.createElement("help");
    			help6.setTextContent(x);
    			y.appendChild(help6);
    			
    			
    		}
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			
			}
			}
		
	}
    			

////GT			
		
for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
		ArrayList<String> full2 =  new ArrayList<String>();

		
		if(z.getTextContent().matches("(.*)>(.*)")){
			String[] help = z.getTextContent().split(">");
			for(String x:help){full2.add(x);}
		
		
		
		
		Element y = doc.createElement("gt");

		for(String x:full2){
			
			Element help7 = doc.createElement("help");
			help7.setTextContent(x);
			y.appendChild(help7);
			
			
		}
		
		z.getParentNode().appendChild(y);
		z.getParentNode().removeChild(z);
		
		
		}
	
		}
}
	
	
	
////EQ			
		

for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
			System.out.println("is help!");
		ArrayList<String> full2 =  new ArrayList<String>();

		
		if(z.getTextContent().matches("(.*)=(.*)")){
			System.out.println("has =");

			String[] help = z.getTextContent().split("=");
			for(String x:help){full2.add(x);}
		
		
		
		
		Element y = doc.createElement("eq");

		for(String x:full2){
			
			Element help8 = doc.createElement("help");
			help8.setTextContent(x);
			y.appendChild(help8);
			
			
		}
		
		z.getParentNode().appendChild(y);
		z.getParentNode().removeChild(z);
		
		}
		}
	
	
}
	
    			
    		
    		
    		
    		//// VARIABLES, STRINGS, NUMBERS, SYSTEM VARIABLES?
    		////ALL REMAINING NODE VALUES SHOULD BE VALUES	
	


// NOTHING?





	
// STRING
for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
		ArrayList<String> full2 =  new ArrayList<String>();


		
		
		// NOTHING?
		
		if(z.getTextContent().matches("(\\s*)")){

		
		
		/// STRING
		} else if(z.getTextContent().matches("(.*)\"(.*)\"(.*)")){
			String[] help = z.getTextContent().split("\"");
			
		
		

			String x = help[1];
			
				
				Element y = doc.createElement("string");

				y.setTextContent(x);
				
				z.getParentNode().appendChild(y);
				z.getParentNode().removeChild(z);
				
			
			
		
			
		
		
		} else if(z.getTextContent().matches(Global.REGEXP_NUM)){

			Element y = doc.createElement("num");

			y.setTextContent(z.getTextContent().trim());
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
		} else if(z.getTextContent().matches("(\\s*)(true|false|True|False|TRUE|FALSE)(\\s*)")){

			Element y = doc.createElement("bool");

			y.setTextContent(z.getTextContent());
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
		
		} else {
			
			//System.out.println(z.getTextContent());
			//System.out.println(z.getTextContent().matches("(\\s*)\\d+(\\s*)"));

			Element y = doc.createElement("var");

			y.setTextContent(z.getTextContent());
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			
		}
	
	
}


    		
    		
    		
    		
    		
    		
    		
    		
    		
}
}
    	
    	
    	
    	
    	
    	
    	
    	return the_if;
    
    
    
    
    
    
    
    }
    
    
    
    
    

    
    public Element getXMLforJSON(Document doc, boolean newform){ 
    	
    	
		Element the_if = doc.createElement("if");
		
		if(newform){
			
			the_if = doc.createElement("condition");
			
		}
		the_if.setNodeValue(fulltext);

    	
    	if(fulltext.contains("<eq>") || fulltext.contains("<lt>") || fulltext.contains("<leq>") || fulltext.contains("<geq>") || fulltext.contains("<gt>") || fulltext.contains("<missionState") || fulltext.contains("<NoRuleFiredYet")){
    		
    	 the_if.setNodeValue("");

			InputStream inputStream = new ByteArrayInputStream(fulltext.getBytes());
			DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
		    newInstance.setNamespaceAware(true);
		    
		    
		    
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = null;
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	 
		
		    Document parse = docBuilder.newDocument();
			try {
				parse = newInstance.newDocumentBuilder().parse(inputStream);
				
				the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
				the_if.setNodeValue("");
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		
    	} else {
    		
    		
    		
    	
    		
    		

    			Element help2 = doc.createElement("helpbrackets");
    			help2.setTextContent(fulltext);
    			the_if.appendChild(help2);
    			
    			
    		

        		
        		the_if = searchForBrackets(the_if,doc);
    		
    		

    		//// OR
    			
    			for(Node z: allSubNodesAsList(the_if)){
    				    				
    				
					if(z.getNodeName().equals("help")){
						
	    				System.out.println("[BRACKETS] concluding element: "+z.getNodeName()+">"+z.getTextContent());

if(z.getTextContent().matches(("\\s*"))){
	    					
	    					z.getParentNode().removeChild(z);
	    					
	    				} else {
    					
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					boolean doit = false;
    					if(z.getTextContent().contains("OR")){
    						doit = true;
    						String[] help = z.getTextContent().split("OR");
    						for(String x:help){full2.add(x);}
    					} else if(z.getTextContent().contains("||")){
    						doit = true;
    						String[] help = z.getTextContent().split("||");
    						for(String x:help){full2.add(x);}
    					}
    					
    					if(doit == true){
		    			Element and = doc.createElement("or");

    					for(String x:full2){
    		    			
    						Element help = doc.createElement("help");
    		    			help.setTextContent(x);
    		    			and.appendChild(help);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(and);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					
	    				}
    					
    					
    				}
    				
    			}
    		
    		
    		
    		//// AND
    			
    			for(Node z: allSubNodesAsList(the_if)){
    				
					if(z.getNodeName().equals("help")){
    					
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					boolean doit = false;
    					if(z.getTextContent().contains("AND")){
    						doit = true;
    						String[] help = z.getTextContent().split("AND");
    						for(String x:help){full2.add(x);}
    					} else if(z.getTextContent().contains("&&")){
    						doit = true;
    						String[] help = z.getTextContent().split("&&");
    						for(String x:help){full2.add(x);}
    					}
    					
    					if(doit == true){
		    			Element and = doc.createElement("and");

    					for(String x:full2){
    		    			
    						Element help = doc.createElement("help");
    		    			help.setTextContent(x);
    		    			and.appendChild(help);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(and);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					
    				}
    				
    			}
    			
    			
    		
    		
    		//// OPERATORS: =,!=,<,<=,>,>=
    			
    			
    			
    //// LEQ			
	for(Node z: allSubNodesAsList(the_if)){
    				
    			
    					if(z.getNodeName().equals("help")){
    					
    		    		ArrayList<String> full2 =  new ArrayList<String>();

    					
    					if(z.getTextContent().matches("(.*)<=(.*)")){
    						String[] help = z.getTextContent().split("<=");
    						for(String x:help){full2.add(x);}
    					
    					
    					
    					
		    			Element y = doc.createElement("leq");

    					for(String x:full2){
    		    			
    						Element help3 = doc.createElement("help");
    		    			help3.setTextContent(x);
    		    			y.appendChild(help3);
    		    			
    		    			
    		    		}
    					
    					z.getParentNode().appendChild(y);
		    			z.getParentNode().removeChild(z);
    					}
    					
    					}
    				
    				
    			}
		
//// GEQ			
for(Node z: allSubNodesAsList(the_if)){
			
		
				if(z.getNodeName().equals("help")){
				
	    		ArrayList<String> full2 =  new ArrayList<String>();

				
				if(z.getTextContent().matches("(.*)>=(.*)")){
					String[] help = z.getTextContent().split(">=");
					for(String x:help){full2.add(x);}
				
				
				
				
    			Element y = doc.createElement("geq");

				for(String x:full2){
	    			
					Element help4 = doc.createElement("help");
	    			help4.setTextContent(x);
	    			y.appendChild(help4);
	    			
	    			
	    		}
				
				z.getParentNode().appendChild(y);
    			z.getParentNode().removeChild(z);
				
				}
				}
			
			
		}
	
////NOT EQ		
			
for(Node z: allSubNodesAsList(the_if)){
		
	
			if(z.getNodeName().equals("help")){
			
    		ArrayList<String> full2 =  new ArrayList<String>();

			
			if(z.getTextContent().matches("(.*)!=(.*)")){
				String[] help = z.getTextContent().split("!=");
				for(String x:help){full2.add(x);}
			
			
			
			
			Element y = doc.createElement("not");
			Element y2 = doc.createElement("eq");
			y.appendChild(y2);

			for(String x:full2){
    			
				Element help5 = doc.createElement("help");
    			help5.setTextContent(x);
    			y2.appendChild(help5);
    			
    			
    		}
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			}
			}
		
		
	}
	
	
////LT			
			
for(Node z: allSubNodesAsList(the_if)){
		
	
			if(z.getNodeName().equals("help")){
			
    		ArrayList<String> full2 =  new ArrayList<String>();

			
			if(z.getTextContent().matches("(.*)<(.*)")){
				String[] help = z.getTextContent().split("<");
				for(String x:help){full2.add(x);}
			
			
			
			
			Element y = doc.createElement("lt");

			for(String x:full2){
    			
				Element help6 = doc.createElement("help");
    			help6.setTextContent(x);
    			y.appendChild(help6);
    			
    			
    		}
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			
			}
			}
		
	}
    			

////GT			
		
for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
		ArrayList<String> full2 =  new ArrayList<String>();

		
		if(z.getTextContent().matches("(.*)>(.*)")){
			String[] help = z.getTextContent().split(">");
			for(String x:help){full2.add(x);}
		
		
		
		
		Element y = doc.createElement("gt");

		for(String x:full2){
			
			Element help7 = doc.createElement("help");
			help7.setTextContent(x);
			y.appendChild(help7);
			
			
		}
		
		z.getParentNode().appendChild(y);
		z.getParentNode().removeChild(z);
		
		
		}
	
		}
}
	
	
	
////EQ			
		

for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
			System.out.println("is help!");
		ArrayList<String> full2 =  new ArrayList<String>();

		
		if(z.getTextContent().matches("(.*)=(.*)")){
			System.out.println("has =");

			String[] help = z.getTextContent().split("=");
			for(String x:help){full2.add(x);}
		
		
		
		
		Element y = doc.createElement("eq");

		for(String x:full2){
			
			Element help8 = doc.createElement("help");
			help8.setTextContent(x);
			y.appendChild(help8);
			
			
		}
		
		z.getParentNode().appendChild(y);
		z.getParentNode().removeChild(z);
		
		}
		}
	
	
}
	
    			
    		
    		
    		
    		//// VARIABLES, STRINGS, NUMBERS, SYSTEM VARIABLES?
    		////ALL REMAINING NODE VALUES SHOULD BE VALUES	
	


// NOTHING?





	
// STRING
for(Node z: allSubNodesAsList(the_if)){
	

		if(z.getNodeName().equals("help")){
		
		ArrayList<String> full2 =  new ArrayList<String>();


		
		
		// NOTHING?
		
		if(z.getTextContent().matches("(\\s*)")){

		
		
		/// STRING
		} else if(z.getTextContent().matches("(.*)\"(.*)\"(.*)")){
			String[] help = z.getTextContent().split("\"");
			
		
		

			String x = help[1];
			
				
				Element y = doc.createElement("string");

				y.setTextContent(x);
				
				z.getParentNode().appendChild(y);
				z.getParentNode().removeChild(z);
				
			
			
		
			
		
		
		} else if(z.getTextContent().matches(Global.REGEXP_NUM)){

			Element y = doc.createElement("num");

			y.setTextContent(z.getTextContent().trim());
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			
		
		} else {
			
			System.out.println(z.getTextContent());
			System.out.println(z.getTextContent().matches("(\\s*)\\d+(\\s*)"));

			Element y = doc.createElement("var");

			y.setTextContent(z.getTextContent());
			
			z.getParentNode().appendChild(y);
			z.getParentNode().removeChild(z);
			
			
		}
	
	
}


    		
    		
    		
    		
    		
    		
    		
    		
    		
}
}
    	
    	
    	
    	
    	
    	
    	
    	return the_if;
    
    
    
    
    
    
    
    }
    
    
    
    
    
    
    private Element searchForBrackets(Element the_element,Document doc) {
		
    	
    	
    	
    	for(Node z: allSubNodesAsList(the_element)){
			
			 if(z.getNodeName().equals("helpbrackets")){
				
				
	    		ArrayList<String> full2 =  new ArrayList<String>();

				boolean doit = false;
        		System.out.println("[BRACKETS] found text ("+z.getTextContent().length()+"): "+z.getTextContent());

				if(z.getTextContent().contains("(")){
					
					
					
					
					String fulltext = z.getTextContent();
					
					
					char[] text = fulltext.toCharArray();

					int pos = 0;
					int counter = 0;
					int startlookingat = 0;
					
					
					Element or = doc.createElement("or");
					Element and = doc.createElement("and");
					Element deepand = doc.createElement("and");
					Element notel = doc.createElement("not");
					boolean not = false;
					boolean relinkandor = false;
					
			        while (pos < fulltext.length()) {
			            char c = text[pos];

	            		System.out.println("[BRACKETS] looking at: "+pos);

	            		
	            		
	            		
	            		if(pos == fulltext.length()-1 && c!='('){
	            			
	            			char[] leftchars = Arrays.copyOfRange(text,startlookingat,fulltext.length());
	            			String lefttext = new String(leftchars);
	            			
	            			System.out.println("[BRACKETS] text left: "+lefttext);
			            	
			            	
			            	
			            	or = searchForOperator("OR",lefttext,or,doc);
			            	int orcount = countForOperator("OR",lefttext,or,doc);
			            	
			            	if(orcount > 0){
								for(Node z1: allSubNodesAsList(or)){
											            
									if(z1.getNodeName().equals("help")){
										
										 
										
										and = searchForOperator("AND",z1.getTextContent(),and,doc);
										
										
										
									}
									
									
									
									
									
								}
								
								
							
								
								
			            	} else {
			            		
			            		
			            		and = searchForOperator("AND",lefttext,and,doc);
			            		
			            		
			            	}
			            	
			            	
			            	
			            	
			            	
			            	if(or.hasChildNodes() && and.hasChildNodes()){
				        		
			            		
				        		and.appendChild(or);
				        		
				        		
				        	}
	            			
	            			
	            			
	            			
	            			
	            			
	            		}
	            		
	            		
			            
			            if(c == '('){
			            	
			            	if(pos != 0 && text[pos-1] == '!'){
			            		
			            		not = true;
			            		
			            	} else {
			            		
			            		not = false;
			            		
			            	}
			            	
		            		System.out.println("[BRACKETS] found starting bracket: "+pos);

			            	
			            	counter++;
			            	
			            	int startpos = pos;
			            	int endpos = findClosingParen(text,pos);
		            		System.out.println("[BRACKETS] found corresponding ending bracket: "+endpos);

			            	
			            	int difference = endpos-startpos;
		            		char[] subtext = Arrays.copyOfRange(text,startpos+1,endpos);
		            		String subfulltext = new String(subtext);
			            	
		            		
		            		
		            		
	            			System.out.println("[BRACKETS] creating sub-element: "+subfulltext);

		            		Element help1 = doc.createElement("helpbrackets");
		            		help1.setTextContent(subfulltext);
		            		
		            		if(not){
		            			
		            			Element help4 = help1;
		            			notel.appendChild(help4);
		            			
		            		}
			            	
		            		
		            		
			            	
		            		int beforestart = startpos;
		            		
		            		if(startpos == 0){
		            			
		            			beforestart = 0;
		            			
		            		} else {
		            			
		            			beforestart = startpos-1;
		            		}
		            		
		            		char[] beforechars = Arrays.copyOfRange(text,startlookingat,beforestart);

			            	String beforetext = new String(beforechars);
			            	

		            		char[] afterchars = new char[0];
		            		
		            		if(endpos+1 < text.length){
		            		afterchars = Arrays.copyOfRange(text,endpos+1,text.length);
		            		}
		            		
			            	String subfull = new String(afterchars);
			            	
		            		System.out.println("[BRACKETS] text after: "+subfull);

			            	String aftertext = deleteBracketContents(subfull);

			      
		            		System.out.println("[BRACKETS] text before: "+beforetext);
		            		System.out.println("[BRACKETS] text after: "+aftertext);
			            	
			            	boolean beforeisdeep = false;
			            	
		            		if(beforetext.contains("OR") || beforetext.contains("||")){
			            	or = searchForOperator("OR",beforetext,or,doc);
			            	
			            	
			            
			            	
			            	int countnodes = 1;
			            	int counthelps = 0;
			            	
			            	
			            	
			            	for(Node z1: allSubNodesAsList(or)){
					            
								if(z1.getNodeName().equals("help")){
									counthelps++;
									System.out.println(z1.getTextContent());
									
								}}
			            	
			            	
								for(Node z1: allSubNodesAsList(or)){
											            
									if(z1.getNodeName().equals("help")){
										
										 if(z1.getTextContent().contains("AND") || z1.getTextContent().contains("&&")){ 
										
											 System.out.println(countnodes+" =? "+ allSubNodesAsList(or).size());
											 
											 if(countnodes == counthelps){
												 
											 
											 beforeisdeep = true;
											 
											 System.out.println("[BRACKETS] Beforetext contains AND in deep OR");
											 
											 }
											 deepand = doc.createElement("and");
											 
											 deepand = searchForOperator("AND",z1.getTextContent(),deepand,doc);
										
											 or.appendChild(deepand);
											 
											 z1.getParentNode().removeChild(z1);
											 
										 }
										
										 countnodes++;
										 
									

										
									}
									
									
								}
								
			            	} else if(beforetext.contains("AND") || beforetext.contains("&&")){ 
			            		
			            		
			            		and = searchForOperator("AND",beforetext,and,doc);
			            		
			            		
			            	}
			            	
			            	
			            	
			            	

			            	
			            	
			            	
			            	/// WHAT CAME LAST?
			            	
			            	int indexofand = beforetext.lastIndexOf("AND");
			            	if(beforetext.lastIndexOf("&&")>indexofand){ indexofand = beforetext.lastIndexOf("&&");  }
			            	int indexofor = beforetext.lastIndexOf("OR");
			            	if(beforetext.lastIndexOf("||")>indexofor){ indexofor = beforetext.lastIndexOf("||");  }

			                
			            	
			            	
			            	/// WHAT WILL COME FIRST?

			            	int indexofand2 = aftertext.indexOf("AND");
			            	if(beforetext.indexOf("&&")>indexofand2){ indexofand2 = beforetext.indexOf("&&");  }
			            	int indexofor2 = aftertext.indexOf("OR");
			            	if(aftertext.indexOf("||")>indexofor2){ indexofor2 = aftertext.indexOf("||");  }

			            	
			            	
			            	int indexofand2check = indexofand2;
	            			int indexofor2check = indexofor2;
	            			if(indexofand2 == -1){ indexofand2check = 999999999; }
	            			if(indexofor2 == -1){ indexofor2check = 999999999; }
			            	
			            	
		            		System.out.println(indexofand+","+indexofand2+","+indexofor+","+indexofor2);

			            	
			            	/// 
			            	if(indexofand > 0 || indexofor > 0 || indexofand2 > 0 || indexofor2 > 0){
			            		
			            		
			            		System.out.println("[BRACKETS] found corresponding AND or OR");
			            		
			            		
			            		if(indexofand > 0 || indexofor > 0){
			            			// before was something
			            			
			            		if(indexofand > indexofor){
			            			/// before came AND
			            			
			            			
			            			
			            			
			            			
			            			if(beforeisdeep){
			            				
				            			System.out.println("[BRACKETS] found corresponding DEEP AND");

				            			// put it inside AND element
				            			
				            			
				            			if(not){
					            			notel =searchForBrackets(notel,doc);
				            				deepand.appendChild(notel);
					            			

				            			} else {
				            			deepand.appendChild(help1);
					            		deepand = searchForBrackets(deepand,doc);

				            			}
				            			
			            				
			            				
			            				
			            			} else {
			            			
			            			System.out.println("[BRACKETS] found corresponding AND");

			            			
			            			// put it inside AND element
			            			
			            			if(not){
				            			notel =searchForBrackets(notel,doc);
				            			and.appendChild(notel);
			            			} else {
			            			and.appendChild(help1);
			            			
			            			 
			            		and = searchForBrackets(and,doc);
			            			}
			            		
			            			}
			            			
			            		} else {
			            			
			            			
			            			
			            			/// before came OR
			           

			            			
			            			if(indexofand2 > 0 || indexofor2 > 0){
			            			// after came something	
			            				
			            				
			            				if(indexofand2check < indexofor2check){
			            					// and came before or
			            					System.out.println("[BRACKETS] found corresponding AND");

					            			
					            			// put it inside AND element
					            			
			            					
			            					if(not){
						            			notel =searchForBrackets(notel,doc);
						            			and.appendChild(notel);
			            					} else {
					            			and.appendChild(help1);
					            			
					            			
					            		and = searchForBrackets(and,doc);
			            					}
			            					
			            					
			            				} else {
			            					// or came before and
			            					// only OR remains
			            					System.out.println("[BRACKETS] found corresponding OR #1");

					            			
					            			// put it inside OR element
					            			
			            					if(not){
						            			notel =searchForBrackets(notel,doc);
						            			or.appendChild(notel);
			            					} else {
					            			or.appendChild(help1);
					            			
					            			
					            			
						            		or = searchForBrackets(or,doc);
			            					}
			            					
			            				}
			            				
			            				
			            				
			            				
			            				
			            			} else {
			            				// only OR remains
			            				
			            				System.out.println("[BRACKETS] found corresponding OR #2");

				            			
				            			// put it inside OR element
				            			
			            				if(not){
					            			notel =searchForBrackets(notel,doc);
					            			or.appendChild(notel);
			            				} else {
				            			or.appendChild(help1);
				            			
				            			
				            			
					            		or = searchForBrackets(or,doc);
			            				}
			            				
			            				
			            			}
			            			
			            			
			            			
			            			
			            		}
			            		
			            		
			            		} else {
			            			
			            			/// nothing was before
			            			
			            			
			            			if(indexofand2 > 0 || indexofor2 > 0){
				            			// after came something	
				            				
				            				
				            				if(indexofand2check < indexofor2check){
				            					// and came before or
				            					System.out.println("[BRACKETS] found corresponding AND");

						            			
						            			// put it inside AND element
						            			
				            					if(not){
							            			notel =searchForBrackets(notel,doc);
							            			and.appendChild(notel);
				            					} else {
						            			and.appendChild(help1);
						            			
						            			
						            		and = searchForBrackets(and,doc);
				            					}
				            					
				            					
				            					
				            					
				            				} else {
				            					// or came before and
				            					// only OR remains
				            					System.out.println("[BRACKETS] found corresponding OR #3");

						            			
						            			// put it inside OR element
						            			
				            					if(not){
							            			notel =searchForBrackets(notel,doc);
							            			or.appendChild(notel);
				            					} else {
						            			or.appendChild(help1);
						            			
						            			
						            			
							            		or = searchForBrackets(or,doc);
				            					}
				            					
				            				}
				            				
				            				
				            				
				            				
				            				
				            			} 
				            			
			            			
			            			
			            			
			            			
			            		}
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		
			            		z.getParentNode().removeChild(z);

			            		
			            		

				            	
				            	startlookingat = endpos+1;
				            	
				            	pos = endpos;
			            		
			            	} else {
			            		
			            		System.out.println("[BRACKETS] seems to be nothing in before and aftertext");

			            		
			            		// SUB ELEMENT DIRECTLY TO the_element
			            		

			            		z.getParentNode().appendChild(help1);
			            		z.getParentNode().removeChild(z);
			            		
			            		the_element = searchForBrackets(the_element,doc);

			            		
			            		pos = fulltext.length()+1;
			            		startlookingat = fulltext.length();
			            		
			            	}
			            	
			            	
			            	
			            	
			            
			            	
			            	
			            	
			            	
			            	
			            	
			            	
			            
			            	
			            	
			            	
			            	
			            	
			            	
			            }  else {
			            	
			            	
			            	pos++;
			            	
			            }
			            
			        }
					
					
					
			        
			        
					
					
			        
			        
			        
			        if(or.hasChildNodes()){
			        	
			        	
			        	
			        
			        		
			        		if(z.getParentNode() != null){
					        	z.getParentNode().appendChild(or);

			        			
			        		} else {
			        			System.out.println("[BRACKETS] Z HAS NO PARENT!!!!");
			        			the_element.appendChild(or);
			        			
			        		}
			        		
			        	
			        	
			        	
			        	
			        	
			        	
			        	if(and.hasChildNodes()){
			        		
			        		
			        		or.appendChild(and);
			        		
			        		
			        	}
			        	
			        	
			        } else {
			        	
			        	
			        	if(and.hasChildNodes()){
			        		
			        		if(z.getParentNode() != null){
					        	z.getParentNode().appendChild(and);

			        			
			        		} else {
			        			System.out.println("[BRACKETS] Z HAS NO PARENT!!!!");

			        			the_element.appendChild(and);
			        			
			        		}
			        		
			        		
			        	}
			        	
			        	
			        	
			        }
			        
			        
			        
			        
					
					
					
					
					
					
					
					
				} else {
					
					
					
					
		        	
					
					
				Element help3 = doc.createElement("help");
				help3.setTextContent(z.getTextContent());
				z.getParentNode().appendChild(help3);
				z.getParentNode().removeChild(z);
					
					
					
					
				}
				
				
				
			}
			
		}
    	
    	
    	
    	
    
    	
    	
    	
    	
    	
    	
    	
    	
		return the_element;
		
		
		
	}
    
    
    
    
    private int countForOperator(String search, String text, Element or,
			Document doc) {

    	
		
		ArrayList<String> full2 =  new ArrayList<String>();

		
		
		
		if(search.equals("AND")){
			
			
			

			
			if(text.contains("AND") || text.contains("&&")){
				
				String[] help = text.split("AND|\\&&");
				for(String x:help){full2.add(x);}
			}
			
			
			
			
		} else if(search.equals("OR")){
			
			

			
			if(text.contains("OR") || text.contains("||")){
				
				String[] help = text.split("OR|\\|\\|");
				for(String x:help){full2.add(x);
				
				
				}
			}
			
			
		} else {
			
		
		
		if(text.contains(search)){
		
			String[] help = text.split(search);
			for(String x:help){full2.add(x);}
		}
		
		
		}
		
	
		

	return full2.size();

    }


	private Object mySubNodesAsList(Element the_element) {
		// TODO Auto-generated method stub
		return null;
	}


	private String deleteBracketContents(String s) {
         String re = "\\([^()]*\\)";
         Pattern p = Pattern.compile(re);
         Matcher m = p.matcher(s);
         while (m.find()) {
             s = m.replaceAll("");
             m = p.matcher(s);
         }
        return s;
	}


	private Element searchForOperator(String search, String text, Element integrate,Document doc) {
		
    	
				
	    		ArrayList<String> full2 =  new ArrayList<String>();

				boolean doit = false;
				
				
				if(search.equals("AND")){
					
					
					

					
					if(text.contains("AND") || text.contains("&&")){
						doit = true;
						String[] help = text.split("AND|\\&&");
						for(String x:help){full2.add(x);}
					}
					
					
					
					
				} else if(search.equals("OR")){
					
					

					
					if(text.contains("OR") || text.contains("||")){
						doit = true;
						String[] help = text.split("OR|\\|\\|");
						for(String x:help){full2.add(x);
						
						
						}
					}
					
					
				} else {
					
				
				
				if(text.contains(search)){
					doit = true;
					String[] help = text.split(search);
					for(String x:help){full2.add(x);}
				}
				
				
				}
				
				if(doit == true){
    			

				for(String x:full2){
	    			
					Element help = doc.createElement("help");
	    			help.setTextContent(x);
	    			integrate.appendChild(help);
	    			
	    			
	    		}
				
				}
				
				
			return integrate;
    	
    	
    	
    	
	}
    
    
    
    



	public int findClosingParen(char[] text, int openPos) {
        int closePos = openPos;
        int counter = 1;
        while (counter > 0 && closePos < text.length) {
            char c = text[++closePos];
            if (c == '(') {
                counter++;
            }
            else if (c == ')') {
                counter--;
            }
        }
        return closePos;
    }
    
    
    
    
    
    
    

   


	public Long getId(){
        return id;
    }

   



    public static final Finder<Long, Condition> find = new Finder<Long, Condition>(
            Long.class, Condition.class);


	public Condition copyMe(GameCopyContext copyContext) {
		
		Condition c;
		
		if(is_full){
			
			
			c = new Condition(true,fulltext);
			
		} else {
		if(!is_type){
			
			c = new Condition(trigger, varA, opt,varB, enclosure);
		
		} else {
			
			c = new Condition(trigger);
			
		}
		}
		c.save();
		return c;
	
	}


	public void setFull(String text) {
		fulltext = text;
		
	}




}
