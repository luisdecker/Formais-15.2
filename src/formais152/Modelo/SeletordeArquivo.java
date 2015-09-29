package formais152.Modelo;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class SeletordeArquivo {

	static public String selectFile() {
		Shell shell = new Shell(new Display());
		FileDialog dialog = new FileDialog(shell, SWT.NULL);
		
		String path = dialog.open();
	
	
		return path;
	}

}
