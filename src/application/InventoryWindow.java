import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryWindow extends JFrame {
    private JTable inventoryTable;

    public InventoryWindow() {
        // Set up the inventory window
        setTitle("Inventory System");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Simulated data for the inventory table
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("Product ID");
        columnNames.add("Product Name");
        columnNames.add("Quantity");
        columnNames.add("Price");

        ArrayList<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("Product 1");
        row1.add("10");
        row1.add("$19.99");

        ArrayList<String> row2 = new ArrayList<>();
        row2.add("2");
        row2.add("Product 2");
        row2.add("5");
        row2.add("$29.99");

        data.add(row1);
        data.add(row2);

        // Create a table with the data
        inventoryTable = new JTable(new ArrayListTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Add the table to the inventory window
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryWindow().setVisible(true);
            }
        });
    }
}

class ArrayListTableModel extends AbstractTableModel {
    private final ArrayList<ArrayList<String>> data;
    private final ArrayList<String> columnNames;

    public ArrayListTableModel(ArrayList<ArrayList<String>> data, ArrayList<String> columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }
}
