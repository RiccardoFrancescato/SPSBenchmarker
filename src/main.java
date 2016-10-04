import java.util.ArrayList;

public class Main {
	public static ArrayList<Long> times;
	public static void main(String[] args) throws InterruptedException {
		Long summ = new Long(0);
		times=new ArrayList();
		ArrayList<Test> threads=new ArrayList();
		long startTime = System.currentTimeMillis();
		for (int n=0; n<10; n++) {
            threads.add(n, new Test(""+n));
            threads.get(n).start();
        }
		for (Test test : threads) {
			test.join();
		}
		for (Long i : times) {
			summ+=i;
		}
		System.out.println("AVG: "+ ((summ/times.size())/1000)+","+((summ/times.size())%1000));
		System.out.println("Test time: "+ ((System.currentTimeMillis()-startTime)/1000)+","+((System.currentTimeMillis()-startTime)%1000));
		
	}
	
}
