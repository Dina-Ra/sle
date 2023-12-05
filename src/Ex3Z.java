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
        double norm_a = getNormMatrix(a, b);
        System.out.println("\nнорма матрицы А = " + norm_a);

        // находим норму матрицы С - - верхняя треугольная матрица
        //с диагональными элементами, отличными от нуля
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                c[i][j] = a[i][j];
            }
        }
        double norm_c = getNormMatrix(c, b);
        System.out.println("\nнорма матрицы С = " + norm_c);
        System.out.println();

        int count_iterable = 0; // число итераций
        double e = 0.01;        // точность

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
            if (accuracy_x <= e) {
                break;
            }
            x = Arrays.copyOf(x_copy, x_copy.length);
        }

        System.out.println();
        System.out.println("Число итераций = " + count_iterable + " при точности = " + e);


    }

    public static double getNormMatrix(double[][] a, double[] b) {
        double a1 = 0.0;
        double a2 = 0.0;
        double ac = 0.0;
        for (int i = 0; i < a.length; i++) {
            double el1 = 0.0;
            double el2 = 0.0;
            double elc = 0.0;
            for (int j = 0; j < a.length; j++) {
                el1 = el1 + Math.abs(a[j][i]);
                el2 = el2 + Math.pow(a[i][j], 2);
                elc = elc + Math.abs(a[i][j]);
            }
            if (a1 < el1) {
                a1 = el1;
            }
            a2 = a2 + el2;
            if (ac < elc) {
                ac = elc;
            }
        }
        a2 = Math.sqrt(a2);

        double b1 = 0.0;
        double b2 = 0.0;
        double bc = 0.0;
        for (int i = 0; i < b.length; i++) {
            b1 = b1 + Math.abs(b[i]);
            b2 = b2 + Math.pow(b[i], 2);
            if (bc < Math.abs(b[i])) {
                bc = Math.abs(b[i]);
            }
        }
        b2 = Math.sqrt(b2);

        double[] c = new double[b.length];
        for (int i = 0; i < a.length; i++) {
            double el = 0.0;
            for (int j = 0; j < a.length; j++) {
                el = el + a[i][j] * b[j];
            }
            c[i] = el;
        }
        double c1 = 0.0;
        double c2 = 0.0;
        double cc = 0.0;
        for (int i = 0; i < b.length; i++) {
            c1 = c1 + Math.abs(c[i]);
            c2 = c2 + Math.pow(c[i], 2);
            if (cc < Math.abs(c[i])) {
                cc = Math.abs(c[i]);
            }
        }
        c2 = Math.sqrt(c2);

        double[] norm_a = new double[]{a1, a2, ac};
        double[] norm_b = new double[]{b1, b2, bc};
        double[] norm_c = new double[]{c1, c2, cc};

        for (int i = 0; i < norm_a.length; i++) {
            int count = 0;
            for (int j = 0; j < norm_a.length; j++) {
                double h = norm_b[j] * norm_a[i];
                if (norm_c[j] <= h) {
                    count++;
                }
            }
            if (count == 3) {
                return norm_a[i];
            }
        }
        return 0.0;
    }
}
