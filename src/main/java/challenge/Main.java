package challenge;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class Main {

    public final static String FILE_20K="20000x20000.json";
    public  final static String FILE_10K="10000x10000.json";
    public  final static String FILE_1K="1000x1000.json";
    public  final static String FILE_100="100x100.json";
    public  final static String U_SHAPE="u_shape.json";
    public  final static String COBRA_SHAPE="cobra_shape.json";

    public static void main(String[] args) throws IOException {

        //Main m=new Main(FILE_20K,20000);
        //Main m=new Main(FILE_10K,10000);
        //Main m=new Main(FILE_1K,1000);
        //Main m=new Main(FILE_100,100);
        //Main m=new Main(COBRA_SHAPE,6);
        //Main m=new Main(U_SHAPE,6);
        //m.run();

        Utils.printTitle("Adjacent Cells");
        String file=Utils.readLineFromConsole("File path");
        int size=Integer.parseInt(Utils.readLineFromConsole("matrix size"));
        try {
            Main m=new Main(file,size);
            m.run();
        }catch (IOException e){
            System.out.println("Invalid file/path");
        }

    }

    private String file;
    private int size;
    /**
     *
     */
    public Main(String file, int size){
        this.file=file;
        this.size=size;
    }

    public void run() throws IOException {
        int [][] matrix=fileParser(new File(file));
        List<List<Cells>> lst=new ArrayList<>();
        search(matrix,size,0,0,lst);
        /*for (List<Cells> cells: lst) {
            System.out.println(cells);
        }*/

    }

    /**
     * extract array from json file
     * @param file
     * @return
     * @throws IOException
     */
    private  int[][] fileParser(File file) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.readValue(file,int[][].class);
    }

    /**
     * search for the first '1' element
     * @param matrix
     * @param size
     * @param row
     * @param col
     * @param lst
     */
    private void search(int[][] matrix, int size, int row, int col, List<List<Cells>> lst){
        List<Cells> cells;
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                if(matrix[i][j]==1){
                    cells=new ArrayList<>();
                    searchDown(matrix,size,i,j,cells);
                    if(cells.size()>1) {
                        //lst.add(cells);
                        System.out.println(cells);

                    }
                }
            }
        }
    }

    /**
     * search for adjacent cells bellow
     * @param matrix
     * @param size
     * @param row
     * @param col
     * @param list
     */
    private void searchDown(int[][]matrix,int size,int row, int col,List<Cells> list){
        if(row>=size || col<0  || col>=size)return;
        if (matrix[row][col] == 1) {
            list.add(new Cells(col,row));
            matrix[row][col]=0;
            searchDown(matrix,size,row+1,col,list);
            searchRight(matrix,size,row,col+1,list);
            searchLeft(matrix,size,row,col-1,list);
        }
    }

    private void searchUp(int[][]matrix,int size,int row, int col,List<Cells> list){
        if(row<0 || col<0  || col>=size)return;
        if (matrix[row][col] == 1) {
            list.add(new Cells(col,row));
            matrix[row][col]=0;
            searchUp(matrix,size,row-1,col,list);
            searchRight(matrix,size,row,col+1,list);
            searchLeft(matrix,size,row,col-1,list);
        }
    }

    /**
     * search for adjacent cells on the left side
     * @param matrix
     * @param size
     * @param row
     * @param col
     * @param list
     */
    private  void searchLeft(int[][]matrix,int size,int row, int col,List<Cells> list){
        if (col>0 && matrix[row][col] == 1) {
            searchLeft(matrix,size,row,col-1,list);
            list.add(new Cells(col,row));
            matrix[row][col]=0;
            searchUp(matrix,size,row-1,col,list);
            searchDown(matrix,size,row+1,col,list);
        }
    }

    /**
     * search for adjacent cells on the right side
     * @param matrix
     * @param size
     * @param row
     * @param col
     * @param list
     */
    private  void searchRight(int[][]matrix,int size,int row, int col,List<Cells> list){
            if (col<size && matrix[row][col] == 1) {
                searchRight(matrix,size,row,col+1,list);
                list.add(new Cells(col,row));
                matrix[row][col]=0;
                searchUp(matrix,size,row-1,col,list);
                searchDown(matrix,size,row+1,col,list);
            }
    }

    /**
     * cells class
     */
    public class Cells {
        private int col;
        private int row;

        public Cells(int col, int row){
            this.col=col;
            this.row=row;
        }

        @Override
        public String toString() {
            return "[" + row +
                    "," + col +
                    ']';
        }
    }




    }
