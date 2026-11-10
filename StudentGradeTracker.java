import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    static class Student {
        String name;
        double grade;

        Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateGrade();
                case 3 -> removeStudent();
                case 4 -> showStatistics();
                case 5 -> showReport();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n===== STUDENT GRADE TRACKER =====");
        System.out.println("1. Add student");
        System.out.println("2. Update student grade");
        System.out.println("3. Remove student");
        System.out.println("4. Show average / highest / lowest");
        System.out.println("5. Show summary report");
        System.out.println("0. Exit");
    }

    private static void addStudent() {
        System.out.print("Student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (find(name) != null) {
            System.out.println("Student already exists. Use 'Update grade'.");
            return;
        }
        double grade = readGrade();
        students.add(new Student(name, grade));
        System.out.println("Added " + name + ".");
    }

    private static void updateGrade() {
        System.out.print("Student name: ");
        Student s = find(sc.nextLine().trim());
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        s.grade = readGrade();
        System.out.println("Grade updated.");
    }

    private static void removeStudent() {
        System.out.print("Student name: ");
        Student s = find(sc.nextLine().trim());
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(s);
        System.out.println("Removed " + s.name + ".");
    }

    private static void showStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        Student highest = students.get(0), lowest = students.get(0);
        double sum = 0;
        for (Student s : students) {
            sum += s.grade;
            if (s.grade > highest.grade) highest = s;
            if (s.grade < lowest.grade) lowest = s;
        }
        System.out.printf("Average: %.2f%n", sum / students.size());
        System.out.printf("Highest: %.2f (%s)%n", highest.grade, highest.name);
        System.out.printf("Lowest : %.2f (%s)%n", lowest.grade, lowest.name);
    }

    private static void showReport() {
        if (students.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        System.out.println("\n---------- SUMMARY REPORT ----------");
        System.out.printf("%-20s %8s %6s%n", "Name", "Grade", "Letter");
        System.out.println("------------------------------------");
        for (Student s : students) {
            System.out.printf("%-20s %8.2f %6s%n", s.name, s.grade, letter(s.grade));
        }
        System.out.println("------------------------------------");
        System.out.println("Total students: " + students.size());
        showStatistics();
    }

    private static String letter(double g) {
        if (g >= 90) return "A";
        if (g >= 80) return "B";
        if (g >= 70) return "C";
        if (g >= 60) return "D";
        return "F";
    }

    private static Student find(String name) {
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    private static double readGrade() {
        while (true) {
            System.out.print("Grade (0-100): ");
            try {
                double g = Double.parseDouble(sc.nextLine().trim());
                if (g >= 0 && g <= 100) return g;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a number between 0 and 100.");
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}