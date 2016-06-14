package io.disordinary.react;

import javax.annotation.Nullable;

import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.common.ReactConstants;
// import com.facebook.react.modules.common.ModuleDataCleaner;



import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;
import java.security.MessageDigest;



// public final class DBManager extends ReactContextBaseJavaModule implements ModuleDataCleaner.Cleanable {

public final class APIAUTH extends ReactContextBaseJavaModule {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";



	private String _id = "";
	private String _secret = "";

	public APIAUTH(ReactApplicationContext reactContext) {
		super(reactContext);
	}

	@Override
	public String getName() {
		return "APIAUTH";
	}

@ReactMethod
public void md5( String message  , Callback callBack ) {
	String digest = null;
	try {
        // Create MD5 Hash
        /*MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(message.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<messageDigest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
					}*/

					MessageDigest md = MessageDigest.getInstance("MD5");
		 byte[] hash = md.digest(message.getBytes("UTF-8"));

		 /*
		 StringBuilder sb = new StringBuilder(2*hash.length);

		 for(byte b : hash)
		 {
				 sb.append(String.format("%02x", b&0xff));
		 }

		 digest = sb.toString();*/

					callBack.invoke( null , Base64.encodeToString(hash , Base64.DEFAULT ) );


    } catch (Exception e) {
      callBack.invoke( "failed to generate md5: " + e.getMessage() , null );
    }
}


 @ReactMethod
 public void hmac(String secret , String message ,  Callback callBack) {


	  try {

	   // get an hmac_sha1 key from the raw key bytes
	   SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA1_ALGORITHM);

	   // get an hmac_sha1 Mac instance and initialize with the signing key
	   Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
	   mac.init(signingKey);

	   // compute the hmac on input data bytes
	   byte[] rawHmac = mac.doFinal(message.getBytes());

	   // base64-encode the hmac
	   String result =   Base64.encodeToString( rawHmac , Base64.DEFAULT );//new String( rawHmac );
		 callBack.invoke( null , result ) ;
	  } catch (Exception e) {
	   	callBack.invoke( "failed to generate hmac: " + e.getMessage() , null );
	  }




 }

}
