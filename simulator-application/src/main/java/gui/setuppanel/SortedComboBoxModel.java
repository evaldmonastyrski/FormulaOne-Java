package gui.setuppanel;

import org.jetbrains.annotations.Nullable;

import javax.swing.DefaultComboBoxModel;

class SortedComboBoxModel extends DefaultComboBoxModel<Integer> {

    @Override
    public void addElement(@Nullable Integer element) {
        insertElementAt(element, 1);
    }

    @Override
    public void insertElementAt(@Nullable Integer element, int index) {
        if (element != null) {
            int i;
            for (i = 1; i < getSize(); i++) {
                Comparable<Integer> c = getElementAt(i);
                if (c.compareTo(element) > 0) {
                    break;
                }
            }
            super.insertElementAt(element, i);
        } else {
            super.insertElementAt(null, 0);
        }
    }
}
