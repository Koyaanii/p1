package function;

import java.util.ArrayList;
// Написать функцию, которая принимает массив чисел и возвращает строку с диапазонами через запятую
// Пример: [1, 2, 3, 5, 7, 8] -> "1-3,5,7-8"
public class Func {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 5, 7, 8, 10};
        System.out.println(f(array));
    }

    static String f(int[] arr) {
        ArrayList list = new ArrayList();
        String finalStr = "";
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] + 1 == arr[i + 1]) {
                if (list.size() == 0) {
                    list.add(arr[i]);
                }
                list.add(arr[i+1]);
            }
            else{
                String str = "";
                if (list.size() > 1) {
                    str = list.get(0) + "-" + list.get(list.size()-1);
                }
                else{
                    str = String.valueOf(arr[i]);
                }
                finalStr += str + ",";
                list = new ArrayList<>();
            }
        }
        String str = "";
        if (list.size() > 1) {
            str = list.get(0) + "-" + list.get(list.size()-1);
        }
        else {
            str = String.valueOf(arr[arr.length - 1]);
        }
        finalStr += str;

        return finalStr;
    }
}
