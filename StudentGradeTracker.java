
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentGradeTracker extends JFrame {

    JTextField nameField, marksField;
    DefaultTableModel model;
    JLabel resultLabel;

    public StudentGradeTracker() {

        setTitle("Student Grade Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField(12);
        panel.add(nameField);

        panel.add(new JLabel("Marks:"));
        marksField = new JTextField(5);
        panel.add(marksField);

        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");

        panel.add(addButton);
        panel.add(summaryButton);

        model = new DefaultTableModel(
            new String[]{"Name", "Marks", "Grade"}, 0
        );

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        resultLabel = new JLabel("Enter student details.");

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        summaryButton.addActionListener(e -> showSummary());

        setVisible(true);
    }

    void addStudent() {
        try {
            String name = nameField.getText().trim();
            int marks = Integer.parseInt(marksField.getText().trim());

            if (name.isEmpty() || marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this,
                    "Enter a valid name and marks (0-100).");
                return;
            }

            String grade;
            if (marks >= 90) grade = "A+";
            else if (marks >= 80) grade = "A";
            else if (marks >= 70) grade = "B";
            else if (marks >= 60) grade = "C";
            else if (marks >= 40) grade = "D";
            else grade = "F";

            model.addRow(new Object[]{name, marks, grade});

            nameField.setText("");
            marksField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numeric marks.");
        }
    }

    void showSummary() {
        int count = model.getRowCount();

        if (count == 0) {
            JOptionPane.showMessageDialog(this,
                "No student records available.");
            return;
        }

        int total = 0;
        int highest = 0;
        int lowest = 100;

        for (int i = 0; i < count; i++) {
            int marks = (int) model.getValueAt(i, 1);
            total += marks;
            highest = Math.max(highest, marks);
            lowest = Math.min(lowest, marks);
        }

        double average = (double) total / count;

        JOptionPane.showMessageDialog(this,
            "Total Students: " + count +
            "\nAverage Marks: " + average +
            "\nHighest Marks: " + highest +
            "\nLowest Marks: " + lowest);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new StudentGradeTracker()
        );
    }
}
