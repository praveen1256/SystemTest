package systemtest.test.com.test.utility;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to group the web server communications.
 *
 * @version 1.0
 * @since 28-01-2016
 */
public class WebService {

    // Member variable
    private static boolean mResetWebServiceCall = false;

    /**
     * This method is used to perform the login operation in the application.
     *
     * @param
     * @return void
     */
//    public static String login(String email, String password) throws IOException {
//        String url = "";
//        Log.v("WebService", url);
//        WebCallThread login = new WebCallThread();
//        login.execute(url);
//        try {
//            return login.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return "";
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    /**
     * This method is used to check the web service call mode.
     *
     * @param
     * @return void
     */
    public static void reset() {
        mResetWebServiceCall = true;
    }

    public static String testCall(String url) throws IOException {
        WebCallThread test = new WebCallThread();
        test.execute(url);
        try {
            return test.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isResetWebServiceCall() {
        return mResetWebServiceCall;
    }

    public static void setResetWebServiceCall(boolean mResetWebServiceCall) {
        WebService.mResetWebServiceCall = mResetWebServiceCall;
    }
}
