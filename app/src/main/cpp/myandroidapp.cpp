#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("myandroidapp");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("myandroidapp")
//      }
//    }

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT void JNICALL
Java_com_codingwithnobody_myandroidapp_MainActivity_convertVideoToGrayscale(JNIEnv *env,
                                                                            jobject thiz,
                                                                            jstring input_path,
                                                                            jstring output_path) {


}



































































