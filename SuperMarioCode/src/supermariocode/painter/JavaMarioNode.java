/*
 Code Reimagined, classes for representing the information extracted from the java AST
    Copyright (C) 2010-2013  José Javier Asensio Montiel

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    */

package supermariocode.painter;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Rectangle;

public class JavaMarioNode {
	String name;
	int nodeType;
	ArrayList<SpriteComposite> compList;
	int lineNumber;
	public Rectangle rectangle;
	public ArrayList<JavaMarioNode> children;
	
	public JavaMarioNode(String name, int nodeType, int _linenumber){
		this.name = name;
		this.nodeType = nodeType;
		this.compList = new ArrayList<SpriteComposite>();
		this.lineNumber = _linenumber;
		rectangle = new Rectangle(0, 0, 0, 0);
		children = new ArrayList<JavaMarioNode>();
	}

	public int getLineNumber(){
		return lineNumber;
	}
	
	public void setLineNumber(int currLine){
		this.lineNumber = currLine;
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
		String str = name + " [";
		for(JavaMarioNode mn: this.children) {
			str+= " " + mn.toString();
		}
		str += " ]";
		return str ;
	}
	
	//Devolvemos las posiciones del SpriteComposite:
	void calculateBounds(){
		//rectangle.x = 
		for(int i=0; i < compList.size(); i++){
			//componente x inicial:
			if( compList.get(i).x < rectangle.x) {
				rectangle.x = compList.get(i).x;	
			}	
			//componente y inicial:
			if( compList.get(i).y < rectangle.y) {
				rectangle.y = compList.get(i).y;	
			}
			//componente x final:
			if( (compList.get(i).x + compList.get(i).width)  > rectangle.width) {
				rectangle.width = compList.get(i).x + compList.get(i).width;	
			}	
			//componente y final:
			if( (compList.get(i).y + compList.get(i).height)  > rectangle.height) {
				rectangle.height = compList.get(i).y + compList.get(i).height;	
			}
		}
		

	}
	
}
