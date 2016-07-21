import java.util.concurrent.TimeUnit;

public class ExampleClass {
	public static void main(String[] args) {

		Runnable task1, task2, task3, task4;
		task1 = () -> {
			for (int i = 0; i <= 100; i++) {
				String threadName = Thread.currentThread().getName();
				System.out.println(i + ") task1 " + threadName);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		task2 = () -> {
			for (int i = 0; i <= 100; i++) {
				String threadName = Thread.currentThread().getName();
				System.out.println(i + ")task2 " + threadName);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		task3 = () -> {
			for (int i = 0; i <= 100; i++) {
				String threadName = Thread.currentThread().getName();
				System.out.println(i + ") task3 " + threadName);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		task4 = () -> {
			for (int i = 0; i <= 100; i++) {
				String threadName = Thread.currentThread().getName();
				System.out.println(i + ") task4 " + threadName);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		task1.run();
		task2.run();
		task3.run();
		task4.run();

		Thread thread1 = new Thread(task1);	thread1.start();
		Thread thread2 = new Thread(task2);	thread2.start();
		Thread thread3 = new Thread(task3);	thread3.start();
		Thread thread4 = new Thread(task4); thread4.start();

		System.out.println("Done!");
	}
}