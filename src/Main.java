import java.util.Scanner;

public class Main {
    static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Please enter network capacity:   ");
        int number = reader.nextInt();
        reader.nextLine();
        System.out.print("Please enter IP address:         ");
        String netAddress = reader.nextLine();
        System.out.println();
        int pow = calculation(number);
        String subnetMask = "";


        if (pow > 24){
            int octet4 = pow - 24;
            for (int i = 8 - octet4; i < 8; i++){
                subnetArray[0][i] = 0;
            }

            for (int i = 1; i < 4; i++){
                for (int j = 0; j < 8; j++){
                    subnetArray[i][j] = 0;
                }
            }
        }
        else if(pow > 16 && pow < 25){
            int octet3 = pow - 16;
            for (int i = 8 - octet3; i < 8; i++){
                subnetArray[1][i] = 0;
            }

            for (int i = 2; i < 4; i++){
                for (int j = 0; j < 8; j++){
                    subnetArray[i][j] = 0;
                }
            }
        }
        else if(pow > 8 && pow < 17){
            int octet2 = pow - 8;
            for (int i = 8 - octet2; i < 8; i++){
                subnetArray[2][i] = 0;
            }

            for (int i = 3; i < 4; i++){
                for (int j = 0; j < 8; j++){
                    subnetArray[i][j] = 0;
                }
            }
        }
        else{
            for (int i = 8 - pow; i < 8; i++){
                subnetArray[3][i] = 0;
            }
        }


        // for converting subnet mask from array to string
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 8; j++){
                subnetMask += String.valueOf(subnetArray[i][j]);
            }
            if (i < 3) subnetMask += '.';
        }

        String[] netAddressInput = netAddress.split("\\.");

        String octet1 = netAddressInput[0];
        String octet2 = netAddressInput[1];
        String octet3 = netAddressInput[2];
        String octet4 = netAddressInput[3];


        String ipAddressBinary = decimalToBinary(Integer.parseInt(octet1)) + '.' + decimalToBinary(Integer.parseInt(octet2)) + '.' + decimalToBinary(Integer.parseInt(octet3)) + '.' + decimalToBinary(Integer.parseInt(octet4));


        String networkAddress = "";

        for (int i = 0; i < 35; i++){
            networkAddress += compare(ipAddressBinary.charAt(i), subnetMask.charAt(i));
        }

        StringBuffer broadcastAddress = new StringBuffer(networkAddress);


        int extra = 0;

        if (pow > 8 && pow < 17) extra = 1;
        else if (pow > 16 && pow < 25) extra = 2;
        else if (pow > 24) extra = 3;

        for (int i = 34; i > 34 - pow - extra; i--){
            if (broadcastAddress.charAt(i) == '.') {
                continue;
            }else {
                broadcastAddress.setCharAt(i, '1');
            }
        }

        String broadCastAddress = String.valueOf(broadcastAddress);



        String[] fullIpAddressDisplay = ipAddressBinary.split("\\.");
        String[] subnetStringDisplay = subnetMask.split("\\.");
        String[] networkAddressDisplay = networkAddress.split("\\.");
        String[] broadcastAddressDisplay = broadCastAddress.split("\\.");

        System.out.println("IP Address:                 " +  binaryToDecimal(fullIpAddressDisplay[0]) + '.' + binaryToDecimal(fullIpAddressDisplay[1]) + '.' + binaryToDecimal(fullIpAddressDisplay[2]) + '.' + binaryToDecimal(fullIpAddressDisplay[3]));
        System.out.println("Subnet Mask:                " +  binaryToDecimal(subnetStringDisplay[0]) + '.' + binaryToDecimal(subnetStringDisplay[1]) + '.' + binaryToDecimal(subnetStringDisplay[2]) + '.' + binaryToDecimal(subnetStringDisplay[3]));
        System.out.println("Network Address:            " +  binaryToDecimal(networkAddressDisplay[0]) + '.' + binaryToDecimal(networkAddressDisplay[1]) + '.' + binaryToDecimal(networkAddressDisplay[2]) + '.' + binaryToDecimal(networkAddressDisplay[3]));
        System.out.println("Network range:              " +  binaryToDecimal(networkAddressDisplay[0]) + '.' + binaryToDecimal(networkAddressDisplay[1]) + '.' + binaryToDecimal(networkAddressDisplay[2]) + '.' + (binaryToDecimal(networkAddressDisplay[3]) + 1) + "   --  " + binaryToDecimal(broadcastAddressDisplay[0]) + '.' + binaryToDecimal(broadcastAddressDisplay[1]) + '.' + binaryToDecimal(broadcastAddressDisplay[2]) + '.' + (binaryToDecimal(broadcastAddressDisplay[3]) - 1));
        System.out.println("Broadcast Address:          " +  binaryToDecimal(broadcastAddressDisplay[0]) + '.' + binaryToDecimal(broadcastAddressDisplay[1]) + '.' + binaryToDecimal(broadcastAddressDisplay[2]) + '.' + binaryToDecimal(broadcastAddressDisplay[3]) + "\n");

        System.out.println("IP Address Binary:          " + ipAddressBinary);
        System.out.println("Subnet Mask Binary:         " + subnetMask);
        System.out.println("Network Address Binary:     " + networkAddress);
        System.out.println("Broadcast Address Binary:   " + broadcastAddress);
    }

    static byte[][] subnetArray = {{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}};

    //static String subnetString = "";

    public static int calculation(int number) {
        int pow = 1;
        while(number > Math.pow(2, pow) - 2){
            pow++;
        }

        return pow;
    }

    public static String decimalToBinary(int number){
        String binary = "";

        for (int i = 128; i > 0; i = i / 2){
            if (number - i >= 0){
                binary += "1";
                number = number - i;
            }else{
                binary += "0";
            }
        }

        return binary;
    }

    public static char compare(char one, char two){
        char three;
        if(one == '1' && two == '1'){
            three = '1';
        }else{
            three = '0';
        }

        if (one == '.'){
            three = '.';
        }

        return three;
    }

    static int[] decimalArray = {128, 64, 32, 16, 8, 4, 2, 1};

    static int binaryToDecimal(String number){
        int decimal = 0;

        for(int i = 0; i < 8; i++){
            if(number.charAt(i) == '1'){
                decimal += decimalArray[i];
            }

            //System.out.println(number.charAt(i));
        }

        return decimal;
    }
}