/*
 * @test /nodynamiccopyright/
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs:-missing MissingCommentTest.java
 * @run main DocLintTester -Xmsgs:missing -ref MissingCommentTest.out MissingCommentTest.java
 */

public class MissingCommentTest {
    MissingCommentTest() { }

    int missingComment;

    void missingComment() { }
}
