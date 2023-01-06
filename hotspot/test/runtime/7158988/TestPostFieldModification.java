/*
 * Copyright 2012 SAP AG.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

public class TestPostFieldModification {

  public String value;  // watch modification of value

  public static void main(String[] args){

    System.out.println("Start threads");
    // this thread modifies the field 'value'
    new Thread() {
      TestPostFieldModification test = new TestPostFieldModification();
      public void run() {
        test.value="test";
        for(int i = 0; i < 10; i++) {
          test.value += new String("_test");
        }
      }
    }.start();

    // this thread is used to trigger a gc
    Thread d = new Thread() {
      public void run() {
        while(true) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {

          }
          System.gc();
        }
      }
    };
    d.setDaemon(true);
    d.start();
  }
}
