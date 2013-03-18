package supermariocode.painter.nodes;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteComposite;
import supermariocode.painter.SpriteProvider;


public class ForStatementNode extends JavaMarioNode {

	public ForStatementNode(String name, int nodeType, int _linenumber) {
		super(name, nodeType, _linenumber);
		// TODO Auto-generated constructor stub
	}
	

	Rectangle getForStatementSprites(JavaMarioNode elem, int x, int y) {

		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		JavaMarioNode block = elem.getFirstChildOfType(ASTNode.BLOCK);
		SpriteComposite comp = SpriteProvider.forTube(x, y);		
		elem.addComposite(comp);
		x += comp.width;
		y += comp.height;
		Rectangle blockBox = block.getSprites(x, y);
		boundingBox = SpriteProvider.getUnionBox(boundingBox, blockBox);
		y -= 2;
		int iterx = 0;
		while (iterx < blockBox.width) {
			comp = SpriteProvider.constructorCenter(x+iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;
		comp = SpriteProvider.forTube(x, y);
		elem.addComposite(comp);
		boundingBox = SpriteProvider.getUnionBox(boundingBox, comp.box);
	
		elem.rectangle = boundingBox;
		return boundingBox;
	}

	@Override
	public Rectangle getSprites(int x, int y) {
		// TODO Auto-generated method stub
		return getForStatementSprites(this, x, y);
	}
}
