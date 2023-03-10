/*
 * @test /nodynamiccopyright/
 * @bug 6843077 8006775
 * @summary check for missing annotation value
 * @author Mahmood Ali
 * @compile/fail/ref=MissingAnnotationValue.out -XDrawDiagnostics MissingAnnotationValue.java
 */
class MissingAnnotationValue<K> {
  MissingAnnotationValue<@A ?> l;
}

@interface A { int field(); }
