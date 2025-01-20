package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToFile<T> {
    //функция записи отсортированного массива в файл
    public void writeSortArrayToFile(ArrayList<T> t, String nameFile, String pathToFile, Boolean rewrite){
        if(!t.isEmpty()){
            try (FileWriter writer = new FileWriter(pathToFile + nameFile, rewrite)) {
                for (int i = 0; i < t.size(); i++) {
                    writer.write(t.get(i).toString() + "\n");
                    writer.flush();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


}
