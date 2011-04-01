package supermariocode.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {
	
	
	ArrayList inner = new ArrayList();
	ArrayList outer;
	
	private void begin(String s) {
		outer = inner;
		inner = new ArrayList();
		inner.add(s);
		outer.add(inner);
	}
	private void end() {
		inner = outer;	
	}
	private void add(String s) {
		inner.add(s);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		begin("METHOD");
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {				
		end();		
	}
	@Override
	public boolean visit(ExpressionStatement node) {
		add("expression");
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		begin("FOR");
		return super.visit(node);
	}
	public void endVisit(ForStatement node) {				
		end();
	}
	public ArrayList getTree() {
		return outer;
	}

}

