package ex3;

import java.util.Arrays;

public class Ex3Z {
    public static void main(String[] args) {
        double[] b = new double[]{96, -26, 35, -234};
        double[][] a = new double[][]{
                new double[]{-22, -2, -6, 6},
                new double[]{3, -17, -3, 7},
                new double[]{2, 6, -17, 5},
                new double[]{-1, -8, 8, 23}};

        // исходную матрицу привести к диагональному преобладанию элементов
        for (int k = 0; k < a.length - 1; k++) {
            for (int i = k; i < a.length; i++) {
                double sum_el = 0.0;
                for (int j = 0; j < a.length; j++) {
                    if (k != j) {
                        sum_el = sum_el + Math.abs(a[i][j]);
                    }
                }
                if (sum_el < Math.abs(a[i][k])) {
                    if (k == i) {
                        break;
                    }
                    double[] f = a[k];
                    a[k] = a[i];
                    a[i] = f;

                    double b_elem = b[k];
                    b[k] = b[i];
                    b[i] = b_elem;
                    break;
                }
            }
        }
        Arrays.stream(a).forEach(ar -> System.out.println(Arrays.toString(ar)));

        // приведем к эквивалентному виду
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                double el = 0 - a[i][i];
                if (i != j) {
                    a[i][j] = a[i][j] / el;
                }
            }
            b[i] = b[i] / a[i][i];
            a[i][i] = 0.0;
        }

        System.out.println("Эквивалентный вид");
        Arrays.stream(a).forEach(ar -> System.out.println(Arrays.toString(ar)));

        // находим норму матрицы А
        double norm_a = NormMatrix.getNormMatrix(a, b);
        System.out.println("\nнорма матрицы А = " + norm_a);

        // находим норму матрицы С - - верхняя треугольная матрица
        //с диагональными элементами, отличными от нуля
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                c[i][j] = a[i][j];
            }
        }
        double norm_c = NormMatrix.getNormMatrix(c, b);
        System.out.println("\nнорма матрицы С = " + norm_c);
        System.out.println();

        int count_iterable = 0; // число итераций
        double eps = 0.01;        // точность

        double[] x = new double[b.length];

        // нулевая приблеженность, x = b
        for (int i = 0; i < b.length; i++) {
            x[i] = b[i];
        }
        System.out.println(" x для 0-й приближенности");
        System.out.println(Arrays.toString(x));

        while (true) {
            count_iterable++;

            double[] x_copy = Arrays.copyOf(x, x.length);
            for (int i = 0; i < a.length; i++) {
                double el = b[i];
                for (int j = 0; j < a.length; j++) {
                    el = el + a[i][j] * x_copy[j];
                }
                x_copy[i] = el;
            }

            System.out.println("\n x для " + count_iterable + "-й приближенности");
            System.out.println(Arrays.toString(x_copy));

            double accuracy_x = 0.0;
            for (int i = 0; i < x.length; i++) {
                double el = Math.abs(norm_c) / (1 - Math.abs(norm_a)) * Math.abs(x[i] - x_copy[i]);
                if (el > accuracy_x) {
                    accuracy_x = el;
                }
            }
            System.out.println("точность - " + accuracy_x);
            if (accuracy_x <= eps) {
                break;
            }
            x = Arrays.copyOf(x_copy, x_copy.length);
        }

        System.out.println();
        System.out.println("Число итераций = " + count_iterable + " при точности = " + eps);


    }
}
