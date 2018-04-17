package main;

/**
 * Created by dryzu on 11.04.2018.
 */
public class ConsoleWaiter {
	private volatile boolean isWaiting = false;

	public void startWait() {
		isWaiting = true;
		new Thread(this::waitWorker).start();
	}


	public void stopWaiting() {
		synchronized (this) {
			isWaiting = false;
		}
	}

	private void waitWorker() {
		int i = 0;
		System.out.println("Пожалуйста, подождите, выполняется поиск");
		while (true) {
			if (i > 4)
				i = 0;
			i++;
			synchronized (this) {
				if (!isWaiting)
					break;
			}

			for (int j = 0; j < i; j++) {
				System.out.print('.');
			}
			System.out.println();
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
