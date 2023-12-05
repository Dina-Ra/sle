package ex3;

public class NormMatrix {
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
