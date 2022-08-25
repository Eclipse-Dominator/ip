import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static final String DEFAULT_SAVE_PATH = "data/SavedData.duke";
    private String filePath;
    private File file;

    Storage(File file) {
        this.file = file;
        filePath = file.getAbsolutePath();
    }

    static Storage createStorage(String path) throws IOException {
        File newFile = new File(path);
        newFile.createNewFile();
        return new Storage(newFile);
    }

    static Storage createStorage() throws IOException {
        File newFile = new File(DEFAULT_SAVE_PATH);
        newFile.createNewFile();
        return new Storage(newFile);
    }

    List<Task> readFile() throws FileNotFoundException {
        List<Task> ret = new ArrayList<>();
        List<Integer> corruptedLines = new ArrayList<>();
        Scanner sc = new Scanner(file);
        String line;
        int lineNum = 0;
        ParsedData tmpData;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            lineNum++;
            try {
                tmpData = Parser.parseDataFromLine(line);
                ret.add(Parser.makeTaskFromParsed(tmpData));
            } catch (DukeException e) {
                corruptedLines.add(lineNum);
            }
        }
        sc.close();
        return ret;
    }

    void saveData(ParsedData[] dataList) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (ParsedData pd : dataList) {
            sb.append(pd.getSavedString());
            sb.append('\n');
        }
        FileWriter fw = new FileWriter(file);
        fw.write(sb.toString());
        fw.close();
    }    

    void saveTask(Task task) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.append(String.format("%s%n", task.convertToParseData().getSavedString()));
        fw.close();
    }
}
