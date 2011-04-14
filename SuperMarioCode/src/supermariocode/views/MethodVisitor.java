package supermariocode.views;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

class MethodVisitor extends ASTVisitor {
      StringBuffer buffer = new StringBuffer();
      public void preVisit(ASTNode node) {
         //write the name of the node being visited
         
         String name = node.getClass().getName();          
         if ((buffer.length() > 0) && (buffer.charAt(buffer.length()-1) == '}'))
         {
        	 buffer.append(",\n");         
         }
         
         printDepth(node);
         name = name.substring(name.lastIndexOf('.')+1);
         buffer.append("\""+name+"\": {\n");
         
      }
      public void postVisit(ASTNode node) {
         //write a closing brace to indicate end of the node
         printDepth(node);
         buffer.append("}");
      }
      void printDepth(ASTNode node) {
         //indent the current line to an appropriate depth
         while (node != null) {
            node = node.getParent();
            buffer.append("   ");
         }
      }
   }
  


