package nutee.nutee_m;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class HttpGet {
    public String httpget(String url, ArrayList<NameValuePair> params)
    {
        String result="";

        HttpClient client = HttpGet.getHttpClient();
        try{
            HttpPost post= new HttpPost(url);

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,"UTF-8");
            post.setEntity(ent);


            HttpResponse responsePOST=client.execute(post);

            HttpEntity resEntity= responsePOST.getEntity();


            if (resEntity != null) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        responsePOST.getEntity().getContent()));

                String line = "";
                while ((line = rd.readLine()) != null) {
                    result += line;}

            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static String URL = "https://nutee.kr:47892/api/";
    public static DefaultHttpClient httpclient;

    public static HttpClient getHttpClient() {
        if (httpclient == null)
            HttpGet.setHttpclient(new DefaultHttpClient());
        return httpclient;
    }

    public static void setHttpclient(DefaultHttpClient httpclient) {
        HttpGet.httpclient = httpclient;
    }
}

