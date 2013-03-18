package supermariocode.painter.nodes;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteProvider;


public class TypeDeclarationNode extends JavaMarioNode {

	public TypeDeclarationNode(String name, int nodeType, int _linenumber) {
		super(name, nodeType, _linenumber);
		// TODO Auto-generated constructor stub
	}
	
	Rectangle getTypeDeclarationSprites(JavaMarioNode elem, int x, int y) {
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		for (JavaMarioNode child : elem.children) {
			if (child.getNodeType() == ASTNode.METHOD_DECLARATION) {
				Rectangle r = child.getSprites(x, y);
				boundingBox = SpriteProvider.getUnionBox(boundingBox, r);
				x += r.width;
			}
		}
		return boundingBox;				
	}
	
	@Override
	public Rectangle getSprites(int x, int y) {
		return getTypeDeclarationSprites(this, x, y); 		
	}

}
