import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static ArrayList<Long> times;
	public static ArrayList<String> links;
	public static void main(String[] args) throws InterruptedException {
		Long summ = new Long(0);
		times=new ArrayList();
		links= new ArrayList();
		System.out.println("load links");
		try {
			String path = "C:\\page.csv";
			BufferedReader buffer= new BufferedReader(new FileReader(path));
			String nextline;
			int i=0,j=0;
			Random rng= new Random();
			int salto= rng.nextInt(10000);
			while((nextline = buffer.readLine())!=null && i<10){
				if(j>salto){
					links.add(nextline);
					i++;
				}
				j++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(" links loaded "+links.size());
		ArrayList<Test> threads=new ArrayList();
		System.out.println("Test started");
		long startTime = System.currentTimeMillis();
		for (int n=0; n<1; n++) {
            threads.add(n, new Test(""+n));
            threads.get(n).start();
        }
		for (Test test : threads) {
			test.join();
		}
		for (Long i : times) {
			summ+=i;
		}
		System.out.println("AVG: "+ summ/times.size());
		System.out.println("Test time: "+ (System.currentTimeMillis()-startTime));
		
	}
	
}
