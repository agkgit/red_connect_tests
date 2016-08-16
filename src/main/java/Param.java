
public class Param {
	public static void main(String[] args) throws InterruptedException {
		My page = new My(true);
		page.openMy();
		Thread.sleep(2000);

		page.openRedHelperMenu();
		Thread.sleep(2000);

		page.openRHSetup();
		Thread.sleep(2000);

		page.openRHInterface();
		Thread.sleep(2000);

		page.openRHAgents();
		Thread.sleep(2000);

		page.openRHDepartaments();
		Thread.sleep(2000);

		page.openRHStatistics();
		Thread.sleep(2000);

		page.openRHBuy();
	}
}