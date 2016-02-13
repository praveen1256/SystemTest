package systemtest.test.com.test.utility;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class WebService {

    // Member variable
    private static boolean mResetWebServiceCall = false;

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
