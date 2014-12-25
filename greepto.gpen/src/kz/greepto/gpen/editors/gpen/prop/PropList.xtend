package kz.greepto.gpen.editors.gpen.prop

import java.util.List
import java.util.Map

class PropList implements Iterable<PropAccessor> {

  val List<PropAccessor> list
  var Map<String, PropAccessor> mapCache = null

  private new(List<PropAccessor> list) {
    this.list = list
  }

  public static def PropList from(List<PropAccessor> list) { new PropList(list) }

  override iterator() { list.iterator }

  private static PropList EMPTY = from(#[])

  public static def PropList empty() { EMPTY }

  private def Map<String, PropAccessor> map() {
    if (mapCache === null) {
      mapCache = newHashMap
      for (it : list) {
        mapCache.put(name, it)
      }
    }
    return mapCache
  }

  def PropAccessor byName(String name) { map.get(name) }
}
