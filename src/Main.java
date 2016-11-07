import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	//Constants
	public static final String filePath = "..\\page.csv";
//	public static final String requestUrl = "http://192.168.1.10/wikimirror/index.php/";
	public static final String requestUrl = "https://en.wikipedia.org/wiki/"; //for testing
	public static final int numThread = 2;
	public static final int numLinkPerThread = 5;	
	public static final int nWarmUpLinkPerThread = 1;
	public static final int totNumLink = numThread*numLinkPerThread;
	
	//Initializations
	public static ArrayList<Long> times;
	public static ArrayList<String> links;
	public static int numTotLines = 0;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		times = new ArrayList<Long>();
		links = new ArrayList<String>();
		
		
		BufferedReader fileBuffer = null;
		
		//Read total number of links
		try {
			fileBuffer = new BufferedReader(new FileReader(filePath));
			numTotLines = (int) fileBuffer.lines().count();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fileBuffer != null){
				fileBuffer.close();
			}
		}
		System.out.println("Total lines:"+numTotLines);

		//Read file with links
		try {
			fileBuffer = new BufferedReader(new FileReader(filePath));
			
			//Skip a random number of line (to avoid caching problems)
			Random randGen = new Random();
			int skipLine = randGen.nextInt(numTotLines/2);
			System.out.println("skipLine="+skipLine);
			int i = 0;
			while(fileBuffer.readLine()!=null &&  i<skipLine){
				i++;
			}
			//save the predefined (totNumLink) number of link
			i = 0;
			String nextLine;
			while((nextLine = fileBuffer.readLine())!=null &&  i<totNumLink){
				links.add(nextLine);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fileBuffer != null){
				fileBuffer.close();
			}
		}
		//print link for testing
//		for(String s : links){
//			System.out.println(s);
//		}
		
		
		//Test close loop
		
		ArrayList<Test> threads = new ArrayList<Test>();
		
		long startTime = System.currentTimeMillis();
		int i;
		for (i=0; i<numThread; i++) {
            threads.add(i, new Test(""+i));
            threads.get(i).start();
        }
		
		for (Test test : threads) {
			test.join();
		}
		long endTime = System.currentTimeMillis();
		
		//Calculate the total time
		Long totalThreadTime = new Long(0);
		for (Long j : times) {
			totalThreadTime+=j;
		}
		//check
		System.out.println(times.size());
		
		System.out.println("AVG thread time: "+ ((totalThreadTime/times.size())/1000)+","+((totalThreadTime/times.size())%1000));		//times is converted in seconds
		System.out.println("Test time: "+ ((endTime-startTime)/1000)+","+((endTime-startTime)%1000));
		
		
	}
	
}