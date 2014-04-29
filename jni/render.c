
#include <jni.h>
#include <md5.h>

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_getBaseUrl(JNIEnv *env, jobject obj){
	char* url = "http://99zdj.com/";
	jstring result = (*env)->NewStringUTF(env, url);

	return result;
}

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_render(JNIEnv *env, jobject obj, jstring raw_string){

}

JNIEXPORT jstring JNICALL Java_com_xingfutao_utils_Renderer_wipe(JNIEnv *env, jobject obj, jstring rendered_string){

}

JNIEXPORT jstring JNICALL Java_com_xingfutao_utils_Renderer_md5(JNIEnv *env, jobject obj, jstring raw_string){

}
