/*
 * Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


/* C++ Agent class */

class Agent {

  private:
    enum {
      initial_monitor_list_size = 64,
      monitor_list_grow_size = 16
    };
    Monitor     **monitor_list;
    unsigned      monitor_list_size;
    unsigned      monitor_count;
    Thread *get_thread(jvmtiEnv *jvmti, JNIEnv *env, jthread thread);
    Monitor *get_monitor(jvmtiEnv *jvmti, JNIEnv *env, jobject object);

  public:
    Agent(jvmtiEnv *jvmti, JNIEnv *env, jthread thread);
    ~Agent();
    void vm_death(jvmtiEnv *jvmti, JNIEnv *env);
    void thread_start(jvmtiEnv *jvmti, JNIEnv *env, jthread thread);
    void thread_end(jvmtiEnv *jvmti, JNIEnv *env, jthread thread);
    void monitor_contended_enter(jvmtiEnv* jvmti, JNIEnv *env,
                   jthread thread, jobject object);
    void monitor_contended_entered(jvmtiEnv* jvmti, JNIEnv *env,
                   jthread thread, jobject object);
    void monitor_wait(jvmtiEnv* jvmti, JNIEnv *env,
                   jthread thread, jobject object, jlong timeout);
    void monitor_waited(jvmtiEnv* jvmti, JNIEnv *env,
                   jthread thread, jobject object, jboolean timed_out);
    void object_free(jvmtiEnv* jvmti, jlong tag);

};
