/*
 * @test /nodynamiccopyright/
 * @bug 8004832
 * @summary Add new doclint package
 * @library ..
 * @build DocLintTester
 * @run main DocLintTester -ref InsertImplicit.out InsertImplicit.java
 */

// tidy: Warning: inserting implicit <.*>

/**
 * </p>
 * <i> <blockquote> abc </blockquote> </i>
 */
public class InsertImplicit { }
