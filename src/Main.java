import java.util.Collections;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {
    public static void main(String[] args) {
        System.out.println("Введите арифметическое выражение: ");
        Scanner scanner = new Scanner(System.in);
        String arithmExp = scanner.nextLine();
        Deistv operation = new Deistv();
        operation.virazj = arithmExp;
        IntegerConverter perevod = new IntegerConverter();

        //Массив со знаком и количество вхождений
        int[] y = operation.opredelenieOperac();

        //Проверки на вхождение
        int vb = y[1]+y[2]+y[3]+y[4];
        if(!(vb==1)){
            System.out.println("В выражение должно входить два члена и один оператор!");
            System.exit(0);
        }

        String[] u = operation.delenieNaChleni(); //разделенная на члены строка

        //Убирание пробелов из строкового представления членов  выражения
        String qw = u[0].trim();
        String qe = u[1].trim();
        operation.kl = qw;
        operation.po = qe;
        int bi = operation.proverkaNaArabscie();
        int bu = operation.proverkaNaRimscie();

        if((bi==1) && (bu==1)){
            System.out.println("Используются одновременно разные системы счисления!");
            System.exit(0);
        }
        if ((bi==0) && (bu==0)){
            System.out.println("Используются неверные символы!");
            System.exit(0);
        }
        if(((bi==1) || (bi==2)) && (bu==0)){
            try {
                Byte qwb = Byte.valueOf(qw);
                Byte qeb = Byte.valueOf(qe);
                switch (y[0]){
                    case 0:
                        System.out.println(qwb*qeb);
                        break;
                    case 1:
                        System.out.println(qwb+qeb);
                        break;
                    case 2:
                        System.out.println(qwb/qeb);
                        break;
                    case 3:
                        System.out.println(qwb-qeb);
                        break;
                }
            } catch (NumberFormatException e){
                System.out.println("Используются неверные символы!");
                System.exit(0);
            }
        }

        if(((bu==1) || (bu==2)) && (bi==0)){
            try {
                int mo = Rimscie.valueOf(qw).getZnachenie();
                int mp = Rimscie.valueOf(qe).getZnachenie();
                switch (y[0]){
                    case 0:
                        System.out.println(perevod.intToRoman(mo*mp));
                        break;
                    case 1:
                        System.out.println(perevod.intToRoman(mo+mp));
                        break;
                    case 2:
                        System.out.println(perevod.intToRoman(mo/mp));
                        break;
                    case 3:
                        System.out.println(perevod.intToRoman(mo-mp));
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Используются неверные символы!");
                System.exit(0);
            }
        }
    }
}
class Deistv{
    String virazj, kl, po;
    int q;
    int[] opredelenieOperac() { //возвращает массив первый член - индекс операции в опер, а остальные 4 - количество вхождений операндов в выражение
        String [] p = virazj.split("");
        int k = 0;
        int l = 0;
        int m = 0;
        int n =0;
        for(int i=0; i<p.length; i++) {
            switch (p[i]){
                case "*":
                    q = 0;
                    k++;
                    break;
                case "+":
                    q = 1;
                    l++;
                    break;
                case "/":
                    q = 2;
                    m++;
                    break;
                case "-":
                    q = 3;
                    n++;
                    break;
            }
        }
        int [] e = {q, k, l, m, n};
        return e;
    }
    String[] delenieNaChleni(){
        String[] nb = new String[0];
        String [] p = virazj.split("");
        for(int i=0; i<p.length; i++) {
            switch(p[i]){
                case "*":
                    nb = virazj.split("\\*");
                    break;
                case "+":
                    nb = virazj.split("\\+");
                    break;
                case "/":
                    nb = virazj.split("/");
                    break;
                case "-":
                    nb = virazj.split("-");
                    break;
            }
        }
        return nb;
    }
    int proverkaNaArabscie(){
        String[] ach = {"1","2","3","4","5","6","7","8","9","10"};
        int sch = 0;
        for(int i=0; i< ach.length; i++){
            int uo = ach[i].compareTo(kl);
            int ui = ach[i].compareTo(po);
            if((uo==0) && (ui==0)){
                sch++;
            }else if((uo==0) || (ui==0)){
                sch++;
            }
        }
        return sch;
    }
    int proverkaNaRimscie(){
        String[] ach = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        int sch = 0;
        for(int i=0; i< ach.length; i++){
            int uo = ach[i].compareTo(kl);
            int ui = ach[i].compareTo(po);
            if((uo==0) && (ui==0)){
                sch++;
            }else if((uo==0) || (ui==0)){
                sch++;
            }
        }
        return sch;
    }
}
class IntegerConverter {
    public static String intToRoman(int number) {
        if (number >= 4000 || number <= 0)
            return null;
        StringBuilder result = new StringBuilder();
        for(Integer key : units.descendingKeySet()) {
            while (number >= key) {
                number -= key;
                result.append(units.get(key));
            }
        }
        return result.toString();
    }
    private static final NavigableMap<Integer, String> units;
    static {
        NavigableMap<Integer, String> initMap = new TreeMap<>();
        initMap.put(1000, "M");
        initMap.put(900, "CM");
        initMap.put(500, "D");
        initMap.put(400, "CD");
        initMap.put(100, "C");
        initMap.put(90, "XC");
        initMap.put(50, "L");
        initMap.put(40, "XL");
        initMap.put(10, "X");
        initMap.put(9, "IX");
        initMap.put(5, "V");
        initMap.put(4, "IV");
        initMap.put(1, "I");
        units = Collections.unmodifiableNavigableMap(initMap);
    }
}