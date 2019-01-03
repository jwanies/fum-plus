package fum.driver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FumHelper {
	
	private static final String COOKIES_HEADER = "Set-Cookie";
	private static CookieManager msCookieManager = new CookieManager();
	
	enum HttpMethod {
		PUT, POST, GET, DELETE
	}
	
	public static String sendHttpRequest(String requestUrl, String payload, HttpMethod method) {
	    try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	        
	        connection.setDoOutput(true);
	        connection.setRequestMethod(method.toString());
	        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
	        	connection.setRequestProperty("Cookie", msCookieManager.getCookieStore().getCookies().get(0).toString());
	        }
	        
	        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
	        	connection.setDoInput(true); 
	        	connection.setRequestProperty("Accept", "application/json");
		        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		        writer.write(payload);
		        writer.close();
	        }
	        
	        InputStream inputStream;
	        int status = connection.getResponseCode();
	        if (status != HttpURLConnection.HTTP_OK) {
                inputStream = connection.getErrorStream();
	        }
            else {
                inputStream = connection.getInputStream();
                List<String> cookiesHeader = connection.getHeaderFields().get(COOKIES_HEADER);
                if (cookiesHeader != null) {
                    for (String cookie : cookiesHeader) {
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	        return jsonString.toString();
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	
	}
	
}
