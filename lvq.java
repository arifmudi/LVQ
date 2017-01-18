/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LVQ;
import java.util.Arrays;

/**
 *
 * @author Cakra Karta
 */

public class LVQ {
    
    public double weight[][];
    public double data[][];
    public int maxEpoch = 0;
    public double minError = 1;
    public double α = 0; // leraning rate

    /**
     * Sebagai method constructor dari class LVQ
     *
     * @param input
     * @param learning_rate
     * @param pengurangan_α
     * @param maxEpoch
     */
    public LVQ(int input[][], double learning_rate, double pengurangan_α, int maxEpoch) {
	this.α = learning_rate;
	this.maxEpoch = maxEpoch;
	this.setWeight(input);
	for (int epoch = 0; epoch < this.maxEpoch; epoch++) {
	    System.out.println("EPOCH KE-" + (epoch + 1) + "\n"
		    + "##############################################################################################################################################################");
	    for (int index = 0; index < this.data.length; index++) {
		this.getJarak(this.data[index], index);
	    }
	    this.α = this.α - pengurangan_α * this.α;
            System.out.println(" pengurangan alpha = " + this.α);
	}
	System.out.println("-----------------------SELESAI------------------------------");
    }

    /**
     * digunakan untuk menentukan banyak nya jumlah target atau class dari inputan yang dimasukkan
     *
     * @param input
     * @return count
     */
    public int countTarget(int input[][]) {
	int count = 1;
	int tmp[] = new int[input.length];
	int in = input[0][input[0].length - 1];
	for (int indexRow = 0; indexRow < input.length; indexRow++) {
	    tmp[indexRow] = input[indexRow][input[indexRow].length - 1];
	}
	Arrays.sort(tmp);
	int a = tmp[0];
	for (int index = 1; index < tmp.length; index++) {
	    if (a != tmp[index]) {
		count++;
		a = tmp[index];
                System.out.println("A target = "+a);
	    }
	} 
        
	return count;
    }

    /**
     * digunakan untuk mengatur array weight dan array data
     *
     * @see LVQ#weight
     * @see LVQ#data
     *
     * @param input
     */
    public void setWeight(int input[][]) {
	int target = this.countTarget(input);
	// mengatur jumlah baris dan kolom array data
	this.data = new double[input.length - target][input[0].length];
	// mengatur jumlah baris dan kolom array bobot
	this.weight = new double[target][input[0].length];
	// variabel index untuk baris dan kolom array data
	int indexRowData = 0;
	int indexColumeData = 0;

	// proses inisialisasi bobot dan data
	for (int indexRow = 0; indexRow < input.length; indexRow++) {
	    for (int indexColume = 0; indexColume < input[indexRow].length; indexColume++) {
		// periksa index baris array input < banyak target atau class
		if (indexRow < target) {
		    // mengisi array bobot dengan beberapa data dari array input
		    weight[indexRow][indexColume] = input[indexRow][indexColume];
		} else {
		    // mengisi array data dengan beberapa data dari array input setelah kondisi tidak sesuai
		    data[indexRowData][indexColumeData] = input[indexRow][indexColume];
		    // penambahan 1 atau increament
		    indexColumeData++;
		}
	    }
	    // memeriksa indexrow > target
	    if (!(indexRow < target)) {
		indexRowData++;	// penambahan 1 atau increament dan
		indexColumeData = 0; // mengatur indexColumeData = 0
	    }
	}
    }

    /**
     * Digunakan untuk menghitung jarak
     *
     * @param input
     * @param indexData
     */
    public void getJarak(double input[], int indexData) {
	double jarak[] = new double[this.weight.length];
	for (int indexRow = 0; indexRow < this.weight.length; indexRow++) {
	    double count = 0;
	    String datake = "\tData ke-" + (indexData + 1) + " = [ ";
	    String bobotke = "\tBobot ke-" + (indexRow + 1) + " = √(";
	    String bobotValue = "\t= √(";
	    for (int indexColume = 0; indexColume < (this.weight[indexRow].length - 1); indexColume++) {
		datake += (int) input[indexColume] + "";
		datake += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " , " : "]";
		count += Math.pow((input[indexColume] - this.weight[indexRow][indexColume]), 2);
		bobotke += "(X[" + (indexData + 1) + "][" + (indexColume + 1) + "] - W[" + (indexRow + 1) + "][" + (indexColume + 1) + "])^2";
		bobotValue += "(" + input[indexColume] + " - " + this.weight[indexRow][indexColume] + ")^2";
		bobotValue += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " + " : ")";
		bobotke += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " + " : ")";
	    }
	    System.out.println(datake);
	    System.out.println(bobotke);
	    System.out.println(bobotValue);
	    jarak[indexRow] = Math.sqrt(count);
	    System.out.println("\t= " + jarak[indexRow]);
	    System.out.println("--------------------------------------------------------------------------");
	}

	double minJarak = jarak[0];
	int indexBobot = 0;
	for (int index = 0; index < jarak.length; index++) {
	    if (jarak[index] <= minJarak) {
		minJarak = jarak[index];
		indexBobot = index;
	    }
	}

	System.out.println("\tJARAK TERKECIL PADA BOBOT KE-" + (indexBobot + 1));

	/* Perbaiki W j dengan ketentuan ;  */
	/* Jika T = Cj maka: wj (baru) = wj (lama) + α (x - wj (lama)); */
	/* Jika T  ≠ Cj maka: wj (baru) = wj (lama) - α (x - wj (lama))	 */
	if ((indexBobot+1) == input[input.length - 1]) {
	    System.out.println("\tTarget Data ke-" + (indexData + 1) + " = T , yaitu T = " + input[input.length - 1]);
	    System.out.println("---------------------------------------------------------------------------");
	    for (int index = 0; index < (this.weight[indexBobot].length - 1); index++) {
		System.out.print("\tW[" + (indexBobot + 1) + "][" + (index + 1) + "] = W[" + (indexBobot + 1) + "][" + (index + 1) + "] + α * (X[" + (indexData + 1) + "][" + index + "] - W[" + (indexBobot + 1) + "][" + (index + 1) + "])");
		System.out.print(" = " + (weight[indexBobot][index]) + " + " + α + " * " + "(" + input[index] + " - " + weight[indexBobot][index] + ") = ");
		this.weight[indexBobot][index] = (this.weight[indexBobot][index]) + (this.α * (input[index] - this.weight[indexBobot][index]));
		System.out.print(this.weight[indexBobot][index] + "\n");
	    }
	} else {
	    System.out.println("\tTarget Data ke-" + (indexData + 1) + " ≠ T ,  yaitu T = " + input[input.length - 1]);
	    System.out.println("---------------------------------------------------------------------------");
	    for (int index = 0; index < (this.weight[indexBobot].length - 1); index++) {
		System.out.print("\tW[" + (indexBobot + 1) + "][" + (index + 1) + "] = W[" + (indexBobot + 1) + "][" + (index + 1) + "] - α * (X[" + (indexData + 1) + "][" + (index + 1) + "] - W[" + (indexBobot + 1) + "][" + (index + 1) + "])");
		System.out.print(" = " + (weight[indexBobot][index]) + " - " + α + " * " + "(" + input[index] + " - " + weight[indexBobot][index] + ") = ");
		this.weight[indexBobot][index] = ((this.weight[indexBobot][index]) - (this.α * (input[index] - this.weight[indexBobot][index])));
		System.out.print(this.weight[indexBobot][index] + "\n");
	    }
	}
	System.out.println("##############################################################################################################################################################");
    }

    /**
     *
     * @param input
     * @param index
     */
    public void test(double input[]) {
	System.out.println("######################################################################### TESTTING #####################################################################################");
	double jarak[] = new double[this.weight.length];
	for (int indexRow = 0; indexRow < this.weight.length; indexRow++) {
	    double count = 0;
	    String datake = "\tData ke-" + (indexRow + 1) + " = [ ";
	    String bobotke = "\tBobot ke-" + (indexRow + 1) + " = √(";
	    String bobotValue = "\t= √(";
	    for (int indexColume = 0; indexColume < (this.weight[indexRow].length - 1); indexColume++) {
		datake += (int) input[indexColume] + "";
		datake += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " , " : "]";
		count += Math.pow((input[indexColume] - this.weight[indexRow][indexColume]), 2);
		bobotke += "(X[" + (indexRow + 1) + "][" + (indexColume + 1) + "] - W[" + (indexRow + 1) + "][" + (indexColume + 1) + "])^2";
		bobotValue += "(" + input[indexColume] + " - " + this.weight[indexRow][indexColume] + ")^2";
		bobotValue += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " + " : ")";
		bobotke += (indexColume + 1 < (this.weight[indexRow].length - 1)) ? " + " : ")";
	    }
	    System.out.println(datake);
	    System.out.println(bobotke);
	    System.out.println(bobotValue);
	    jarak[indexRow] = Math.sqrt(count);
	    System.out.println("\t= " + jarak[indexRow]);
	    System.out.println("--------------------------------------------------------------------------");
	}

	double minJarak = jarak[0];
	int indexBobot = 0;
	for (int index = 0; index < jarak.length; index++) {
	    if (jarak[index] <= minJarak) {
		minJarak = jarak[index];
		indexBobot = index;
	    }
	}

	System.out.println("\tJARAK TERKECIL PADA BOBOT KE-" + (indexBobot + 1));
        if((indexBobot + 1)==1){
            System.out.println("Target yang di capai sistem yaitu Dispepsia ");
        }else if((indexBobot + 1)==2){
            System.out.println("Target yang di capai sistem yaitu MAAG");
    }else{
            System.out.println("Target yang di capai sistem yaitu GERD");
        }

	/* Perbaiki W j dengan ketentuan ;  */
	/* Jika T = Cj maka: wj (baru) = wj (lama) + α (x - wj (lama)); */
	/* Jika T  ≠ Cj maka: wj (baru) = wj (lama) - α (x - wj (lama))	 */
        
/*	if ((indexBobot+1) == this.weight[indexBobot][this.weight[0].length - 1]) {
	           System.out.println("TARGET TERCAPI");
	} else {
	           System.out.println("TARGET BELUM TERCAPAI");
	} */
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
	int input[][] = {
//	    Data : 
//	     0  1  2  3  4  5  T   // NO.
//	    ---------------------------------------------------------	
{1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1}, // 2 B 			} W[1]
{1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2}, // 3 C  <--- pindah posisi	} W[2]
{0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 3},
//--------------------------------------------------------------------
{1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
{0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
{0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
{0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 3},
{1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 3},
{1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3},
{0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 2},
{0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 2},
{1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 2}	  
	};

	double learning_rate = 0.5;
	double pengurangan_α = 0.1;
	int maxEpoch = 15;

	LVQ lvq = new LVQ(input, learning_rate, pengurangan_α, maxEpoch);
	double test[] = 
        {0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1}; //1
        //{1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}; //2
        //{0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1}; //3
        //{0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1}; //4
        //{1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1}; //5
        //{0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}; //6
        //{0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 2}; //7
        //{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2}; //8
        //{0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 2}; //9
        //{0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2}; //10
        //{0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 3}; //11
        //{1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 3}; //12
        //{0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 3}; //13
        //{1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 3}; //14
        
     /*   double test1[] = {1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}; //2
        double test2[] = {0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1}; //3
        double test3[] = {0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1}; //4
        double test4[] ={1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1}; //5
        double test5[] ={0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}; //6
        double test6[] ={0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 2}; //7
        double test7[] ={0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2}; //8
        double test8[] ={0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 2}; //9
        double test9[] ={0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2}; //10
        double test10[] ={0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 3}; //11
        double test11[] ={1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 3}; //12
        double test12[] ={0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 3}; //13
        double test13[] ={1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 3}; //14  */
	lvq.test(test);
     /*   lvq.test(test1);
        lvq.test(test2);
        lvq.test(test3);
        lvq.test(test4);
        lvq.test(test5);
        lvq.test(test6);
        lvq.test(test7);
        lvq.test(test8);
        lvq.test(test9);
        lvq.test(test10);
        lvq.test(test11);
        lvq.test(test12);
        lvq.test(test13);
        */
        
	

    }
}

