package land.fx.wnfs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.module.annotations.ReactModule;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import fulamobile.Client;
import land.fx.wnfslib.Config;


@ReactModule(name = WnfsModule.NAME)
public class WnfsModule extends ReactContextBaseJavaModule {
    public static final String NAME = "WnfsModule";
    Client fula;
    String appDir;
    String fulaStorePath;

    public WnfsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        appDir = reactContext.getFilesDir().toString();
        fulaStorePath = appDir + "/fula";
        File storeDir = new File(fulaStorePath);
        boolean success = true;
        if (!storeDir.exists()) {
            success = storeDir.mkdirs();
        }
        if(success){
            Log.d(NAME,"Fula store folder created");
        }else{
            Log.d(NAME,"Unable to create fula store folder!");
        }
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    private byte[] toByte(String input) {
      return input.getBytes(StandardCharsets.UTF_8);
    }

  private String toString(byte[] input) {
    return new String(input, StandardCharsets.UTF_8);
  }

  private static int[] stringArrToIntArr(String[] s) {
    int[] result = new int[s.length];
    for (int i = 0; i < s.length; i++) {
      result[i] = Integer.parseInt(s[i]);
    }
    return result;
  }

  private static byte[] convertIntToByte(int[] input){
    byte[] result = new byte[input.length];
    for (int i = 0; i < input.length; i++) {
      byte b = (byte) input[i];
      result[i] = b;
    }
    return result;
  }

  private static byte[] convertStringToByte(String data){
    String[] keyInt_S = data.split(",");
    int[] keyInt = new int[36];
    keyInt = stringArrToIntArr(keyInt_S);

    byte[] bytes = convertIntToByte(keyInt);
    return bytes;
  }

  @ReactMethod
  public void createRootDir(String identityString, String storePath, Promise promise) {
    ThreadUtils.runOnExecutor(() -> {
      try{
        Log.d("initJNI",storePath);
        WritableArray arr = new WritableNativeArray();
        byte[] identity = toByte(identityString);
        init(identity, storePath);
        promise.resolve(true);
      }
      catch(Exception e){
        promise.reject(e);
        Log.d("init",e.getMessage());
      }
    });
  }

  public Client writeFile(byte[] identity, String storePath) {
      try{
        Config config_ext = new Config();
        if(storePath != null && !storePath.trim().isEmpty()) {
          config_ext.setStorePath(fulaStorePath);
        }

        if (identity != null) {
          config_ext.setIdentity(identity);
        }
        this.fula = Fulamobile.newClient(config_ext);
        return this.fula;
      }
      catch(Exception e){
        Log.d("init",e.getMessage());
      }
      return null;
  }

    public byte[] readFile(byte[] key) {
            try{
              Log.d("ReactNative", "get: key.toString() = "+toString(key));
              Log.d("ReactNative", "get: key.toString().bytes = "+ Arrays.toString(key));
              byte[] value = fula.get(key);
              Log.d("ReactNative", "get: value.toString() = "+toString(value));
              return value;
            }
            catch(Exception e){
              Log.d("ReactNative", "get: error = "+e.getMessage());
              Log.d("get",e.getMessage());
            }
      return null;
    }

    @ReactMethod
    public void ls(byte[] key, Promise promise) {
      ThreadUtils.runOnExecutor(() -> {
          try{
            boolean res = fula.has(key);
            promise.resolve(res);
          }
          catch(Exception e){
            promise.reject(e);
            Log.d("has",e.getMessage());
          }
       });
    }



}
