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
import prg381group4.util.DataAccessException;

// Reports view: how much of each material has been issued in total, most-used
// first. The controller does the adding-up; this panel just shows the result.
public class MaterialUsageReportPanel extends JPanel {

    private final ReportController reportController = new ReportController();
    private final JTable table = new JTable();

    public MaterialUsageReportPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Material Usage - total issued per material");
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
        String[] columns = {"Material", "Total Issued"};
        DefaultTableModel model = ReportTableUtil.readOnlyModel(columns);

        try {
            List<ReportController.MaterialUsage> usage = reportController.getMaterialUsageReport();
            for (int i = 0; i < usage.size(); i++) {
                ReportController.MaterialUsage row = usage.get(i);
                Object[] cells = {
                    row.getMaterialName(),
                    row.getTotalIssued()
                };
                model.addRow(cells);
            }
            table.setModel(model);
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not load the material usage report:\n" + ex.getMessage(),
                    "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
