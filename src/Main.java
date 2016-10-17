import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	public static ArrayList<Long> times;
	public static ArrayList<String> links;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		times = new ArrayList<Long>();
		links = new ArrayList<String>();
		
		//Read file with links
		int numTotLines = 16814304;
		int numLink = 100;
		BufferedReader fileBuffer = null;
		
		try {
			String filePath = "..\\page.csv";
			fileBuffer = new BufferedReader(new FileReader(filePath));
			
			//Skip a random number of line (to avoid caching problems)
			Random randGen = new Random();
			int skipLine = randGen.nextInt(numTotLines/2);
			System.out.println("skipLine="+skipLine);
			int i = 0;
			while(fileBuffer.readLine()!=null &&  i<skipLine){
				i++;
			}
			
			//save the predefined (numLink) number of link
			i = 0;
			String nextLine;
			while((nextLine = fileBuffer.readLine())!=null &&  i<numLink){
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
		
		
//		ArrayList<Test> threads = new ArrayList();
//		
//		long startTime = System.currentTimeMillis();
//		
//		for (int n=0; n<2; n++) {
//            threads.add(n, new Test(""+n));
//            threads.get(n).start();
//        }
//		
//		for (Test test : threads) {
//			test.join();
//		}
//		
//		//calculate the total time
//		Long summ = new Long(0);
//		for (Long i : times) {
//			summ+=i;
//		}
//		System.out.println("AVG: "+ ((summ/times.size())/1000)+","+((summ/times.size())%1000));		//times is converted in seconds
//		System.out.println("Test time: "+ ((System.currentTimeMillis()-startTime)/1000)+","+((System.currentTimeMillis()-startTime)%1000));
		
	}
	
}
