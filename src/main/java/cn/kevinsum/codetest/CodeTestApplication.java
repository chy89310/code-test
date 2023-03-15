package cn.kevinsum.codetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

@SpringBootApplication
public class CodeTestApplication {

	// payment records
	public static final HashMap<String, Double> records = new HashMap<>();
	// regex pattern to match currency with any 3 uppercase letters
	private static final Pattern CURRENCY_PATTERN = Pattern.compile("^[A-Z]{3}$");
	// quit command
	private static final String QUIT_CMD = "quit";
	// interval to print out the records
	private static final long INTERVAL = 60000;

	public static void main(String[] args) {
		SpringApplication.run(CodeTestApplication.class, args);
		// load the file if specified
		if (args.length > 0) {
			loadFromFile(args[0]);
		}
		Timer timer = new Timer();
		timer.schedule(new PrintTask(), 0, INTERVAL);
		// read the input from stdin in the main thread
		Scanner scanner = new Scanner(System.in);
		while (true) {
			// move the cursor to the start of current line
			System.out.print("\033[0G");
			System.out.print("input: ");
			String command = scanner.nextLine();
			// user enter the QUID_CMD, break the while loop and kill the process
			if (command.equalsIgnoreCase(QUIT_CMD)) {
				break;
			}
			// try to add a new payment record according to the command
			addNewPayment(command);
		}
		timer.cancel();
		scanner.close();
	}

	private static void loadFromFile(String filepath) {
		try {
			Scanner scanner = new Scanner(new File(filepath));
			while (scanner.hasNext()) {
				addNewPayment(scanner.nextLine());
			}
			scanner.close();
		} catch (Exception ignored) { }
	}

	public static void addNewPayment(String input) {
		String[] commands = input.split(" ");
		if (commands.length == 2) {
			try {
				String currency = commands[0];
				if (!CURRENCY_PATTERN.matcher(currency).matches()) {
					throw new IOException("Currency Not Match");
				}
				Double amount = Double.valueOf(commands[1]);
				amount += records.getOrDefault(currency, 0.0);
				records.put(currency, amount);
				return;
			} catch (Exception ignored) { }
		}
		System.out.println("Please enter CurrencyCode(any 3 uppercase letter) Amount.\nOr enter \"quit\" to exit");
	}

	private static class PrintTask extends TimerTask {
		@Override
		public void run() {
			synchronized (records) {
				// move the cursor to the top-left of the console
				System.out.println("\033[H\033[2J");
				for (String currency : records.keySet()) {
					System.out.printf("%s %.2f%n", currency, records.get(currency));
				}
				System.out.print("input: ");
			}
		}
	}
}
