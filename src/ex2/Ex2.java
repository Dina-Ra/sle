package ex2;

import java.util.Arrays;

public class Ex2 {

    public static void main(String[] args) {
        double[][] dano = new double[][] {
                new double[]{-1, -1, 0, 0, 0},
                new double[]{7, -17, -8, 0, 0},
                new double[]{0, -9, 19, 8, 0},
                new double[]{0, 0, 7, -20, 4},
                new double[]{0, 0, 0, -4, 12}
        };

        double[] d = new double[]{-4, 132, -59, -193, -40};

        double[] x = new double[d.length];

        /*
            Заполним матрицу pq где каждая строка соответствует исходной строке
            столбец с индексом 0 - P
            столбец с индексом 1 - Q
         */
        double[][] pq = new double[dano.length][2];
        for (int i = 0; i < dano.length; i++) {
            double a = 0.0;
            double b = dano[i][i];
            double c = 0.0;
            if (i > 0) {
                a = dano[i][i-1];
            }
            if (i < dano.length - 1) {
                c = dano[i][i+1];
            }

            if (a == 0.0) {
                pq[i][0] = (-c)/ b;
                pq[i][1] = d[i] / b;
            } else {
                pq[i][0] = (-c)/ (b + a * pq[i-1][0]);
                pq[i][1] = (d[i] - a * pq[i-1][1]) / (b + a * pq[i-1][0]);
            }
        }

        // вывод в консоль полученных значений матрицы pq
        Arrays.stream(pq).forEach(ar -> System.out.println(Arrays.toString(ar)));

        for (int i = dano.length - 1; i >=0 ; i--) {
            if (pq[i][0] == 0) {
                x[i] = pq[i][1];
            } else {
                x[i] = pq[i][0] * x[i+1] + pq[i][1];
            }
        }

        System.out.println(Arrays.toString(x)); // вывод в консоль x

        /*
            Результат
            x1 = 6.0
            x2 = -2.0
            x3 = -7.0
            x4 = 6.999999999999999
            x5 = -1.0000000000000002
         */
    }


}
