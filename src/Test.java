import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


public class Test extends Thread {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public Test (String str){
		super(str);
	}
	
	public void run() {
		int avgWaitTime = 1000;
		
		//Each thread make numLink request
		int i = 0;
		while (i < Main.numLinkPerThread){
			
			int indexLink = (Integer.parseInt(this.getName())*Main.numLinkPerThread)+i;
			System.out.println("Thread: "+this.getName()+" make req: "+i+" link ("+indexLink+"): "+Main.links.get(indexLink));

			long reqTime = makeRequest(Main.requestUrl+Main.links.get(i));
			if(reqTime>0 && i>Main.nWarmUpLinkPerThread)	//Not saving times for first nWarmUpLinkPerThread requests.
				Main.times.add(reqTime);
			
			//thread wait
			try {
				Random ranGen = new Random();
				long waitTime = (long) Math.log(ranGen.nextFloat())*(-1)*avgWaitTime;
				System.out.println("Thread: "+this.getName()+" sleep("+waitTime+")");
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			i++;
		}
		
	}
	
	private long makeRequest(String targetURL){
//		String urlParameters = "";
		HttpURLConnection connection = null;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		
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
//		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
//		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//		    String line;
//		    while ((line = rd.readLine()) != null) {
//		    	response.append(line);
//		    	response.append('\r');
//		    }
//		    rd.close();
		    //System.out.println(response.toString());	//print all response
		    
		    endTime = System.currentTimeMillis();
			//System.out.println("Request received");
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
	 		if (connection != null) {
	 			connection.disconnect();
		    }
	 	}
	 	
 		return(endTime-startTime);
	}
	

}