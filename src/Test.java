import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author fmondin
 * @author Riccardo Francescato 
 *
 */
public class Test extends Thread {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public Test (String str){
		super(str);
	}
	public void run() {
		// TODO Auto-generated method stub

		String targetURL = "https://it.wikipedia.org/wiki/Pagina_principale";
		String urlParameters = "";
		
		HttpURLConnection connection = null;
		//System.out.println("Start request");
		long startTime = System.currentTimeMillis();
		 try {
			    //Create connection
			    URL url = new URL(targetURL);
			    connection = (HttpURLConnection) url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//			    connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			    connection.setRequestProperty("Content-Language", "en-US");  

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			    wr.writeBytes(urlParameters);
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			    String line;
			    //!!!!!commented for problems in exceptions!!!!!
			    /*while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			    }*/
			    rd.close();
//			    System.out.println(response.toString());	//print all response
			    
			    long endTime = System.currentTimeMillis();
			    //System.out.println("Request received");
			    System.out.println("Time: "+ (endTime-startTime));
			    Main.times.add(new Long(endTime-startTime));
			    
			  } catch (Exception e) {
			    e.printStackTrace();
			  } finally {
			    if (connection != null) {
			      connection.disconnect();
			    }
			  }
		
		
	}

}
