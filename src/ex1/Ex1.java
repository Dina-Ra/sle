package ex1;

import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) {
        double[] b = new double[]{-3, 30, -90, 12};
        double[][] a = new double[][]{
                new double[]{-1, -3, -4, 0},
                new double[]{3, 7, -8, 3},
                new double[]{1, -6, 2, 5},
                new double[]{-8, -4, -1, -1}};

    /*
        Решение СЛАУ
        LUx = b; Ux = z; Lz = b;

        LU-разложение матрицы
    */
        double[][] l = new double[a.length][a.length]; // в матрице l[][] диагональным значениям присвоить 1.0
        for (int i = 0; i < l.length; i++) {
            l[i][i] = 1.0;
        }

        //
        double[][] u = new double[a.length][a.length]; // в матрицу u[][] копируем значения a[][]
        for (int i = 0; i < u.length; i++) {
            u[i] = Arrays.copyOf(a[i], a[i].length);
        }

        int count = 0; // число перестановок строк
        for (int k = 0; k < u.length - 1; k++) {
            // если первый элемент строки равен нулю, проводим поиск строки с ненулевым первым элементом, переставляем строки
            if (u[k][k] == 0) {
                for (int n = k + 1; k < u.length; k++) {
                    if (u[k][n] != 0.0) {
                        double[] copy = u[n];
                        u[n] = u[k];
                        u[k] = copy;
                        count++;
                        break;
                    }
                }
            }

            if (u[k][k] == 0) {
                break;
            } else {
                for (int i = k + 1; i < l.length; i++) {
                    double el = u[i][k] / u[k][k];
                    l[i][k] = el;
                }

                for (int i = k + 1; i < u.length; i++) {
                    for (int j = k; j < u.length; j++) {
                        u[i][j] = u[i][j] - l[i][k] * u[k][j];
                    }
                }
            }
        }

    /*
    1 этап - решить Lz = b;

    z1      z2      z3          z4
    ________________________________
    1.0     0.0     0.0         0.0 | b1
    -3.0    1.0     0.0         0.0 | b2
    -1.0    7.5     1.0         0.0 | b3
    8.0     6.5     0.9785      1.0 | b4
     */

        double[] z = new double[4];
        for (int i = 0; i < l.length; i++) {
            z[i] = b[i];
            for (int j = i - 1; j >= 0; j--) {
                z[i] = z[i] - l[i][j] * z[j];
            }
        }

    /*
    z = [
         -3.0
         21.0
         -250.5
         144.63214285714287
         ]



    2 этап - решить Ux = z

    x1      x2      x3          x4
    ____________________________________
    -1.0    -3.0    4.0         0.0     | z1
    0.0     -2.0    20.0        3.0     | z2
    0.0     0.0     140.0       -17.5   | z3
    0.0     0.0     0.0         -6.375  | z4
     */
        double[] x = new double[4];
        for (int i = l.length - 1; i >= 0; i--) {
            double each = 0.0;
            for (int j = l.length - 1; j > i; j--) {
                each = each + x[j] * u[i][j];
            }
            x[i] = (z[i] - each) / u[i][i];
        }

        System.out.println("Решение СЛАУ");
        System.out.println(Arrays.toString(x)); // вывод в консоль
    /*
        Результат x = [
                        -3.0000000000000107,
                        6.0000000000000036,
                        -3.0,
                        -8.999999999999998
                        ]
     */


    /*
        вычисление определителя матрицы
     */
        double det = Math.pow(-1, count);
        for (int i = 0; i < u.length; i++) {
            det = det * u[i][i];
        }
        System.out.println("----------------------------------");
        System.out.println("Определитель матрицы А = " + det); // вывод в консоль
        // det a = 2231.0


    /*
        Обратная матрица a[][]

        x14 x24 x34 x44
        x13 x23 x33 x43
        x12 x22 x32 x42
        x11 x21 x31 x41  b1  b2  b3  b4
        _____________________________
        -1  -3  -4  0  | 1   0   0   0
        3   7   -8  3  | 0   1   0   0
        1   -6  2   5  | 0   0   1   0
        -8  -4  -1  -1 | 0   0   0   1
     */
        double[][] e = new double[][]{
                new double[]{1, 0, 0, 0},
                new double[]{0, 1, 0, 0},
                new double[]{0, 0, 1, 0},
                new double[]{0, 0, 0, 1}};

        double[][] a_ = new double[a.length][a.length];

        // прямой ход
        for (int k = 0; k < e.length-1; k++) {
            for (int i = k + 1; i < e.length; i++) {
                for (int j = 0; j < e.length; j++) {
                    e[i][j] = e[i][j] - e[k][j] * l[i][k];
                }
            }
        }

        // обратный ход
        for (int k = 0; k < a_.length; k++) {
            for (int i = a_.length - 1; i >= 0; i--) {
                double each = 0.0;
                for (int j = a_.length - 1; j > i; j--) {
                    each = each + a_[j][k] * u[i][j];
                }
                a_[i][k] = (double) Math.round(((e[i][k] - each) / u[i][i]) * 100000) / 100000; // производим окрегление вещественного числа
            }
        }
        System.out.println("--------------------");
        System.out.println("Обратная матрица А");
        Arrays.stream(a_).forEach(doubles -> System.out.println(Arrays.toString(doubles))); // вывод в консоль
        /* Результат
           [
                0.12147019273868187     -0.04258180188256391    -0.005826983415508816   -0.15688032272523533
                -0.16405199462124598    0.06857911250560289     -0.032720753025549054   0.042133572389063206
                -0.15732855221873598    -0.04078888390856119    0.025997310623038995    0.00761990138951143
                -0.1582250112057372     0.10712684894666065     0.15150156880322727     0.07888839085611833
           ]
         */


        /*
         проверка результата
          a * a_ = e;
         */
        double[][] multiplicationAandA_ = new double[a.length][a.length];
        for (int k = 0; k < a.length; k++) {
            for (int i = 0; i < a.length; i++) {
                double element = 0.0;
                for (int j = 0; j < a[i].length; j++) {
                    element = element + a[k][j] * a_[j][i];
                }
                multiplicationAandA_[k][i] = (double) Math.round(element * 1000) /1000; // производим округление вещественного числа
            }
        }
        System.out.println("---------------------------------------------");
        System.out.println("В результате произведения матрицы А и обратной матрицы получена единичная матрица");
        Arrays.stream(multiplicationAandA_).forEach(doubles -> System.out.println(Arrays.toString(doubles))); // вывод в консоль
        /*
            в результате расчета получим единичную матрицу
            [
                1.0     0.0     0.0     0.0
                0.0     1.0     0.0     0.0
                0.0     0.0     1.0     0.0
                0.0     0.0     -0.001  1.0
             ]
         */
    }
}