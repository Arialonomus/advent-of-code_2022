package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtils {
    /**
     * Groups elements of a list into smaller lists of a given size. The last list may be smaller than
     * size if input_list.size() % partition_size != 0
     * @param input_list the list of elements to partition
     * @param partition_size the size of each partition
     * @return a list of sub-lists of size input_list.size() / partition_size
     * @param <T> type of the list elements
     */
    public static <T> List<List<T>> partition(List<T> input_list, int partition_size) {
        int num_partitions = input_list.size() / partition_size;
        if (input_list.size() % partition_size != 0)
            num_partitions++;

        Iterator<T> iterator = input_list.iterator();
        List<List<T>> result = new ArrayList<>(num_partitions);
        for (int i = 0; i < num_partitions; i++) {
            List<T> partition = new ArrayList<>(partition_size);
            for (int j = 0; j < partition_size && iterator.hasNext(); j++) {
                partition.add(iterator.next());
            }
            result.add(partition);
        }

        return result;
    }
}
