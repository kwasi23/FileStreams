import javax.swing.*;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.UUID;

public class RandProductMaker {
    private JFrame frame;
    private JTextField nameField, descriptionField, costField;
    private JButton addButton;
    private JLabel recordCountLabel;
    private int recordCount = 0;

    public RandProductMaker() {
        frame = new JFrame("RandProductMaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        costField = new JTextField(20);
        addButton = new JButton("Add Product");
        recordCountLabel = new JLabel("Record Count: 0");

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Description:"));
        frame.add(descriptionField);
        frame.add(new JLabel("Cost:"));
        frame.add(costField);
        frame.add(addButton);
        frame.add(recordCountLabel);

        addButton.addActionListener(e -> addProduct());

        frame.pack();
        frame.setVisible(true);
    }

    private void addProduct() {
        try {
            String id = generateID();
            String name = nameField.getText();
            String description = descriptionField.getText();
            double cost = Double.parseDouble(costField.getText());

            Product product = new Product(id, name, description, cost);
            writeProductToFile(product);

            recordCount++;
            recordCountLabel.setText("Record Count: " + recordCount);
            clearFields();
        } catch (NumberFormatException | IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateID() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private void writeProductToFile(Product product) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile("products.dat", "rw")) {
            file.seek(file.length());
            file.writeUTF(product.toFixedLengthString());
        }
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}
