/*
 * @test /nodynamiccopyright/
 * @bug 6843077 8006775
 * @summary check for missing annotation value
 * @author Mahmood Ali
 * @compile/fail/ref=MissingAnnotationValue.out -XDrawDiagnostics MissingAnnotationValue.java
 */
class MissingAnnotationValue {
  void innermethod() {
    class Inner<@A K> { }
  }
}

@interface A { int field(); }
