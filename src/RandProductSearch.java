import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch {
    private JFrame frame;
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Random Product Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Enter Partial Product Name:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);
        frame.add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(15, 30);
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchProducts());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void searchProducts() {
        String searchText = searchField.getText().trim().toLowerCase();
        resultArea.setText("");

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("products.dat", "r")) {
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String record = randomAccessFile.readUTF();
                Product product = parseProduct(record);

                if (product != null && product.getName().toLowerCase().contains(searchText)) {
                    resultArea.append(product.toString() + "\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Product parseProduct(String record) {
        try {
            String id = record.substring(0, 6).trim();
            String name = record.substring(6, 41).trim();
            String description = record.substring(41, 116).trim();
            double cost = Double.parseDouble(record.substring(116).trim());
            return new Product(id, name, description, cost);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error parsing product: " + ex.getMessage(), "Parse Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
