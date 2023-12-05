package ex4;

import java.util.Arrays;

public class Ex4 {
    public static void main(String[] args) {
        double[][] a = new double[][]{
                new double[]{-7, -5, -9},
                new double[]{-5, 5, 2},
                new double[]{-9, 2, 9}};

        double eps = 0.01;

//        double[][] a = new double[][]{
//                new double[]{4, 2, 1},
//                new double[]{2, 5, 3},
//                new double[]{1, 3, 6}};
//
//        double eps = 0.3;

        double[][] u_k = new double[][]{
                new double[]{1, 0, 0},
                new double[]{0, 1, 0},
                new double[]{0, 0, 1}};

        // проверить симметричность матрицы
        boolean check_symmetry = true;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i][j] != a[j][i]) {
                    check_symmetry = false;
                    break;
                }
            }
            if (!check_symmetry) {
                break;
            }
        }

        // вывод в консоль - true
        System.out.println("матрица А симметрична? - " + check_symmetry);

        if (check_symmetry) {
            int count = 0; // число итераций

            while (true) {
                count++;
                System.out.println("---------------");
                System.out.println(count + " итерация");
                // Выбираем максимальный по модулю внедиагональный элемент матрицы
                double max_elem = 0.0;
                int pos_i = -1;
                int pos_j = -1;
                for (int i = 0; i < a.length - 1; i++) {
                    for (int j = i + 1; j < a.length; j++) {
                        double abc_elem = Math.abs(a[i][j]);
                        if (max_elem < abc_elem) {
                            max_elem = abc_elem;
                            pos_i = i;
                            pos_j = j;
                        }
                    }
                }
                if (pos_i < 0) {
                    break;
                }
                System.out.println("максимальный внедиагональный элемент = " + max_elem + " i=" + pos_i + " j=" + pos_j);

                // Находим соответствующую этому элементу матрицу вращения
                double[][] u = new double[][]{
                        new double[]{1, 0, 0},
                        new double[]{0, 1, 0},
                        new double[]{0, 0, 1}};
                double fi = 0.5 * Math.atan(2 * a[pos_i][pos_j] / (a[pos_i][pos_i] - a[pos_j][pos_j]));
                double cos_fi = (double) Math.round(Math.cos(fi) * 10000) / 10000;
                double sin_fi = (double) Math.round(Math.sin(fi) * 10000) / 10000;
                u[pos_i][pos_j] = -1 * sin_fi;
                u[pos_j][pos_i] = sin_fi;
                u[pos_i][pos_i] = u[pos_j][pos_j] = cos_fi;

                // вычисляем произведение U_0 * U_1 тд
                // копируем матрицу u_k
                double[][] u_k_copy = new double[u_k.length][u_k.length];
                for (int i = 0; i < u_k_copy.length; i++) {
                    System.arraycopy(u_k[i], 0, u_k_copy[i], 0, u_k_copy.length);
                }
                for (int i = 0; i < u.length; i++) {
                    for (int j = 0; j < u.length; j++) {
                        double el = 0.0;
                        for (int k = 0; k < a.length; k++) {
                            el = el + u_k_copy[i][k] * u[k][j];
                        }
                        u_k[i][j] = (double) Math.round(el * 10000) / 10000;
                    }
                }

                //
                // Вычисляем матрицу A_1 = U_0t * A_0 * U_0
                //
                // транспонируем матрицу U
                double[][] u_t = new double[u.length][u.length];
                for (int i = 0; i < u.length; i++) {
                    for (int j = 0; j < u.length; j++) {
                        if (i != j) {
                            u_t[j][i] = u[i][j];
                        } else {
                            u_t[i][i] = u[i][i];
                        }
                    }
                }
                // 1 шаг U_0t * A_0
                double[][] multiplication_ut_a0 = new double[a.length][a.length];
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < a.length; j++) {
                        double el = 0.0;
                        for (int k = 0; k < a.length; k++) {
                            el = el + u_t[i][k] * a[k][j];
                        }
                        multiplication_ut_a0[i][j] = (double) Math.round(el * 10000) / 10000;
                    }
                }
                // 2 шаг A_0 * U_0
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < a.length; j++) {
                        double el = 0.0;
                        for (int k = 0; k < a.length; k++) {
                            el = el + multiplication_ut_a0[i][k] * u[k][j];
                        }
                        a[i][j] = (double) Math.round(el * 10000) / 10000;
                    }
                }

                // вычисляем точность - сумма квадратов внедиагональных элементов
                double t = 0.0;
                for (int i = 0; i < a.length - 1; i++) {
                    for (int j = i + 1; j < a.length; j++) {
                        t = t + Math.pow(a[i][j], 2);
                    }
                }
                t = (double) Math.round(Math.sqrt(t) * 10000) / 10000;
                System.out.println("t = " + t);
                if (t < eps) {
                    break;
                }
            }
            System.out.println();
            System.out.println("число итераций " + count + " для eps = " + eps);// вывод в консоль число итераций
            System.out.println();

            System.out.println("Собственные значения матрицы А");
            double[] lambda = new double[a.length];
            for (int i = 0; i < a.length; i++) {
                lambda[i] = a[i][i];
                System.out.println("lambda" + (i + 1) + " = " + lambda[i]);
            }
            System.out.println();

            System.out.println("Собственные векторы матрицы А");
            double[][] x = new double[a.length][a.length];
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x.length; j++) {
                    x[j][i] = u_k[i][j];
                }
            }
            for (int i = 0; i < x.length; i++) {
                System.out.println("x" + (i + 1) + " = " + Arrays.toString(x[i]));
            }

            // Определение ортогональности векторов х1 х2 х3
            boolean perpendicularly = true;
            for (int k = 0; k < x.length - 1; k++) {
                for (int i = k + 1; i < x.length; i++) {
                    double el = 0.0;
                    for (int j = 0; j < x.length; j++) {
                        el = el + (double) Math.round(x[k][j] * x[i][j] * 10000) / 10000;
                    }
                    if (el >= eps) {
                        perpendicularly = false;
                        break;
                    }
                }
                if (!perpendicularly) {
                    break;
                }
            }
            System.out.println("Ортогональность собственных векторов = " + perpendicularly);
        }
    }
}
