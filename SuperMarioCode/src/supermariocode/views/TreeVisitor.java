
/* 
  Code Reimagined, Class for generating a tree from the AST
    Copyright (C) 2010-2013 José Javier Asensio Montiel

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
    
package supermariocode.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import supermariocode.painter.JavaMarioNode;

class TreeVisitor extends ASTVisitor {
      JavaMarioNode root;
      Stack stack;
      Map<Integer, ArrayList<JavaMarioNode>> hm = new HashMap<Integer, ArrayList<JavaMarioNode>>();
      
      public TreeVisitor(){
    	  super();    	
    	  root = new JavaMarioNode("ROOT", -1, -1);
    	  stack = new Stack();
    	  stack.push(root);
      }
            
      public void preVisit(ASTNode node) {         
    	 // write the name of the node being visited
    	 
    	 
    	 String name = node.getClass().getName();
         name = name.substring(name.lastIndexOf('.')+1);
         
         JavaMarioNode current = (JavaMarioNode) stack.peek();
         CompilationUnit cu = (CompilationUnit)node.getRoot();
<<<<<<< HEAD
         JavaMarioNode child =  new JavaMarioNode(name, node.getNodeType(), cu.getLineNumber(node.getStartPosition()));
         current.children.add(child);       
                   
         stack.push(child);                           
=======
         int codeLine = cu.getLineNumber(node.getStartPosition());
         JavaMarioNode child =  new JavaMarioNode(name, node.getNodeType(), codeLine);
         
         //System.out.println("Linea: " + codeLine + " nombre: " + name);
          
         current.children.add(child);    
         addToHashMap(codeLine, child);
         
         stack.push(child);      
>>>>>>> origin/origin/testing
      }
      
      public void addToHashMap(int codeLine, JavaMarioNode node){
    	  
    	  ArrayList<JavaMarioNode> nodes = hm.get(codeLine);
    	  if(nodes==null){
    		  nodes = new ArrayList<JavaMarioNode>();
    		  nodes.add(node);
    		  hm.put(codeLine, nodes);
    	  }else{
    		  nodes.add(node);
    		  
    		  
    	  }		  
    	  
      }
      
      public ArrayList<JavaMarioNode> getNodes (int codeLine){
    	  return hm.get(codeLine);
      }
      
      
      
      public void postVisit(ASTNode node) {
    	  stack.pop();
      }
      public String toString() {
    	  
    	  return root.toString();
      }
   }
  


