package babylon.sniper.com.babylon.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import babylon.sniper.com.babylon.R;

/**
 * Created by sniper on 2/28/16.
 */
public class ErrorUtil {
    @StringRes
    public static int getErrorCode(@NonNull Throwable throwable){

        if(throwable != null && throwable instanceof UnknownHostException){
            return R.string.no_internet_connection;
        }else if(throwable != null && throwable instanceof SocketTimeoutException){
            //we can provide different message or action for socket time out
            return R.string.no_internet_connection;
        }

        return R.string.generic_error_message;

    }
}
