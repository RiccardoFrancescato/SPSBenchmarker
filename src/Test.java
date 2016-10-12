import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

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
		Random rng= new Random();
		int avg=2000;
		int i=rng.nextInt(Main.links.size()-1), j=Main.links.size()-1;
		while (j>=0){
			if(i>=Main.links.size()-1){
				i=0;
			}
			try {
				Thread.sleep((long) (Math.log(rng.nextFloat())*(-1)*avg));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long app = makeRequest("http://192.168.1.10/wikimirror/index.php/"+Main.links.get(i));
			if(app>0){
				Main.times.add(app);
			}
			i++;
			j--;
		}
		
	}
	private long makeRequest(String targetURL){
		String urlParameters = "";
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
			    wr.writeBytes(urlParameters);
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    
			    endTime = System.currentTimeMillis();
			    System.out.println("Request received " + (endTime-startTime));
			  } catch (Exception e) {
				  System.out.println(e.getMessage());
			  } finally {
			    if (connection != null) {
			      connection.disconnect();
			    }
			  }
		 return(endTime-startTime);
	}

}
