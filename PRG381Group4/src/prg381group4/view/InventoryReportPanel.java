package prg381group4.view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import prg381group4.controller.ReportController;
import prg381group4.model.Material;
import prg381group4.util.DataAccessException;

// Reports view: full inventory listing (every material in stock).
// Talks ONLY to ReportController - never to the DAO layer - so it obeys the
// project's golden rule that the view goes through a controller.
public class InventoryReportPanel extends JPanel {

    private final ReportController reportController = new ReportController();
    private final JTable table = new JTable();

    public InventoryReportPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Inventory Report - all materials in stock");
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(title, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // A refresh button so the report can be re-pulled after stock changes
        // during the demo, without reopening the window.
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadData());
        JPanel south = new JPanel();
        south.add(refresh);
        add(south, BorderLayout.SOUTH);

        loadData();
    }

    // Pull the report from the controller and fill the table. Any database
    // problem is shown to the user instead of crashing the window.
    private void loadData() {
        String[] columns = {"Code", "Name", "Unit", "Quantity",
                            "Reorder Level", "Unit Price", "Supplier"};
        DefaultTableModel model = ReportTableUtil.readOnlyModel(columns);

        try {
            List<Material> materials = reportController.getInventoryReport();
            for (int i = 0; i < materials.size(); i++) {
                Material m = materials.get(i);
                Object[] row = {
                    m.getCode(),
                    m.getName(),
                    m.getUnit(),
                    m.getQuantity(),
                    m.getReorderLevel(),
                    String.format("R%.2f", m.getUnitPrice()),
                    m.getSupplierName()
                };
                model.addRow(row);
            }
            table.setModel(model);
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not load the inventory report:\n" + ex.getMessage(),
                    "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
