package supermariocode.views;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class MarioCodeView extends ViewPart implements ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "supermariocode.views.MarioCodeView";

	private Action action1;
	private Action action2;
	
	
	private Canvas myCanvas;
	Image image1, image2;
	
	Label label;
	Label labelState;
	Composite control;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	/**
	 * The constructor.
	 */
	public MarioCodeView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
	    control = parent;
		myCanvas = new Canvas(parent, SWT.H_SCROLL);
		
		image1 = new Image(myCanvas.getDisplay(),
			MarioCodeView.class.getResourceAsStream("background1.jpg"));
		image2 = new Image(myCanvas.getDisplay(),
				MarioCodeView.class.getResourceAsStream("background2.jpg"));
		
		myCanvas.setBackgroundImage(image1);		
		
		label = new Label(parent, SWT.WRAP);
        label.setText("Hello World");
        
        //labelState = new Label(parent, SWT.WRAP);
        
        // add myself as a global selection listener
        getSite().getPage().addSelectionListener(this);
        
        
        
        // prime the selection
        selectionChanged(getSite().getPage().getActiveEditor(), getSite().getPage().getSelection());



		
		myCanvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub			
				System.out.println("x: "+e.x+", y:"+e.y);
				
			}
		});		
		
		
		// probando esto...de los resource listener 
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new myResourceListener();
		workspace.addResourceChangeListener(listener);
		
		makeActions();
		contributeToActionBars();
	}
	
	public class myResourceListener implements IResourceChangeListener 
	{
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
				// TODO Auto-generated method stub					
			if (event.getType() ==  IResourceChangeEvent.POST_CHANGE) {				
				Display disp = control.getDisplay();
				disp.asyncExec(new Runnable() {					
					@Override
					public void run() {
						label.setText(label.getText()+"\nEvent type: CHANGED");
						//IResourceDelta rootDelta = event.getDelta();						
					}
				});					
			}
		}
	}


	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		
		/* Action 1 */
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
				myCanvas.setBackgroundImage(image1);
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		/* Action 2 */
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
				myCanvas.setBackgroundImage(image2);
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
	}

	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			myCanvas.getShell(),
			"Mario View",
			message);
			
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		myCanvas.setFocus();
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
		if (part != null) {
			Class c = part.getClass();			
			if (c.getName().equals("org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor")) {
				IEditorPart editor = (IEditorPart) part;
				IEditorInput input = editor.getEditorInput();
				
				
				
				String text = input.getName() + " should be painted...?";				
				text += "\n getting its IDocument object...does not work.";				
				IFile file = (IFile) input.getAdapter(IFile.class);				
				IDocument doc = (IDocument) file.getAdapter(Document.class);
				if (doc==null) text+="Document is NULL!!";
				
				
				label.setText(text);
				
				/*doc.addDocumentListener(new IDocumentListener() {

					@Override
					public void documentAboutToBeChanged(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void documentChanged(DocumentEvent arg0) {
						label.setText(label.getText()+"\nDocument Changed occurred");						
					}
					
											
					});
				*/
				
				//file.getContents();
				input.getPersistable();
				
				
				
				
			}
			else {
				label.setText("Hello World! Open or select a valid java file");
			}							
		}		
		label.setText(label.getText()+"\n-----\n"+getSite().getPage().getEditorReferences().length+" opened editors.");
		
	}
}