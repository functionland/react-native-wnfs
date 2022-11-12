package com.reactnativewnfs
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise
import com.functionland.wnfslib.createPrivateForest
import com.functionland.wnfslib.createRootDir
import com.functionland.wnfslib.Config
import com.functionland.wnfslib.writeFile
import com.functionland.wnfslib.readFile
import com.functionland.wnfslib.ls
import java.io.File
import java.nio.charset.Charset

class WnfsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
      System.loadLibrary("wnfslib")
    }

    override fun getName(): String {
        return "Wnfs"
    }

    @ReactMethod
    fun createPrivateForest(dbPath: String, promise: Promise) {
        promise.resolve(createPrivateForest(dbPath))
    }

    fun convertConfigToJson(config: Config): String{
        return "{\"cid\": \"${config.cid}\", \"private_ref\": ${config.private_ref}}"
    }

    @ReactMethod
    fun createRootDir(dbPath: String, cid: String, promise: Promise) {
        promise.resolve(convertConfigToJson(createRootDir(dbPath, cid)))
    }

    @ReactMethod
    fun writeFile(dbPath: String, cid: String, private_ref: String, path: String, localFilePath: String, promise: Promise) {
        val contentBytes = File(localFilePath).readBytes()    
        promise.resolve(writeFile(dbPath, cid, private_ref, path, contentBytes))
    }

    @ReactMethod
    fun readFile(dbPath: String, cid: String, private_ref: String, path: String, promise: Promise) {
        promise.resolve(readFile(dbPath, cid, private_ref, path).toString(Charsets.UTF_8))
    }

    @ReactMethod
    fun ls(dbPath: String, cid: String, private_ref: String, path: String, promise: Promise) {
        promise.resolve(ls(dbPath, cid, private_ref, path))
    }
}
