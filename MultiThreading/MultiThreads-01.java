package threads;

import java.text.SimpleDateFormat;
import java.util.Calendar;

class Hi extends Thread {
	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		for (int i = 1; i <= 5; i++) {
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

			System.out.println("Hi    : " + currentThread.getId() + " " + currentThread.getName() + " " + timeStamp);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			} // Sleep for a second
		}
	}
}

class Hello extends Thread {
	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		for (int i = 1; i <= 5; i++) {
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			System.out.println("Hello : " + currentThread.getId() + " " + currentThread.getName() + " " + timeStamp);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			} // Sleep for a second
		}
	}
}

public class ThreadDemo01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hi Hi_Object = new Hi();
		Hello Hello_Object = new Hello();
		Hi_Object.start(); // Internally it will execute the run method
		try {
			Thread.sleep(100);// Sleep for some milliseconds. to avoid random two executions of same thread
		} catch (Exception e) {
		}
		Hello_Object.start();

	}

}
