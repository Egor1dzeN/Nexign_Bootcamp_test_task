import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main1 {
    public static void main(String[] args) throws IOException {
        String path_cdr = System.getProperty("user.dir") + "\\src\\main\\resources\\cdr.txt";
        File file_cdr = new File(path_cdr);
        String path_reports = System.getProperty("user.dir")+"\\reports";
        File file_reports = new File(path_reports);
        if(!file_reports.exists())
            Files.createDirectory(Path.of(path_reports));
        Map<String, Integer> map = new HashMap<>();
        FileReader fr1 = new FileReader(file_cdr);
        BufferedReader reader1 = new BufferedReader(fr1);
        String line1_cdr = reader1.readLine();
        while (line1_cdr != null) {
            FileReader fr = new FileReader(file_cdr);
            BufferedReader reader = new BufferedReader(fr);
            String words1[] = line1_cdr.split(", ");
            String number_phone = words1[1];
            String line = reader.readLine();
            if (map.get(number_phone) == null) {
                String path_file = System.getProperty("user.dir") + "\\reports\\" + number_phone + ".txt";
                List<String> for_file = new ArrayList<>();
                ArrayList<String[]> arr = new ArrayList<>();
                while (line != null) {
                    String words[] = line.split(", ");
                    if (words[1].equals(number_phone))
                        arr.add(words);
                    line = reader.readLine();
                }
                for (int i = 0; i < arr.size() - 1; ++i) {
                    for (int j = 0; j < arr.size() - i - 1; ++j) {
                        if (Integer.valueOf(arr.get(j)[0]) > Integer.valueOf(arr.get(j + 1)[0])) {
                            String[] b = arr.get(j);
                            arr.set(j, arr.get(j + 1));
                            arr.set(j + 1, b);
                        } else if ((Long.valueOf(arr.get(j)[2]) > Long.valueOf(arr.get(j + 1)[2])) && (Integer.valueOf(arr.get(j)[0]) == Integer.valueOf(arr.get(j + 1)[0]))) {
                            String[] b = arr.get(j);
                            arr.set(j, arr.get(j + 1));
                            arr.set(j + 1, b);
                        }
                    }
                }
                for_file.add("Tariff index: " + arr.get(0)[4]);
                for_file.add("----------------------------------------------------------------------------");
                for_file.add("Report for phone number " + number_phone + ":");
                for_file.add("----------------------------------------------------------------------------");
                for_file.add("| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n" +
                        "----------------------------------------------------------------------------");
                long result_for_rate_11 = 100;
                long result_for_rate_06 = 300;
                long ans = 0;
                for (int kol = 0; kol < arr.size(); ++kol) {
                    String for_files = "";
                    for_files += "|     " + arr.get(kol)[0] + "    | ";
                    String years0 = arr.get(kol)[2].substring(0, 4);
                    int year0 = Integer.valueOf(years0);
                    String months0 = arr.get(kol)[2].substring(4, 6);
                    int month0 = Integer.valueOf(months0);
                    String days0 = arr.get(kol)[2].substring(6, 8);
                    int day0 = Integer.valueOf(days0);
                    String hours0 = arr.get(kol)[2].substring(8, 10);
                    int hour0 = Integer.valueOf(hours0);
                    String minuts0 = arr.get(kol)[2].substring(10, 12);
                    int minut0 = Integer.valueOf(minuts0);
                    String sec0 = arr.get(kol)[2].substring(12, 14);
                    int se0 = Integer.valueOf(sec0);
                    String years1 = arr.get(kol)[3].substring(0, 4);
                    int year1 = Integer.valueOf(years1);
                    String months1 = arr.get(kol)[3].substring(4, 6);
                    int month1 = Integer.valueOf(months1);
                    String days1 = arr.get(kol)[3].substring(6, 8);
                    int day1 = Integer.valueOf(days1);
                    String hours1 = arr.get(kol)[3].substring(8, 10);
                    int hour1 = Integer.valueOf(hours1);
                    String minuts1 = arr.get(kol)[3].substring(10, 12);
                    int minut1 = Integer.valueOf(minuts1);
                    String sec1 = arr.get(kol)[3].substring(12, 14);
                    int se1 = Integer.valueOf(sec1);
                    long seconds0 = day0 * 86400 + hour0 * 3600 + minut0 * 60 + se0;
                    long seconds1 = day1 * 86400 + hour1 * 3600 + minut1 * 60 + se1;
                    long result = seconds1 - seconds0;
                    long result1 = result;
                    long hour = result / 3600;
                    result %= 3600;
                    long minuts = result / 60;
                    result %= 60;
                    long seconds = result;
                    for_files += years0 + "-" + months0 + "-" + days0 + " " + hours0 + ":" + minuts0 + ":" + sec0 + " | ";
                    for_files += years1 + "-" + months1 + "-" + days1 + " " + hours1 + ":" + minuts1 + ":" + sec1 + " | ";
                    for_files += hour < 10 ? "0" + hour : hour + "";
                    for_files += ":";
                    for_files += minuts < 10 ? "0" + minuts : minuts;
                    for_files += ":";
                    for_files += seconds < 10 ? "0" + seconds : seconds;
                    for_files += " |  ";
                    if (arr.get(0)[4].equals("11")) {
                        if (arr.get(kol)[0].equals("02")) {
                            for_files += "0.00";
                        } else {
                            long minut11 = (result1 / 60 + (result1 % 60 == 0 ? 0 : 1));
                            long out;
                            if (result_for_rate_11 - minut11 >= 0) {
                                out = (result1 / 60 + (result1 % 60 == 0 ? 0 : 1)) * 50;
                                result_for_rate_11 -= minut11;
                            } else {
                                out = result_for_rate_11 * 50 + (minut11 - result_for_rate_11) * 150;

                                result_for_rate_11 = 0;
                            }
                            ans += out;
                            for_files += out / 100 + "." + (out % 100 < 10 ? "0" + out % 100 : out % 100);
                        }
                    } else if (arr.get(0)[4].equals("03")) {
                        long out = (result1 / 60 + (result1 % 60 == 0 ? 0 : 1)) * 150;
                        ans += out;
                        for_files += out / 100 + "." + (out % 100 < 10 ? "0" + out % 100 : out % 100);
                    } else if (arr.get(0)[4].equals("06")) {
                        long minut06 = result1 / 60 + (result1 % 60 == 0 ? 0 : 1);
                        long out;
                        if (result_for_rate_06 - minut06 >= 0) {
                            out = 0;
                            result_for_rate_06 -= minut06;
                        } else if (result_for_rate_06 > 0) {
                            out = 10000 + (minut06 - result_for_rate_06) * 100;
                            result_for_rate_06 = 0;
                        } else {
                            out = minut06 * 100;
                        }
                        ans += out;
                        for_files += out / 100 + "." + (out % 100 < 10 ? "0" + out % 100 : out % 100);
                    }
                    for_files += " |";
                    for_file.add(for_files);
                }
                for_file.add("----------------------------------------------------------------------------");
                String coast = "";
                coast += "|                                           Total Cost: |     ";
                coast += ans / 100 + "." + (ans % 100 < 10 ? "0" + ans % 100 : ans % 100);
                coast += " rubles |";
                for_file.add(coast);
                for_file.add("----------------------------------------------------------------------------");
                map.put(number_phone, 1);
                Path file_path = Paths.get(path_file);
                Files.write(file_path, for_file, StandardCharsets.UTF_8);
            }
            line1_cdr = reader1.readLine();
        }
        System.out.println("Вся информация об абонентах сохранена в папке reports");
    }
}



