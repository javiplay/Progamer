/* 
  Code Reimagined, Class for generating the JSON from AST
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
package progamer.views;

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
  


