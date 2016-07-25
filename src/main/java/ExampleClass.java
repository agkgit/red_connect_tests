import java.util.concurrent.TimeUnit;

public class ExampleClass {
	public static void main(String[] args) {

		Runnable task;

		task = () -> {
			for (int i = 0; i <= 100; i++) {
				String threadName = Thread.currentThread().getName();
				System.out.println(i + ") task1 " + threadName);
			}
		};

		task.run();

		Thread thread = new Thread(task);
		thread.start();


	}
}