package kz.greepto.gpen.util;

import org.eclipse.core.runtime.IAdapterFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractAdapterFactory implements IAdapterFactory {
  
  @Override
  public Object getAdapter(Object adaptableObject, Class adapterType) {
    return adapter(adaptableObject, adapterType);
  }
  
  @Override
  public Class[] getAdapterList() {
    return servableTypes();
  }
  
  protected abstract <T> T adapter(Object adaptableObject, Class<T> adapterType);
  
  protected abstract Class<?>[] servableTypes();
}
