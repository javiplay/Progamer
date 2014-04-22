package progamer.drawing.nodes;

import org.eclipse.swt.graphics.Rectangle;

import progamer.drawing.SpriteComposite;
import progamer.drawing.SpriteProvider;



public class ReturnStatementNode extends JavaMarioNode {

	public ReturnStatementNode(String name, int nodeType, int offset) {
		super(name, nodeType, offset);
		// TODO Auto-generated constructor stub
	}
	
	
	Rectangle getReturnSprites(JavaMarioNode elem, int x, int y) {
		
		SpriteComposite comp = SpriteProvider.ret(x, y);
		elem.addComposite(comp);
		elem.rectangle = SpriteProvider.getUnionBox(new Rectangle(x, y, 0, 0),comp.box);
		return elem.rectangle;
	}


	@Override
	public Rectangle getSprites(int x, int y) {
		// TODO Auto-generated method stub
		return getReturnSprites(this, x, y);
	}

}
