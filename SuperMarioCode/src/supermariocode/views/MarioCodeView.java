package supermariocode.views;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
/*import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
*/
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

	public final static int width = 1200;
	public final static int height = 200;

	private Action action1;
	private Action action2;
	
	
	public static Canvas myCanvas;
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
				
				final ArrayList<IResource> changed = new ArrayList<IResource>();
				IResourceDelta rootDelta = event.getDelta();

				//definimos el visitante
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
		            public boolean visit(IResourceDelta delta) {
		               //only interested in changed resources (not added or removed)
		               if (delta.getKind() != IResourceDelta.CHANGED)
		                  return true;
		               //only interested in content changes
		               if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
		                  return true;
		               IResource resource = delta.getResource();
		               //only interested in files with the "java" extension
		               if (resource.getType() == IResource.FILE && 
						"java".equalsIgnoreCase(resource.getFileExtension())) {
		                  changed.add(resource);
		               }
		               return true;
		            }
		         };
		         try {
		             rootDelta.accept(visitor);
		          } catch (CoreException e) {
		             //open error dialog with syncExec or print to plugin log file
		          }

				

		        if (changed.size()>0){
					System.out.println("Evento resourceChanged (tipo POST_CHANGE)");
					for (IResource res: changed ) {
						System.out.println("Resource: "+ res.getName());				
					}
					System.out.println("-----------------------------------------");
		        }
				
				
				// actualizaci�n de la interfaz
				Display disp = control.getDisplay();
				disp.asyncExec(new Runnable() {					
					@Override
					public void run() {
						label.setText(label.getText()+"\nEvent type: CHANGED");

										
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

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file
	 * 
	 * @param unit
	 * @return
	 */

	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
	
	
	private void makeActions() {
		
		/* Action 1 */
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
					
				
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				
				// Get all projects in the workspace
				IProject[] projects = root.getProjects();
				
				// Loop over all projects
				for (IProject project : projects) {
					try {
						if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {

							IPackageFragment[] packages = JavaCore.create(project)
									.getPackageFragments();
							// parse(JavaCore.create(project));
							for (IPackageFragment mypackage : packages) {
								if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
									for (ICompilationUnit unit : mypackage
											.getCompilationUnits()) {
										// Now create the AST for the ICompilationUnits
										CompilationUnit parse = parse(unit);
										MethodVisitor visitor = new MethodVisitor();
										parse.accept(visitor);
									
										String l = visitor.buffer.toString();
										l = "{"+l+"}";
																				
										System.out.println(l);
										ObjectMapper mapper = new ObjectMapper();							
										JsonNode rootNode = mapper.readValue(l, JsonNode.class);
										System.out.println(rootNode.get("CompilationUnit").toString());																				
										
										System.out.println("Compilation Unit: " + unit.getElementName());
																														
									}
								}
							}
							
						}
					} catch (CoreException e) {
						e.printStackTrace();
					/*} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						*/
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
				
				StyledText st = (StyledText) editor.getAdapter(Control.class);
				
				try {
				if (st.getListeners(0).length==0){
					
					st.addCaretListener(new CaretListener() {
						
						@Override
						public void caretMoved(CaretEvent event) {
							// TODO Auto-generated method stub
							System.out.println(event.caretOffset);
							
						}
					});
					}
				}
				catch (NullPointerException e) {
					System.out.println("nooooo");
				
				}
				
				IEditorInput input = editor.getEditorInput();
				System.out.println("Evento de selecci�n sobre un editor java");
				System.out.println("IWortkbenchPart = "+c.getName());				
				System.out.println("IEditorInput = "+input.getName());
				System.out.println("----------------CONTENT-----------------");
				
				System.out.println("----------------------------------------");
			}
		}		
		System.out.println(getSite().getPage().getEditorReferences().length+" editores abiertos.");
	}
}