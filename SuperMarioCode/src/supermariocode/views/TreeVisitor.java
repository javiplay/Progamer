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
  


