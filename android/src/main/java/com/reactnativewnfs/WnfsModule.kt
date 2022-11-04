package com.reactnativewnfs
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.functionland.wnfslib.*

class WnfsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
      System.loadLibrary("wnfslib")
    }

    override fun getName(): String {
        return "Wnfs"
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    fun testWNFSLib( promise: Promise) {
          promise.resolve(testWNFS())
    }
}
