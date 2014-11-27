package kz.greepto.gpen.editors.gdoc;

import kz.greepto.gpen.util.ColorManager;

import org.eclipse.ui.editors.text.TextEditor;

public class GdocEditor extends TextEditor {
  
  private ColorManager colorManager;
  
  public GdocEditor() {
    colorManager = new ColorManager();
    setSourceViewerConfiguration(new XMLConfiguration(colorManager));
    setDocumentProvider(new XMLDocumentProvider());
  }
  
  public void dispose() {
    colorManager.dispose();
    super.dispose();
  }
  
}
