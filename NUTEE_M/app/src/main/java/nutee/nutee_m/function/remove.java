package nutee.nutee_m.function;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

import nutee.nutee_m.HttpGet;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class remove extends AsyncTask<ArrayList<NameValuePair>,Void,String> {
    @Override
    protected String doInBackground(ArrayList<NameValuePair>... params) {
        HttpGet get = new HttpGet();
        String result = get.httpget(HttpGet.URL + "remove", params[0]);
        Log.i("remove", result);
        return result;
    }
}
