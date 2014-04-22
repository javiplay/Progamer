/*
Code Reimagined, Class for generating a tree from the AST
Copyright (C) 2010-2013 José Javier Asensio Montiel

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package progamer.views;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import progamer.drawing.nodes.*;



class TreeVisitor extends ASTVisitor {
	
	JavaMarioNode root;
	ASTNode rootAST;
	Stack stack;
	Map<Integer, ArrayList<JavaMarioNode>> hm = new HashMap<Integer, ArrayList<JavaMarioNode>>();

	public TreeVisitor(ASTNode rootAST) {
		super();		
		this.rootAST = rootAST;
		
		
		root = new JavaMarioNode("ROOT", -1, -1);
		
		stack = new Stack();
		
		stack.push(root);
	}

	public void preVisit(ASTNode node) {
		// write the name of the node being visited

		String name = node.getClass().getName();
		name = name.substring(name.lastIndexOf('.') + 1);

		JavaMarioNode current = (JavaMarioNode) stack.peek();
		
		int offset = node.getStartPosition();
		int nodeType = node.getNodeType();
		
		JavaMarioNode child = null;
		
		// añadir objeto según tipo
		switch (nodeType) {
		
		case ASTNode.TYPE_DECLARATION: 
			child = new TypeDeclarationNode(name, nodeType, offset);			
			break;			

		case ASTNode.FIELD_DECLARATION:
			child = new VariableDeclarationStatementNode(name, nodeType, offset);
			break;			

		case ASTNode.METHOD_DECLARATION:
			child = new MethodDeclarationNode(name, nodeType, offset);
			break;  

		case ASTNode.BLOCK:
			child = new BlockDeclarationNode(name, nodeType, offset);
			break;
			
		case ASTNode.EXPRESSION_STATEMENT:
			child = new ExpressionStatementNode(name, nodeType, offset);
			break;
						
		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			child = new VariableDeclarationStatementNode(name, nodeType, offset);
			break;

		case ASTNode.SWITCH_CASE:			
		case ASTNode.RETURN_STATEMENT:
			child = new ReturnStatementNode(name, nodeType, offset);
			break;		
					
		case ASTNode.IF_STATEMENT:
			child = new IfStatementNode(name, nodeType, offset);
			break;

		case ASTNode.FOR_STATEMENT:
		case ASTNode.ENHANCED_FOR_STATEMENT:
			child = new ForStatementNode(name, nodeType, offset);
			break;		
			
		case ASTNode.SWITCH_STATEMENT:
			child = new SwitchStatementNode(name, nodeType, offset);
			break;
			
		default:
			child = new JavaMarioNode(name, nodeType, offset);
		
		}
		
		current.children.add(child);
		addToHashMap(offset, child);

		stack.push(child);
	}

	public void addToHashMap(int codeLine, JavaMarioNode node) {

		ArrayList<JavaMarioNode> nodes = hm.get(codeLine);
		if (nodes == null) {
			nodes = new ArrayList<JavaMarioNode>();
			nodes.add(node);
			hm.put(codeLine, nodes);
		} else {
			nodes.add(node);

		}

	}

	public ArrayList<JavaMarioNode> getNodes(int codeLine) {
		return hm.get(codeLine);
	}

	public void postVisit(ASTNode node) {
		stack.pop();
	}

	public String toString() {

		return root.toString();
	}
	

}