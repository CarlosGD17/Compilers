import java.io.*;

public class azulAL {

    static int a_a = 0; // Apuntador de avance
    static int a_i = 0;   // Apuntador de inicio
    static int filesize = 0;
    static boolean fin_archivo = false;
    public static char[] linea;
    static int DIAG;
    static int ESTADO;
    static int c;
    static String LEX = "";
    static String entrada = "";
    static String salida = "";
    static String MiToken = "";
    static int Renglon = 1;
    static String[] reservada = new String[15];

    public static boolean es_reservada(String x) {
        for (int i = 0; i <= 1; i++) {
            if (reservada[i].equals(x)) {
                return true;
            }
        }
        return false;
    }

    public static boolean creaEscribeArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(xFile, true));
            fileOut.println(mensaje);
            fileOut.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean es_any(int x) {

        return x != 10 && x != 13 && x != 255;
    }

    public static boolean es_delim(int x) {
        if (x == 9 || x == 10 || x == 13 || x == 32) {
            return (true);
        } else {
            return (false);
        }
    }

    public static String ob_lex() {
        String x = "";
        for (int i = a_i; i < a_a; i++)
            x = x + linea[i];
        return (x);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static int lee_car() {
        if (a_a <= filesize - 1) {
            if (linea[a_a] == 10) {
                Renglon++;
            }
            return (linea[a_a++]);
        } else {
            fin_archivo = true;
            return 255;
        }
    }

    public static char[] abreLeeCierra(String xName) {
        File xFile = new File(xName);
        char[] data;
        try {
            FileReader fin = new FileReader(xFile);
            filesize = (int) xFile.length();
            data = new char[filesize + 1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize++;
            return (data);
        } catch (FileNotFoundException exc) {
        } catch (IOException exc) {
        }
        return null;
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
            return (nada);
        } catch (Exception e) {
            System.err.println(e);
        }
        return ("");
    }

    public static void error() {
        System.out.println("ERROR: en el caracter: " + (char) c);
        System.exit(4);
    }

    public static boolean esLetra() {
        return ((c >= 65 && c <= 90) || (c >= 97 && c <= 122));
    }

    public static boolean esDigito() {
        return ((c >= 48 && c <= 57));
    }

    public static int DIAGRAMA() {
        a_a = a_i;
        switch (DIAG) {
            case 0:
                DIAG = 3;
                break;
            case 3:
                DIAG = 10;
                break;
            case 10:
                DIAG = 13;
                break;
            case 13:
                DIAG = 15;
                break;
            case 15:
                error();
        }
        return (DIAG);
    }

    public static String TOKEN() {
        do {
            switch (ESTADO) {
                case 0:
                    c = lee_car();
                    if (esDigito())
                        ESTADO = 1;
                    else
                        ESTADO = DIAGRAMA();
                    break;
                case 1:
                    c = lee_car();
                    if (esDigito())
                        ESTADO = 1;
                    else
                        ESTADO = 2;
                    break;
                case 2:
                    a_a--;
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("num");
                case 3:
                    c = lee_car();
                    switch (c) {
                        case 'n':
                            ESTADO = 4;
                            break;
                        case 's':
                            ESTADO = 5;
                            break;
                        case 'e':
                            ESTADO = 6;
                            break;
                        case 'o':
                            ESTADO = 7;
                            break;
                        case '(':
                            ESTADO = 8;
                            break;
                        case ')':
                            ESTADO = 9;
                            break;
                        default:
                            ESTADO = DIAGRAMA();
                    }
                    break;
                case 4:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("n");
                case 5:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("s");
                case 6:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("e");
                case 7:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("o");
                case 8:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("(");
                case 9:
                    LEX = ob_lex();
                    a_i = a_a;
                    return (")");
                case 10:
                    c = lee_car();
                    if (es_delim(c))
                        ESTADO = 11;
                    else
                        ESTADO = DIAGRAMA();
                    break;
                case 11:
                    c = lee_car();
                    if (es_delim(c))
                        ESTADO = 11;
                    else
                        ESTADO = 12;
                    break;
                case 12:
                    a_a--;
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("basura");
                case 13:
                    c = lee_car();
                    if (c == 255)
                        ESTADO = 14;
                    else
                        ESTADO = DIAGRAMA();
                    break;
                case 14:
                    a_i = a_a;
                    return ("eof");
                case 15:
                    if (lee_car() == '"')
                        ESTADO = 16;
                    else
                        ESTADO = DIAGRAMA();
                    break;
                case 16:
                    c = lee_car();
                    if (c == '"') {
                        ESTADO = 17;
                        break;
                    }
                    if (es_any(c)) {
                        ESTADO = 16;
                    } else {
                        ESTADO = DIAGRAMA();
                    }
                    break;
                case 17:
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("str");
                case 18:
                    a_a--;
                    LEX = ob_lex();
                    a_i = a_a;
                    return ("basura");//antes era nosirve
            }
        } while (true);
    }

    public static void main(String[] args) {

        entrada = args[0] + ".txt";
        salida = args[0] + ".anl";

        reservada[0] = "print";
        reservada[1] = "else";

        if (!xArchivo(entrada).exists()) {
            System.out.println("\n\nERROR: El archivo " + entrada + " no existe"); //CHECAR ESTA PARTE
            System.exit(4);
        }

        linea = abreLeeCierra(entrada);

        do {
            ESTADO = 0;
            DIAG = 0;
            MiToken = TOKEN();
            if (!MiToken.equals("basura")) {
                creaEscribeArchivo(xArchivo(salida), MiToken);
                creaEscribeArchivo(xArchivo(salida), LEX);
                creaEscribeArchivo(xArchivo(salida), Renglon + "");
            }
            a_i = a_a;
        } while (!fin_archivo);
        creaEscribeArchivo(xArchivo(salida), "eof");
        creaEscribeArchivo(xArchivo(salida), "eof");
        creaEscribeArchivo(xArchivo(salida), Renglon + ""); //antes era 666
        System.out.println("Analisis lexicografico exitoso");
    }
}
