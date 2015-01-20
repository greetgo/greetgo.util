package kz.greepto.gpen.editors.gpen.prop

import java.util.List
import java.util.Map

class PropList implements Iterable<PropAccessor> {

  val List<PropAccessor> list = newArrayList
  var Map<String, PropAccessor> mapCache = null
  var PropList modiListCache = null

  private new(Iterable<PropAccessor> list) {
    this.list += list
  }

  public static def PropList from(Iterable<PropAccessor> list) { new PropList(list) }

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
    for (var i = 0, var C = left.size; i < C; i++) {
      list += left.get(i) + right.get(i)
    }

    mapCache = null
    modiListCache = null

    return this
  }

  def PropList getModiList() {
    if (modiListCache !== null) return modiListCache;
    return modiListCache = new PropList(
      this.list.filter[!options.readonly].sortWith [ PropAccessor a, PropAccessor b |
        var sow = a.options.orderWeightForSet - b.options.orderWeightForSet
        if(sow != 0) return sow
        return a.name.compareTo(b.name)
      ])
  }
}
