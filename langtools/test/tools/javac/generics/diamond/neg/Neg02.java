/*
 * @test /nodynamiccopyright/
 * @bug 6939620 7020044
 *
 * @summary  Check that diamond fails when inference violates declared bounds
 *           (test with nested class, qualified/simple type expressions)
 * @author mcimadamore
 * @compile/fail/ref=Neg02.out Neg02.java -XDrawDiagnostics
 *
 */

class Neg02 {

    static class Foo<X extends Number> {
        Foo(X x) {}
        <Z> Foo(X x, Z z) {}
    }

    void testSimple() {
        Foo<String> f1 = new Foo<>("");
        Foo<? extends String> f2 = new Foo<>("");
        Foo<?> f3 = new Foo<>("");
        Foo<? super String> f4 = new Foo<>("");

        Foo<String> f5 = new Foo<>("", "");
        Foo<? extends String> f6 = new Foo<>("", "");
        Foo<?> f7 = new Foo<>("", "");
        Foo<? super String> f8 = new Foo<>("", "");
    }

    void testQualified() {
        Foo<String> f1 = new Neg02.Foo<>("");
        Foo<? extends String> f2 = new Neg02.Foo<>("");
        Foo<?> f3 = new Neg02.Foo<>("");
        Foo<? super String> f4 = new Neg02.Foo<>("");

        Foo<String> f5 = new Neg02.Foo<>("", "");
        Foo<? extends String> f6 = new Neg02.Foo<>("", "");
        Foo<?> f7 = new Neg02.Foo<>("", "");
        Foo<? super String> f8 = new Neg02.Foo<>("", "");
    }
}
