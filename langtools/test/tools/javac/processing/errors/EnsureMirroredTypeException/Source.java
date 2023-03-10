/*
 * @test /nodynamiccopyright/
 * @bug 8019243
 * @summary AnnotationTypeMismatchException instead of MirroredTypeException
 * @library /tools/javac/lib
 * @build JavacTestingAbstractProcessor Processor
 * @compile/fail/ref=Source.out -XDrawDiagnostics -processor Processor Source.java
 */

@Processor.A(a=some.path.to.SomeUnknownClass$Inner.class)
class Source1{}

@Processor.B(a=SomeUnknownClass.class)
class Source2{}

@Processor.C(a=SomeUnknownClass.clas) // this is not a class literal
class Source3{}
