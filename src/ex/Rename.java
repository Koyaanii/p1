package ex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Rename extends Thread {
    public static int count;
//"C:/Users/Koyaanisqatsi/IdeaProjects/p1/src/ex/papka"
    @Override
    public void run() {
        File dir = null;
        Scanner scanner = new Scanner(System.in);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String filePath = "C:/Users/Koyaanisqatsi/IdeaProjects/p1/src/ex/file.Json";
        try {
            String json = (readUsingApacheCommonsIO(filePath));
            dir = gson.fromJson(json, File.class);
        } catch (Exception ignored) {
        }
        if (dir != null) {
            System.out.println("������������ ������� ����� ��� ���������? (�� / ���)\n" + dir);
            if (scanner.nextLine().equalsIgnoreCase("���")) {
                System.out.println("������� ������ ���� �����, � ������� ������ ������������� �����.");
                dir = new File(scanner.nextLine());
            }
        }

        // ��� ������ ����� �� ����� ������ ����������������� � ����,
        // � ��� ������ ��������� � ��� ��� ������ ���� ������ ���� �����.

        Gson gson1 = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson1.toJson(dir);
        File file = new File("C:/Users/Koyaanisqatsi/IdeaProjects/p1/src/ex/file.Json");
        try {
            FileUtils.write(file, json, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��������� ���������� ������

        System.out.println("������� ���������� ������, ������� ������ �������������.");
        String expansion = scanner.nextLine();
        String newExpansion = "." + expansion;

        // ���� ����� � ������� ����� � �������

        System.out.println("������� ���������� ���������� ��������.");
        HashMap<String, String> map = new HashMap<>();
        int countReplace = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < countReplace; i++) {
            System.out.println("������� ���� ��� ������. ������: 'a-A',\n" +
                    "����������: Windows �� ����� ������ ��������� � ������ �������� ������� �������");
            String[] couple = scanner.nextLine().split("-"); // A-a
            map.put(couple[0], couple[1]);
        }
        try {
            for (Object key : map.keySet()) {
                for (Object value : map.values()) {
                    if (key.equals(value)) {
                        map.remove(key);
                    }
                }
            }
        } catch (Exception ignored) {
        }
        renameFilesFromFolder(dir, newExpansion, map);
        System.out.println("��������� ���������: " + count);
    }

    private static String readUsingApacheCommonsIO(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
    }

    public static void renameFilesFromFolder(File folder, String newExpansion, HashMap map) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                renameFilesFromFolder(entry, newExpansion, map);
            }
            if (entry.getPath().endsWith(newExpansion)) {
                String nameFile = entry.getName().split("\\.")[0];
                if (!map.isEmpty()) {
                    for (Object key : map.keySet()) {
                        if (nameFile.contains((String) key)) {
                            nameFile = nameFile.replaceAll((String) key, (String) map.get(key));
                            try {
                                Path source = Paths.get(entry.getPath());
                                Path newName = source.resolveSibling(entry.getParent() + "\\" + nameFile + newExpansion);
                                Files.move(source, newName);

                                System.out.println(source + " to " + newName);
                                count++;

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
    }
}
