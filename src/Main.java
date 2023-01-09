import java.util.Collections;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {
    public static void main(String[] args) throws DifferentNumberSystems, InvalidCharacters, WrongFormat, NotMathematicalOperation, NegativeRomanNumbers {
        System.out.println("Введите арифметическое выражение: ");
        Scanner scanner = new Scanner(System.in);
        String arithmExp = scanner.nextLine();
        calc(arithmExp);
    }

    public static void calc(String input) throws WrongFormat, NotMathematicalOperation, DifferentNumberSystems, InvalidCharacters, NegativeRomanNumbers {
        Deistv operation = new Deistv();
        operation.virazj = input;

        //Массив со знаком и количество вхождений
        int[] y = operation.opredelenieOperac();

        //Проверки на вхождение
        int vb = y[1]+y[2]+y[3]+y[4];

        if(!(vb==1) && !(vb==0)){
            throw new WrongFormat("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if(vb==0){
            throw new NotMathematicalOperation("т.к. строка не является математической операцией");
        }

        String[] u = operation.delenieNaChleni(); //разделенная на члены строка
        String qw = u[0].trim();
        String qe = u[1].trim();
        operation.kl = qw;
        operation.po = qe;
        int bi = operation.proverkaNaArabscie();
        int bu = operation.proverkaNaRimscie();

        if((bi==1) && (bu==1)){
            throw new DifferentNumberSystems("т.к. используются одновременно разные системы счисления");
        }
        if ((bi==0) && (bu==0)){
            throw new InvalidCharacters("т.к. введенные символы не являются арабскими или римскими цифрами ");
        }

        if((((bi==1) && (qe.equals(qw))) || (bi==2)) && (bu==0)) {
            Byte qwb = Byte.valueOf(qw);
            Byte qeb = Byte.valueOf(qe);
            switch (y[0]) {
                case 0 -> System.out.println(qwb * qeb);
                case 1 -> System.out.println(qwb + qeb);
                case 2 -> System.out.println(qwb / qeb);
                case 3 -> System.out.println(qwb - qeb);
            }
        } else if((((bi==1) && !(qe.equals(qw))) || (bi==2)) && (bu==0))  {
            throw new InvalidCharacters("т.к. один из символов не является арабской цифрой ");
        }

        if((((bu==1) && (qe.equals(qw))) || (bu==2)) && (bi==0)) {
            int mo = Rimscie.valueOf(qw).getZnachenie();
            int mp = Rimscie.valueOf(qe).getZnachenie();
            switch (y[0]) {
                case 0 -> System.out.println(IntegerConverter.intToRoman(mo * mp));
                case 1 -> System.out.println(IntegerConverter.intToRoman(mo + mp));
                case 2 -> System.out.println(IntegerConverter.intToRoman(mo / mp));
                case 3 -> {
                    if ((mo - mp) < 0) {
                        throw new NegativeRomanNumbers("т.к. в римской системе нет отрицательных чисел");
                    } else {
                        System.out.println(IntegerConverter.intToRoman(mo - mp));
                    }
                }
            }
        } else if(((((bu==1) && !(qe.equals(qw))) || (bu==2)) && (bi==0))) {
            throw new InvalidCharacters("т.к. один из символов не является римской цифрой ");
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
        for (String s : p) {
            switch (s) {
                case "*" -> {
                    q = 0;
                    k++;
                }
                case "+" -> {
                    q = 1;
                    l++;
                }
                case "/" -> {
                    q = 2;
                    m++;
                }
                case "-" -> {
                    q = 3;
                    n++;
                }
            }
        }
        int [] e = {q, k, l, m, n};
        return e;
    }
    String[] delenieNaChleni(){
        String[] nb = new String[0];
        String [] p = virazj.split("");
        for (String s : p) {
            switch (s) {
                case "*" -> nb = virazj.split("\\*");
                case "+" -> nb = virazj.split("\\+");
                case "/" -> nb = virazj.split("/");
                case "-" -> nb = virazj.split("-");
            }
        }
        return nb;
    }
    int proverkaNaArabscie(){
        String[] ach = {"1","2","3","4","5","6","7","8","9","10"};
        int sch = 0;
        for (String s : ach) {
            int uo = s.compareTo(kl);
            int ui = s.compareTo(po);
            if ((uo == 0) && (ui == 0)) {
                sch++;
            } else if ((uo == 0) || (ui == 0)) {
                sch++;
            }
        }
        return sch;
    }
    int proverkaNaRimscie(){
        String[] ach = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        int sch = 0;
        for (String s : ach) {
            int uo = s.compareTo(kl);
            int ui = s.compareTo(po);
            if ((uo == 0) && (ui == 0)) {
                sch++;
            } else if ((uo == 0) || (ui == 0)) {
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