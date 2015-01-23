package kz.greepto.gpen.editors.gpen.prop

import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(FIELD, METHOD)
@Retention(RetentionPolicy.RUNTIME)
annotation Polilines {}