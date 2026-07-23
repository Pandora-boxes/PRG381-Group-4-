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

// Reports view: materials at or below their reorder level. This is the same
// business rule (quantity <= reorder_level) that drives the dashboard alert;
// here it is shown as a full list so the storekeeper knows what to re-order.
public class LowStockReportPanel extends JPanel {

    private final ReportController reportController = new ReportController();
    private final JTable table = new JTable();
    private final JLabel title = new JLabel();

    public LowStockReportPanel() {
        setLayout(new BorderLayout());

        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(title, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadData());
        JPanel south = new JPanel();
        south.add(refresh);
        add(south, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        String[] columns = {"Code", "Name", "Quantity", "Reorder Level", "Supplier"};
        DefaultTableModel model = ReportTableUtil.readOnlyModel(columns);

        try {
            List<Material> materials = reportController.getLowStockReport();
            for (int i = 0; i < materials.size(); i++) {
                Material m = materials.get(i);
                Object[] row = {
                    m.getCode(),
                    m.getName(),
                    m.getQuantity(),
                    m.getReorderLevel(),
                    m.getSupplierName()
                };
                model.addRow(row);
            }
            table.setModel(model);

            // Tell the user how many items need attention right in the heading.
            title.setText("Low-Stock Report - " + materials.size()
                    + " item(s) at or below reorder level");
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not load the low-stock report:\n" + ex.getMessage(),
                    "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
