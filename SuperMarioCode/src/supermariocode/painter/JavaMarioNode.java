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
