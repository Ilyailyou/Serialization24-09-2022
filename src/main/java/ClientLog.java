

import java.io.File;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<int[]> log = new ArrayList<>();

    public void log(int productNum, int amount) {
        this.log.add(new int[] {productNum, amount});
    }
    public void exportAsCSV(File txtFile) throws IOException {

        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile))) {
            log.stream()
                    .map(entry->new String[] {String.valueOf(entry[0]), String.valueOf(entry[1])})
                    .forEach(csvWriter::writeNext);
        }

    }
}
