package progamer.drawing.nodes;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Rectangle;

import progamer.drawing.SpriteComposite;
import progamer.drawing.SpriteProvider;



public class VariableDeclarationStatementNode extends JavaMarioNode {

	public VariableDeclarationStatementNode(String name, int nodeType, int offset) {
		super(name, nodeType, offset);
		// TODO Auto-generated constructor stub
	}
	
	
	Rectangle getVariableDeclarationSprites(JavaMarioNode elem, int x, int y) {
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		
		if (elem.children.get(0).getNodeType() == ASTNode.PRIMITIVE_TYPE){
			for (int i = 1; i < elem.children.size() ; i++) {
				SpriteComposite comp = SpriteProvider.local(x, y);
				elem.addComposite(comp);
				y+=comp.height;
				boundingBox = SpriteProvider.getUnionBox(boundingBox, comp.box);
			}
		} 
		elem.rectangle = boundingBox;		
		return boundingBox;		
	}

	@Override
	public Rectangle getSprites(int x, int y) {
		return getVariableDeclarationSprites(this, x, y); 		
	}

}
