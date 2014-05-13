
#include <jni.h>
#include <md5.c>

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_getBaseUrl(JNIEnv *env, jobject obj){
	char* url = "http://hwwshop.99zdj.com/api/";
	jstring result = (*env)->NewStringUTF(env, url);

	return result;
}

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_getBaseForumUrl(JNIEnv *env, jobject obj){
	char* url = "http://bbs.aawap.net/";
	jstring result = (*env)->NewStringUTF(env, url);

	return result;
}

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_render(JNIEnv *env, jobject obj, jstring raw_string){

}

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_wipe(JNIEnv *env, jobject obj, jstring rendered_string){

}

JNIEXPORT jstring JNICALL Java_com_pz_xingfutao_utils_Renderer_md5(JNIEnv *env, jobject obj, jstring raw_string){
	char* rawString = (*env)->GetStringUTFChars(env, raw_string, 0);

	char* crypted = MDString(rawString);

	jstring result = (*env)->NewStringUTF(env, crypted);

	return result;
}


