package gui.setuppanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComboBox;
import java.util.List;

enum ComboBoxManagerUtil {
    ;

    static void newNumberSelected(int cacheIndex,
                                          @NotNull JComboBox<Integer> receivedCB,
                                          @Nullable Integer position,
                                          Integer[] cache,
                                          @NotNull List<JComboBox<Integer>> positions) {
        if (cache[cacheIndex] == null && position != null) {
            cache[cacheIndex] = position;
            for (JComboBox<Integer> cb : positions) {
                if (!cb.equals(receivedCB)) {
                    cb.removeItem(position);
                }
            }
        }
    }

    static void numberReplaced(int cacheIndex,
                                       @NotNull JComboBox<Integer> receivedCB,
                                       @Nullable Integer position,
                                       Integer[] cache,
                                       @NotNull List<JComboBox<Integer>> positions) {
        if (cache[cacheIndex] != null && position != null) {
            if (!cache[cacheIndex].equals(position)) {
                Integer temp = cache[cacheIndex];
                cache[cacheIndex] = position;
                for (JComboBox<Integer> cb : positions) {
                    if (!cb.equals(receivedCB)) {
                        cb.addItem(temp);
                        cb.removeItem(position);
                    }
                }
            }
        }
    }

    static void numberRemoved(int cacheIndex,
                                      @NotNull JComboBox<Integer> receivedCB,
                                      @Nullable Integer position,
                                      Integer[] cache,
                                      @NotNull List<JComboBox<Integer>> positions) {
        if (cache[cacheIndex] != null && position == null) {
            Integer temp = cache[cacheIndex];
            cache[cacheIndex] = position;
            for (JComboBox<Integer> cb : positions) {
                cb.addItem(temp);
            }
        }
    }
}
