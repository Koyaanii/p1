package function;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] arrray = {1, 2, 3, 5, 7, 8};
        System.out.println(Arrays.toString(arrray));
        StringBuilder temp = new StringBuilder();
        StringBuilder finalStr = new StringBuilder();
        for (int i = 0; i < arrray.length - 1; i++) {
            if (arrray[i] + 1 == arrray[i + 1]) {
                temp.append(arrray[i]);
            }
        }
        temp.setLength(0);
        temp.append("abc");
        finalStr.append(temp.charAt(0)).append("-").append(temp.charAt(temp.length()-1)).append(",");
        System.out.println(temp);
        System.out.println(finalStr);
    }
}
