import java.util.*;
class Main {
    static int[] maxSubArraySum(int[] arr) {
        int max = arr[0];
        int maxIndex = 0;
        int sum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];

            if (sum > max) {
                max = sum;
                maxIndex = i;
            }
        }

        int[] result = new int[2];
        result[0] = max<0?0:max;
        result[1] = maxIndex ; 
        return result;
    }
   
//for returning each chaqracter value
    static int getCharValue(char c) {
        if (c >= 'a' && c <= 'z') {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return -((c - 'a' + 1));
            } else {
                return c - 'a' + 1;
            }
        } else if (c >= 'A' && c <= 'Z') {
            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                return -((c - 'A' + 1) * 4 + 1);
            } else {
                return c - 'A' + 1;
            }
        }
        return 0; // Return 0 for non-alphabetic characters
    }
//Main program executes from here
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inputString = sc.next();
        sc.close();
        char[] chars = inputString.toCharArray();
        int[] result = new int[chars.length];
        int[] resultPos = new int[chars.length];
        int[] realCharInt = downPositionsArray(chars);
        int xlength = 0;
        int ylength = calculateYLength(realCharInt);
        
        int manPosition = 0;
        for (int i = 0; i < chars.length; i++) {
            int value = getCharValue(chars[i]);
           
            if (i < chars.length - 1) {
                if (((chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u') && 
                     (chars[i + 1] == 'a' || chars[i + 1] == 'e' || chars[i + 1] == 'i' || chars[i + 1] == 'o' || chars[i + 1] == 'u')) ||
                    ((chars[i] != 'a' && chars[i] != 'e' && chars[i] != 'i' && chars[i] != 'o' && chars[i] != 'u') &&
                     (chars[i + 1] != 'a' && chars[i + 1] != 'e' && chars[i + 1] != 'i' && chars[i + 1] != 'o' && chars[i + 1] != 'u'))) {
                    if (value < 0) {
                        value--;
                    } else {
                        value++;
                    }
                }
            }
            result[i] = value;
            resultPos[i] = Math.abs(value);
            xlength+=Math.abs(value);
        }
   
        char[][] finalResult = new char[ylength+3][xlength];

       int res[] =maxSubArraySum(result);
       int maxSum = res[0];
       int maxIndex = res[1];
       for(int i=0;i<=maxIndex;i++){
           manPosition+=resultPos[i];
       }
     
        char[][] resultArray = drawMan(manPosition,realCharInt,maxIndex,finalResult);
 
        for(int i=0;i<xlength;i++){
            for(int j=0;j<ylength+1;j++){
                System.out.print(resultArray[i][j]);
            }
            System.out.println();
        }
    }
    
    static int[] downPositionsArray(char[] chars){
        int result[] = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int value = Math.abs(getCharValue(chars[i]));
            result[i]=value;
          
        }
        return result;
    }
    
    public static int maxYSum(ArrayList<Integer> nums) {
        int maxSoFar = nums.get(0);
        int maxEndingHere = nums.get(0);

        for (int i = 1; i < nums.size(); i++) {
            int num = nums.get(i);
            maxEndingHere = Math.max(num, maxEndingHere + num);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }
    
    static int calculateYLength(int[] arr){
        int sum =0;
        ArrayList<Integer> dynamicArray = new ArrayList<>();
        int prev =getRealValue(arr[0]);
        dynamicArray.add(prev);
        for(int i=1;i<arr.length;i++){
             prev = getRealValue(arr[i-1]);
            int curr = getRealValue(arr[i]);
 
            if(prev<0 && curr < 0){
                dynamicArray.add(-2);
            }
            if(prev>0 && curr>0){
                dynamicArray.add(2);
            }
            dynamicArray.add(curr);
        }

        int result = maxYSum(dynamicArray);
  
        return result;
    }
    
    static char[][] drawMan(int manPosition,int[] realCharInt,int index, char[][] resultArray){
        resultArray[0][manPosition+1] = 'o';
        resultArray[1][manPosition] = '/';
        resultArray[1][manPosition+1] = '|';
        resultArray[1][manPosition+2] = '\\';
        resultArray[2][manPosition] = '<';
        resultArray[2][manPosition+2] = '>';
        return drawPattern(manPosition,manPosition+2,realCharInt,index,resultArray);
    }
    
    static char[][] drawPattern(int pointer1,int pointer2,int[] chars, int index,char[][] resultArray){
        int n = chars.length;
        int prev;
        int temp=3;
        int x= pointer1-1;
        boolean downDash =false,upDash=false;
        
        for(int i=index;i>=0;i--){
            int val = -(getRealValue(chars[i]));
            if(i-1>=0){
              prev =  -(getRealValue(chars[i-1]));
              if(val>0 && prev>0){
                  upDash=true;
              }
              if(val<0 && prev < 0){
                  downDash=true;
              }
            }
            if(val<0){
                while(val!=0){
                    resultArray[temp][x]='/';
                    x--;
                    temp++;
                    val++;
                }
                if(downDash){
                    resultArray[temp][x]='|';
                    resultArray[temp+1][x]='|';
                    temp=temp+2;
                    x--;
                    downDash=false;
                }
             }else{
                 if(temp!=0)
                 temp--;
                while(val!=0){
                    resultArray[temp][x]='\\';
                    x--;
                    temp--;
                    val--;
                }
                if(upDash){
                    resultArray[temp][x]='|';
                    resultArray[temp-1][x]='|';
                    temp=temp-2;
                    x--;
                    upDash=false;
                } 
            }
        }
        return resultArray;
     
    }
    
    static int getRealValue(int value){
        if(value == 1 || value == 5 || value == 9 || value == 15 || value == 21 ){
            return -(value);
        }
        return value;
    }
}