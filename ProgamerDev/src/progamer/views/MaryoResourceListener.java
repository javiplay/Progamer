package progamer.views;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class MaryoResourceListener implements IResourceChangeListener 
{
	
	/**
	 * @param marioCodeView
	 */
	MaryoResourceListener() {
	}

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
	        	
	        	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				ProgamerMainView view = (ProgamerMainView) page.findView(ProgamerMainView.ID);
				view.action1.run();	
				
				System.out.println("Evento resourceChanged (tipo POST_CHANGE)");
				for (IResource res: changed ) {
					System.out.println("Resource: "+ res.getName());				
				}
				System.out.println("-----------------------------------------");				
	        }
	        
	        
					
							
		}
	}
}