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

package progamer.drawing.nodes;


import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import progamer.drawing.SpriteComposite;
import progamer.drawing.SpriteProvider;


public class JavaMarioNode {
	public String name;
	public int nodeType;
	public ArrayList<SpriteComposite> compList;
	int offset;
	public Rectangle rectangle;
	public ArrayList<JavaMarioNode> children;
	
	public JavaMarioNode(String name, int nodeType, int offset){
		this.name = name;
		this.nodeType = nodeType;
		this.compList = new ArrayList<SpriteComposite>();
		this.offset = offset;
		rectangle = new Rectangle(0, 0, 0, 0);
		children = new ArrayList<JavaMarioNode>();
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
		return name;
	}
	
	public String toLongString(){
		
		String str = name + " [";
		for(JavaMarioNode mn: this.children) {
			str+= " " + mn.toString();
		}
		str += " ]";
		return str ;		
	}
	
	
	public Rectangle getSprites(int x, int y) {

		rectangle.x = x;
		rectangle.y = y;
		
		for (JavaMarioNode child: children) {
			rectangle = SpriteProvider.getUnionBox(rectangle, child.getSprites(x, y) );
		}
		return rectangle;		
	}
	
	
	void calculateBounds(){
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
	
	public JavaMarioNode getFirstChildOfType(int type) {
		
		for (JavaMarioNode child : children) {
			if (child.getNodeType() == type) {
				return child;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param p: Point
	 * @return : The corresponding line of code for that element. 
	 */
	public int  getOffset(Point p) {
		
		for (JavaMarioNode child: children) {
				if (child.rectangle.contains(p)) {
					return child.getOffset(p);
				}
		}			

		if (rectangle.contains(p)) {
			return offset;
		}
		else 
			return -1;
	}
	
}
