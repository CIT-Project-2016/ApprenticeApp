package citaapp.citapprenticeshipapp;

/**
 * Copied from http://danielnugent.blogspot.com.au/2015/06/updated-jsonparser-with.html
 * edited by Donald Cameron November 2016
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

class JSONParser
{
    private final String CHARSET = "UTF-8";
    private final int TIMEOUT_READ = 10000;
    private final int TIMEOUT_CONN = 10000;
    private HttpURLConnection conn;
    private StringBuilder sbResult;
    private String stringResult;
    private URL urlObj;
    private JSONObject jsonRootObject = null;
    private StringBuilder sbParams;
    private String stringParams;

    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {
        //handling parameters
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) sbParams.append("&"); //add a '&' before strings, except for first one

                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), CHARSET));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        stringParams = sbParams.toString();

        //******************************************************************************************
        //                          Send Request
        //******************************************************************************************
        if (method.equals("POST"))
        {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", CHARSET);

                conn.setReadTimeout(TIMEOUT_READ);
                conn.setConnectTimeout(TIMEOUT_CONN);

                conn.connect();

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(stringParams);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET"))
        {
            // request method is GET

            if (sbParams.length() != 0) //if there are any parameters, append them to the end of the URL
            {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", CHARSET);

                conn.setConnectTimeout(TIMEOUT_CONN);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //------------------------------------------------------------------------------------------

        //******************************************************************************************
        //                          Receive Response
        //******************************************************************************************
        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            sbResult = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }

            stringResult = sbResult.toString();

            //remove the leading <> angle brackets from the start of the string so that it can be parsed correctly
            int trimIndex = stringResult.indexOf(">");
            stringResult = stringResult.substring(trimIndex + 1);

            Log.d("JSON Parser", "result: " + stringResult);

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jsonRootObject = new JSONObject(stringResult);}
        catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jsonRootObject;
    }
}