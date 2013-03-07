
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
import java.util.Stack;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import supermariocode.painter.JavaMarioNode;

class TreeVisitor extends ASTVisitor {
      ArrayList tree;
      Stack stack;
      
      
      public TreeVisitor(){
    	  super();
    	  tree = new ArrayList();
    	  stack = new Stack();
    	  stack.push(tree);
      }
            
      public void preVisit(ASTNode node) {         
    	 // write the name of the node being visited
    	  

    	 String name = node.getClass().getName();
         name = name.substring(name.lastIndexOf('.')+1);
         
         ArrayList current = (ArrayList) stack.peek();
         JavaMarioNode marioNode = new JavaMarioNode(name, node.getNodeType());       
         current.add(marioNode);
                  
         ArrayList children = new ArrayList();
         current.add(children);
         stack.push(children);                           
      }
      
      public void postVisit(ASTNode node) {
    	  stack.pop();
      }
      public String toString() {
    	  return tree.toString();
      }
   }
  


