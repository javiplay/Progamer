package supermariocode.painter.nodes;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteComposite;
import supermariocode.painter.SpriteProvider;


public class MethodDeclarationNode extends JavaMarioNode {

	public MethodDeclarationNode(String name, int nodeType, int _linenumber) {
		super(name, nodeType, _linenumber);
		// TODO Auto-generated constructor stub
	}
	
	Rectangle getMethodDeclarationSprites(JavaMarioNode elem, int x, int y){
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		JavaMarioNode block = elem.getFirstChildOfType(ASTNode.BLOCK);

		SpriteComposite comp = SpriteProvider.constructorLeft(x, y);
		elem.addComposite(comp);

		x += comp.width;
		y += comp.height;

		Rectangle methodBox = block.getSprites(x, y);

		y -= 2;
		int iterx = 0;
		while (iterx < methodBox.width) {
			comp = SpriteProvider.constructorCenter(x + iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;

		comp = SpriteProvider.constructorRight(x, y);
		elem.addComposite(comp);


		boundingBox.width = methodBox.width + 2*comp.width + 2; // for the last 2 spaces
		boundingBox = SpriteProvider.getUnionBox(boundingBox, methodBox);
		elem.rectangle = boundingBox;
		return boundingBox;
	}
	
	
	@Override
	public Rectangle getSprites(int x, int y) {
		return getMethodDeclarationSprites(this, x, y); 		
	}

}
