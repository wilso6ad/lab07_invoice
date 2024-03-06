import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}

class LineItem {
    private int quantity;
    private Product product;

    public LineItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateTotal() {
        return quantity * product.getUnitPrice();
    }

    public String toString() {
        return String.format("%s - Quantity: %d - Total: $%.2f", product.getName(), quantity, calculateTotal());
    }
}

class Invoice {
    private List<LineItem> lineItems;

    public Invoice() {
        lineItems = new ArrayList<>();
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    public double calculateTotalAmountDue() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.calculateTotal();
        }
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LineItem item : lineItems) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total Amount Due: $").append(String.format("%.2f", calculateTotalAmountDue()));
        return sb.toString();
    }
}

public class InvoiceGUI extends JFrame implements ActionListener {
    private JTextField productNameField, unitPriceField, quantityField;
    private JButton addButton, displayButton;
    private JTextArea displayArea;
    private Invoice invoice;

    public InvoiceGUI() {
        invoice = new Invoice();
        setTitle("Invoice System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        productNameField = new JTextField(15);
        unitPriceField = new JTextField(10);
        quantityField = new JTextField(5);
        addButton = new JButton("Add Line Item");
        displayButton = new JButton("Display Invoice");
        displayArea = new JTextArea(10, 30);

        addButton.addActionListener(this);
        displayButton.addActionListener(this);

        add(new JLabel("Product Name:"));
        add(productNameField);
        add(new JLabel("Unit Price:"));
        add(unitPriceField);
        add(new JLabel("Quantity:"));
        add(quantityField);
        add(addButton);
        add(displayButton);
        add(new JScrollPane(displayArea));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String productName = productNameField.getText();
            double unitPrice = Double.parseDouble(unitPriceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Product product = new Product(productName, unitPrice);
            LineItem lineItem = new LineItem(quantity, product);
            invoice.addLineItem(lineItem);
            productNameField.setText("");
            unitPriceField.setText("");
            quantityField.setText("");
        } else if (e.getSource() == displayButton) {
            displayArea.setText(invoice.toString());
        }
    }

    public static void main(String[] args) {
        InvoiceGUI invoiceGUI = new InvoiceGUI();
        invoiceGUI.setVisible(true);
    }
}