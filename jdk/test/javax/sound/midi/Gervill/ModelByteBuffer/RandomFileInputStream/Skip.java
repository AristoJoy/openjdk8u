/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

/* @test
   @summary Test ModelByteBuffer.RandomFileInputStream skip(long) method */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class Skip {

    static float[] testarray;
    static byte[] test_byte_array;
    static File test_file;
    static AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

    static void setUp() throws Exception {
        testarray = new float[1024];
        for (int i = 0; i < 1024; i++) {
            double ii = i / 1024.0;
            ii = ii * ii;
            testarray[i] = (float)Math.sin(10*ii*2*Math.PI);
            testarray[i] += (float)Math.sin(1.731 + 2*ii*2*Math.PI);
            testarray[i] += (float)Math.sin(0.231 + 6.3*ii*2*Math.PI);
            testarray[i] *= 0.3;
        }
        test_byte_array = new byte[testarray.length*2];
        AudioFloatConverter.getConverter(format).toByteArray(testarray, test_byte_array);
        test_file = File.createTempFile("test", ".raw");
        FileOutputStream fos = new FileOutputStream(test_file);
        fos.write(test_byte_array);
    }

    static void tearDown() throws Exception {
        if(!test_file.delete())
            test_file.deleteOnExit();
    }

    public static void main(String[] args) throws Exception {
        try
        {
            setUp();

            for (int i = 0; i < 8; i++) {
                ModelByteBuffer buff;
                if(i % 2 == 0)
                    buff = new ModelByteBuffer(test_file);
                else
                    buff = new ModelByteBuffer(test_byte_array);
                if((i / 2) == 1)
                    buff.subbuffer(5);
                if((i / 2) == 2)
                    buff.subbuffer(5,500);
                if((i / 2) == 3)
                    buff.subbuffer(5,600,true);

                long capacity = buff.capacity();
                InputStream is = buff.getInputStream();
                try
                {
                    int ret = is.available();
                    long n = is.skip(75);
                    if(n == -1)
                        throw new RuntimeException("is.read shouldn't return -1!");
                    if(is.available() != ret - n)
                        throw new RuntimeException(
                                "is.available() returns incorrect value ("
                                + is.available() + "!="+(ret - n)+") !");

                    ret = is.available();
                    n = is.skip(-100);
                    if(n != 0)
                        throw new RuntimeException("is.skip(-100) shouldn't skip values!");
                    if(is.available() != ret - n)
                        throw new RuntimeException(
                                "is.available() returns incorrect value ("
                                + is.available() + "!="+(ret - n)+") !");

                    ret = is.available();
                    n = is.skip(5000);
                    if(is.available() != ret - n)
                        throw new RuntimeException(
                                "is.available() returns incorrect value ("
                                + is.available() + "!="+(ret - n)+") !");
                    if(is.available() != 0)
                        throw new RuntimeException(
                                "is.available() returns incorrect value ("
                                + is.available() + "!="+(0)+") !");                }
                finally
                {
                    is.close();
                }
                if(buff.capacity() != capacity)
                    throw new RuntimeException("Capacity variable should not change!");
            }
        }
        finally
        {
            tearDown();
        }
    }

}
