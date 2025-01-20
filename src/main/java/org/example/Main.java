package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import static java.nio.file.Files.readAllBytes;

public class Main {

    public static String dirapp = System.getProperty("user.dir"); //Путь к директории программы
    public static ArrayList<Integer> intList = new ArrayList<Integer>(); //отсортированный массив с целыми числами
    public static ArrayList<Double> doubleList = new ArrayList<Double>(); //отсортированный массив с вещественными числами
    public static ArrayList<String> stringList = new ArrayList<String>(); //отсортированный массив со строками
    public static String intFile = "integers.txt"; //имя файла с целыми числами
    public static String doubleFile = "floats.txt"; //имя файла с вещественными числами
    public static String stringFile = "strings.txt"; //имя файла со строками
    public static Boolean rewrite = false; //перезаписывать файл или дописывать
    public static Boolean fullStat = false; //выводить или нет полную статистику
    public static Boolean chortStat = false; //выводить или нет полную статистику
    public static ArrayList<String> listFiles = new ArrayList<String>(); //Несорторованный масив
    public static String pathToFile = ""; //Путь к файлу

    public static void main(String[] args) {
        //Обработка аргументов
        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {
                case ("-o")://изменение пути к директории, где необходимо файлы с результатами
                    i++;
                    pathToFile = args[i] + "/";
                    break;

                case ("-p")://добавление префикса к созданным файлам
                    i++;
                    args[i] += intFile;
                    args[i] += doubleFile;
                    args[i] += stringFile;
                    break;

                case ("-a")://Изменение функции записи в файл (перезапись или добавление)
                    rewrite = true;
                    break;

                case ("-s"): //Аргумент вывода короткой статистики
                    chortStat = true;
                    break;

                case ("-f"): //Аргумент вывода полной статистики
                    fullStat = true;
                    break;

                default:
                    listFiles.add(args[i]); //запить имён файлов в массив
                    break;
            }
        }

        //переборка всех файлов, перевод содержимого построчно в массив строк и последующая сортировка их
        for (String file : listFiles) {
            String[] arrayString = makeFromFileToStringArray(file);
            makeFromSrtingArrayToSortArray(arrayString);
        }

       //приоверка необходимости показывать полной статистики
        if (fullStat) {
            showFullStat();
        }

        //приоверка необходимости показывать краткой статистики
        if (chortStat) {
            showShortStat();
        }
        // создаем дирикторию если такой нет
        File f = new File(pathToFile);
        if (!f.exists()) {
            f.mkdir();
        }
        //Запись отсортированных массивов в файлы
        WriteToFile<Integer> writeToFileInt = new WriteToFile<>();
        writeToFileInt.writeSortArrayToFile(intList, intFile, pathToFile, rewrite);
        WriteToFile<Double> writeToFileDouble = new WriteToFile<>();
        writeToFileDouble.writeSortArrayToFile(doubleList, doubleFile, pathToFile, rewrite);
        WriteToFile<String> writeToFileString = new WriteToFile<>();
        writeToFileString.writeSortArrayToFile(stringList, stringFile, pathToFile, rewrite);

    }

    //метод, который получает масив строк из файла
    public static String[] makeFromFileToStringArray(String nameFile) {
        File file = new File(nameFile);

        String result = null;
        //рповерка существования файла в директории
        if(file.exists()) {
            try {
                result = new String(readAllBytes(Paths.get(nameFile)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] parts = result.split("\n");
            return parts;
        } else System.out.println("\nФайла " + nameFile + " нет в директории приложения");
        return new String[0];
    }

    //метод, который сортирует масив(целые числа, вещественные, строки)
    public static void makeFromSrtingArrayToSortArray(String[] arrayString) {

        for (String a : arrayString) {
            try {
                Double b = Double.parseDouble(a);
                if (b % 1 == 0) {
                    intList.add(Integer.parseInt(a));
                } else doubleList.add(b);
            } catch (NumberFormatException e) {
                stringList.add(a);
            }
        }
    }

    //метод вывода полной статистики
    public static void showFullStat() {
        if (intList.size() == 0){
            System.out.println("\nЦелых чисел в файлах не найдено");
        } else {
            int sumInt = intList.get(0);
            int minInt = intList.get(0);
            int maxInt = intList.get(0);
            for (int i = 1; i < intList.size(); i++) {
                {
                    if (intList.get(i) > maxInt) {
                        maxInt = intList.get(i);
                    }
                    if (intList.get(i) < minInt) {
                        minInt = intList.get(i);
                    }
                    sumInt += intList.get(i);
                }
            }
            int averageInt = sumInt / intList.size();
            System.out.println("\nПолная статистика по целым числам:\n" +
                    "Минимальное целое число:      " + minInt +
                    "\nМаксимальное целое число:     " + maxInt +
                    "\nСумма целых чисел: " + sumInt +
                    "\nСреднее значение целых чисел: " + averageInt +
                    "\nКолличество целых чисел:      " + intList.size());
        }


        if (doubleList.size() == 0){
            System.out.println("\nВещественных чисел в файлах не найдено");
        } else {
            double sumDouble = doubleList.get(0);
            double minDouble = doubleList.get(0);
            double maxDouble = doubleList.get(0);
            for (int i = 1; i < doubleList.size(); i++) {
                if (doubleList.get(i) > maxDouble) {
                    maxDouble = doubleList.get(i);
                }
                if (doubleList.get(i) < minDouble) {
                    minDouble = doubleList.get(i);
                }
                sumDouble += doubleList.get(i);
            }
            double averageDouble = sumDouble / doubleList.size();
            System.out.println("\nПолная статистика по вещественным числам:\n" +
                    "Минимальное вещественное число:      " + minDouble +
                    "\nМаксимальное вещественное число:     " + maxDouble +
                    "\nСумма вещественных чисел: " + sumDouble +
                    "\nСреднее значение вещественных чисел: " + averageDouble +
                    "\nКолличество вещественных чисел:      " + doubleList.size());
        }

        if (stringList.size() == 0){
            System.out.println("\nСтрок в файлах не найдено");
        } else {
            int minString = stringList.get(0).length();
            int maxString = stringList.get(0).length();
            for (int i = 0; i < stringList.size(); i++) {
                String string = stringList.get(i);
                int a = string.length();
                if (a > maxString) {
                    maxString = a;
                }
                if (a < minString) {
                    minString = a;
                }

            }
            System.out.println("\nПолная статистика по строкам:\n" +
                    "Минимальное колличество символов в одной строке:      " + minString +
                    "\nМаксимальное колличество символов в одной строке:     " + maxString +
                    "\nВсего строк в файлах: " + stringList.size());
        }
    }

    //Метод вывода краткой статистики
    public static void showShortStat(){
        System.out.println("Краткая статистика:\n" +
                "Колличество целых чисел:         " + intList.size() +
                "\nКолличество вущественнвых чисел: " + doubleList.size() +
                "\nКолличество строк:               " + stringList.size());
    }

}