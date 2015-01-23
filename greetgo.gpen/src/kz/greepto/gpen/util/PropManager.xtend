package kz.greepto.gpen.util

import java.util.Map

interface PropManager {
  def Map<String, Class<?>> meta()

  def Object get(String name)

  def void set(String name, Object value)

  def void setAsStr(String name, String strValue)

  def String getAsStr(String name)
}
