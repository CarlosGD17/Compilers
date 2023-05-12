import java.io.*;

import static java.lang.System.exit;

public class azulSLR1 {
    static String a, RENGLON, LEX;
    static String Entrada, Salida;
    static int Posicion = 0;

    static String[] terminales = new String[8];
    static String[] n_terminales = new String[7];
    static String[] parteIzquierda = new String[11];
    static int[] tamanoParteDerecha = new int[11];
    static int[][] M = new int[32][24];

    static String[] PROD = new String[13];
    static String[] pila = new String[1000];

    static int tope = -1;
    static String X;

    static int P_x, P_y;
    static int C_x, C_y;
    static int E_x, E_y;
    static int O_x, O_y;
    static int S_x, S_y;
    static int N_x, N_y;
    static int var;

    public static void lee_token(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            a = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
            //System.out.print(".");

        } catch (IOException e) {
            System.out.println("Errorzote");
        }
    }

    public static void error(){
        System.out.println(" ! ERROR: En el token [" + a + "] cerca del renglón " +RENGLON +"\n");
        exit(4);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
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

    public static int terminal(String X){
        for(int i = 0; i < terminales.length; i++){
            if (X.equals(terminales[i])){
                return i;
            }
        }
        return -1;
    }

    public static int noTerminal(String X){
        for(int i = 0; i < n_terminales.length; i++){
            if (X.equals(n_terminales[i])){
                return i + 8;
            }
        }
        return -1;
    }

    public static void push(String x){
        if(tope >= 9999){
            System.out.println("Error: pila llena");
            exit(4);
        }
        if(!x.equals("epsilon")){
            pila[++tope] = x;
        }
    }

    public static String pop(){
        if(tope < 0){
            System.out.println("Error: pila vacia");
            exit(4);
        }
        return pila[tope--];
    }

    public static void printPila(){
        System.out.println("Pila --> ");
        for(int i = 0; i <= tope; i++){
            System.out.println(pila[i] + " ");
        }
        System.out.println("");
        pausa();
    }

    public static void main(String [] args){
        Entrada = args[0] +".anl";
        if(!xArchivo(Entrada).exists()){
            System.out.println("\n\nERROR: El archivo no existe [" +Entrada +"]");
            exit(4);
        }

        terminales[0]="n";
        terminales[1]="s";
        terminales[2]="e";
        terminales[3]="o";
        terminales[4]="(";
        terminales[5]="num";
        terminales[6]=")";
        terminales[7]="eof";

        n_terminales[0] = "PP";
        n_terminales[1] = "P";
        n_terminales[2] = "C";
        n_terminales[3] = "N";
        n_terminales[4] = "S";
        n_terminales[5] = "E";
        n_terminales[6] = "O";

        parteIzquierda[0]="PP";
        parteIzquierda[1]="P";
        parteIzquierda[2]="P";
        parteIzquierda[3]="C";
        parteIzquierda[4]="C";
        parteIzquierda[5]="C";
        parteIzquierda[6]="C";
        parteIzquierda[7]="N";
        parteIzquierda[8]="S";
        parteIzquierda[9]="E";
        parteIzquierda[10]="O";

        tamanoParteDerecha[0]=1;
        tamanoParteDerecha[1]=1;
        tamanoParteDerecha[2]=2;
        tamanoParteDerecha[3]=1;
        tamanoParteDerecha[4]=1;
        tamanoParteDerecha[5]=1;
        tamanoParteDerecha[6]=1;
        tamanoParteDerecha[7]=4;
        tamanoParteDerecha[8]=4;
        tamanoParteDerecha[9]=4;
        tamanoParteDerecha[10]=4;

        for(int i = 0; i < M.length; i++){
            for(int j = 0; j< M[0].length; j++){
                M[i][j] = 0;
            }
        }

        // shifts
        M[0][0]=7;
        M[0][1]=8;
        M[0][2]=9;
        M[0][3]=10;
        M[1][0]=7;
        M[1][1]=8;
        M[1][2]=9;
        M[1][3]=10;
        M[7][4]=12;
        M[8][4]=13;
        M[9][4]=14;
        M[10][4]=15;
        M[12][5]=16;
        M[13][5]=17;
        M[14][5]=18;
        M[15][5]=19;
        M[16][6]=20;
        M[17][6]=21;
        M[18][6]=22;
        M[19][6]=23;
        // go to
        M[0][9]=1;
        M[0][10]=2;
        M[0][11]=3;
        M[0][12]=4;
        M[0][13]=5;
        M[0][14]=6;
        M[1][10]=11;
        M[1][11]=3;
        M[1][12]=4;
        M[1][13]=5;
        M[1][14]=6;
        M[1][7]=1117;
        // reduce
        M[2][0]=-1;
        M[2][1]=-1;
        M[2][2]=-1;
        M[2][3]=-1;
        M[2][7]=-1;
        M[3][0]=-3;
        M[3][1]=-3;
        M[3][2]=-3;
        M[3][3]=-3;
        M[3][7]=-3;
        M[4][0]=-4;
        M[4][1]=-4;
        M[4][2]=-4;
        M[4][3]=-4;
        M[4][7]=-4;
        M[5][0]=-5;
        M[5][1]=-5;
        M[5][2]=-5;
        M[5][3]=-5;
        M[5][7]=-5;
        M[6][0]=-6;
        M[6][1]=-6;
        M[6][2]=-6;
        M[6][3]=-6;
        M[6][7]=-6;
        M[11][0]=-2;
        M[11][1]=-2;
        M[11][2]=-2;
        M[11][3]=-2;
        M[11][7]=-2;
        M[20][0]=-7;
        M[20][1]=-7;
        M[20][2]=-7;
        M[20][3]=-7;
        M[20][7]=-7;
        M[21][0]=-8;
        M[21][1]=-8;
        M[21][2]=-8;
        M[21][3]=-8;
        M[21][7]=-8;
        M[22][0]=-9;
        M[22][1]=-9;
        M[22][2]=-9;
        M[22][3]=-9;
        M[22][7]=-9;
        M[23][0]=-10;
        M[23][1]=-10;
        M[23][2]=-10;
        M[23][3]=-10;
        M[23][7]=-10;

        PROD[1] = "P";
        PROD[2] = "C";
        PROD[3] = "P C";
        PROD[4] = "N";
        PROD[5] = "S";
        PROD[6] = "E";
        PROD[7] = "O";
        PROD[8] = "n ( num )";
        PROD[9] = "s ( num )";
        PROD[10] = "e ( num )";
        PROD[11] = "o ( num )";

        // inicia el parser
        push("0");
        lee_token(xArchivo(Entrada));
        String s;
        int e;
        int m;
        do{
            s = pila[tope];
            m = M[Integer.parseInt(s)][terminal(a)];
            if(m == 1117){
                System.out.println("-> Parser SLR terminado con exito   :)");

                System.out.println("Las coordenadas finales son: x = " +P_x +", y = " +P_y);

                exit(0);
            } else{
                if(m > 0){
                    push(a);
                    push(m + "");

                    cod_shift(m);

                    lee_token(xArchivo(Entrada));
                } else if(m < 0) {

                    cod_reduce(m);

                    for (int i = 0; i < tamanoParteDerecha[m * -1] * 2; i++) {
                        pop();
                    }
                    e = Integer.parseInt(pila[tope]);
                    push(parteIzquierda[m * -1]);
                    if (M[e][noTerminal(parteIzquierda[m * -1])] == 0) {
                        error();
                    } else {
                        push(M[e][noTerminal(parteIzquierda[m * -1])] + "");
                    }
                } else{
                    error();
                }
            }
        }while(true);
    }

    public static void cod_reduce(int R){
        R = -R;
        switch (R){
            case 1:
                P_x = C_x;
                P_y = C_y;
                break;
            case 2:
                P_x = P_x + C_x;
                P_y = P_y + C_y;
                break;
            case 3:
                C_x = N_x;
                C_y = N_y;
                break;
            case 4:
                C_x = S_x;
                C_y = S_y;
                break;
            case 5:
                C_x = E_x;
                C_y = E_y;
                break;
            case 6:
                C_x = O_x;
                C_y = O_y;
                break;
            case 7:
                N_x = 0;
                N_y = var;
                break;
            case 8:
                S_x = 0;
                S_y = -var;
                break;
            case 9:
                E_x = var;
                E_y = 0;
                break;
            case 10:
                O_x = -var;
                O_y = 0;
                break;
        }
    }

    public static void cod_shift(int S){

        switch(S){
            case 16:
                var = Integer.parseInt(LEX);
                break;
            case 17:
                var = Integer.parseInt(LEX);
                break;
            case 18:
                var = Integer.parseInt(LEX);
                break;
            case 19:
                var = Integer.parseInt(LEX);
                break;
        }

    }
}
