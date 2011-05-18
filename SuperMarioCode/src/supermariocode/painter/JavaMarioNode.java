package supermariocode.painter;

import java.util.ArrayList;

public class JavaMarioNode {
	String name;
	int nodeType;
	ArrayList<SpriteComposite> compList;
	public JavaMarioNode(String name, int nodeType){
		this.name = name;
		this.nodeType = nodeType;
		this.compList = new ArrayList<SpriteComposite>();
	}
	public int getNodeType() {
		return nodeType;
	}
	public void addComposite(SpriteComposite comp) {
		this.compList.add(comp);
		
	}
	public void addComposite(int pos, SpriteComposite comp) {
		this.compList.add(pos, comp);
		
	}
	public String toString(){
		//return "JavaMarioNode: "+ name+"\n"+ "CompositeList: "+ compList.toString()+"\n";
		return name;
	}
}
