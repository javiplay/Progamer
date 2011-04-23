package supermariocode.views;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

class MethodVisitor extends ASTVisitor {
      StringBuffer buffer = new StringBuffer();
      int i = 0;
      public void preVisit(ASTNode node) {
         
    	 //write the name of the node being visited         
         String name = node.getClass().getName();   
         // separate objects for JSON
         if ((buffer.length() > 0) && (buffer.charAt(buffer.length()-1) == '}')) {
        	 buffer.append(",");         
         }
         // get only the last class name
         name = name.substring(name.lastIndexOf('.')+1);
         // put in the form  ... "name": {
         buffer.append("\""+name+i+"\": {");
         i++;
         
      }
      public void postVisit(ASTNode node) {
         //write a closing brace to indicate end of the node
    	  if ((buffer.length() > 0) && (buffer.charAt(buffer.length()-1) != '{')) {
         	 buffer.append(",");         
          }
    	 buffer.append("\"StartPosition\":" +node.getStartPosition());         
         buffer.append("}");
      }
      public String toString() {
    	  return "{" + buffer.toString() + "}";
      }
   }
  


