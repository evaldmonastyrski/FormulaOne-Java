package gui.resultpanel;

import gui.setuppanel.Colours;
import model.DreamTeam;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

abstract class CombinationsDialog {

    private double maxPoints;

    CombinationsDialog(@NotNull String[] header,
                       @NotNull List<DreamTeam> dreamTeams,
                       @NotNull String name,
                       @NotNull CombinationsType combinationsType,
                       double maxPoints) {
        this.maxPoints = maxPoints;
        JFrame dialog = new JFrame(name);
        dialog.setSize(935, 820);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        TableModel tableModel = combinationsType.equals(CombinationsType.REGULAR)
                ? new RegularCombinationsTableModel(header, dreamTeams)
                : new RiskCombinationsTableModel(header, dreamTeams);
        @NotNull JTable teamsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamsTable);
        teamsTable.setFillsViewportHeight(true);
        setTableAlignment(teamsTable);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void setTableAlignment(@NotNull JTable teamsTable) {
        if (teamsTable.getModel() instanceof RegularCombinationsTableModel) {
            setRegularTableAlignment(teamsTable);
        }
    }

    private void setRegularTableAlignment(@NotNull JTable teamsTable) {
        DefaultTableCellRenderer leftAlignment = new DefaultTableCellRenderer();
        leftAlignment.setHorizontalAlignment(JLabel.LEFT);

        DefaultTableCellRenderer rightAlignment = new DefaultTableCellRenderer();
        leftAlignment.setHorizontalAlignment(JLabel.RIGHT);

        ValueColumnCellRenderer points = new ValueColumnCellRenderer();
        points.setHorizontalAlignment(JLabel.RIGHT);

        ValueColumnCellRenderer priceChange = new ValueColumnCellRenderer();
        priceChange.setHorizontalAlignment(JLabel.RIGHT);

        ValueColumnCellRenderer priceOffset = new ValueColumnCellRenderer();
        priceOffset.setHorizontalAlignment(JLabel.RIGHT);

        for (int i = 0; i < 5; i++) {
            teamsTable.getColumnModel().getColumn(i).setCellRenderer(rightAlignment);
        }
        teamsTable.getColumnModel().getColumn(5).setCellRenderer(leftAlignment);
        teamsTable.getColumnModel().getColumn(6).setCellRenderer(points);
        teamsTable.getColumnModel().getColumn(7).setCellRenderer(priceChange);
        teamsTable.getColumnModel().getColumn(8).setCellRenderer(priceOffset);
        for (int i = 9; i < 11; i++) {
            teamsTable.getColumnModel().getColumn(i).setCellRenderer(leftAlignment);
        }
    }

    private class ValueColumnCellRenderer extends DefaultTableCellRenderer {
        @Override
        @NotNull
        public Component getTableCellRendererComponent(JTable table,
                                                       @NotNull Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            table.setSelectionBackground(Color.BLUE.brighter().darker());
            RegularCombinationsTableModel tableModel = (RegularCombinationsTableModel) table.getModel();

            switch (column) {
                case 6 : {
                    colorPointsCell(table, row, column, cell, tableModel);
                    break;
                }
                case 7 : {
                    colorPriceCell(table, row, column, cell, tableModel);
                    break;
                }
                case 8 : {
                    colorPriceCell(table, row, column, cell, tableModel);
                    break;
                }
            }
            return cell;
        }

        private void colorPointsCell(@NotNull JTable table,
                                     int row,
                                     int column,
                                     @NotNull JLabel cell,
                                     @NotNull RegularCombinationsTableModel tableModel) {
            double cellValue = Double.parseDouble(tableModel.getValueAt(row, column));
            if (cellValue > maxPoints * 0.8) {
                if (cellValue > maxPoints * 0.9) {
                    chooseCorrectDarkness(table, cell, Colours.CHARTREUSE, row);
                } else {
                    chooseCorrectDarkness(table, cell, Colours.PALE_GREEN, row);
                }
            } else {
                if (cellValue > 0.65) {
                    chooseCorrectDarkness(table, cell, Color.RED, row);
                } else {
                    chooseCorrectDarkness(table, cell, Colours.FIRE_BRICK, row);
                }
            }
        }

        private void colorPriceCell(@NotNull JTable table,
                                    int row,
                                    int column,
                                    @NotNull JLabel cell,
                                    @NotNull RegularCombinationsTableModel tableModel) {
            double cellValue = Double.parseDouble(tableModel.getValueAt(row, column));
            if (cellValue > 0d) {
                if (cellValue > 5d) {
                    chooseCorrectDarkness(table, cell, Colours.CHARTREUSE, row);
                } else {
                    chooseCorrectDarkness(table, cell, Colours.PALE_GREEN, row);
                }
            } else {
                if (cellValue > -5d) {
                    chooseCorrectDarkness(table, cell, Color.RED, row);
                } else {
                    chooseCorrectDarkness(table, cell, Colours.FIRE_BRICK, row);
                }
            }
        }

        private void chooseCorrectDarkness(@NotNull JTable table, @NotNull JLabel cell, @NotNull Color color, int row) {
            if (table.getSelectedRow() != row) {
                cell.setBackground(color);
            } else {
                cell.setBackground(color.darker().darker());
            }
        }
    }
}
