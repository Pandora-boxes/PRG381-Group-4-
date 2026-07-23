package prg381group4.view;

import java.awt.BorderLayout;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import prg381group4.controller.ReportController;
import prg381group4.model.Issuance;
import prg381group4.util.DataAccessException;

// Reports view: history of every stock issue to a cleaner. The material name,
// cleaner name and issued-by name are filled in by the DAO's JOIN, so this
// panel just reads them off each Issuance object.
public class IssuanceHistoryReportPanel extends JPanel {

    private final ReportController reportController = new ReportController();
    private final JTable table = new JTable();

    // One shared date formatter so every row shows the date the same way.
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public IssuanceHistoryReportPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Issuance History - stock issued to cleaners");
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
        String[] columns = {"Date", "Material", "Cleaner", "Quantity", "Issued By", "Notes"};
        DefaultTableModel model = ReportTableUtil.readOnlyModel(columns);

        try {
            List<Issuance> history = reportController.getIssuanceHistoryReport();
            for (int i = 0; i < history.size(); i++) {
                Issuance issue = history.get(i);
                Object[] row = {
                    formatDate(issue.getIssueDate()),
                    issue.getMaterialName(),
                    issue.getCleanerName(),
                    issue.getQuantity(),
                    issue.getIssuedByName(),
                    issue.getNotes()
                };
                model.addRow(row);
            }
            table.setModel(model);
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not load the issuance history:\n" + ex.getMessage(),
                    "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Guard against a null date so a missing timestamp cannot break the table.
    private String formatDate(Timestamp when) {
        if (when == null) {
            return "";
        }
        return dateFormat.format(when);
    }
}
