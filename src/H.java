import java.util.Arrays;

public class H {
    public static void main(String[] args) {
        double[][] e = new double[][]{
                new double[]{1, 0, 0},
                new double[]{0, 1, 0},
                new double[]{0, 0, 1}};

        double[][] u = new double[][]{
                new double[]{10, 1, 1},
                new double[]{0, 9.8, 0.8},
                new double[]{0, 0, 9.653}};

        double[][] l = new double[][]{
                new double[]{1.0, 0, 0},
                new double[]{0.2, 1.0, 0},
                new double[]{0.2, 0.184, 1.0}};

        // прямой ход
        for (int k = 0; k < e.length-1; k++) {
            for (int i = k + 1; i < e.length; i++) {
                for (int j = 0; j < e.length; j++) {
                    e[i][j] = e[i][j] - e[k][j] * l[i][k];
                }
            }
        }

        System.out.println(Arrays.toString(e[0]));
        System.out.println(Arrays.toString(e[1]));
        System.out.println(Arrays.toString(e[2]));


        double[][] a_ = new double[e.length][e.length];

        for (int k = 0; k < a_.length; k++) {
            for (int i = a_.length - 1; i >= 0; i--) {
                double each = 0.0;
                for (int j = a_.length - 1; j > i; j--) {
                    each = each + a_[j][k] * u[i][j];
                }
                a_[i][k] = (double) Math.round(((e[i][k] - each) / u[i][i]) * 100000) / 100000;
            }
        }


        System.out.println(Arrays.toString(a_[0]));
        System.out.println(Arrays.toString(a_[1]));
        System.out.println(Arrays.toString(a_[2]));
//        System.out.println(Arrays.toString(a_[3]));

        double[][] a = new double[][]{
                new double[]{10, 1, 1},
                new double[]{2, 10, 1},
                new double[]{2, 2, 10}};

        double[][] multiplicationAandA_ = new double[a.length][a.length];

        for (int k = 0; k < a.length; k++) {

                for (int i = 0; i < a.length; i++) {
                    double element = 0.0;
                    for (int j = 0; j < a[i].length; j++) {
                        element = element + a[k][j] * a_[j][i];
                    }
                    multiplicationAandA_[k][i] = (double) Math.round(element * 1000) /1000;
                }

        }
        System.out.println(Arrays.toString(multiplicationAandA_[0]));
        System.out.println(Arrays.toString(multiplicationAandA_[1]));
        System.out.println(Arrays.toString(multiplicationAandA_[2]));
    }
}
