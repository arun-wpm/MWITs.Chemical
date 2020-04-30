package th.ac.mwits.www.mwitschemical;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Arun on 02-Jan-16.
 */
public class AddNewChemicalClass {
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {
        String response = "";
        URL url = null;
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //Log.d("TAG", "database connected: " + url);

            //Log.d("TAG", "before os");
            OutputStream os = conn.getOutputStream();
            //Log.d("TAG", "os");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //Log.d("TAG", "writer");
            writer.write(getPostDataString(postDataParams));
            //Log.d("TAG", "It's me");

            writer.flush();
            writer.close();
            os.close();
            //Log.d("TAG", "Hello");
            int responseCode = conn.getResponseCode();
            //Log.d("TAG", "responseCode" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
            } else {
                response = "Error adding chemical";
            }
        } catch (IOException e) {
            //Log.d("TAG", "IOException");
            response = "Network error";
            e.printStackTrace();
        } catch (Exception e) {
            //Log.d("TAG", "Some exception");
            response = "An unexpected error occured.";
            e.printStackTrace();
        }
        //Log.d("TAG", "luc: " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        //Log.d("TAG", result.toString());
        return result.toString();
    }
}
