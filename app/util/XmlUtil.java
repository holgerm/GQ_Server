package util;

import java.util.*;
import org.w3c.dom.*;

public final class XmlUtil {
  private XmlUtil(){}

  public static List<Node> asList(NodeList n) {
    return n.getLength()==0?
      Collections.<Node>emptyList(): new NodeListWrapper(n);
  }
  
  public static List<Node> allSubNodesAsList(Node i){
	  
	  List<Node> all = new ArrayList<Node>();
	  for(Node x:asList(i.getChildNodes())){
		  
		 all.add(x);
		  all.addAll(allSubNodesAsList(x));
		  
		  
	  }
	  
	  
	  return all;
  }
  
  
  
  public static List<Node> mySubNodesAsList(Node i){
	  
	  List<Node> all = new ArrayList<Node>();
	  for(Node x:asList(i.getChildNodes())){
		  
		 all.add(x);
		
		  
		  
	  }
	  
	  
	  return all;
  }
  
  static final class NodeListWrapper extends AbstractList<Node>
  implements RandomAccess {
    private final NodeList list;
    NodeListWrapper(NodeList l) {
      list=l;
    }
    public Node get(int index) {
      return list.item(index);
    }
    public int size() {
      return list.getLength();
    }
  }
}