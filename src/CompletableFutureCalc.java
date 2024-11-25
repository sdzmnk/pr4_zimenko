import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCalc {

    public static void main(String[] args) {
        System.out.println("Головний потік почав роботу");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть число для обчислень: ");
        int userInput = scanner.nextInt();

        // runAsync() - повідомлення про старт фонових завдань
        CompletableFuture<Void> startTask = CompletableFuture.runAsync(() -> {
            System.out.println("Старт асинхронних задач...");
        });

        // supplyAsync() - прийняття введених даних для подальших обчислень
        CompletableFuture<Integer> inputTask = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            System.out.println("Дані отримані: " + userInput);
            return userInput;
        });

        // thenApplyAsync() - обчислення квадрата числа
        CompletableFuture<Integer> squareTask = inputTask.thenApplyAsync(number -> {
            sleep(1);
            int square = number * number; // Квадрат числа
            System.out.println("Обчислений квадрат: " + square);
            return square;
        });

        // thenAcceptAsync() - виведення результату обчислень
        CompletableFuture<Void> displayTask = squareTask.thenAcceptAsync(result -> {
            sleep(1);
            System.out.println("Фінальний результат (квадрат введеного числа): " + result);
        });

        // thenRunAsync() - повідомлення про завершення всіх завдань
        CompletableFuture<Void> endTask = displayTask.thenRunAsync(() -> {
            System.out.println("Всі фонові задачі успішно завершені.");
        });

        // Очікуємо завершення всіх задач
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(startTask, inputTask, squareTask, displayTask, endTask);
        allTasks.join();

        System.out.println("Головний потік завершив роботу");
        scanner.close();
    }

    // Метод для імітації затримки
    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
