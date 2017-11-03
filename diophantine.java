// <XMP>
// Diophantine Quadratic Equation Solver
// ax^2 + bxy + cy^2 + dx + ey + f  =  0 (unknowns x,y integer numbers)
//
// Written by Dario Alejandro Alpern (Buenos Aires - Argentina)
// Last updated December 15th, 2003
//
// No part of this code can be used for commercial purposes without
// the written consent from the author. Otherwise it can be used freely.
// 
//import java.applet.*;



import java.util.Scanner;
import java.util.*;
//import java.awt.*;
import java.math.*;

//public final class quad extends Applet implements Runnable {
public class diophantine {
//private static BigInteger Primes[];
//private static int Exponents[];
//private static BigInteger PrimesBak[];
//private static int ExponentsBak[];

    private static int digitsInGroup = 6;
    private static Vector sortedSolsX = new Vector(50, 50);
    private static Vector sortedSolsY = new Vector(50, 50);
    private static boolean allSolsFound;
    private static long A, B, C, D, E, F;
    private static long Xi;
    private static long Xl;
    private static long Yi;
    private static long Yl;
    private static long CY1, CY0;
    private static boolean also, ExchXY, teach;
    private static long SQD;
    private static long NUM[] = new long[6];
    private static long DEN[] = new long[6];
    private static long DET;
    private static final long BILL = 1000000000;
    private static final long BILL_BILL = BILL * BILL;
    private static final long DosALa32 = (long) 1 << 32;
    private static final long DosALa31 = (long) 1 << 31;
    private static final double dDosALa32 = (double) DosALa32;
    private static final double dDosALa64 = dDosALa32 * dDosALa32;
    private static final double dDosALa96 = dDosALa64 * dDosALa32;
    private static final double dDosALa128 = dDosALa96 * dDosALa32;
    private static final double dDosALa160 = dDosALa128 * dDosALa32;
    private static final long DosALa32_1 = DosALa32 - 1;
    private static long A1;
    private static long A2;
    private static long B1;
    private static long B2;
    private static String UU = "";
    private static String VU = "";
    private static String UL = "";
    private static String VL = "";
    private static String UL1 = "";
    private static String VL1 = "";
    private static String FP = "";
    private static final String MSG = "There are no solutions !!!";
    private static String sq = "^2";
    private static String txt = "";
    private static final String DIVGCD = "Dividing the equation by the greatest common divisor we obtain:\n";
//private static long CX2;
//private static long CXY;
//private static long CY2;
//private static long CX;
//private static long CY;
//private static long C1;
    private static long H1[];
    private static long H2[];
    private static long K1[];
    private static long K2[];
    private static long L1[];
    private static long L2[];
    private static int NbrSols, NbrCo, NbrEqs, EqNbr;
//  private static StringBuffer info;
//  private static BigInteger ValA, ValB, ValC, ValD, ValE, ValF;
//  private volatile Thread calcThread;
//  private static String textError;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {
//      info = new StringBuffer();
            long a, b, c, d, e, f;
            boolean owndata = choice("\"Enter own data (else run standard tests)? (Y/N): ");
            teach = choice("Detailed explanation required? (Y/N): ");
            if (owndata) {
                System.out.println("Solve Diophantine equations of the form: Ax^2 + Bxy + Cy^2 + Dx + Ey + T = 0");
                a = enternum("enter value for A ");
                b = enternum("enter value for B ");
                c = enternum("enter value for C ");
                d = enternum("enter value for D ");
                e = enternum("enter value for E ");
                f = enternum("enter value for F ");
                
                Date OldDate = new Date();
                long Old = OldDate.getTime();  // store clock time
                NbrSols = 0;
                init();
                SolveEquation(a, b, c, d, e, f);
                if (also == false) {
                    w(MSG);
                }
                
                /* calculate elapsed time */
                Date NewDate = new Date();
                long New = NewDate.getTime();    // get clock time
                w("\nCalculation time: ");
                int t = (int) (((New - Old) / 1000 + 86400) % 86400);
                w(t / 3600 + "h " + ((t % 3600) / 60) + "m " + (t % 60) + "s");
                
            } else {
                Date OldDate = new Date();
                long Old = OldDate.getTime();
                NbrSols = 0;
                init();

                System.out.println("Run standard tests");

                System.out.println("\nNo solution exists \n");
                a = 0;
                b = 0;
                c = 0;
                d = 10;
                e = 84;
                f = 15;
                SolveEquation(a, b, c, d, e, f);

                System.out.println("\nExample 1");
                /* x  =  -136 + 42t,  y  =  16 - 5t,  where t is any integer number. */
                a = 0;
                b = 0;
                c = 0;
                d = 10;
                e = 84;
                f = 16;
                SolveEquation(a, b, c, d, e, f);

                System.out.println("\nExample 2");
                a = 0;
                b = 2;
                c = 0;
                d = 5;
                e = 56;
                f = 7;
                /* 
        x  =  (2-56)/2     =   -27, y  =  (266/2-5)/2       =   64
        x  =  (-2-56)/2    =   -29, y  =  [266/(-2)-5]/2    =  -69
        x  =  (14-56)/2    =   -21, y  =  (266/14-5)/2      =    7
        x  =  (-14-56)/2   =   -35, y  =  [266/(-14)-5]/2   =  -12
        x  =  (38-56)/2    =    -9, y  =  (266/38-5)/2      =    1
        x  =  (-38-56)/2   =   -47, y  =  [266/(-38)-5]/2   =   -6
        x  =  (266-56)/2   =   105, y  =  (266/266-5)/2     =   -2
        x  =  (-266-56)/2  =  -161, y  =  [266/(-266)-5]/2  =   -3
        The only 8 solutions to the equation are above 
                 */
                SolveEquation(a, b, c, d, e, f);

                System.out.println("\nExample 3");
                a = 42;
                b = 8;
                c = 15;
                d = 23;
                e = 17;
                f = -4915;
                /* x  =  -11, y = -1 is the only solution */
//            teach = true;
                SolveEquation(a, b, c, d, e, f);
//            teach = false;

                System.out.println("\nExample 4");
                a = 8;
                b = -24;
                c = 18;
                d = 5;
                e = 7;
                f = 16;
                /*
            set 1: x  =  -174t^2 - 17t - 2, y  =  -116t^2 - 21t - 2
            set 2: x  =  -174t^2 - 41t - 4, y  =  -116t^2 - 37t - 4
                 */
                SolveEquation(a, b, c, d, e, f);

                System.out.println("\nProject Euler problem 140");
                a = -1;
                b = 0;
                c = 5;
                d = 0;
                e = 14;
                f = 1;
                SolveEquation(a, b, c, d, e, f);
                /*
            basic solutions are:
            x = ±1, 	y = 0 
            x = ±2, 	y = -3
            x = ±5, 	y = -4
            x = ±7, 	y = 2
            also, for each of the solutions above we can generate an infinite number of other
            solutions using the formulas:
            Xn+1  =  PXn + QYn + K
            Yn+1  =  RXn + SYn + L
            where:
            P  =  -9
            Q  =  -20
            K  =  28
            R  =  -4
            S  =  -9
            L  =  -14
                 */

                System.out.println("\nExample 5\n");
//            teach = true;
                a = 18;
                b = 41;
                c = 19;
                d = 0;
                e = 0;
                f = -24;
                /* 
            this is called a homogeneous equation
            basic solutions are:
            x =  10,                            y = -6
            x = -10,                            y =  6
            X =  7,				Y = -11 
            X = -7				Y =  11
            X = -202				Y =  312 
            X =  202				Y = -312
            x =  10130				Y = -15646
            x = -10130				Y =  15646
            X = -14 247838 (8 digits)		Y =  22 006088 (8 digits)
            X = -9245 980567 328630 (16 digits)	Y =  14280 613101 505146 (17 digits)
            X =  9245 980567 328630 (16 digits)	Y = -14280 613101 505146 (17 digits)
            X =  6582 595298 (10 digits)	Y = -4037 589688 (10 digits)
            X = -6582 595298 (10 digits)	Y =  4037 589688 (10 digits)
            X =  9 258415 606510 (13 digits)	Y = -5 678867 025506 (13 digits)
            X = -9 258415 606510 (13 digits)	Y =  5 678867 025506 (13 digits)
            X =  464 279223 068342 (15 digits) Y = -284 776584 090312 (15 digits)
            X = -464 279223 068342 (15 digits) Y =  284 776584 090312 (15 digits)
                 */
                SolveEquation(a, b, c, d, e, f);

                a = 2;
                b = 5;
                c = 2;
                d = 6;
                e = 6;
                f = 4;
                SolveEquation(a, b, c, d, e, f);

                Date NewDate = new Date();
                long New = NewDate.getTime();
                w("\nCalculation time: ");
                int t = (int) (((New - Old) / 1000 + 86400) % 86400);
                w(t / 3600 + "h " + ((t % 3600) / 60) + "m " + (t % 60) + "s");
            }
        } catch (ArithmeticException err) {
            w("\nexception: " + err.getMessage());
            err.printStackTrace();
        }

        w("\nIf you found any mistake or any solution is missing, please send me an  email to: alpertron@hotmail.com\n");
//      w("<A HREF = \"GBOOK.HTM\">Click here</A> to view or sign my Guestbook.\n");
//      calcThread  =  null;
    }

    /* get a number. If user types invalid format it will crash! */
    private static long enternum(String msg) {
        long result;

        System.out.print(msg);
        result = input.nextLong();
        return result;
    }

    private static boolean choice(String msg) {
        String yn = "";
        while (!yn.startsWith("y") && !yn.startsWith("Y")
                && !yn.startsWith("N") && !yn.startsWith("n")) {
            System.out.print(msg);
            yn = input.next();
        }
        return (yn.startsWith("y") || yn.startsWith("Y"));
    }

    private static void SolveEquation(long a, long bb, long c, long d, long e,
            long f) {
        byte b;
        long Fact1, Fact2;
        long biA[] = new long[6];
        long biB[] = new long[6];
        long biC[] = new long[6];
        boolean teachaux;
        long NegDisc, E1, F1, G, H, K, X1, Y1;
        long Disc, T, g, gcdA_E;
        String x, y, x1, y1;
        also = ExchXY = false;
        sortedSolsX.removeAllElements();
        /* Start fresh for new equation */
        sortedSolsY.removeAllElements();
        allSolsFound = false;
        NbrCo = -1;
        w(txt);

        A = a;
        B = bb;
        C = c;
        D = d;
        E = e;
        F = f;
        ShowEq(A, B, C, D, E, F, "x", "y");
        w("  =  0\n by Dario Alejandro Alpern\n");
        gcdA_E = gcd(A, gcd(B, gcd(C, gcd(D, E))));
        if (teach) {
            w("First of all we must determine the gcd of all coefficients but the constant term, that is: gcd(" + A + ", " + B + ", " + C + ", " + D + ", " + E + ")  =  " + gcdA_E + ".\n");
        }
        if (gcdA_E != 0) {
            if (F % gcdA_E != 0) {
                NoGcd(F);
                return;
            } else {
                A /= gcdA_E;
                /* divide all coefficients by gcd */
                B /= gcdA_E;
                C /= gcdA_E;
                D /= gcdA_E;
                E /= gcdA_E;
                F /= gcdA_E;
                if (teach && gcdA_E != 1) {
                    w(DIVGCD);
                    ShowEq(A, B, C, D, E, F, "x", "y");
                    w("  =  0\n");
                }
            }
        }
        if (D == 0 && A != 0 && C != 0) {
            if (CheckMod(A, B, C, E, F)) {
                return;
            }
        }
        if (E == 0 && A != 0 && C != 0) {
            if (CheckMod(B, C, A, D, F)) {
                return;
            }
        }

        Disc = B * B - 4 * A * C;
        if (Disc > 0
                && sqrt(Disc) * sqrt(Disc) != Disc
                && D == 0 && E == 0
                && F != 0) {
            /* Disc is not a perfect square, D, E  are 0, F not zeor.
		this is a type of homogeneous equation*/
            teachaux = teach;
            if (abs(F) != 1) {
                teach = false;
            }
            LongToDoublePrecLong(A, biA);
            LongToDoublePrecLong(B, biB);
            LongToDoublePrecLong(C, biC);
            GetRoot(biA, biB, biC);
            teach = teachaux;
            G = H = F;
            K = 1;
            T = 3;

            /* remove any double factors from G, add to K*/
            while (G % 4 == 0) {
                G /= 4;
                K *= 2;
            }
            while (abs(G) >= T * T) {
                while (G % (T * T) == 0) {
                    G /= T * T;
                    K *= T;
                }
                T += 2;
            }
            for (T = 1; T * T <= K; T++) {
                if (K % T == 0) {
                    SolContFrac(H, T, A, B, C, "");
                    /* call SolContFrac for each factor of K */
                }
            }
            for (T = T - 1; T > 0; T--) {
                if (K % T == 0 && T * T < K) {
                    SolContFrac(H, K / T, A, B, C, "");
                }
            }
            w("\n");
            if (also == false) {
                return;
            }
            if (teach) {
//              w("<TABLE BORDER = 1><TR><TH>");
            } else {
                ShowAllLargeSolutions();
            }
            if (teach == false) {
                w("If (x,y) is a solution, (-x,-y) is also a solution.\n");
            }
            ShowRecursion((byte) 0);
            also = true;
            return;
        }

        if (A == 0 && C == 0) {
            if (B == 0) {
                /* linear equation: A=B=C=0 */
                b = Linear(D, E, F);
                if (teach) {
//                  w("<TABLE BORDER = 1><TR><TH>");
                }
                PrintLinear(b, "t");
                if (teach) {
//                  w("</TABLE>");
                }
                return;
            } else {
                /* simple hyperbolic; A = C = 0; B ≠ 0 */
                SolveSimpleHyperbolic();
                return;
            }
        }

        /* not linear or simple hyperbolic equation */
        if (teach) {
            w("We try now to solve this equation module 9, 16 and 25.\n");
        }
        if (Mod(9)) {
            return;
        }
        if (Mod(16)) {
            return;
        }
        if (Mod(25)) {
            return;
        }
        if (teach) {
            w("There are solutions, so we must continue.\n");
        }
        if (A == 0) {
            T = A;
            /* swap A and C */
            A = C;
            C = T;
            T = D;
            /* swap D and E */
            D = E;
            E = T;
            ExchXY = true;
        }
        NegDisc = 4 * A * C - B * B;
        E1 = 4 * A * E - 2 * B * D;
        F1 = 4 * A * F - D * D;
        x = (ExchXY ? "y" : "x");
        y = (ExchXY ? "x" : "y");
        x1 = x + "´";
        y1 = y + "´";
        if (NegDisc == 0) {
            /* Parabolic case */
            SolveParabolic(E1, F1, x, y, x1);
            return;
        }

        /* not linear,  parabolic or simple hyperbolic equation */
        g = gcd(NegDisc, E1 / 2);
        CY1 = NegDisc / g;
        CY0 = E1 / 2 / g;
        long D0 = NegDisc;
        long N0 = CY0 * CY0 * g - CY1 * F1;
        long h = gcd(CY1, gcd(g, N0));
        double sqrt = Math.sqrt((double) g * (double) N0);

        if (teach) {
            w("We want to convert this equation to one of the form:\n");
            w(x1 + sq + " + B " + y + sq + " + C " + y + " + D  =  0\n");
            w("Multiplying the equation by " + par(4 * A) + ":\n");
            ShowEq(4 * A * A, 4 * A * B, 4 * A * C, 4 * A * D, 4 * A * E, 4 * A * F, x, y);
            w("  =  0\n");
            ShowLin(4 * A * A, 0, 0, x + sq, y);
            if (B != 0 || D != 0) {
                w(" + (");
                ShowLin(0, 4 * A * B, 4 * A * D, x, y);
                w(")" + x);
            }
            if (C != 0 || E != 0 || F != 0) {
                w(" + (");
                ShowEq(0, 0, 4 * A * C, 0, 4 * A * E, 4 * A * F, x, y);
                w(")  =  0\n");
            }
            if (B != 0 || D != 0) {
                w("To complete the square we should add and subtract:\n(");
                ShowLin(0, B, D, x, y);
                w(")" + sq + "\nThen the equation converts to:\n(");
                ShowLin(2 * A, B, D, x, y);
                w(")" + sq + " + (");
                ShowEq(0, 0, 4 * A * C, 0, 4 * A * E, 4 * A * F, x, y);
                w(") - (");
                ShowEq(0, 0, B * B, 0, 2 * B * D, D * D, x, y);
                w(")  =  0\n");
            }
            w("(");
            ShowLin(2 * A, B, D, x, y);
            w(")" + sq + " + (");
            ShowEq(0, 0, NegDisc, 0, E1, F1, x, y);
            w(")  =  0\nNow we perform the substitution:\n");
            w(x1 + "  =  ");
            ShowLin(2 * A, B, D, x, y);
            w("\nThis gives:\n");
            ShowEq(1, 0, NegDisc, 0, E1, F1, x1, y);
            w("  =  0\n");
        }
        if (D0 > 0) {
            /* elliptical case */
            SolveElliptical(NegDisc, E1, F1, N0, sqrt, x, x1, y);
            return;
        }

        if (teach) {
            if (NegDisc != g * h) {
                w("Multiplying the equation by " + CY1 / h + ":\n");
                ShowEq(CY1 / h, 0, NegDisc * CY1 / h, 0, NegDisc * E1 / g / h, NegDisc * F1 / g / h, x1, y);
            }
            w("  =  0\n");
            if (E1 != 0) {
                Show(g / h, "(", Show(CY1 / h, x1 + sq, (byte) 0));
                ShowEq(CY1 * CY1, 0, 0, 2 * CY0 * CY1, 0, 0, y, "");
                w(")");
                Show1(NegDisc * F1 / g / h, (byte) 1);
                w("  =  0\n");
                Show(g / h, "(", Show(CY1 / h, x1 + sq, (byte) 0));
                if (CY1 != 1) {
                    w(par(CY1) + sq + " ");
                }
                w(y + sq + " + 2*");
                if (CY1 != 1) {
                    w(par(CY1) + "*");
                }
                w(par(CY0) + " " + y + ")");
                Show1(NegDisc * F1 / g / h, (byte) 1);
                w("  =  0\n");
                w("Adding and subtracting " + (g == h ? "" : g / h + " * ") + par(E1 / 2 / g) + sq + ":\n");
            }
            Show(g / h, "(", Show(CY1 / h, x1 + sq, (byte) 0));
            if (CY1 != 1) {
                w(par(CY1) + sq + " ");
            }
            w(y + sq + " + 2*");
            if (CY1 != 1) {
                w(par(CY1) + "*");
            }
            w(par(CY0) + " " + y + " + " + par(CY0) + sq + ")");
            Show1(NegDisc * F1 / g / h, (byte) 1);
            w(" - " + (g == h ? "" : g / h + " * ") + par(E1 / 2 / g) + sq + "  =  0\n");
            Show(g / h, "(", Show(CY1 / h, x1 + sq, (byte) 0));
            ShowLin(0, CY1, CY0, x, y);
            w(")" + sq);
            Show1(-N0 / h, (byte) 1);
            w("  =  0\nMaking the substitution " + y1 + "  =  ");
            ShowLin(0, CY1, CY0, x, y);
            w(":\n");
            ShowLin(CY1 / h, g / h, -N0 / h, x1 + sq, y1 + sq);
            w("  =  0\n");
        }
        long Sqd = sqrt(-D0);
        long N1 = abs(N0 / h);
        long Xc = sqrt(abs(CY1 / h));
        long Yc = sqrt(g / h);
        if (Sqd * Sqd == -D0) {
            if (teach) {
                w("(");
                ShowLin(Yc, Xc, 0, y1, x1);
                w(") (");
                ShowLin(Yc, -Xc, 0, y1, x1);
                w(")  =  " + (N0 / h) + "\n");
            }
            if (N0 == 0) {
                if (teach) {
                    w("\nOne of the parentheses must be zero, so:\n");
                }
                for (T = (Sqd == 0 ? 1 : 0); T < 2; T++) {
                    if (teach) {
//                       w("<LI>");
                        ShowLin(Yc, Xc, 0, y1, x1);
                        w("  =  0\n" + par(Yc) + " (");
                        ShowLin(0, CY1, CY0, x, y);
                        w(") + " + par1(Xc) + " (");
                        ShowLin(2 * A, B, D, x, y);
                        w(")  =  0\n");
                        ShowLin(2 * A * Xc, Xc * B + CY1 * Yc, D * Xc + CY0 * Yc, x, y);
                        w("  =  0\n");
                    }
                    b = Linear(2 * A * Xc, Xc * B + CY1 * Yc, D * Xc + CY0 * Yc);
                    if (teach) {
//                      w("<TABLE BORDER = 1><TR><TH>");
                    }
                    PrintLinear(b, "t");
                    if (teach) {
                        w("\n");
                    }
                    Xc = -Xc;
                }
                Sqd = abs(Sqd);
                if (teach) {
//                  w("</UL>");
                }
                return;
            }
            Disc = sqrt(N1);
            if (teach) {
                w("Now we have to find all factors of " + N1 + ".\n");
            }
            for (T = 1; T <= Disc; T++) {
                if (N1 % T == 0) {
                    Fact1 = T;
                    Fact2 = N0 / h / T;
                    if (teach) {
                        w("Since " + (Fact1 * Fact2) + " is equal to " + Fact1 + " times " + Fact2 + ", we can set:\n");
                        ShowLin(Yc, Xc, 0, y1, x1);
                        w("  =  " + Fact1 + "\n");
                        ShowLin(Yc, -Xc, 0, y1, x1);
                        w("  =  " + Fact2 + "\n");
                        if ((Fact1 - Fact2) % (2 * Xc) == 0 && (Fact1 + Fact2) % (2 * Yc) == 0) {
                            X1 = (Fact1 - Fact2) / (2 * Xc);
                            Y1 = (Fact1 + Fact2) / (2 * Yc);
                            w(x1 + "  =  " + X1 + "\n" + y1 + "  =  " + Y1 + "\n");
                            ShowX1Y1(X1, Y1, A, B, D, CY1, CY0);
                            w("");
                        } else {
                            w("Solving this system we do not obtain integer values for " + x1 + " and " + y1 + ".\n");
                        }
                    } else {
                        if ((Fact1 - Fact2) % (2 * Xc) == 0 && (Fact1 + Fact2) % (2 * Yc) == 0) {
                            X1 = (Fact1 - Fact2) / (2 * Xc);
                            Y1 = (Fact1 + Fact2) / (2 * Yc);
                            ShowX1Y1(X1, Y1, A, B, D, CY1, CY0);
                        }
                    }
                }
            }
            if (teach) {
                w("\n");
            }
            return;
        }
        if (N0 == 0) {
            ShowX1Y1(0, 0, A, B, D, NegDisc, E1 / 2);
            return;
        }
        /* Test if we need two cycles or four cycles */
        LongToDoublePrecLong(A, biA);
        LongToDoublePrecLong(C, biB);
        MultDoublePrecLong(biA, biB, biC);
        LongToDoublePrecLong(1, biA);
        LongToDoublePrecLong(B, biB);
        GetRoot(biA, biB, biC);
        LongToDoublePrecLong(A, biA);
        ContFrac(biA, (byte) 5, (byte) 1, 0, B * B - 4 * A * C, 1, A);
        /* A2, B2 solutions */
        DET = B * B - 4 * A * C;
        G = (2 * A2 + B * B2) % DET;
        H = (B * A2 + 2 * A * C * B2) % DET;
        if (((C * D * (G - 2) + E * (B - H)) % DET != 0 || (D * (B - H) + A * E * (G - 2)) % DET != 0)
                && ((C * D * (-G - 2) + E * (B + H)) % DET != 0 || (D * (B + H) + A * E * (-G - 2)) % DET != 0)) {
            NbrCo *= 2;
        }
        LongToDoublePrecLong(NegDisc / g / h, biA);
        LongToDoublePrecLong(0, biB);
        LongToDoublePrecLong(g / h, biC);
        GetRoot(biA, biB, biC);
        G = H = -N0 / h;
        K = 1;
        T = 3;
        while (G % 4 == 0) {
            G /= 4;
            K *= 2;
        }
        while (abs(G) >= T * T) {
            while (G % (T * T) == 0) {
                G /= T * T;
                K *= T;
            }
            T += 2;
        }
        for (T = 1; T * T <= K; T++) {
            if (K % T == 0) {
                SolContFrac(H, T, NegDisc / g / h, 0, g / h, "'");
            }
        }
        for (T = T - 1; T > 0; T--) {
            if (K % T == 0 && T * T < K) {
                SolContFrac(H, K / T, NegDisc / g / h, 0, g / h, "'");
            }
        }
        w("\n");
        if (also == false) {
            return;
        } else {
            if (teach == false) {
                ShowAllLargeSolutions();
            }
        }
        ShowRecursion((byte) 1);
        also = true;
        return;
    }

    private static void SolveElliptical(long NegDisc, long E1, long F1, long N0,
            double sqrt, String x, String x1, String y) {
        byte b;
        long u, w1, w2;

        /* elliptical case */
        if (N0 < 0) {
            w("The polynomial in " + y + " is always positive,");
            NoSol();
            return;
        }
        double R3 = (-E1 / 2 - sqrt) / NegDisc;
        long R1 = (long) Math.ceil(R3);
        double R4 = (-E1 / 2 + sqrt) / NegDisc;
        long R2 = (long) Math.floor(R4);

        if (teach) {
            w("Since " + x1 + sq + " is always greater than, or equal to zero,\n");
            ShowEq(0, 0, NegDisc, 0, E1, F1, x1, y);
            w(" must be less than, or equal to zero. This is verified in the segment limited by the roots.\n");
            w("The roots are: (-" + par(E1) + " - sqrt(" + E1 + sq + " - 4 * " + par(NegDisc) + " * " + par(F1) + ")) / (2 * " + par(NegDisc) + ")  =  " + R3 + "\n");
            w("and: (-" + par(E1) + "  +  sqrt(" + E1 + sq + " - 4 * " + par(NegDisc) + " * " + par(F1) + ")) / (2 * " + par(NegDisc) + ")  =  " + R4 + "\n");
            if (R2 < R1) {
                w("There are no integers in this range,");
                NoSol();
                return;
            }
            w("All values of " + y + " from " + R1 + " to " + R2 + " should be replaced in \n");
            ShowEq(0, 0, NegDisc, 0, E1, F1, x1, y);
            w(". The result should be the negative of a perfect square.\n");
            b = 0;
            for (u = R1; u <= R2; u++) {
                w1 = -NegDisc * u * u - E1 * u - F1;
                w2 = sqrt(w1);
                if (w2 * w2 == w1) {
                    if (b != 0) {
                        w(", " + u);
                    } else {
                        w("The values of " + y + " are: " + u);
                        b = 1;
                    }
                }
            }
            if (b == 0) {
                w("This is not satisfied by any value of " + y);
                NoSol();
                return;
            }
            w("\n");
        }
        for (u = R1; u <= R2; u++) {
            w1 = -NegDisc * u * u - E1 * u - F1;
            w2 = sqrt(w1);
            if (w2 * w2 == w1) {
                if (teach) {
                    w("" + y + "  =  " + u + "\n");
                    w(x1 + "  =  ");
                    ShowLin(2 * A, B, D, x, y);
                    w("  =  ±sqrt(" + (w2 * w2) + ")  =  ±" + w2 + "\n");
                }
                ShowElipSol(A, B, D, u, x, x1, y, w2);
                if (w2 != 0) {
                    ShowElipSol(A, B, D, u, x, x1, y, -w2);
                }
                if (teach) {
//                  w("</UL>");
                }
            }
        }
        if (teach) {
//          w("</UL>");
        }
        return;
    }

    private static void SolveSimpleHyperbolic() {
        long R, S, T;
        /* simple hyperbolic; A = C = 0; B ≠ 0 */
        R = D * E - B * F;
        if (teach) {
            w("Multiplying by " + B + " we obtain:\n");
            ShowEq(0, B * B, 0, D * B, E * B, 0, "x", "y");
            w("  =  " + (-F * B) + "\n");
            w("Adding " + D * E + " to both sides of the equal sign:\n");
            ShowEq(0, B * B, 0, D * B, E * B, D * E, "x", "y");
            w("  =  " + R + "\n");
            w("Now the left side can be factored as follows:\n");
            w("(");
            Show1(E, Show(B, " x", (byte) 0));
            w(") (");
            Show1(D, Show(B, " y", (byte) 0));
            w(")  =  " + R + "\n");
            if (R != 0) {
                w("Then ");
                Show1(E, Show(B, " x", (byte) 0));
                w(" must be a factor of " + R + ", so we must find the factors of " + R + ":\n ");
            } else {
                w("One of the parentheses must be zero, so:\n");
                Show1(E, Show(B, " x", (byte) 0));
                w("  =  0");
                if (E % B == 0) {
                    w(" means that x  =  " + (-E / B) + " and y could be any integer.\n");
                    also = true;
                } else {
                    w("This equation cannot be solved in integers.\n");
                }
                Show1(D, Show(B, " y", (byte) 0));
                w("  =  0");
                if (D % B == 0) {
                    w(" means that y  =  " + (-D / B) + " and x could be any integer.\n");
                    also = true;
                } else {
                    w("This equation cannot be solved in integers.\n");
                }
                return;
            }
        }
        if (R != 0) {
            S = sqrt(abs(R));
            for (T = 1; T <= S; T++) {
                if (R % T == 0) {
                    SolByFact(R, T, B, D, E);
                    SolByFact(R, -T, B, D, E);
                    if (T * T != abs(R)) {
                        SolByFact(R, R / T, B, D, E);
                        SolByFact(R, -R / T, B, D, E);
                    }
                }
            }
            /* end for */
            if (teach) {
//          w("</UL>");
            }
            return;
        }
        if (E % B == 0) {
            Xi = -E / B;
            Xl = 0;
            Yi = 0;
            Yl = 1;
            PrintLinear((byte) 0, "t");
        }
        if (D % B == 0) {
            Xi = 0;
            Xl = 1;
            Yi = -D / B;
            Yl = 0;
            PrintLinear((byte) 0, "t");
        }
        return;
    }

    private static void SolveParabolic(long E1, long F1, String x, String y,
            String x1) {
        /* Parabolic case */
        byte b;
        String t1;
        long r, s, u, P, P1, P2, Q, Q1, Q2, R, R1, S, S1, S2, T;

        r = gcd(2 * A, B);
        s = 2 * A / r;
        P = r / 2;
        Q = D;
        R = (2 * A * E - B * D) / r;
        S = 2 * A * F / r;
        if (teach) {
            if (s != 1) {
                w("Multiplying the equation by " + par(s) + ":\n");
                ShowEq(A * s, B * s, C * s, D * s, E * s, F * s, x, y);
                w("  =  0\n");
            }
            if (r != 2) {
                w("Extracting the factor " + r / 2 + " in the quadratic terms:\n");
                w(par1(r / 2) + " (");
                ShowEq(s * s, 2 * s * B / r, 2 * s * C / r, 0, 0, 0, x, y);
                w(")");
                b = Show(D * s, " " + x, (byte) 1);
                b = Show(E * s, " " + y, b);
                Show1(F * s, b);
                w("  =  0\n");
            }
            if (B != 0) {
                w(par1(r / 2) + "(");
                ShowLin(s, B / r, 0, x, y);
                w(")" + sq);
                if (D != 0 || E != 0 || F != 0) {
                    w(" + (");
                    ShowLin(D * s, E * s, F * s, x, y);
                    w(")");
                }
                w("  =  0\n");
                if (D != 0) {
                    w("Adding and subtracting " + par1(B * D / r) + "" + y + ":\n");
                    w(par1(r / 2) + "(");
                    ShowLin(s, B / r, 0, x, y);
                    w(")" + sq);
                    w("  +  " + par1(D) + " (");
                    ShowLin(s, B / r, 0, x, y);
                    w(")");
                }
                if (E1 != 0 || F != 0) {
                    w("  +  (");
                    ShowLin(0, R, F * s, x, y);
                    w(")");
                }
            } else {
                w(par1(r / 2) + "(");
                ShowLin(s, B / r, 0, x, y);
                w(")" + sq);
                if (D != 0) {
                    w("  +  " + par1(D) + " (");
                    ShowLin(s, B / r, 0, x, y);
                    w(")");
                }
                if (E1 != 0 || F != 0) {
                    w(" + (");
                    ShowLin(0, R, F * s, x, y);
                    w(")");
                }
            }
            w("  =  0\nNow we perform the substitution:\n");
            w(x1 + "  =  ");
            ShowLin(s, B / r, 0, x, y);
            w("\nThis gives:\n");
            ShowEq(r / 2, 0, 0, D, R, F * s, x1, y);
            w("  =  0\n");
        }
        if (E1 == 0) {
            if (teach) {
                w("This can be solved by the standard quadratic equation formula:\n");
                w("The roots are: " + x1 + "  =  ");
                if (D != 0) {
                    w("(" + par(-D) + " ± sqrt(" + (-F1) + "))");
                } else {
                    w(" ± sqrt(" + (-F1) + ")");
                }
                w(" / " + par(r) + "\n");
            }
            if (F1 > 0) {
                if (teach) {
                    w("This quadratic equation has no solution in reals, so it has no solution in integers.\n");
                    also = true;
                }
                return;
            }
            T = (long) Math.floor((-D + Math.sqrt(-F1)) / r + 0.5);
            if (r * T * T / 2 + D * T + 2 * A * F / r == 0) {
                if (teach) {
                    w("The first root is: " + x1 + "  =  " + T + "\n");
                    ShowLin(s, B / r, 0, x, y);
                    w("  =  " + T + "\n");
                }
                b = Linear(s, B / r, -T);
                if (teach) {
//                 w("<TABLE BORDER = 1><TR><TH>");
                }
                PrintLinear(b, "t");
                if (teach) {
//                  w("</TABLE>");
                }
            } else {
                w("The first root is not an integer.\n");
            }
            if (F1 == 0) {
                return;
            }
            T = (long) Math.floor((-D - Math.sqrt(-F1)) / r + 0.5);
            if (r * T * T / 2 + D * T + 2 * A * F / r == 0) {
                if (teach) {
                    w("The second root is: " + x1 + "  =  " + T + "\n");
                    ShowLin(s, B / r, 0, x, y);
                    w("  =  " + T + "\n");
                }
                b = Linear(s, B / r, -T);
                if (teach) {
//                 w("<TABLE BORDER = 1><TR><TH>");
                }
                PrintLinear(b, "t");
                if (teach) {
//                  w("</TABLE>");
                }
            } else {
                w("The second root is not an integer.\n");
            }
            return;
        }
        long N = DivideGcd(P, Q, R, S, x1, y);
        if (N == 0) {
            return;
        }
        P /= N;
        Q /= N;
        R /= N;
        S /= N;
        int numEq = 1;
        for (u = 0; u < abs(R); u++) {
            T = P * u * u + Q * u + S;
            if (T % R == 0) {
                int numEq2 = 3;
                P1 = B * P * R / r;
                Q1 = abs(R) + B * (2 * P * u + Q) * abs(R) / R / r;
                R1 = -s;
                S1 = B * T / R / r + u;
                P2 = -P * R;
                Q2 = (2 * P * u + Q) * abs(R) / -R;
                S2 = -T / R;
                if (teach) {
                    t1 = ((u == 0) ? "" : u + " + ") + abs(R) + "t";
                    w("" + x1 + "  =  " + t1 + "   (" + numEq + ")");
                    w("\nReplacing this in the equation shown above:\n");
                    w(par1(-R) + y + "  =  ");
                    ShowEq(P, 0, 0, Q, 0, S, "(" + t1 + ")", y);
                    w("\n" + par1(-R) + y + "  =  ");
                    ShowEq(P * R * R, 0, 0, (2 * P * u + Q) * abs(R), 0, T, "t", y);
                    w("\n" + y + "  =  ");
                    ShowEq(P2, 0, 0, Q2, 0, S2, "t", y);
                    w("   (" + (numEq + 1) + ")\nFrom (" + numEq + "): ");
                    ShowLin(s, B / r, 0, x, y);
                    w("  =  " + t1 + "\nReplacing (" + (numEq + 1) + ") here:\n");
                    w(par1(s) + x + "  =  ");
                    ShowEq(P1, 0, 0, Q1, 0, S1, "t", y);
                    w("   (" + (numEq + 2) + ")\n");
                }
                long N1 = DivideGcd(P1, Q1, R1, S1, "t", x);
                if (N1 == 0) {
                    continue;
                }
                P1 /= N1;
                Q1 /= N1;
                R1 /= N1;
                S1 /= N1;
                for (long u1 = 0; u1 < abs(R1); u1++) {
                    long T1 = P1 * u1 * u1 + Q1 * u1 + S1;
                    if (T1 % R1 == 0) {
                        if (teach && abs(R1) != 1) {
                            t1 = ((u1 == 0) ? "" : u1 + "  +  ") + abs(R1) + "u";
                            w("t  =  " + t1 + "   (" + (numEq + numEq2) + ")");
                            w("\nReplacing this in (" + (numEq + 2) + "):\n");
                            w(par1(-R1) + x + "  =  ");
                            ShowEq(P1, 0, 0, Q1, 0, S1, "(" + t1 + ")", y);
                            w("\n" + par1(-R1) + x + "  =  ");
                            ShowEq(P1 * R1 * R1, 0, 0, (2 * P1 * u1 + Q1) * abs(R1), 0, T1, "u", "");
                            w("\n" + x + "  =  ");
                            ShowEq(-P1 * R1, 0, 0, (2 * P1 * u1 + Q1) * abs(R1) / -R1, 0, -T1 / R1, "u", y);
                            w("\nFrom (" + (numEq + 1) + ") and (" + (numEq + numEq2) + "):\n" + y + "  =  ");
                            ShowEq(P2, 0, 0, Q2, 0, S2, "(" + t1 + ")", y);
                            w("\n" + y + "  =  ");
                            ShowEq(P2 * R1 * R1, 0, 0, (2 * P2 * u1 + Q2) * abs(R1), 0, P2 * u1 * u1 + Q2 * u1 + S2, "u", "");
                            w("\n");
                            numEq2++;
                        }
                        if (teach) {
//                          w("<TABLE BORDER = 1><TR><TH>");
                        }
                        showAlso();
                        w("x  =  ");
                        if (ExchXY == false) {
                            ShowEq(-P1 * R1, 0, 0, (2 * P1 * u1 + Q1) * abs(R1) / -R1, 0, -T1 / R1, "u", "");
                            w("\ny  =  ");
                            ShowEq(P2 * R1 * R1, 0, 0, (2 * P2 * u1 + Q2) * abs(R1), 0, P2 * u1 * u1 + Q2 * u1 + S2, "u", "");
                        } else {
                            ShowEq(P2 * R1 * R1, 0, 0, (2 * P2 * u1 + Q2) * abs(R1), 0, P2 * u1 * u1 + Q2 * u1 + S2, "u", "");
                            w("\ny  =  ");
                            ShowEq(-P1 * R1, 0, 0, (2 * P1 * u1 + Q1) * abs(R1) / -R1, 0, -T1 / R1, "u", "");
                        }
                        if (teach) {
//                          w("</TABLE>");
                        }
                        w("\n");
                    }
                }
                if (teach && abs(R1) != 1) {
//                  w("</UL>");
                }
                numEq += numEq2;
            }
        }
        if (teach) {
//          w("</UL>");
        }
    }

    private static void NoSol() {
        w(" so there are no integer solutions.\n");
        also = true;
    }

    private static void NoGcd(long F) {
        if (teach) {
            w("This gcd is not a divisor of the constant term (" + F + "),");
        }
        NoSol();
    }

    private static void w(String texto) {
//  info = info.append(texto);
        System.out.print(texto);
    }

    private static long gcd(long M, long N) {
        long P = M;
        long Q = N;
        while (P != 0) {
            long R = Q % P;
            Q = P;
            P = R;
        }
        return abs(Q);
    }

    private static long abs(long num) {
        return (num < 0) ? -num : num;
    }

    private static long floordiv(long num, long den) {
        if ((num < 0 && den > 0 || num > 0 && den < 0) && num % den != 0) {
            return num / den - 1;
        }
        return num / den;
    }

    private static long ceildiv(long num, long den) {
        if ((num > 0 && den > 0 || num < 0 && den < 0) && num % den != 0) {
            return num / den + 1;
        }
        return num / den;
    }

    private static long sqrt(long num) {
        long num1 = 0;
        long num2 = (long) 65536 * (long) 32768;
        /* 2^31 */
        while (num2 != 0) {
            if ((num1 + num2) * (num1 + num2) <= num) {
                num1 += num2;
            }
            num2 /= 2;
        }
        return num1;
    }

    /* Calculate factor1*factor2 mod Mod */
    private static long MultMod(long factor1, long factor2, long Mod) {
        long aux;
        aux = factor1 * factor2 - Mod * (long) (((double) factor1 * (double) factor2) / (double) Mod);
        if (aux >= Mod) {
            return aux - Mod;
        }
        if (aux < 0) {
            return aux + Mod;
        }
        return aux;
    }

    private static long ModPow(long Base, long Exp, long Mod) {
        long Pot, Pwr, mask, value;
        if (Exp == 0) {
            return 1L;
        }
        mask = 1L;
        Pot = 1L;
        Pwr = Base;
        value = 0;
        while (true) {
            if ((Exp & mask) != 0) {
                Pot = MultMod(Pot, Pwr, Mod);
                value += mask;
                if (value == Exp) {
                    return Pot;
                }
            }
            mask *= 2L;
            Pwr = MultMod(Pwr, Pwr, Mod);
        }
    }

    private static long ModInv(long Val, long Mod) {
        long U1, U3, V1, V3, Aux, Q;
        U1 = 1;
        U3 = Val;
        V1 = 0;
        V3 = Mod;
        while (V3 != 0) {
            Q = U3 / V3;
            Aux = U1 - V1 * Q;
            U1 = V1;
            V1 = Aux;
            Aux = U3 - V3 * Q;
            U3 = V3;
            V3 = Aux;
        }
        return (U1 + Mod) % Mod;
    }

    private static boolean Mod(long mod) {
        for (long x = 0; x < mod; x++) {
            long z = A * x * x + D * x + F;
            long t = B * x + E;
            for (long y = 0; y < mod; y++) {
                if ((z + y * (t + C * y)) % mod == 0) {
                    if (teach) {
                        w("Solution found with x=" + x + " y=" + y + " mod=" + mod + "\n");
                    }
                    return false;
                }
            }
        }
        if (teach) {
            w("No solutions found using mod " + mod + ",");
            NoSol();
        }
        return true;
    }

    private static long DivideGcd(long P, long Q, long R, long S, String x1,
            String y) {
        byte t;
        long N = gcd(P, gcd(Q, R));
        if (N != 1) {
            if (teach) {
                w("We must get the gcd of all terms except the constant:\n");
                w("gcd(" + P);
                if (Q != 0) {
                    w(", " + Q);
                }
                if (R != 0) {
                    w(", " + R);
                }
                w(")  =  " + N + "\n");
            }
            if (S % N != 0) {
                NoGcd(S);
                return 0;
            }
            if (teach) {
                w(DIVGCD);
                P /= N;
                Q /= N;
                R /= N;
                S /= N;
                ShowEq(P, 0, 0, Q, R, S, x1, y);
                w("  =  0\n");
            }
        }
        if (teach) {
            if (abs(R) != 1) {
                w("This means that ");
                ShowEq(P, 0, 0, Q, 0, S, x1, y);
                w(" should be a multiple of " + abs(R) + "\n");
                w("To determine this, we should try all values of " + x1 + " from 0 to " + (abs(R) - 1) + " to check if the condition holds.\n");
                t = 0;
                for (long u = 0; u < abs(R); u++) {
                    if ((P * u * u + Q * u + S) % R == 0) {
                        if (t != 0) {
                            w(", " + u);
                        } else {
                            w("The values of " + x1 + " (mod " + abs(R) + ") are: " + u);
                            t = 1;
                        }
                    }
                }
                if (t == 0) {
                    w("The modular equation is not satisfied by any " + x1);
                    NoSol();
                    return 0;
                }
//               w("<UL>");
            } else {
                t = 1;
            }
        }
        return N;
    }

    private static String par(long num) {
        if (num < 0) {
            return "(" + num + ")";
        }
        return "" + num;
    }

    private static String par1(long num) {
        return (num == 1) ? "" : par(num);
    }

    private static byte Linear(long D, long E, long F) {
        long Tx;
        int t;
        if (teach) {
            w("This is a linear equation ");
            ShowLin(D, E, F, "x", "y");
            w("  =  0\n");
        }
        if (D == 0) {
            if (E == 0) {
                if (F != 0) {
                    return 1;             // No solutions
                } else {
                    return 2;             // Infinite number of solutions
                }
            }
            if (F % E != 0) {
                return 1;               // No solutions
            } else {
                Xi = 0;
                Xl = 1;
                Yi = -F / E;
                Yl = 0;
                return 0;               // Solution found
            }
        }
        if (E == 0) {
            if (F % D != 0) {
                return 1;               // No solutions
            } else {
                Xi = -F / D;
                Xl = 0;
                Yi = 0;
                Yl = 1;
                return 0;               // Solution found
            }
        }
        long Q = gcd(D, E);
        if (Q != 1 && Q != -1) {
            if (teach) {
                w("To solve it, we first find the gcd of the linear coefficients, that is: gcd(" + D + ", " + E + ")  =  " + Q + ".\n");
            }
            if (F % Q != 0) {
                NoGcd(F);
                return 1;               // No solutions
            }
            D = D / Q;
            E = E / Q;
            F = F / Q;
        }
        if (teach) {
            w(DIVGCD);
            ShowLin(D, E, F, "x", "y");
            w("  =  0\n");
            w("Now we must apply the Generalized Euclidean algorithm:\n");
        }
        long U1 = 1;
        long U2 = 0;
        long U3 = D;
        long V1 = 0;
        long V2 = 1;
        long V3 = E;
        t = 1;
        while (V3 != 0) {
            if (teach) {
                w("Step " + t + ": " + par(U1) + " * " + par(D) + "  +  " + par(U2) + " * " + par(E) + "  =  " + U3 + "\n");
            }
            long q = floordiv(U3, V3);
            long T1 = U1 - q * V1;
            long T2 = U2 - q * V2;
            long T3 = U3 - q * V3;
            U1 = V1;
            U2 = V2;
            U3 = V3;
            V1 = T1;
            V2 = T2;
            V3 = T3;
            t++;
        }
        Xi = -U1 * F / U3;
        Xl = E;
        Yi = -U2 * F / U3;
        Yl = -D;
        if (teach) {
            w("Step " + t + ": " + par(U1) + " * " + par(D) + "  +  " + par(U2) + " * " + par(E) + "  =  " + U3 + "\n");
            w("\nMultiplying the last equation by " + par(-F / U3) + " we obtain:\n");
            w(par(Xi) + " * " + par(D) + "  +  " + par(Yi) + " * " + par(E) + "  =  " + (-F) + "\n");
            w("Adding and subtracting " + par(D) + " * " + par(E) + " t' we obtain:\n");
            w("(" + Xi + "  +  " + par(E) + " t') * " + par(D) + " + " + Yi + " - " + par(D) + " t') * " + par(E) + "  =  " + (-F) + "\n");
            w("So, the solution is given by the set:\n");
            PrintLinear((byte) 0, "t'");
        }
        V1 = D * D + E * E;
        Tx = floordiv((D * Yi - E * Xi) + V1 / 2, V1);
        if (teach) {
            w("By making the substitution t  =  " + Tx + "  +  t' we finally obtain:\n");
        }
        Xi += E * Tx;
        Yi += -D * Tx;
        if (Xl < 0 && Yl < 0) {
            Xl = -Xl;
            Yl = -Yl;
        }
        return 0;
    }

    private static void showAlso() {
        if (also) {
            w("and also:\n");
        } else {
            also = true;
        }
    }

    private static byte PrintLinear(byte Ret, String va) {
        if (Ret == 1) {
            return 0;
        }
        if (va.equals("t")) {
            showAlso();
        }
        if (Ret == 2) {
            w("x, y: any integer");
            return 1;
        }
        if (ExchXY) {
            long T = Xi;   // swap Xi and Yi
            Xi = Yi;
            Yi = T;
            T = Xl;        // swap X1 and Y1
            Xl = Yl;
            Yl = T;
        }
        w("x  =  ");
        if (Xi == 0 && Xl == 0) {
            w("0");
        }
        if (Xi != 0) {
            w("" + Xi);
        }
        if (Xl < 0) {
            w(" - ");
        }
        if (Xl > 0 && Xi != 0) {
            w(" + ");
        }
        if (Xl != 0) {
            if (abs(Xl) == 1) {
                w(" " + va);
            } else {
                w(abs(Xl) + " " + va);
            }
        }
        w("\ny  =  ");
        if (Yi == 0 && Yl == 0) {
            w("0");
        }
        if (Yi != 0) {
            w("" + Yi);
        }
        if (Yl < 0) {
            w(" - ");
        }
        if (Yl > 0 && Yi != 0) {
            w(" + ");
        }
        if (Yl != 0) {
            if (abs(Yl) == 1) {
                w(" " + va);
            } else {
                w(abs(Yl) + " " + va);
            }
        }
        w("\n");
        return 0;
    }

    private static void ShowXY(long X, long Y) {
        showAlso();
        if (ExchXY == false) {
            w("x  =  " + X);
            w("   y  =  " + Y + "\n");
        } else {
            w("x  =  " + Y);
            w("   y  =  " + X + "\n");
        }
    }

    private static void ShowX1Y1(long X1, long Y1, long A, long B, long D,
            long D1, long E1) {
        if ((Y1 - E1) % D1 == 0) {
            long Y = (Y1 - E1) / D1;
            if ((X1 - B * Y - D) % (2 * A) == 0) {
                long X = (X1 - B * Y - D) / (2 * A);
                ShowXY(X, Y);
            }
            if (X1 != 0 && (-X1 - B * Y - D) % (2 * A) == 0) {
                long X = (-X1 - B * Y - D) / (2 * A);
                ShowXY(X, Y);
            }
        }
        if (Y1 != 0 && (-Y1 - E1) % D1 == 0) {
            long Y = (-Y1 - E1) / D1;
            if ((X1 - B * Y - D) % (2 * A) == 0) {
                long X = (X1 - B * Y - D) / (2 * A);
                ShowXY(X, Y);
            }
            if (X1 != 0 && (-X1 - B * Y - D) % (2 * A) == 0) {
                long X = (-X1 - B * Y - D) / (2 * A);
                ShowXY(X, Y);
            }
        }
    }

    private static byte Show(long num, String str, byte t) {
        if (t == 2) {
            txt = "";
        }
        if (0 != num) {
            String str1 = "";
            if ((t & 1) != 0 && num > 0) {
                str1 += " +";
            }
            if (num < 0) {
                str1 += " -";
            }
            if (abs(num) != 1) {
                str1 += " " + abs(num);
            }
            str1 += str;
            if ((t & 2) != 0) {
                txt += str1;
            } else {
                w(str1);
            }
            return (byte) (t | 1);
        }
        return t;
    }

    private static void Show1(long num, byte t) {
        byte u = Show(num, "", t);
        if ((u & 1) == 0 || abs(num) == 1) {
            if ((u & 2) != 0) {
                txt += abs(num);
            } else {
                w("" + abs(num));
            }
        }
    }

    private static void ShowElipSol(long A, long B, long D, long u, String x,
            String x1, String y, long w2) {
        if (teach) {
//          w("<LI>");
            ShowLin(2 * A, B, D, x, y);
            w("  =  " + w2 + "\n");
            ShowLin(2 * A, B, D, x, par(u));
            w("  =  " + w2 + "\n");
            w(par1(2 * A) + x + "  =  " + (w2 - D - B * u) + "\n");
            if ((w2 - D - B * u) % (2 * A) == 0) {
                w(x + "  =  " + ((w2 - D - B * u) / (2 * A)));
            } else {
                w("There is no integer solution for " + x + "\n");
            }
        }
        if ((w2 - D - B * u) % (2 * A) == 0) {
            if (teach) {
//              w("<TABLE BORDER = 1><TR><TH>");
            }
            ShowXY((w2 - D - B * u) / (2 * A), u);
            if (teach) {
                w("\n");
            } else {
                w("\n");
            }
            also = true;
        }
    }

    private static void SolByFact(long R, long T, long B, long D, long E) {
        if (teach) {
            w("" + T + " is a factor of " + R + ", so we can set:\n");
            Show1(E, Show(B, " x", (byte) 0));
            w("  =  " + T);
            if (E != 0) {
                w("\nThis means that ");
                Show(B, " x  =  ", (byte) 0);
                w("" + (T - E));
            }
            w("\n");
            Show1(D, Show(B, " y", (byte) 0));
            w("  =  " + R + "/" + par(T) + "  =  " + R / T);
            if (D != 0) {
                w("\nThis means that ");
                Show(B, " y  =  ", (byte) 0);
                w("" + (R / T - D));
            }
            w("\n");
        }
        if ((T - E) % B == 0 && (R / T - D) % B == 0) {
            if (teach) {
//              w("<TABLE BORDER = 1><TR><TH>");
            }
            ShowXY((T - E) / B, (R / T - D) / B);
            if (teach) {
//              w("</TABLE>");
            }
        } else {
            if (teach) {
                w("These equations are not valid in integers.\n");
            }
        }
    }

    private static void ShowLin(long D, long E, long F, String x, String y) {
        byte t = Show(D, " " + x, (byte) 0);
        t = Show(E, " " + y, t);
        Show1(F, t);
    }

    private static void ShowEq(long A, long B, long C, long D, long E, long F,
            String x, String y) {
        byte t = Show(A, " " + x + sq, (byte) 0);
        t = Show(B, " " + x + y, t);
        t = Show(C, " " + y + sq, t);
        t = Show(D, " " + x, t);
        t = Show(E, " " + y, t);
        Show1(F, t);
    }

    public static void init() {
        H1 = new long[20000];
        H2 = new long[20000];
        K1 = new long[20000];
        K2 = new long[20000];
        L1 = new long[20000];
        L2 = new long[20000];
    }

    private static void ChangeSign(long Nbr[], byte type) {
        long Cy = BILL_BILL;
        if (LargeNumberIsZero(Nbr)) {
            return;
        }
        for (int s = 1; s <= abs(Nbr[0]); s++) {
            Nbr[s] = Cy - Nbr[s];
            if (Nbr[s] == BILL_BILL) {
                Nbr[s] = 0;
            } else {
                Cy = BILL_BILL - 1;
            }
        }
        if (type == 1) {
            Nbr[0] = -Nbr[0];
        }
    }

    private static void ShowLargeNumber(long Nbr[]) {
        String value;
        int i;

        if (Nbr[0] < 0) {
            w("-");
            ChangeSign(Nbr, (byte) 0);
        }
        int s = (int) abs(Nbr[0]);
        while (s > 1 && Nbr[s] == 0) {
            s--;
        }
        int Dig = 0;
        for (long R = Nbr[s]; R >= 1; R /= 10) {
            Dig++;
        }
        value = Nbr[s] + "";
        while (s > 1) {
            Dig += 18;
            value += (BILL_BILL + Nbr[--s] + "").substring(1);
        }
        i = (value.length() - 1) % digitsInGroup + 1;
        w(value.substring(0, i));
        while (i < value.length()) {
            w(" " + value.substring(i, i + digitsInGroup));
            i += digitsInGroup;
        }
        if (Dig > 6) {
            w(" (" + Dig + " digits)");
        }
        if (Nbr[0] < 0) {
            ChangeSign(Nbr, (byte) 0);
        }
    }

    private static void ExtendSign(long Nbr[]) {
        if (Nbr[0] < 0) {
            Nbr[1 - (int) Nbr[0]] = Nbr[2 - (int) Nbr[0]] = BILL_BILL - 1;
        } else {
            Nbr[1 + (int) Nbr[0]] = Nbr[2 + (int) Nbr[0]] = 0;
        }
    }

    private static void AdjustSign(long Nbr[]) {
        int S = (int) abs(Nbr[0]) + 2;
        if (Nbr[S] * 2 >= BILL_BILL) {
            while (Nbr[S] == BILL_BILL - 1 && --S > abs(Nbr[0])) {
            }
            Nbr[0] = -S;
        } else {
            while (Nbr[S] == 0 && --S > abs(Nbr[0])) {
            }
            Nbr[0] = S;
        }
    }

    private static boolean LargeNumberIsZero(long Nbr[]) {
        int i;
        if (Nbr[0] > 0) {
            for (i = (int) abs(Nbr[0]); i > 0; i--) {
                if (Nbr[i] != 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static long DivLargeNumber(long Nbr[], long Coef, long Dest[]) {
        long Q, R, Rem, C;
        int i;
        if (LargeNumberIsZero(Nbr)) {
            return 0;
        };
        C = (Coef < 0 ? -Coef : Coef);
        Rem = (Nbr[0] < 0 ? C - 1 : 0);
        for (i = (int) (Nbr[0] < 0 ? -Nbr[0] : Nbr[0]); i > 0; i--) {
            R = Rem * BILL + Nbr[i] / BILL;
            Q = (long) ((double) R / (double) C);
            R = R - C * Q;
            if (R >= C && R < C + C) {
                Q++;
                R -= C;
            } else {
                if (R >= -C && R < 0) {
                    Q--;
                    R += C;
                }
            }
            Rem = Q;
            R = R * BILL + Nbr[i] % BILL;
            Q = (long) ((double) R / (double) C);
            R = R - C * Q;
            if (R >= C && R < C + C) {
                Q++;
                R -= C;
            } else {
                if (R >= -C && R < 0) {
                    Q--;
                    R += C;
                }
            }
            Dest[i] = Rem * BILL + Q;
            Rem = R;
        }
        Dest[0] = Nbr[0];
        AdjustSign(Dest);
        if (Coef < 0) {
            ChangeSign(Dest, (byte) 1);
        }
        return Rem;
    }

    private static void Mult2LargeNumbers(long Nbr1[], long Nbr2[], long Dest[]) {
        long LSM1, MSM1, LSM2, MSM2;
        long Re2, Re1, Re4, Tmp, MaxJ;
        int i, j;
        if (Nbr1[0] < 0) {
            ChangeSign(Nbr1, (byte) 0);
        }
        if (Nbr1 != Nbr2 && Nbr2[0] < 0) {
            ChangeSign(Nbr2, (byte) 0);
        }
        i = (int) abs(Nbr1[0]);
        j = (int) abs(Nbr2[0]);
        Nbr1[i + 1] = 0;
        Nbr1[i + 2] = 0;
        Nbr2[j + 1] = 0;
        Nbr2[j + 2] = 0;
        MaxJ = j + 2;
        for (i = i + j + 3; i > 0; i--) {
            Dest[i] = 0;
        }
        for (i = 1; i <= abs(Nbr1[0]) + 2; i++) {
            LSM1 = Nbr1[i] % BILL;
            MSM1 = Nbr1[i] / BILL;
            long CyL = 0;
            long CyH = 0;
            for (j = 1; j <= MaxJ; j++) {
                LSM2 = Nbr2[j] % BILL;
                MSM2 = Nbr2[j] / BILL;
                Tmp = LSM1 * LSM2 + CyL + Dest[i + j - 1];
                Re2 = Tmp / BILL;
                Re1 = Tmp % BILL;
                Tmp = MSM1 * LSM2 + LSM1 * MSM2 + CyH + Re2;
                Re4 = Tmp / BILL;
                Re1 += (Tmp % BILL) * BILL;
                Tmp = MSM1 * MSM2 + Re4 + Re1 / BILL_BILL;
                CyH = Tmp / BILL;
                CyL = Tmp % BILL;
                Dest[i + j - 1] = Re1 % BILL_BILL;
            }
        }
        Dest[0] = abs(Nbr1[0]) + abs(Nbr2[0]) + 2;
        if (Nbr1[0] * Nbr2[0] < 0) {
            ChangeSign(Dest, (byte) 0);
        }
        Dest[0] -= 2;
        if (Nbr1 != Nbr2 && Nbr2[0] < 0) {
            ChangeSign(Nbr2, (byte) 0);
        }
        if (Nbr1[0] < 0) {
            ChangeSign(Nbr1, (byte) 0);
        }
        AdjustSign(Dest);
    }

    private static void MultLargeNumber(long Coef, long Nbr[], long Dest[]) {
        long LSM, MSM, Re2, Re1, Re4, Tmp;
        ExtendSign(Nbr);
        long CfH = Coef / BILL;
        long CfL = Coef % BILL;
        long CyL = 0;
        long CyH = 0;
        for (int S = 1; S <= abs(Nbr[0]) + 2; S++) {
            LSM = Nbr[S] % BILL;
            MSM = Nbr[S] / BILL;
            Tmp = CfL * LSM + CyL;
            Re2 = Tmp / BILL;
            Re1 = Tmp % BILL;
            Tmp = CfH * LSM + CfL * MSM + CyH + Re2;
            Re4 = Tmp / BILL;
            Re1 += (Tmp % BILL) * BILL;
            Tmp = CfH * MSM + Re4 - (Re1 < 0 ? 1 : 0);
            CyH = Tmp / BILL;
            CyL = Tmp % BILL;
            Dest[S] = (Re1 + BILL_BILL) % BILL_BILL;
        }
        Dest[0] = Nbr[0];
        AdjustSign(Dest);
    }

    private static void AddLargeNumbers(long Nbr1[], long Nbr2[], long Sum[]) {
        int S = (int) abs(Nbr1[0]);
        long T = (Nbr1[0] < 0 ? BILL_BILL - 1 : 0);
        long U = (Nbr2[0] < 0 ? BILL_BILL - 1 : 0);
        while (abs(Nbr2[0]) > S) {
            Nbr1[++S] = T;
        }
        S = (int) abs(Nbr2[0]);
        while (S < abs(Nbr1[0])) {
            Nbr2[++S] = U;
        }
        Nbr1[S + 1] = Nbr1[S + 2] = T;
        Nbr2[S + 1] = Nbr2[S + 2] = U;
        Sum[0] = S;
        long Cy = 0;
        for (S = 1; S <= Sum[0] + 2; S++) {
            Cy += Nbr1[S] + Nbr2[S];
            Sum[S] = Cy % BILL_BILL;
            Cy /= BILL_BILL;
        }
        AdjustSign(Sum);
    }

    private static void AddLargeLong(long Src[], long Nbr, long Dest[]) {
        ExtendSign(Src);
        long Cy = Nbr;
        for (int i = 1; i < abs(Src[0]) + 3; i++) {
            Dest[i] = Src[i] + (Cy < 0 ? Cy + BILL_BILL : Cy);
            Cy = Dest[i] / BILL_BILL - (Cy < 0 ? 1 : 0);
            Dest[i] %= BILL_BILL;
        }
        Dest[0] = abs(Src[0]);
        AdjustSign(Dest);
    }

    private static void MultAddLargeNumbers(long CPrev, long Prev[], long CAct,
            long Act[], long Dest[]) {
        long Tmp, Re1, Re2, Re4, PL, PH, AL, AH;
        int S = (int) abs(Act[0]);
        long T = (Prev[0] < 0 ? BILL_BILL - 1 : 0);
        long U = (Act[0] < 0 ? BILL_BILL - 1 : 0);
        while (S < abs(Prev[0])) {
            Act[++S] = U;
        }
        S = (int) abs(Prev[0]);
        while (S < abs(Act[0])) {
            Prev[++S] = T;
        }
        Prev[S + 1] = Prev[S + 2] = T;
        Act[S + 1] = Act[S + 2] = U;
        Dest[0] = S;
        long CPH = CPrev / BILL;
        long CPL = CPrev % BILL;
        long CAH = CAct / BILL;
        long CAL = CAct % BILL;
        long CyL = 0;
        long CyH = 0;
        for (S = 1; S <= Dest[0] + 2; S++) {
            PL = Prev[S] % BILL;
            PH = Prev[S] / BILL;
            AL = Act[S] % BILL;
            AH = Act[S] / BILL;
            Tmp = PL * CPL + AL * CAL + CyL;
            Re2 = Tmp / BILL;
            Re1 = Tmp % BILL;
            Tmp = PH * CPL + PL * CPH + AH * CAL + AL * CAH + CyH + Re2;
            Re4 = Tmp / BILL;
            Re1 += (Tmp % BILL) * BILL;
            Tmp = PH * CPH + AH * CAH + Re4 - (Re1 < 0 ? 1 : 0);
            CyH = Tmp / BILL;
            CyL = Tmp % BILL;
            Dest[S] = (Re1 + BILL_BILL) % BILL_BILL;
        }
        AdjustSign(Dest);
    }

    private static void LongToDoublePrecLong(long Nbr, long Out[]) {
        Out[0] = Nbr & DosALa32_1;
        Out[1] = Nbr >>> 32;
        Out[2] = Out[3] = Out[4] = Out[5] = (Nbr < 0 ? DosALa32_1 : 0);
    }

    private static void ChSignDoublePrecLong(long Nbr[]) {
        long Cy = 0;
        for (int i = 0; i < 6; i++) {
            Cy -= Nbr[i];
            Nbr[i] = (Cy >= 0 ? Cy : Cy + DosALa32);
            Cy = (Cy >= 0 ? 0 : -1);
        }
    }

    private static void AddDoublePrecLong(long Nbr1[], long Nbr2[], long Sum[]) {
        long Cy = 0;
        for (int i = 0; i < 6; i++) {
            Cy += Nbr1[i] + Nbr2[i];
            Sum[i] = Cy & DosALa32_1;
            Cy >>= 32;
        }
    }

    private static void SubtDoublePrecLong(long Nbr1[], long Nbr2[], long Diff[]) {
        long Cy = 0;
        for (int i = 0; i < 6; i++) {
            Cy += Nbr1[i] - Nbr2[i];
            Diff[i] = Cy & DosALa32_1;
            Cy = (Cy >= 0 ? 0 : -1);
        }
    }

    private static void MultDoublePrecLong(long Nbr1[], long Nbr2[], long Prod[]) {
        long Cy, Pr;
        int i, j;
        Cy = Pr = 0;
        for (i = 0; i < 6; i++) {
            Pr = Cy & DosALa32_1;
            Cy >>>= 32;
            for (j = 0; j <= i; j++) {
                Pr += Nbr1[j] * Nbr2[i - j];
                Cy += (Pr >>> 32);
                Pr &= DosALa32_1;
            }
            Prod[i] = Pr;
        }
    }

    private static long DivDoublePrecLong(long Dividend[], long Divisor[]) {
        boolean ChSignDividend = false;
        boolean ChSignDivisor = false;
        double dDividend, dDivisor;
        long QuotientH, QuotientL, Cy;
        long D0, D1, D2, D3, D4, D5, E0, E1, E2, E3, E4, E5;

        if (Dividend[5] >= DosALa31) {
            ChSignDividend = true;
            ChSignDoublePrecLong(Dividend);
        }
        if (Divisor[5] >= DosALa31) {
            ChSignDivisor = true;
            ChSignDoublePrecLong(Divisor);
        }

        dDividend = ((((((double) Dividend[5] * dDosALa32
                + (double) Dividend[4]) * dDosALa32
                + (double) Dividend[3]) * dDosALa32
                + (double) Dividend[2]) * dDosALa32
                + (double) Dividend[1]) * dDosALa32
                + (double) Dividend[0]);

        dDivisor = ((((((double) Divisor[5] * dDosALa32
                + (double) Divisor[4]) * dDosALa32
                + (double) Divisor[3]) * dDosALa32
                + (double) Divisor[2]) * dDosALa32
                + (double) Divisor[1]) * dDosALa32
                + (double) Divisor[0]);

        QuotientH = (long) (dDividend / dDivisor / dDosALa32) + 3;
        do {
            QuotientH--;
            D1 = Dividend[1] - Divisor[0] * QuotientH;
            if (D1 > Dividend[1] || D1 < 0) {
                Cy = (D1 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            D2 = Dividend[2] - Divisor[1] * QuotientH + Cy;
            if (D2 > Dividend[2] || D2 < 0) {
                Cy = (D2 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            D3 = Dividend[3] - Divisor[2] * QuotientH + Cy;
            if (D3 > Dividend[3] || D3 < 0) {
                Cy = (D3 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            D4 = Dividend[4] - Divisor[3] * QuotientH + Cy;
            if (D4 > Dividend[4] || D4 < 0) {
                Cy = (D4 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            D5 = Dividend[5] - Divisor[4] * QuotientH + Cy;
        } while (D5 < 0);

        D0 = Dividend[0];
        D1 &= DosALa32_1;
        D2 &= DosALa32_1;
        D3 &= DosALa32_1;
        D4 &= DosALa32_1;

        dDividend = ((((((double) D5 * dDosALa32
                + (double) D4) * dDosALa32
                + (double) D3) * dDosALa32
                + (double) D2) * dDosALa32
                + (double) D1) * dDosALa32
                + (double) D0);

        QuotientL = (long) (dDividend / dDivisor) + 3;
        do {
            QuotientL--;
            E0 = D0 - Divisor[0] * QuotientL;
            if (E0 > D0 || E0 < 0) {
                Cy = (E0 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E1 = D1 - Divisor[1] * QuotientL + Cy;
            if (E1 > D1 || E1 < 0) {
                Cy = (E1 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E2 = D2 - Divisor[2] * QuotientL + Cy;
            if (E2 > D2 || E2 < 0) {
                Cy = (E2 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E3 = D3 - Divisor[3] * QuotientL + Cy;
            if (E3 > D3 || E3 < 0) {
                Cy = (E3 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E4 = D4 - Divisor[4] * QuotientL + Cy;
            if (E4 > D4 || E4 < 0) {
                Cy = (E4 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E5 = D5 - Divisor[5] * QuotientL + Cy;
        } while (E5 < 0);

        if (ChSignDividend != ChSignDivisor) {
            if (((E0 | E1 | E2 | E3 | E4 | E5) & DosALa32_1) != 0) {
                QuotientL++;
            }
        }

        if (ChSignDividend) {
            ChSignDoublePrecLong(Dividend);
        }
        if (ChSignDivisor) {
            ChSignDoublePrecLong(Divisor);
        }

        if (ChSignDividend == ChSignDivisor) {
            return QuotientH << 32 | QuotientL;
        }
        return -(QuotientH << 32 | QuotientL);
    }

    private static void DivideDoublePrecLong(long Dividend[], long Divisor[],
            long Quotient[]) {
        boolean ChSignDividend = false;
        boolean ChSignDivisor = false;
        double dDividend, dDivisor, dAux;
        double dDosALa32 = (double) DosALa32;
        long QuotientH, D0, D1, D2, D3, D4, D5, E0, E1, E2, E3, E4, E5, Cy;

        if (Dividend[5] >= DosALa31) {
            ChSignDividend = true;
            ChSignDoublePrecLong(Dividend);
        }
        if (Divisor[5] >= DosALa31) {
            ChSignDivisor = true;
            ChSignDoublePrecLong(Divisor);
        }

        Quotient[0] = Quotient[1] = Quotient[2] = Quotient[3] = Quotient[4] = Quotient[5] = 0;

        D0 = Dividend[0];
        D1 = Dividend[1];
        D2 = Dividend[2];
        D3 = Dividend[3];
        D4 = Dividend[4];
        D5 = Dividend[5];

        dDividend = ((((((double) D5 * dDosALa32
                + (double) D4) * dDosALa32
                + (double) D3) * dDosALa32
                + (double) D2) * dDosALa32
                + (double) D1) * dDosALa32
                + (double) D0);

        dDivisor = ((((((double) Divisor[5] * dDosALa32
                + (double) Divisor[4]) * dDosALa32
                + (double) Divisor[3]) * dDosALa32
                + (double) Divisor[2]) * dDosALa32
                + (double) Divisor[1]) * dDosALa32
                + (double) Divisor[0]);

        if (dDividend >= dDosALa32 * dDivisor) {
            if (dDividend >= dDosALa64 * dDivisor) {
                if (dDividend >= dDosALa96 * dDivisor) {
                    if (dDividend >= dDosALa128 * dDivisor) {
                        if (dDividend >= dDosALa160 * dDivisor) {
                            QuotientH = (long) (dDividend / dDivisor / dDosALa160) + 3;
                            do {
                                QuotientH--;
                                E5 = D5 - Divisor[0] * QuotientH;
                            } while (E5 < 0);
                            Quotient[5] = QuotientH;
                            D5 = E5;

                            dDividend = ((((((double) D5 * dDosALa32
                                    + (double) D4) * dDosALa32
                                    + (double) D3) * dDosALa32
                                    + (double) D2) * dDosALa32
                                    + (double) D1) * dDosALa32
                                    + (double) D0);
                        }
                        QuotientH = (long) (dDividend / dDivisor / dDosALa128) + 3;
                        do {
                            QuotientH--;
                            E4 = D4 - Divisor[0] * QuotientH;
                            if (E4 > D4 || E4 < 0) {
                                Cy = (E4 >>> 32) - DosALa32;
                            } else {
                                Cy = 0;
                            }
                            E5 = D5 - Divisor[1] * QuotientH + Cy;
                        } while (E5 < 0);
                        Quotient[4] = QuotientH;
                        D4 = E4 & DosALa32_1;
                        D5 = E5;
                        dDividend = ((((((double) D5 * dDosALa32
                                + (double) D4) * dDosALa32
                                + (double) D3) * dDosALa32
                                + (double) D2) * dDosALa32
                                + (double) D1) * dDosALa32
                                + (double) D0);
                    }
                    QuotientH = (long) (dDividend / dDivisor / dDosALa96) + 3;
                    do {
                        QuotientH--;
                        E3 = D3 - Divisor[0] * QuotientH;
                        if (E3 > D3 || E3 < 0) {
                            Cy = (E3 >>> 32) - DosALa32;
                        } else {
                            Cy = 0;
                        }
                        E4 = D4 - Divisor[1] * QuotientH + Cy;
                        if (E4 > D4 || E4 < 0) {
                            Cy = (E4 >>> 32) - DosALa32;
                        } else {
                            Cy = 0;
                        }
                        E5 = D5 - Divisor[2] * QuotientH + Cy;
                    } while (E5 < 0);
                    Quotient[3] = QuotientH;
                    D3 = E3 & DosALa32_1;
                    D4 = E4 & DosALa32_1;
                    D5 = E5;
                    dDividend = ((((((double) D5 * dDosALa32
                            + (double) D4) * dDosALa32
                            + (double) D3) * dDosALa32
                            + (double) D2) * dDosALa32
                            + (double) D1) * dDosALa32
                            + (double) D0);
                }
                QuotientH = (long) (dDividend / dDivisor / dDosALa64) + 3;
                do {
                    QuotientH--;
                    E2 = D2 - Divisor[0] * QuotientH;
                    if (E2 > D2 || E2 < 0) {
                        Cy = (E2 >>> 32) - DosALa32;
                    } else {
                        Cy = 0;
                    }
                    E3 = D3 - Divisor[1] * QuotientH + Cy;
                    if (E3 > D3 || E3 < 0) {
                        Cy = (E3 >>> 32) - DosALa32;
                    } else {
                        Cy = 0;
                    }
                    E4 = D4 - Divisor[2] * QuotientH + Cy;
                    if (E4 > D4 || E4 < 0) {
                        Cy = (E4 >>> 32) - DosALa32;
                    } else {
                        Cy = 0;
                    }
                    E5 = D5 - Divisor[3] * QuotientH + Cy;
                } while (E5 < 0);
                Quotient[2] = QuotientH;
                D2 = E2 & DosALa32_1;
                D3 = E3 & DosALa32_1;
                D4 = E4 & DosALa32_1;
                D5 = E5;
                dDividend = ((((((double) D5 * dDosALa32
                        + (double) D4) * dDosALa32
                        + (double) D3) * dDosALa32
                        + (double) D2) * dDosALa32
                        + (double) D1) * dDosALa32
                        + (double) D0);
            }
            QuotientH = (long) (dDividend / dDivisor / dDosALa32) + 3;
            do {
                QuotientH--;
                E1 = D1 - Divisor[0] * QuotientH;
                if (E1 > D1 || E1 < 0) {
                    Cy = (E1 >>> 32) - DosALa32;
                } else {
                    Cy = 0;
                }
                E2 = D2 - Divisor[1] * QuotientH + Cy;
                if (E2 > D2 || E2 < 0) {
                    Cy = (E2 >>> 32) - DosALa32;
                } else {
                    Cy = 0;
                }
                E3 = D3 - Divisor[2] * QuotientH + Cy;
                if (E3 > D3 || E3 < 0) {
                    Cy = (E3 >>> 32) - DosALa32;
                } else {
                    Cy = 0;
                }
                E4 = D4 - Divisor[3] * QuotientH + Cy;
                if (E4 > D4 || E4 < 0) {
                    Cy = (E4 >>> 32) - DosALa32;
                } else {
                    Cy = 0;
                }
                E5 = D5 - Divisor[4] * QuotientH + Cy;
            } while (E5 < 0);
            Quotient[1] = QuotientH;
            D1 = E1 & DosALa32_1;
            D2 = E2 & DosALa32_1;
            D3 = E3 & DosALa32_1;
            D4 = E4 & DosALa32_1;
            D5 = E5;
            dDividend = ((((((double) D5 * dDosALa32
                    + (double) D4) * dDosALa32
                    + (double) D3) * dDosALa32
                    + (double) D2) * dDosALa32
                    + (double) D1) * dDosALa32
                    + (double) D0);
        }
        QuotientH = (long) (dDividend / dDivisor) + 3;
        do {
            QuotientH--;
            E0 = D0 - Divisor[0] * QuotientH;
            if (E0 > D0 || E0 < 0) {
                Cy = (E0 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E1 = D1 - Divisor[1] * QuotientH + Cy;
            if (E1 > D1 || E1 < 0) {
                Cy = (E1 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E2 = D2 - Divisor[2] * QuotientH + Cy;
            if (E2 > D2 || E2 < 0) {
                Cy = (E2 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E3 = D3 - Divisor[3] * QuotientH + Cy;
            if (E3 > D3 || E3 < 0) {
                Cy = (E3 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E4 = D4 - Divisor[4] * QuotientH + Cy;
            if (E4 > D4 || E4 < 0) {
                Cy = (E4 >>> 32) - DosALa32;
            } else {
                Cy = 0;
            }
            E5 = D5 - Divisor[5] * QuotientH + Cy;
        } while (E5 < 0);

        if (ChSignDividend != ChSignDivisor) {
            if (((E0 | E1 | E2 | E3 | E4 | E5) & DosALa32_1) != 0) {
                if (++QuotientH == DosALa32) {
                    QuotientH = 0;
                    if (++Quotient[1] == DosALa32) {
                        Quotient[1] = 0;
                        if (++Quotient[2] == DosALa32) {
                            Quotient[2] = 0;
                            if (++Quotient[3] == DosALa32) {
                                Quotient[3] = 0;
                                if (++Quotient[4] == DosALa32) {
                                    Quotient[4] = 0;
                                    if (++Quotient[5] == DosALa32) {
                                        Quotient[5] = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Quotient[0] = QuotientH;

        if (ChSignDividend) {
            ChSignDoublePrecLong(Dividend);
        }
        if (ChSignDivisor) {
            ChSignDoublePrecLong(Divisor);
        }
        if (ChSignDividend != ChSignDivisor) {
            ChSignDoublePrecLong(Quotient);
        }
    }

    private static void DivDoublePrecLongByLong(long Dividend[], long Divisor,
            long Quotient[]) {
        int i;
        boolean ChSignDivisor = false;
        long Divid, Rem = 0;

        if (Divisor < 0) {
            ChSignDivisor = true;
            Divisor = -Divisor;
        }
        if (Dividend[5] >= DosALa31) {
            Rem = Divisor - 1;
        }
        for (i = 5; i >= 0; i--) {
            Divid = Dividend[i] + (Rem << 32);
            Rem = Divid % Divisor;
            Quotient[i] = Divid / Divisor;
        }
        if (ChSignDivisor) {
            ChSignDoublePrecLong(Quotient);
        }
    }

// Gcd calculation:
// Step 1: Set k<-0, and then repeatedly set k<-k+1, u<-u/2, v<-v/2
//         zero or more times until u and v are not both even.
// Step 2: If u is odd, set t<-(-v) and go to step 4. Otherwise set t<-u.
// Step 3: Set t<-t/2
// Step 4: If t is even, go back to step 3.
// Step 5: If t>0, set u<-t, otherwise set v<-(-t).
// Step 6: Set t<-u-v. If t != 0, go back to step 3.
// Step 7: The GCD is u*2^k.
    private static void GcdDoublePrecLong(long Nbr1[], long Nbr2[], long Gcd[]) {
        long u[] = new long[6];
        long v[] = new long[6];
        long t[] = new long[6];
        int k;

        System.arraycopy(Nbr1, 0, u, 0, 6);
        System.arraycopy(Nbr2, 0, v, 0, 6);
        if (u[0] == 0 && u[1] == 0 && u[2] == 0 && u[3] == 0 && u[4] == 0 && u[5] == 0) {
            System.arraycopy(v, 0, Gcd, 0, 6);
            return;
        }
        if (v[0] == 0 && v[1] == 0 && v[2] == 0 && v[3] == 0 && v[4] == 0 && v[5] == 0) {
            System.arraycopy(u, 0, Gcd, 0, 6);
            return;
        }
        if (u[5] >= DosALa31) {
            ChSignDoublePrecLong(u);
        }
        if (v[5] >= DosALa31) {
            ChSignDoublePrecLong(v);
        }
        k = 0;
        while ((u[0] & 1) == 0 && (v[0] & 1) == 0) {   // Step 1
            k++;
            DivDoublePrecLongByLong(u, 2, u);
            DivDoublePrecLongByLong(v, 2, v);
        }
        if ((u[0] & 1) == 1) {                       // Step 2
            System.arraycopy(v, 0, t, 0, 6);
            ChSignDoublePrecLong(t);
        } else {
            System.arraycopy(u, 0, t, 0, 6);
        }
        do {
            while ((t[0] & 1) == 0) {                  // Step 4
                DivDoublePrecLongByLong(t, 2, t);        // Step 3
            }
            if (t[5] < DosALa31) {                   // Step 5
                System.arraycopy(t, 0, u, 0, 6);
            } else {
                System.arraycopy(t, 0, v, 0, 6);
                ChSignDoublePrecLong(v);
            }
            SubtDoublePrecLong(u, v, t);               // Step 6

        } while (t[0] != 0 || t[1] != 0 || t[2] != 0 || t[3] != 0 || t[4] != 0 || t[5] != 0);
        System.arraycopy(u, 0, Gcd, 0, 6);             // Step 7
        while (k > 0) {
            AddDoublePrecLong(Gcd, Gcd, Gcd);
            k--;
        }
    }

    private static String DoublePrecLongToString(long Nbr[]) {
        long N0, N1, N2, N3, N4, N5, Rem;
        boolean ChSign = false;
        String nbrOutput = "";

        if (Nbr[5] >= DosALa31) {
            ChSignDoublePrecLong(Nbr);
            ChSign = true;
        }
        N0 = Nbr[0];
        N1 = Nbr[1];
        N2 = Nbr[2];
        N3 = Nbr[3];
        N4 = Nbr[4];
        N5 = Nbr[5];
        do {
            Rem = N5 % BILL;
            N5 /= BILL;
            N4 += Rem << 32;
            Rem = N4 % BILL;
            N4 /= BILL;
            N3 += Rem << 32;
            Rem = N3 % BILL;
            N3 /= BILL;
            N2 += Rem << 32;
            Rem = N2 % BILL;
            N2 /= BILL;
            N1 += Rem << 32;
            Rem = N1 % BILL;
            N1 /= BILL;
            N0 += Rem << 32;
            Rem = N0 % BILL;
            N0 /= BILL;
            nbrOutput = ((Rem + BILL) + "").substring(1) + nbrOutput;
        } while (N0 != 0 || N1 != 0 || N2 != 0 || N3 != 0 || N4 != 0 || N5 != 0);
        while (nbrOutput.charAt(0) == '0' && nbrOutput.length() > 1) {
            nbrOutput = nbrOutput.substring(1);
        }
        if (ChSign) {
            ChSignDoublePrecLong(Nbr);
        }
        return nbrOutput;
    }

    /**
     * ********************************************
     */
    /* NextConv:                                 */
 /*  BigInteger Tmp  =  Prev * A1 + Act * B1; */
 /*  Act  =  Prev * A2 + Act * B2;            */
 /*  Prev  =  Tmp;                            */
 /*                                           *
    * ********************************************
     */
    private static void NextConv(long Prev[], long Act[]) {

//        System.out.print("**temp NextConv Prev=");
//        ShowLargeNumber(Prev);
//        System.out.print("  Act=");
//        ShowLargeNumber(Act);
//        System.out.println();
        long CP1H, CP1L, CP2H, CP2L;
        long CA1H, CA1L, CA2H, CA2L;
        long Cy1H, Cy1L, Cy2H, Cy2L;
        long PH, PL, AH, AL;
        long Tmp, Re1, Re2, Re4;
        int S = (int) abs(Act[0]);
        long T = (Prev[0] < 0 ? BILL_BILL - 1 : 0);
        long U = (Act[0] < 0 ? BILL_BILL - 1 : 0);
        while (S < abs(Prev[0])) {
            Act[++S] = U;
        }
        S = (int) abs(Prev[0]);
        while (S < abs(Act[0])) {
            Prev[++S] = T;
        }
        Prev[S + 1] = Prev[S + 2] = T;
        Act[S + 1] = Act[S + 2] = U;
        Act[0] = Prev[0] = S;
        CP1H = A1 / BILL;
        CP1L = A1 % BILL;
        CP2H = A2 / BILL;
        CP2L = A2 % BILL;
        CA1H = B1 / BILL;
        CA1L = B1 % BILL;
        CA2H = B2 / BILL;
        CA2L = B2 % BILL;
        Cy1L = Cy1H = Cy2L = Cy2H = 0;
        for (S = 1; S <= Prev[0] + 2; S++) {
            PL = Prev[S] % BILL;
            PH = Prev[S] / BILL;
            AL = Act[S] % BILL;
            AH = Act[S] / BILL;
            Tmp = PL * CP1L + AL * CA1L + Cy1L;
            Re2 = Tmp / BILL;
            Re1 = Tmp % BILL;
            Tmp = PH * CP1L + PL * CP1H + AH * CA1L + AL * CA1H + Cy1H + Re2;
            Re4 = Tmp / BILL;
            Re1 += (Tmp % BILL) * BILL;
            Tmp = PH * CP1H + AH * CA1H + Re4 - (Re1 < 0 ? 1 : 0);
            Cy1H = Tmp / BILL;
            Cy1L = Tmp % BILL;
            Prev[S] = (Re1 + BILL_BILL) % BILL_BILL;
            Tmp = PL * CP2L + AL * CA2L + Cy2L;
            Re2 = Tmp / BILL;
            Re1 = Tmp % BILL;
            Tmp = PH * CP2L + PL * CP2H + AH * CA2L + AL * CA2H + Cy2H + Re2;
            Re4 = Tmp / BILL;
            Re1 += (Tmp % BILL) * BILL;
            Tmp = PH * CP2H + AH * CA2H + Re4 - (Re1 < 0 ? 1 : 0);
            Cy2H = Tmp / BILL;
            Cy2L = Tmp % BILL;
            Act[S] = (Re1 + BILL_BILL) % BILL_BILL;
        }
        AdjustSign(Prev);
        AdjustSign(Act);

//        System.out.print("**exit NextConv Prev=");
//        ShowLargeNumber(Prev);
//        System.out.print("  Act=");
//        ShowLargeNumber(Act);
//        System.out.println("\n");
    }

    private static void showStatus(String s1) {
        System.out.println(s1);
    }

    private static void ShowBigEq(long biA[], long biB[], long biC[], String x,
            String y) {
        if (biA[5] >= DosALa31) {
            w("-");
        }
        w(DoublePrecLongToString(biA) + " " + x + sq + " ");
        if (biB[0] != 0 || biB[1] != 0 || biB[2] != 0 || biB[3] != 0 || biB[4] != 0 && biB[5] != 0) {
            if (biB[5] < DosALa31) {
                w("+ ");
            } else {
                w("- ");
            }
            w(DoublePrecLongToString(biB) + " " + x + y + " ");
        }
        if (biC[0] != 0 || biC[1] != 0 || biC[2] != 0 || biC[3] != 0 || biC[4] != 0 && biC[5] != 0) {
            if (biC[5] < DosALa31) {
                w("+ ");
            } else {
                w("- ");
            }
            w(DoublePrecLongToString(biC) + (y.equals("") ? " " : " " + y + sq + " "));
        }
    }

    /* prepare for solving by continued fractions. 
return values in Disc, SqrtDisc, Dp_NUM, Dp_DEN */
    private static void GetRoot(long biA[], long biB[], long biC[]) {
        long G, K, L, M, P, S, T, Z;
        long biP[] = new long[6];
        long biM[] = new long[6];
        long biZ[] = new long[6];
        long biG[] = new long[6];
        long biK[] = new long[6];
        boolean DENis1;
        boolean NUMis0;

        G = biA[1] * DosALa32 + biA[0];
        K = biB[1] * DosALa32 + biB[0];
        L = biC[1] * DosALa32 + biC[0];
        DET = K * K - 4 * G * L;
        System.arraycopy(biB, 0, NUM, 0, 6);
        ChSignDoublePrecLong(NUM);
        NUMis0 = (NUM[0] == 0 && NUM[1] == 0 && NUM[2] == 0 && NUM[3] == 0 && NUM[4] == 0 && NUM[5] == 0);
        AddDoublePrecLong(biA, biA, DEN);
        if (teach) {
            w("We have to find the continued fraction expansion of the roots of ");
            ShowBigEq(biA, biB, biC, "t", "");
            w(" =  0, that is, ");
            if (NUMis0 == false) {
                w("(sqrt(" + DET + ") ");
                if (NUM[5] < DosALa31) {
                    w("+ ");
                } else {
                    w("- ");
                }
                w(DoublePrecLongToString(NUM) + ") / ");
            } else {
                w("sqrt(" + DET + ") /");
            }
            if (DEN[5] >= DosALa31) {
                w("(-" + DoublePrecLongToString(DEN) + ")");
            } else {
                w(DoublePrecLongToString(DEN));
            }
            w("\n");
        }

        GcdDoublePrecLong(NUM, DEN, biG);
        MultDoublePrecLong(biG, biG, biZ);
        LongToDoublePrecLong(DET, biG);
        GcdDoublePrecLong(biZ, biG, biG);
        G = biG[0] | biG[1] << 32;
        K = 1;
        T = 3;
        while (G % 4 == 0) {
            G /= 4;
            K *= 2;
        }
        while (G >= T * T) {
            while (G % (T * T) == 0) {
                G /= T * T;
                K *= T;
            }
            T += 2;
        }
        DET /= K * K;
        DivDoublePrecLongByLong(NUM, K, NUM);
        DivDoublePrecLongByLong(DEN, K, DEN);
        DENis1 = (DEN[0] == 1 && DEN[1] == 0 && DEN[2] == 0 && DEN[3] == 0 && DEN[4] == 0 && DEN[5] == 0);
        if (teach && K != 1) {
            w("Simplifying, ");
            if (DENis1 == false && NUMis0 == false) {
                w("(");
            }
            w("sqrt(" + DET + ")");
            if (NUMis0 == false) {
                if (NUM[5] < DosALa31) {
                    w(" + ");
                } else {
                    w(" - ");
                }
                w(DoublePrecLongToString(NUM));
            }
            if (DENis1 == false) {
                if (NUMis0) {
                    w(" / ");
                } else {
                    w(") / ");
                }
            }
            if (DEN[5] >= DosALa31) {
                w("(-" + DoublePrecLongToString(DEN) + ")");
            } else {
                if (DENis1 == false) {
                    w(DoublePrecLongToString(DEN));
                }
            }
            w("\n");
        }
        DET *= K * K;
        System.arraycopy(biB, 0, NUM, 0, 6);
        ChSignDoublePrecLong(NUM);
        AddDoublePrecLong(biA, biA, DEN);
        SQD = sqrt(DET);

        /* temporary */
//        w ("**temp** getroot: BiA =" + DoublePrecLongToString(biA));
//        w (" BiB =" + DoublePrecLongToString(biB));
//        w (" BiC =" + DoublePrecLongToString(biC));
//        w (" NUM =" + DoublePrecLongToString(NUM));
//        w (" DEN =" + DoublePrecLongToString(DEN));
//        w ("SQD =" + SQD + "DET =" + DET + "\n");
        /* end temporary */
        if (teach) {
            w("The continued fraction expansion is:\n");
            System.arraycopy(DEN, 0, biP, 0, 6);
            LongToDoublePrecLong(SQD + (DEN[5] >= DosALa31 ? 1 : 0), biK);
            AddDoublePrecLong(biK, NUM, biK);
            Z = DivDoublePrecLong(biK, DEN);
            LongToDoublePrecLong(Z, biM);
            MultDoublePrecLong(biM, DEN, biK);
            SubtDoublePrecLong(biK, NUM, biM);
            w("" + Z);
            String sep = "+ //";
            int cont = -1;
            K = P = L = M = -1;
            while (cont < 0 || K != P || L != M) {
                w(sep);
                sep = ", ";
                if (cont < 0
                        && biP[0] > 0 && biP[0] <= SQD + biM[0] && biP[1] == 0 && biP[2] == 0
                        && biP[3] == 0 && biP[4] == 0 && biP[5] == 0
                        && biM[0] > 0 && biM[0] <= SQD && biM[1] == 0 && biM[2] == 0
                        && biM[3] == 0 && biM[4] == 0 && biM[5] == 0) {
                    w("");
                    K = P = biP[0];
                    L = M = biM[0];
                    cont = 0;
                }
                if (cont >= 0) {
                    P = (DET - M * M) / P;
                    /* both numerator and denominator are positive */
                    Z = (SQD + M) / P;
                    M = Z * P - M;
                    cont++;
                } else {
                    LongToDoublePrecLong(DET, biZ);
                    MultDoublePrecLong(biM, biM, biG);
                    SubtDoublePrecLong(biZ, biG, biG);
                    DivideDoublePrecLong(biG, biP, biK);
                    System.arraycopy(biK, 0, biP, 0, 6);
                    LongToDoublePrecLong(SQD + (biP[5] >= DosALa31 ? 1 : 0), biZ);
                    AddDoublePrecLong(biZ, biM, biK);
                    Z = DivDoublePrecLong(biK, biP);
                    LongToDoublePrecLong(Z, biG);
                    MultDoublePrecLong(biG, biP, biZ);
                    SubtDoublePrecLong(biZ, biM, biM);
                }
                w("" + Z);
            }
            w("//\nwhere the periodic part is marked in bold");
            if (cont > 1) {
                w(" (the period has " + cont + " coefficients)");
            }
            w(".\n");
        }
    }

    /* type  =  1: Find convergents */
 /* type  =  2: Find convergents for x^2 + Bxy + ACy^2  =  1 (recursion) */
 /* type  =  3: Find convergents for modified equation in homogeneous equation */
 /* type  =  4: Find convergents for modified equation in complete solution */
 /* type  =  5: Find convergents for x^2 + Bxy + ACy^2  =  1 (mod B^2-4AC) */
    private static boolean ContFrac(long biA[], byte type, byte SqrtSign, long s,
            long T, long MagnifY, long A) {

//        System.out.print("**temp ContFrac: type=" + type + "  SqrtSign=" + SqrtSign + "  s=" + s);
//        System.out.println("  T=" + T + "  MagnifY=" + MagnifY + "  A=" + A);
        /*
        uses the following global variables:
        L1, L2 H1, H2, K1[6], K2[6];
        Disc;
        A, B, D, F;
        NUM, DEN;
        CY1, CY0;
        A1, A2, B1, B2;
        NbrSols, NbrCo, NbrEqs, EqNbr;
        teach;
        UU, VU;
         */
        long P, Z, M, P1, M1, Tmp, K, L, Mu;
        long biP[] = new long[6];
        long biM[] = new long[6];
        long biZ[] = new long[6];
        long biG[] = new long[6];
        long biMu[] = new long[6];
        long biK[] = new long[6];
        long biL[] = new long[6];
        long biM1[] = new long[6];
        long biP1[] = new long[6];
        long H1ModCY1 = 1, H2ModCY1 = 0, K1ModCY1 = 0, K2ModCY1 = 1;
        boolean Sols, secondDo = true;
        int i;
        int Conv;
        int Co = -1;
        String U = (type == 4 ? "'" : "");
        String X1 = "X";
        String Y1 = (MagnifY == 1 ? "Y" : "Y'");
        Sols = true;
        if (biA[0] == 1 && biA[1] == 0 && biA[2] == 0
                && biA[3] == 0 && biA[4] == 0 && biA[5] == 0) {
//            System.out.println("**temp ContFrac DP_A =1");
            H1[0] = SqrtSign;
            K1[0] = 1;
            H1[1] = (SqrtSign + BILL_BILL) % BILL_BILL;
            K1[1] = 0;
            if (type == 1) {
                ShowLargeXY(X1, Y1, H1, K1, true, "", "");
//                System.out.println("**temp ContFrac returns true(1)");
                return true;
                /* Indicate there are solutions */
            }
            if ((type == 3 || type == 4) && (DET != 5 || A * F < 0)) {
                if (ShowHomoSols(type, H1, K1, s, T, MagnifY, "", "")) {
//                    System.out.println("**temp ContFrac returns true(2) - solution found");
                    return true;
                    /* Indicate there are solutions */
                }
            }
        }
        /* Paso  =  1: Quick scan for solutions */
 /* Paso  =  2: Show actual solutions */
        for (int Paso = (type == 2 || DET == 5 && A * F > 0 && (type == 3 || type == 4) ? 2 : 1);
                Sols && Paso <= 2; Paso++) {
            Conv = 0;
            Sols = false;
            System.arraycopy(DEN, 0, biP, 0, 6);
            if (SqrtSign < 0) {
                ChSignDoublePrecLong(biP);
            }
            LongToDoublePrecLong(SQD + (biP[5] >= DosALa31 ? 1 : 0), biK);
            if (SqrtSign < 0) {
                SubtDoublePrecLong(biK, NUM, biK);
            } else {
                AddDoublePrecLong(biK, NUM, biK);
            }
            Z = DivDoublePrecLong(biK, biP);
            LongToDoublePrecLong(Z, biM);
            MultDoublePrecLong(biM, DEN, biK);
            SubtDoublePrecLong(biK, NUM, biM);
            if (SqrtSign < 0) {
                ChSignDoublePrecLong(biM);
            }
            if (type == 4) {
                H2ModCY1 = Z % CY1;
            }
            // biM = (NUM+Z*DEN)*SqrtSign;
            if (type == 5) {
                A1 = B2 = 1;
                A2 = Z % T;
                B1 = 0;
            } else {
                H1[0] = SqrtSign;
                H2[0] = (Z * SqrtSign < 0) ? -1 : 1;
                K1[0] = 1;
                K2[0] = H1[0];
                H1[1] = (SqrtSign + BILL_BILL) % BILL_BILL;
                H2[1] = (Z * SqrtSign + BILL_BILL) % BILL_BILL;
                K1[1] = 0;
                K2[1] = H1[1];
                A1 = B2 = 1;
                A2 = B1 = 0;
            }
            Co = -1;
            for (i = 0; i < 6; i++) {
                biK[i] = biL[i] = DosALa32_1;
            }
            switch (type) {
                case 1:
                    LongToDoublePrecLong(-2 * F * SqrtSign, biMu);
                    break;
                case 3:
                case 4:
                    LongToDoublePrecLong(-2 * SqrtSign, biMu);
                    break;
                default:
                    System.arraycopy(DEN, 0, biMu, 0, 6);
                    if (SqrtSign > 0) {
                        ChSignDoublePrecLong(biMu);
                    }
            }
            do {
                LongToDoublePrecLong(DET, biZ);       // biP1  =  (DET-biM*biM)/biP
                MultDoublePrecLong(biM, biM, biG);
                SubtDoublePrecLong(biZ, biG, biG);
                DivideDoublePrecLong(biG, biP, biP1);
                LongToDoublePrecLong(SQD + (biP1[5] >= DosALa31 ? 1 : 0), biZ);
                AddDoublePrecLong(biZ, biM, biK);
                Z = DivDoublePrecLong(biK, biP1);
                LongToDoublePrecLong(Z, biG);         // biM1  =  Z * biP1 - biM
                MultDoublePrecLong(biG, biP1, biZ);
                SubtDoublePrecLong(biZ, biM, biM1);
                if (Co < 0
                        && biP[0] > 0 && biP[0] <= SQD + biM[0] && biP[1] == 0 && biP[2] == 0
                        && biP[3] == 0 && biP[4] == 0 && biP[5] == 0
                        && biM[0] > 0 && biM[0] <= SQD && biM[1] == 0 && biM[2] == 0
                        && biM[3] == 0 && biM[4] == 0 && biM[5] == 0) {
                    Co = 0;
                    System.arraycopy(biP, 0, biK, 0, 6);
                    System.arraycopy(biM, 0, biL, 0, 6);
//                    System.out.print ("**temp ContFrac(4) biK=" + DoublePrecLongToString(biK));
//                    System.out.println ("  biL=" + DoublePrecLongToString(biL));
                }

//                System.out.print ("**temp ContFrac(5)  type=" + type + "  Co=" + Co);
//                System.out.print ("  biP=" + DoublePrecLongToString(biP));
//                System.out.print ("  biMu=" + DoublePrecLongToString(biMu));
//                System.out.print ("  biK=" + DoublePrecLongToString(biK));
//                System.out.println ("  biP1=" + DoublePrecLongToString(biP1));
                if (type == 1 && biP[0] == biMu[0] && biP[1] == biMu[1] && biP[2] == biMu[2]
                        && biP[3] == biMu[3] && biP[4] == biMu[4] && biP[5] == biMu[5]) { // Solution found
                    if (Co % 2 == 0
                            || biK[0] != biP1[0] || biK[1] != biP1[1] || biK[2] != biP1[2]
                            || biK[3] != biP1[3] || biK[4] != biP1[4] || biK[5] != biP1[5]
                            || biL[0] != biM1[0] || biL[1] != biM1[1] || biL[2] != biM1[2]
                            || biL[3] != biM1[3] || biL[4] != biM1[4] || biL[5] != biM1[5]) {
                        if (Paso == 2) {
                            if (A2 != 0) {
                                NextConv(H1, H2);
                                NextConv(K1, K2);
                                A1 = B2 = 1;
                                A2 = B1 = 0;
                            }
                            ShowLargeXY(X1, Y1, H2, K2, true, "NUM(" + Conv + ")  =  ", "DEN(" + Conv + ")  =  ");
                        }
                        Sols = true;
                    }
                    secondDo = false;
//                    System.out.println("**temp ContFrac(5A)");
                    break;
                }
                if (type == 3 || type == 4) {
                    if (Co == 0 && A * F > 0 && DET == 5) {
                        /* Solution found */
                        if (Paso == 1) {
                            secondDo = false;
                            Sols = true;
//                            System.out.println("**temp ContFrac(6)");
                            break;
                        } else {
                            NextConv(H1, H2);
                            NextConv(K1, K2);
                            A1 = B2 = 1;
                            A2 = B1 = 0;
                            ChangeSign(H2, (byte) 1);
                            AddLargeNumbers(H1, H2, H2);
                            ChangeSign(H2, (byte) 1);
                            ChangeSign(K2, (byte) 1);
                            AddLargeNumbers(K1, K2, K2);
                            ChangeSign(K2, (byte) 1);
                            if (ShowHomoSols(type, H2, K2, s, T, MagnifY, "NUM(" + Conv + ") - NUM(" + (Conv - 1) + ")  =  ", "DEN(" + Conv + ") - DEN(" + (Conv - 1) + ")  =  ")) {
                                secondDo = false;
                                Sols = true;
//                                 System.out.println("**temp ContFrac(7) - solution found ");
                                break;
                            }
                            AddLargeNumbers(H1, H2, H2);
                            AddLargeNumbers(K1, K2, K2);
                        }
                    }
                    if (biP1[0] == biMu[0] && biP1[1] == biMu[1] && biP1[2] == biMu[2]
                            && biP1[3] == biMu[3] && biP1[4] == biMu[4] && biP1[5] == biMu[5]) {  // Solution found
                        if (Co % 2 == 0
                                || biK[0] != biP1[0] || biK[1] != biP1[1] || biK[2] != biP1[2]
                                || biK[3] != biP1[3] || biK[4] != biP1[4] || biK[5] != biP1[5]
                                || biL[0] != biM1[0] || biL[1] != biM1[1] || biL[2] != biM1[2]
                                || biL[3] != biM1[3] || biL[4] != biM1[4] || biL[5] != biM1[5]) {
                            if (Paso == 2) {
                                if (A2 != 0) {
                                    NextConv(H1, H2);
                                    NextConv(K1, K2);
                                    A1 = B2 = 1;
                                    A2 = B1 = 0;
                                }
                                if (ShowHomoSols(type, H2, K2, s, T, MagnifY, "NUM(" + Conv + ")  =  ", "DEN(" + Conv + ")  =  ")) {
                                    secondDo = false;
                                    Sols = true;
//                                     System.out.println("**temp ContFrac(8) - solution found");
                                    break;
                                }
                            } else {
                                if (type == 4) {
                                    Tmp = H2ModCY1 * T;
                                    if ((Tmp - CY0) % CY1 == 0 || (Tmp + CY0) % CY1 == 0) {
                                        secondDo = false;
                                        Sols = true;
//                                         System.out.println("**temp ContFrac(9)");
                                        break;
                                    }
                                } else {
                                    secondDo = false;
                                    Sols = true;
//                                     System.out.println("**temp ContFrac(10)");
                                    break;
                                }
                            }
                        }
                    }

                    if (Paso == 1 && type == 4) {
                        Tmp = (H1ModCY1 + Z * H2ModCY1) % CY1;
                        H1ModCY1 = H2ModCY1;
                        H2ModCY1 = Tmp;
                        Tmp = (K1ModCY1 + Z * K2ModCY1) % CY1;
                        K1ModCY1 = K2ModCY1;
                        K2ModCY1 = Tmp;
                    }
                }
                System.arraycopy(biM1, 0, biM, 0, 6);
                System.arraycopy(biP1, 0, biP, 0, 6);
                if (Co == 0) {
                    Co = 1;
                }
                if (type == 5) {
                    Tmp = (A1 + Z * A2) % T;
                    A1 = A2;
                    A2 = Tmp;
                    Tmp = (B1 + Z * B2) % T;
                    B1 = B2;
                    B2 = Tmp;
                }
                ChSignDoublePrecLong(biMu);
                if (Paso == 2) {
                    if (A2 != 0 && Z > (BILL_BILL / 10 - A1) / A2 || B2 != 0 && Z > (BILL_BILL / 10 - B1) / B2) {
                        NextConv(H1, H2);
                        NextConv(K1, K2);
                        A1 = B2 = 1;
                        A2 = B1 = 0;
                    }
                    A1 += Z * A2;
                    B1 += Z * B2;
                    Tmp = A1;
                    A1 = A2;
                    A2 = Tmp;
                    Tmp = B1;
                    B1 = B2;
                    B2 = Tmp;
                }
                Conv++;
            } while (Co < 0);

            if (secondDo == false) {
                continue;
            }
            Mu = biMu[0] | biMu[1] << 32;
            L = biL[0] | biL[1] << 32;
            K = biK[0] | biK[1] << 32;
            M = biM[0] | biM[1] << 32;
            P = biP[0] | biP[1] << 32;

            do {
                P1 = (DET - M * M) / P;
                /* P & Q should be > 0 (See Knuth Ex 4.5.3-12) */
                Z = (SQD + M) / P1;
                M1 = Z * P1 - M;
                if (type == 1 && P == Mu) {
                    /* Solution found */
                    if (Co % 2 == 0 || K != P1 || L != M1) {
                        if (Paso == 2) {
                            if (A2 != 0) {
                                NextConv(H1, H2);
                                NextConv(K1, K2);
                                A1 = B2 = 1;
                                A2 = B1 = 0;
                            }
                            ShowLargeXY(X1, Y1, H1, K1, true, "NUM(" + Conv + ")  =  ", "DEN(" + Conv + ")  =  ");
                        }
                        Sols = true;
                    }
//                    System.out.println ("**temp ContFrac(11)");
                    break;
                }
                if (type == 3 || type == 4) {
                    if ((Co & 1) == 0 && A * F > 0 && DET == 5) {
                        /* Solution found */
                        if (Paso == 1) {
                            Sols = true;
//                            System.out.println ("**temp ContFrac(12)");
                            break;
                        } else {
                            NextConv(H1, H2);
                            NextConv(K1, K2);
                            A1 = B2 = 1;
                            A2 = B1 = 0;
                            ChangeSign(H2, (byte) 1);
                            AddLargeNumbers(H1, H2, H2);
                            ChangeSign(H2, (byte) 1);
                            ChangeSign(K2, (byte) 1);
                            AddLargeNumbers(K1, K2, K2);
                            ChangeSign(K2, (byte) 1);
                            if (ShowHomoSols(type, H2, K2, s, T, MagnifY, "NUM(" + Conv + ") - NUM(" + (Conv - 1) + ")  =  ", "DEN(" + Conv + ") - DEN(" + (Conv - 1) + ")  =  ")) {
                                Sols = true;
//                                System.out.println("**temp ContFrac(13) - solution found");
                                break;
                            }
                            AddLargeNumbers(H1, H2, H2);
                            AddLargeNumbers(K1, K2, K2);
                        }
                    }
                    if (P1 == Mu) {
                        /* Solution found */
                        if (Co % 2 == 0 || K != P1 || L != M1) {
                            if (Paso == 2) {
                                if (A2 != 0) {
                                    NextConv(H1, H2);
                                    NextConv(K1, K2);
                                    A1 = B2 = 1;
                                    A2 = B1 = 0;
                                }
                                if (ShowHomoSols(type, H2, K2, s, T, MagnifY, "NUM(" + Conv + ")  =  ", "DEN(" + Conv + ")  =  ")) {
                                    Sols = true;
////                                    System.out.println("**temp ContFrac(14) - solution found");
                                    break;
                                }
                            } else {
                                if (type == 4) {
                                    Tmp = H2ModCY1 * T;
                                    if ((Tmp - CY0) % CY1 == 0 || (Tmp + CY0) % CY1 == 0) {
                                        Sols = true;
//                                        System.out.println("**temp ContFrac(15)");
                                        break;
                                    }
                                } else {
                                    Sols = true;
//                                    System.out.println("**temp ContFrac(16)");
                                    break;
                                }
                            }
                        }
                    }

                    if (Paso == 1 && type == 4) {
                        Tmp = (H1ModCY1 + Z * H2ModCY1) % CY1;
                        H1ModCY1 = H2ModCY1;
                        H2ModCY1 = Tmp;
                        Tmp = (K1ModCY1 + Z * K2ModCY1) % CY1;
                        K1ModCY1 = K2ModCY1;
                        K2ModCY1 = Tmp;
                    }
                }
                Co++;
                if (Co % 5000 == 0) {
                    showStatus("Conv: " + Co + " (Eq " + EqNbr + " of " + NbrEqs + ")");
                }
                if (Co % 5000 == 2500) {
                    showStatus(NbrSols + " solution" + (NbrSols == 1 ? "" : "s"));
                }
                M = M1;
                P = P1;
                if (type == 2 && P1 == Mu) {
                    NextConv(H1, H2);
                    NextConv(K1, K2);
                    Sols = true;
//                    System.out.println("**temp ContFrac(17)");
                    break;
                }
                if (type == 5) {
                    if (P1 == Mu) {
                        NbrCo = Co;
                        Sols = true;
//                        System.out.println("**temp ContFrac(18) NbrCo=" + NbrCo);
                        break;
                    } else {
                        Tmp = (A1 + Z * A2) % T;
                        A1 = A2;
                        A2 = Tmp;
                        Tmp = (B1 + Z * B2) % T;
                        B1 = B2;
                        B2 = Tmp;
                    }
                }
                Mu = -Mu;
                if (Paso == 2) {
                    if (A2 != 0 && Z > (BILL_BILL / 10 - A1) / A2 || B2 != 0 && Z > (BILL_BILL / 10 - B1) / B2) {
                        NextConv(H1, H2);
                        NextConv(K1, K2);
                        A1 = B2 = 1;
                        A2 = B1 = 0;
                    }
                    A1 += Z * A2;
                    B1 += Z * B2;
                    Tmp = A1;
                    A1 = A2;
                    A2 = Tmp;
                    Tmp = B1;
                    B1 = B2;
                    B2 = Tmp;
                }
                Conv++;
//                System.out.print ("**temp ContFrac(18A) NbrCo=" + NbrCo + "  Co=" + Co);
//                System.out.println ("  K=" + K + "  P=" + P + "  L=" + L + "  M=" + M);
            } while (NbrCo > 0 ? Co != NbrCo : Co % 2 != 0 || K != P || L != M);

//            System.out.println ("**temp ContFrac(18B");
            if (type == 5) {
//                System.out.println("**temp ContFrac(19)");
                break;
            }
        }
        /* end for */
//        System.out.println("**temp ContFrac returns " + Sols);
        return Sols;
    }

    private static boolean ShowHomoSols(byte type, long H1[], long K1[], long s,
            long T, long MagnifY, String eqX, String eqY) {

//        System.out.println ("**temp ShowHomoSols  s=" + s + "  + T="  + T 
//                + "  MagnifY=" + MagnifY);
        int i;
        String U = (type == 4 ? "'" : "");
        String X1 = "X";
        String Y1 = (MagnifY == 1 ? "Y" : "Y'");

        if (teach) {
            ShowLargeXY(Y1, "Z", H1, K1, true, eqX, eqY);
            w("Since " + X1 + U + "0  =  ");
            if (T > 1) {
                w(T + " " + UU + U + "0  =  " + T + " (");
            }
            ShowLin(s, -F, 0, VU + "0", "Z0");
            if (T > 1) {
                w(")\nand " + Y1 + "0  =  " + T + " V0");
            }
            w(":\n");
        }
        MultAddLargeNumbers(s * T, H1, -F * T, K1, L1);
        MultLargeNumber(T, H1, L2);
        if (type == 4) {
            if (teach) {
                ShowLargeXY(X1 + U, Y1 + U, L1, L2, false, "", "");
            }
            for (i = (LargeNumberIsZero(L1) && LargeNumberIsZero(L2) ? 1 : 0); i < 2; i++) {
                AddLargeLong(L2, -CY0, L2);
                if (teach) {
                    w(Y1 + "0  =  (");
                    ShowLin(0, i == 0 ? 1 : -1, -CY0, "", Y1 + "'0");
                    w(")/" + par(CY1) + "\n");
                }
                if (DivLargeNumber(L2, CY1, L2) != 0) {
                    if (teach) {
                        w("It is not an integer number.\n");
                    }
                } else {
                    if (teach) {
                        w(X1 + "0  =  (");
                        ShowLin(i == 0 ? 1 : -1, -B, -D, X1 + "'0", Y1 + "0");
                        w(") / " + par(2 * A) + "\n");
                    }
                    AddLargeLong(L1, -D, L1);
                    MultAddLargeNumbers(1, L1, -B, L2, L1);
                    if (DivLargeNumber(L1, 2 * A, L1) != 0) {
                        if (teach) {
                            w("It is not an integer number.\n");
                        }
                    } else {
                        if (teach) {
                            if (MagnifY != 1) {
                                ShowLargeXY(X1, Y1, L1, L2, false, "", "");
                                w("Since Y  =  " + MagnifY + Y1 + "");
                            }
                            w("\n");
                        }
                        MultLargeNumber(MagnifY, L2, L2);
                        ShowLargeXY("X", "Y", L1, L2, false, "", "");
                        return true;
                    }
                }
                MultAddLargeNumbers(-s * T, H1, F * T, K1, L1);
                MultLargeNumber(-T, H1, L2);
            }
        } else {
            if (teach) {
                if (MagnifY != 1) {
                    ShowLargeXY(X1, Y1, L1, L2, false, "", "");
                    w("Since " + Y1 + "  =  " + MagnifY + "Y");
                }
                w("\n");
            }
            MultLargeNumber(MagnifY, L2, L2);
            if (teach) {
                ShowLargeXY("X", "Y", L1, L2, false, "", "");
                ChangeSign(L1, (byte) 1);
                ChangeSign(L2, (byte) 1);
                ShowLargeXY("X", "Y", L1, L2, false, "", "");
                ChangeSign(L1, (byte) 1);
                ChangeSign(L2, (byte) 1);
            }
//            System.out.println("**temp ShowHomoSols returns true");
            return true;
        }
//        System.out.println("**temp ShowHomoSols returns false");
        return false;
    }

    private static void ShowAllLargeSolutions() {
        int i;

        allSolsFound = true;
        also = false;
        for (i = 0; i < sortedSolsY.size(); i++) {
            ShowLargeXY("X", "Y", ((ArrayLongs) (sortedSolsX.elementAt(i))).element,
                    ((ArrayLongs) (sortedSolsY.elementAt(i))).element,
                    false, "", "");
            w("\n");
        }
    }

    private static void ShowLargeXY(String x, String y, long H1[], long K1[],
            boolean sol, String eqX, String eqY) {
        if (x.equals("X") && y.equals("Y")) {
            if (!allSolsFound) {
                showStatus(NbrSols + " solutions");
            }

//            System.out.print ("**temp ShowLargeXY H1=");
//            ShowLargeNumber (H1);
//            System.out.print("  K1=");
//            ShowLargeNumber (K1);
//            System.out.println("  so1=" + sol);
            if (teach == false && allSolsFound == false) {
                also = true;
                if (sol && LargeNumberIsZero(K1)) {
                    InsertNewSolution(H1, K1);
                    ChangeSign(H1, (byte) 1);
                    InsertNewSolution(H1, K1);
                    ChangeSign(H1, (byte) 1);
                } else {
                    if (sol && K1[0] < 0) {
                        ChangeSign(H1, (byte) 1);
                        ChangeSign(K1, (byte) 1);
                        InsertNewSolution(H1, K1);
                        ChangeSign(H1, (byte) 1);
                        ChangeSign(K1, (byte) 1);
                    } else {
                        InsertNewSolution(H1, K1);
                    }
                }
                return;
            }
        }
        if (teach && y.equals("Y")) {
            w("");
        }
        if (y.equals("Y")) {
            showAlso();
        }
        w(x + "0  =  ");
        if (teach) {
            w(eqX);
        }
        ShowLargeNumber(H1);
        w("    " + y + "0  =  ");
        if (teach) {
            w(eqY);
        }
        ShowLargeNumber(K1);
        w("\n");
        if (y.equals("Y") && sol) {
            ChangeSign(H1, (byte) 1);
            ChangeSign(K1, (byte) 1);
            w("\nand also:\n" + x + "0  =  ");
            if (teach) {
                w(eqX.equals("") ? "" : "-" + eqX);
            }
            ShowLargeNumber(H1);
            w("    " + y + "0  =  ");
            if (teach) {
                w(eqY.equals("") ? "" : "-" + eqY);
            }
            ShowLargeNumber(K1);
            w("\n");
            ChangeSign(H1, (byte) 1);
            ChangeSign(K1, (byte) 1);
        }
        if (teach && y.equals("Y")) {
            w("");
        }
    }

    /* Insert new number into sorted solutions vector */
 /* performing a binary search and then using the  */
 /* insertElementAt method of the vector           */
    private static void InsertNewSolution(long H1[], long K1[]) {
        int sizeVector, indexVector, increment, compare, i;

        sizeVector = sortedSolsY.size();
        indexVector = 0;
        if (sizeVector > 0) {
            increment = 1;
            while (increment * 2 <= sizeVector) {
                increment *= 2;
            }
            while (increment > 0) {
                /* Perform binary search */
                if (indexVector + increment <= sizeVector) {
                    compare = Compare(((ArrayLongs) (sortedSolsY.elementAt(indexVector + increment - 1))).element, K1);
                    if (compare == 0) {
                        compare = Compare(((ArrayLongs) (sortedSolsX.elementAt(indexVector + increment - 1))).element, H1);
                    }
                    if (compare == 0) {
                        return;
                    }
                    if (compare < 0) {
                        indexVector += increment;
                    }
                }
                increment /= 2;
            }
        }

        NbrSols++;
//        System.out.println ("**temp InsertNewSolution NbrSols=" + NbrSols);
        sortedSolsX.insertElementAt(new ArrayLongs((int) (abs(H1[0]) + 1)), indexVector);
        System.arraycopy(H1,
                0,
                ((ArrayLongs) (sortedSolsX.elementAt(indexVector))).element,
                0,
                (int) (abs(H1[0]) + 1));
        sortedSolsY.insertElementAt(new ArrayLongs((int) (abs(K1[0]) + 1)), indexVector);
        System.arraycopy(K1,
                0,
                ((ArrayLongs) (sortedSolsY.elementAt(indexVector))).element,
                0,
                (int) (abs(K1[0]) + 1));
    }

    private static int Compare(long longarray[], long K1[]) {
        int length, i, retcode;
        boolean signlongarray = false, signK1 = false;

        if (longarray[0] < 0) {
            signlongarray = true;
            ChangeSign(longarray, (byte) 1);
        }
        if (K1[0] < 0) {
            signK1 = true;
            ChangeSign(K1, (byte) 1);
        }

        length = (int) longarray[0];
        if (length < (int) K1[0]) {
            retcode = -1;
        } else {
            if (length > (int) K1[0]) {
                retcode = 1;
            } else {
                for (i = length; i > 0; i--) {
                    if (longarray[i] != K1[i]) {
                        break;
                    }
                }
                if (i == 0) {
                    /* Absolute values are equal */
                    if (signlongarray == signK1) {
                        retcode = 0;
                    } else {
                        if (signlongarray == false) {
                            retcode = 1;
                        } else {
                            retcode = -1;
                        }
                    }
                } else {
                    /* Different absolute values */
                    if (longarray[i] < K1[i]) {
                        retcode = -1;
                    } else {
                        retcode = 1;
                    }
                }
            }
        }
        if (signlongarray == true) {
            ChangeSign(longarray, (byte) 1);
        }
        if (signK1 == true) {
            ChangeSign(K1, (byte) 1);
        }
        return retcode;
    }

    /**
     * * ****************************************************
     */
    /* H  =  Constant term                                   */
 /* T  =  Divisor of the square part of the constant term */
 /* A  =  X^2 coefficient                                 */
 /* B  =  XY coefficient                                  */
 /* C  =  Y^2 coefficient                                 */
 /* S  =  Nothing or apostrophe (complete quad equation)  */
    /**
     * ******************************************************
     */
    private static void SolContFrac(long H, long T, long A, long B, long C,
            String S) {
        long factor[] = new long[64];
        long P[] = new long[64];
        long Q[] = new long[64];
        long Dif[] = new long[64];
        /* Holds difference */
        long mod[] = new long[64];
        long pos[] = new long[64];
        long Tmp, q, r, s, t, u, v, Pp, dif, Sol1, Sol2, Modulo;
        long Tmp1 = SQD;
        long Tmp2[] = new long[6];
        long Tmp3[] = new long[6];
        long Tmp4 = DET;
        long ValA, ValB, ValC, ValF, ValAM, ValBM, ValCM;
        long VarD, VarK, VarQ, VarR, VarT, VarV, VarW, VarX, VarY, VarY1;
        long biA[] = new long[6];
        long biB[] = new long[6];
        long biC[] = new long[6];
        long biR[] = new long[6];
        long biS[] = new long[6];
        long biT[] = new long[6];
        ArrayLongs LongArrayX, LongArrayY;
        int index, index2, cont;
        int NbrFactors;
        long gcdAF, MagnifY;
        int cuenta = 0;
        long OrigA, OrigC;
        boolean ShowHR = false;
//        System.out.println ("**temp SolContFrac  H=" + H + "  T=" + T + "  A=" + A +"  B=" +B +"  C=" + C);
        System.arraycopy(NUM, 0, Tmp2, 0, 6);
        System.arraycopy(DEN, 0, Tmp3, 0, 6);
        F = H / T / T;
        if (teach && T > 1) {
            w("Since " + T + " * " + T + " is a divisor of the constant term (" + H + "), the solutions should be " + T + " times the solutions of ");
            ShowEq(A, B, C, 0, 0, F, "u", "v");
            w("  =  0.");
            if (abs(F) != 1) {
                w(" Let F be the constant term.");
            }
            w("\n");
            UU = "U";
            VU = "V";
            UL = "u";
            VL = "v";
            FP = "F";
        }
        if (teach && T == 1) {
            UU = "X" + S;
            VU = "Y" + S;
            UL = "x" + S;
            VL = "y" + S;
            FP = "f" + S;
        }
        gcdAF = gcd(A, F);
        OrigA = A;
        OrigC = C;
        if (teach && gcdAF > 1) {
            w("Since gcd(A, F)  =  gcd(" + A + ", " + F + ")  =  " + gcdAF + " > 1, we have to replace y  =  ny' where n is a divisor of gcd(A,F).\n");
        }
        for (MagnifY = 1; MagnifY * MagnifY <= gcdAF; MagnifY++) {
            do {
                if (gcdAF / MagnifY * MagnifY != gcdAF) {
                    continue;
                }
                if (teach) {
                    if (ShowHR) {
//                        w("<HR>");         // <HR> = 'Horizontal Rule'
                    } else {
                        ShowHR = true;
                    }
                }
                MagnifY = gcdAF / MagnifY;
                F = H / T / T / MagnifY;
                ValF = abs(F);
                A = OrigA / MagnifY;
                C = OrigC * MagnifY;
                ValA = (A + ValF) % ValF;
                ValB = (B + ValF) % ValF;
                ValC = (C + ValF) % ValF;
                if (teach) {
                    if (MagnifY != 1) {
                        w("Let y  =  " + MagnifY + "y'\nWe obtain: ");
                    }
                    w("");
                    ShowEq(A, B, C, 0, 0, F, "x", "y'");
                    w("  =  0\n");
                }
                /* Find factors of F */
                NbrFactors = 0;
                Tmp = ValF;
                if (Tmp == 1) {
                    factor[NbrFactors++] = 1;
                } else {
                    while ((Tmp % 2) == 0) {
                        factor[NbrFactors++] = 2;
                        Tmp /= 2;
                    }
                    while ((Tmp % 3) == 0) {
                        factor[NbrFactors++] = 3;
                        Tmp /= 3;
                    }
                    s = 5;
                    /* Sequence of divisors 5, 7, 11, 13, 17, 19,... */
                    do {
                        while ((Tmp % s) == 0) {
                            factor[NbrFactors++] = s;
                            Tmp /= s;
                        }
                        s += 2;
                        while ((Tmp % s) == 0) {
                            factor[NbrFactors++] = s;
                            Tmp /= s;
                        }
                        s += 4;
                    } while (s * s <= Tmp);
                    if (Tmp != 1) {
                        factor[NbrFactors++] = Tmp;
                    }
                }
                mod[NbrFactors] = Tmp = 1;
                Pp = (2 * ValA) % ValF;
                for (index = NbrFactors - 1; index >= 0; index--) {
                    P[index] = Pp;
                    Tmp *= factor[index];
                    mod[index] = Tmp;
                    Pp = MultMod(MultMod(Pp, factor[index], ValF), factor[index], ValF);
                }
                Modulo = factor[NbrFactors - 1];
                ValAM = (ValA + Modulo) % Modulo;
                ValBM = (ValB + Modulo) % Modulo;
                ValCM = (ValC + Modulo) % Modulo;
                if (ValAM == 0) {
                    /* Linear equation: sol = -C/B */
                    Sol1 = Sol2 = MultMod(Modulo - ValCM, ModInv(ValBM, Modulo), Modulo);
                } else {
                    /* Quadratic equation Ax^2+Bx+C = 0 (mod F) */
                    if (Modulo > 2) {
                        Sol1 = MultMod(ValBM, ValBM, Modulo) - MultMod(4 * ValAM, ValCM, Modulo);
                        if (Sol1 < 0) {
                            Sol1 += Modulo;
                        }
                        /* Find square root of Sol1 mod Modulo */
                        if (Sol1 == 0) {
                            /* if double root: sol  =  -b/2a */
                            Sol1 = Sol2 = MultMod(ModInv((2 * ValAM + Modulo) % Modulo, Modulo), ((-ValBM) + Modulo) % Modulo, Modulo);
                        } else {
                            if (ModPow(Sol1, (Modulo - 1) / 2, Modulo) == 1) {
                                /* if sols exist */
                                if (Modulo % 8 == 5) {
                                    s = ModPow(2 * Sol1, (Modulo - 5) / 8, Modulo);
                                    Sol1 = MultMod(MultMod(MultMod(MultMod(2 * Sol1, s, Modulo), s, Modulo) - 1, Sol1, Modulo), s, Modulo);
                                } else {
                                    if (Modulo % 8 != 1) {
                                        Sol1 = ModPow(Sol1, (Modulo + 1) / 4, Modulo);
                                    } else {
                                        VarR = 1;
                                        VarQ = Modulo - 1;
                                        while (VarQ % 2 == 0) {
                                            VarQ /= 2;
                                            VarR *= 2;
                                        }
                                        VarX = 2;
                                        while (true) {
                                            VarY = ModPow(VarX, VarQ, Modulo);
                                            if (ModPow(VarY, VarR / 2, Modulo) != 1) {
                                                break;
                                            }
                                            VarX++;
                                        }
                                        VarX = ModPow(Sol1, (VarQ - 1) / 2, Modulo);
                                        VarV = MultMod(Sol1, VarX, Modulo);
                                        VarW = MultMod(VarV, VarX, Modulo);
                                        while (VarW != 1) {
                                            VarK = 1;
                                            VarD = VarW;
                                            while (VarD != 1) {
                                                VarD = MultMod(VarD, VarD, Modulo);
                                                VarK *= 2;
                                            }
                                            VarD = ModPow(VarY, VarR / VarK / 2, Modulo);
                                            VarY1 = MultMod(VarD, VarD, Modulo);
                                            VarR = VarK;
                                            VarV = MultMod(VarV, VarD, Modulo);
                                            VarW = MultMod(VarW, VarY1, Modulo);
                                            VarY = VarY1;
                                        }
                                        /* end while */
                                        Sol1 = VarV;
                                    }
                                    /* end modulo 8  =  1 */
                                }
                                s = ModInv((2 * ValAM) % Modulo, Modulo);
                                Sol2 = MultMod((Modulo + Sol1 - ValBM) % Modulo, s, Modulo);
                                Sol1 = MultMod((2 * Modulo - Sol1 - ValBM) % Modulo, s, Modulo);
                            } else {
                                /* No solution exists */
                                Sol1 = Sol2 = -1;
                            }
                        }
                    } else {
                        /* Modulo  <=  2 */
                        if (Modulo == 2) {
                            switch ((int) ValBM * 2 + (int) ValCM) {
                                case 0:
                                    /* A  =  1, B  =  0, C  =  0 */
                                    Sol1 = Sol2 = 0;
                                    /* Solution only for s = 0 */
                                    break;
                                case 1:
                                    /* A  =  1, B  =  0, C  =  1 */
                                    Sol1 = Sol2 = 1;
                                    /* Solution only for s = 1 */
                                    break;
                                case 2:
                                    /* A  =  1, B  =  1, C  =  0 */
                                    Sol1 = 0;
                                    /* Solution for s = 0 and s = 1 */
                                    Sol2 = 1;
                                    break;
                                default:
                                    /* A  =  1, B  =  1, C  =  1 */
                                    Sol1 = Sol2 = -1;
                                    /* No solutions */
                                    break;
                            }
                        } /* End Modulo  =  2 */ else {
                            /* Modulo  =  1 */
                            Sol1 = Sol2 = 0;
                        }
                    }
                }
                /* End Quadratic Equation */

//                System.out.println("**temp SolContFrac Sol1=" + Sol1 + "  Sol2 =" + Sol2);
                ValAM = (ValA + ValF) % ValF;
                ValBM = (ValB + ValF) % ValF;
                ValCM = (ValC + ValF) % ValF;
                if (teach) {
                    UL1 = UL;
                    VL1 = VL + (MagnifY == 1 ? "" : "'");
                    w("Let " + UL1 + "  =  s" + VL1 + " - " + FP + "z, so [-(as" + sq + " + bs + c)/" + FP + "]" + VL1 + sq + " + (2as + b)" + VL1 + "z - a" + FP + "z" + sq + "  =  1.\nSo ");
                    ShowEq(A, 0, 0, B, 0, C, "s", "");
                    w(" should be multiple of " + ValF + ".\n");
                }
                NbrEqs = EqNbr = 0;
                byte sol = 0;
                t = Sol1;
                for (cont = (Sol1 < 0 ? 2 : 0); cont < 2; cont++) {
                    index = NbrFactors - 1;
                    v = mod[index];
                    dif = 0;
                    q = (MultMod((MultMod(ValAM, t, ValF) + ValBM) % ValF, t, ValF) + ValCM) % ValF;
                    /* q%v  =  0 */
                    while (true) {
                        if (q % v == 0) {
                            if (index == 0) {
                                /* Solution found */
                                NbrEqs++;
                                if (teach) {
                                    s = t * mod[1];
                                    for (index2 = 1; index2 < NbrFactors; index2++) {
                                        s += pos[index2] * mod[index2 + 1];
                                    }
                                    s = s % ValF;
                                    if (sol == 0) {
                                        sol = 1;
                                        w("This holds for s  =  " + s);
                                    } else {
                                        w(", " + s);
                                    }
                                }
                            } else {
                                pos[index] = t;
                                t = 0;
                                for (index2 = index; index2 < NbrFactors; index2++) {
                                    t += pos[index2] * mod[index2 + 1];
                                }
                                t = t % ValF;
                                Dif[index] = dif;
                                Q[index] = q;
                                dif = MultMod((MultMod((2 * t + v) % ValF, ValAM, ValF) + ValBM) % ValF, v, ValF);
                                Pp = P[--index];
                                t = 0;
                                v = mod[index];
                                continue;
                            }
                        }
                        if (index != NbrFactors - 1 && ++t < factor[index]) {
                            q = (q + dif) % ValF;
                            dif = (dif + Pp) % ValF;
                            continue;
                        } else {
                            while (++index < NbrFactors) {
                                t = pos[index];
                                v = mod[index];
                                /* Restore previous values */
                                if (index < NbrFactors - 1 && ++t < factor[index]) {
                                    Pp = P[index];
                                    dif = Dif[index];
                                    q = (Q[index] + dif) % ValF;
                                    dif = (dif + Pp) % ValF;
                                    break;
                                }
                            }
                            if (index >= NbrFactors) {
                                break;
                            }
                        }
                    }
                    if (Sol1 == Sol2) {
                        break;
                    }
                    /* Do not process double root */
                    t = Sol2;
                }
                if (teach) {
                    if (sol == 0) {
                        w("No values of s makes the previous assertion true.\n");
                        SQD = Tmp1;
                        System.arraycopy(Tmp2, 0, NUM, 0, 6);
                        System.arraycopy(Tmp3, 0, DEN, 0, 6);
                        DET = Tmp4;
//                        System.out.println("**temp SolContFrac exit(1)");
                        return;
                    }
                    w(".\n");
                }
                /* end if (teach) */
                t = Sol1;
                for (cont = (Sol1 < 0 ? 2 : 0); cont < 2; cont++) {
                    index = NbrFactors - 1;
                    v = mod[index];
                    dif = 0;
                    q = (MultMod((MultMod(ValAM, t, ValF) + ValBM) % ValF, t, ValF) + ValCM) % ValF;
                    /* q%v  =  0 */
                    while (true) {
//                        System.out.println("**temp SolContFrac(2)");
                        if (q % v == 0) {
                            if (index == 0) {
                                /* Solution found */
                                EqNbr++;
                                s = t * mod[1];
                                for (index2 = 1; index2 < NbrFactors; index2++) {
                                    s += pos[index2] * mod[index2 + 1];
                                }
                                s = s % ValF;
                                //  Calculate biA  =  -((As+B)s+C)/F
                                //            biB  =  2As+B
                                //            biC  =  -AF
                                //            biR  =  gcd(biA,  biB,  biC)
                                LongToDoublePrecLong(A, biR);         // biR  =  A
                                LongToDoublePrecLong(s, biS);         // biS  =  s
                                LongToDoublePrecLong(B, biB);         // biB  =  B
                                MultDoublePrecLong(biR, biS, biC);     // biC  =  As
                                AddDoublePrecLong(biC, biB, biT);      // biT  =  As+B
                                MultDoublePrecLong(biT, biS, biA);     // biA  =  (As+B)s
                                LongToDoublePrecLong(C, biR);         // biR  =  C
                                AddDoublePrecLong(biA, biR, biT);      // biT  =  (As+B)s+C
                                LongToDoublePrecLong(-F, biR);        // biR  =  -F
                                DivideDoublePrecLong(biT, biR, biA);   // biA  =  -((As+B)s+C)/F
                                AddDoublePrecLong(biC, biC, biC);      // biC  =  2As
                                AddDoublePrecLong(biC, biB, biB);      // biB  =  2As+B
                                LongToDoublePrecLong(A, biT);         // biT  =  A
                                MultDoublePrecLong(biR, biT, biC);     // biC  =  -AF
                                GcdDoublePrecLong(biA, biB, biT);      // biT  =  gcd(biA,  biB)
                                GcdDoublePrecLong(biT, biC, biR);      // biR  =  gcd(biA, biB, biC)
                                if (teach) {
                                    w("Let s  =  " + s + ". Replacing in the above equation:\n");
                                    ShowBigEq(biA, biB, biC, VL1, "z");
                                    w("  =  1\n");
                                }
                                if (biR[0] > 1 || biR[1] > 0 || biR[2] > 0 || biR[3] > 0 || biR[4] > 0
                                        || biR[5] > 0 && biR[5] < DosALa31) {  // if biR > 1 ...
                                    if (teach) {
                                        w("Since the gcd of the three coefficients is " + DoublePrecLongToString(biR) + " there are no integer solutions.\n");
                                    }
                                } else {
                                    LongArrayX = LongArrayY = null;
                                    GetRoot(biA, biB, biC);
                                    if (S.equals("")) {
                                        if (ContFrac(biA, (byte) 3, (byte) 1, s, T, MagnifY, A)) {
                                            if (L2[0] < 0) {
                                                ChangeSign(L1, (byte) 1);
                                                ChangeSign(L2, (byte) 1);
                                            }
                                            LongArrayX = new ArrayLongs((int) (abs(L1[0]) + 1));
                                            System.arraycopy(L1,
                                                    0,
                                                    LongArrayX.element,
                                                    0,
                                                    (int) (abs(L1[0]) + 1));
                                            LongArrayY = new ArrayLongs((int) (abs(L2[0]) + 1));
                                            System.arraycopy(L2,
                                                    0,
                                                    LongArrayY.element,
                                                    0,
                                                    (int) (abs(L2[0]) + 1));
                                        }

                                        if (ContFrac(biA, (byte) 3, (byte) (-1), s, T, MagnifY, A)) {
                                            if (L2[0] < 0) {
                                                ChangeSign(L1, (byte) 1);
                                                ChangeSign(L2, (byte) 1);
                                            }
                                            if (teach) {
                                                w("Choosing the solution with minimum y:\n");
                                                NbrSols++;
                                            }
                                            if (LongArrayY != null && Compare(L2, LongArrayY.element) > 0) {
//                                                System.out.println("**temp SolContFrac(2A)");
                                                ShowLargeXY("X", "Y", LongArrayX.element, LongArrayY.element, true, "", "");
                                            } else {
//                                                System.out.println("**temp SolContFrac(2B)");
                                                ShowLargeXY("X", "Y", L1, L2, true, "", "");
                                            }
//                                          w("</TABLE>");
                                        } else {
                                            if (LongArrayY != null) {
//                                                 System.out.println("**temp SolContFrac(3)");
                                                ShowLargeXY("X", "Y", LongArrayX.element, LongArrayY.element, true, "", "");
                                            }
                                        }
                                    } else {
                                        ContFrac(biA, (byte) 4, (byte) 1, s, T, MagnifY, A);
                                        ContFrac(biA, (byte) 4, (byte) (-1), s, T, MagnifY, A);
                                    }
                                }
                            } else {
                                pos[index] = t;
                                t = 0;
                                for (index2 = index; index2 < NbrFactors; index2++) {
                                    t += pos[index2] * mod[index2 + 1];
                                }
                                t = t % ValF;
                                Dif[index] = dif;
                                Q[index] = q;
                                dif = MultMod((MultMod((2 * t + v) % ValF, ValAM, ValF) + ValBM) % ValF, v, ValF);
                                Pp = P[--index];
                                t = 0;
                                v = mod[index];
                                continue;
                            }
                        }
                        if (index < NbrFactors - 1 && ++t < factor[index]) {
                            q = (q + dif) % ValF;
                            dif = (dif + Pp) % ValF;
//                            System.out.println("**temp SolContFrac(4)");
                            continue;
                        } else {
                            while (++index < NbrFactors) {
//                                System.out.println("**temp SolContFrac(5)");
                                t = pos[index];
                                v = mod[index];
                                /* Restore previous values */
                                if (index < NbrFactors - 1 && ++t < factor[index]) {
                                    Pp = P[index];
                                    dif = Dif[index];
                                    q = (Q[index] + dif) % ValF;
                                    dif = (dif + Pp) % ValF;
                                    break;
                                }
                            }
                            if (index >= NbrFactors) {
//                                System.out.println("**temp SolContFrac(6)");
                                break;
                            }
                        }
                    }
//                    System.out.println("**temp SolContFrac(7)");
                    if (Sol1 == Sol2) {
                        break;
                    }
                    /* Do not process double root */
//                    System.out.println("**temp SolContFrac(8)");
                    t = Sol2;
                }
                if (teach) {
//      w("</UL>");
                }
                SQD = Tmp1;
                System.arraycopy(Tmp2, 0, NUM, 0, 6);
                System.arraycopy(Tmp3, 0, DEN, 0, 6);
                DET = Tmp4;
//                System.out.println("**temp SolContFrac(9)");
            } while (MagnifY * MagnifY > gcdAF);
//            System.out.println("**temp SolContFrac(10)");
        }
//        System.out.println("**temp SolContFrac exit(11)");
    }

    private static byte ShowSols(byte type) {
        if (type == 1) {
            if (CalculateKandL() != 0) {
                /* if K or L not integers */
                return 1;
                /* bye */
            }
        }
        if (teach) {
            w("m  =  ");
            ShowLargeNumber(H2);
            w("\nn  =  ");
            ShowLargeNumber(K2);
            w("\nUsing the formulas:\nP  = ");
            w(" m\nQ  =  -Cn\n");
            if (type == 1) {
                w("K  =  (CD(P+S-2) + E(B-Bm-2ACn))/(4AC-B^2) \n");
            }
            w("R  =  An\nS  =  m + Bn\n");
            if (type == 1) {
                w("L  = D(B-Bm-2ACn) + AE(P+S-2)/(4AC - B)^2 + Dn \n");
            }
            w("we obtain:\n");
        }
        w("P  =  ");
        ShowLargeNumber(H2);
        w("\nQ  =  ");
        MultLargeNumber(-C, K2, H1);
        ShowLargeNumber(H1);
        if (type == 1) {
            w("\nK  =  ");
            ShowLargeNumber(L1);
        }
        w("\nR  =  ");
        MultLargeNumber(A, K2, H1);
        ShowLargeNumber(H1);
        w("\nS  =  ");
        MultAddLargeNumbers(1, H2, B, K2, H1);
        ShowLargeNumber(H1);
        if (type == 1) {
            w("\nL  =  ");
            ShowLargeNumber(L2);
        }
        w("\n");
        return 0;
    }

    /* It returns zero if K and L are valid */
    private static byte CalculateKandL() {
        MultAddLargeNumbers(2, H2, B, K2, L1);
        /* 2r + Bs */
        AddLargeLong(L1, -2, H1);
        /* Kd  =  2r + Bs - 2 */
        AddLargeLong(H2, -1, K1);
        /* r - 1 */
        MultAddLargeNumbers(-B, K1, -2 * A * C, K2, K1);
        /* Ke */
        MultAddLargeNumbers(C * D, H1, E, K1, L1);
        /* K(4AC - BB) */
        if (DivLargeNumber(L1, 4 * A * C - B * B, L1) != 0) {
            /* K */
            return 1;
            /* K not integer */
        }
        MultAddLargeNumbers(D, K1, A * E, H1, L2);
        if (DivLargeNumber(L2, 4 * A * C - B * B, L2) != 0) {
            return 1;
            /* L not integer */
        }
        MultAddLargeNumbers(1, L2, D, K2, L2);
        /* L */
        return (byte) 0;
    }

    private static void ShowRecursionRoot(byte type) {
        byte t;
        t = ShowSols(type);
        if (type == 1) {
            ChangeSign(H2, (byte) 1);
            ChangeSign(K2, (byte) 1);
            if (t == 1 && ShowSols((byte) 1) == 1) {
                if (teach) {
                    w("m  =  ");
                    ShowLargeNumber(H2);
                    w("\nn  =  ");
                    ShowLargeNumber(K2);
                    w("\nUsing the formulas:\nP  =  m" + sq + " - ACn" + sq + "\nQ  =  -Cn(2m + Bn)\nK  =  -n(Em + CDn)\nR  =  An(2m + Bn)\nS  =  m" + sq + " + 2Bmn + (B" + sq + " - AC)n" + sq + "\nL  =  n(Dm + (BD-AE)n)\nwe obtain:");
                }
                Mult2LargeNumbers(H2, H2, H1);
                /* m^2 */
                Mult2LargeNumbers(H2, K2, K1);
                /* mn */
                Mult2LargeNumbers(K2, K2, L1);
                /* n^2 */
                MultAddLargeNumbers(1, H1, -A * C, L1, L2);
                w("P  =  ");
                ShowLargeNumber(L2);
                MultAddLargeNumbers(-2 * C, K1, -B * C, L1, L2);
                w("\nQ  =  ");
                ShowLargeNumber(L2);
                MultAddLargeNumbers(-E, K1, -C * D, L1, L2);
                w("\nK  =  ");
                ShowLargeNumber(L2);
                MultAddLargeNumbers(2 * A, K1, A * B, L1, L2);
                w("\nR  =  ");
                ShowLargeNumber(L2);
                MultAddLargeNumbers(B * B - A * C, L1, 2 * B, K1, L2);
                AddLargeNumbers(L2, H1, L2);
                w("\nS  =  ");
                ShowLargeNumber(L2);
                MultAddLargeNumbers(D, K1, B * D - A * E, L1, L2);
                w("\nL  =  ");
                ShowLargeNumber(L2);
                w("\n");
            }
        }
    }

    private static void ShowRecursion(byte type) {
        String t1;
        long biA[] = new long[6];
        long biB[] = new long[6];
        long biC[] = new long[6];
        w("Xn+1  =  P Xn + Q Yn" + (type == 1 ? " + K" : ""));
        w("\nYn+1  =  R Xn + S Yn" + (type == 1 ? " + L" : ""));
        t1 = " integer solution of the equation m" + sq + " + bmn + acn" + sq + "  =  ";
        if (teach) {
            w("\nIn order to find the values of P,  Q, R, S we have to find first an" + t1);
            ShowEq(1, B, A * C, 0, 0, 0, "m", "n");
            w("  =  1.\n");
        } else {
            w("\n");
        }
        LongToDoublePrecLong(A, biA);
        LongToDoublePrecLong(C, biB);
        MultDoublePrecLong(biA, biB, biC);
        LongToDoublePrecLong(1, biA);
        LongToDoublePrecLong(B, biB);
        GetRoot(biA, biB, biC);
        ContFrac(biA, (byte) 2, (byte) 1, 0, 0, 1, A);
        if (teach) {
            w("An" + t1);
            ShowEq(1, B, A * C, 0, 0, 0, "m", "n");
            w("  =  1 is:\n");
        }
        ShowRecursionRoot(type);
        if (B != 0) {
            if (teach) {
                w("\nAnother" + t1);
                ShowEq(1, B, A * C, 0, 0, 0, "m", "n");
                w("  =  1 is:\n");
            } else {
                w("\nas well as\n");
            }
            MultAddLargeNumbers(1, H2, B, K2, H2);
            /* r <- r + Bs */
            ChangeSign(K2, (byte) 1);
            /* s <- -s */
            ShowRecursionRoot(type);
        }
    }

    private static boolean CheckMod(long R, long S, long X2, long X1, long X0) {
        long Y2 = gcd(R, S);
        if (teach) {
            if (Y2 != 1) {
                w("We try to check the equation modulo the prime divisors of ");
                if (R != 0 && S != 0) {
                    w("gcd(" + abs(R) + ", " + abs(S) + ")  =  " + Y2 + ".\n");
                } else {
                    w(Y2 + ".\n");
                }
            }
        }
        long Y1 = 2;
        int indH = 1;
        long D1 = abs(Y2);
        while (D1 >= Y1 * Y1) {
            int T = 0;
            while (D1 % Y1 == 0) {
                T++;
                if (T == 1) {
                    H1[indH++] = Y1;
                }
                D1 /= Y1;
            }
            Y1++;
            if (Y1 > 3) {
                Y1++;
            }
        }
        if (D1 > 1) {
            H1[indH++] = D1;
        }
        for (int T = 1; T < indH; T++) {
            if (H1[T] > 1) {
                long Z = ((X1 * X1 - 4 * X0 * X2) % H1[T]) + H1[T];
                long N = (H1[T] - 1) / 2;
                long Y = 1;
                while (N != 0) {
                    if (N % 2 != 0) {
                        Y = (Y * Z) % H1[T];
                    }
                    N /= 2;
                    Z = (Z * Z) % H1[T];
                }
                if (Y > 1) {
                    if (teach) {
                        w("There are no solutions modulo " + H1[T] + ", ");
                        NoSol();
                    }
                    return true;
                }
            }
        }
        return false;

    }
}

class ArrayLongs {

    public long element[];

    public ArrayLongs(int NbrElements) {
        this.element = new long[NbrElements];
    }
}

// </XMP>
