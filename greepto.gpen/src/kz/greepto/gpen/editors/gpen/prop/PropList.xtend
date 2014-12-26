package kz.greepto.gpen.editors.gpen.prop

import java.util.List
import java.util.Map

class PropList implements Iterable<PropAccessor> {

  val List<PropAccessor> list = newArrayList
  var Map<String, PropAccessor> mapCache = null

  private new(List<PropAccessor> list) {
    this.list += list
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

  def PropList operator_plus(PropList a) {
    return new PropList(this.list) += a
  }

  def PropList operator_add(PropList a) {

    var List<PropAccessor> left = newArrayList
    var List<PropAccessor> right = newArrayList

    for (L : list) {
      var no = true
      for (R : a.list) {
        if (no && L.compatibleWith(R)) {
          no = false
          left += L
          right += R
        }
      }
    }

    list.clear
    for(var i = 0, var C = left.size; i < C; i++) {
      list += left.get(i) + right.get(i)
    }

    return this
  }
}
