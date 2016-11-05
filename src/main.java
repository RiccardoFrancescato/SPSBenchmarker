import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static ArrayList<Pair> times;
	public static ArrayList<String> links;
	public static void main(String[] args) throws InterruptedException, IOException {
		Long summ = new Long(0);
		Long warmUp = new Long(200);//ms of warm up
		times=new ArrayList<Pair>();
		links= new ArrayList<String>();
		System.out.println("load links");
		try {
			String path = "C:\\Users\\ricca\\Documents\\GitHub\\SPSBenchmarker\\safe.csv";
			BufferedReader buffer= new BufferedReader(new FileReader(path));
			String nextline;
			int i=0,j=0;
			int salto=0;
			while((nextline = buffer.readLine())!=null && i<57795){
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
		ArrayList<Test> threads=new ArrayList<Test>();
		System.out.println("Test started");
		long startTime = System.currentTimeMillis();
		for (int n=0; n<10; n++) {
            threads.add(n, new Test(""+n));
            threads.get(n).start();
        }
		for (Test test : threads) {
			test.join();
		}
		FileWriter writer = new FileWriter("test.csv");
		for (Pair i : times) {
			writer.append(i.getFirst().toString());
			writer.append(',');
			writer.append(i.getSecond().toString());
			writer.append('\n');
			if((long)i.getFirst()>=startTime+warmUp)
			summ+=(long)i.getSecond()-(long)i.getFirst();
			
		}
		writer.flush();
		writer.close();
		System.out.println("TNoR: " + times.size() + " AVG: "+ summ/times.size());
		System.out.println("Test time: "+ (System.currentTimeMillis()-startTime));
		
	}
	
}
