import java.util.*;
import java.io.*;

public class Hw03
{
  // hashing algorithm method
  public static int UCFxram(String string, int len)
  {
    // aribtrary values
    int randVal1 = 0xbcde98ef;              // aribtrary value 1
    int randVal2 = 0x7890face;              // arbitrary value 2
    int randVal3 = 0x46b6456e;              // arbitrary value 3
    int randVal4 = 0xb6acbe58;              // arbitrary value 4
    int randVal5 = 0x53ea2b2c;              // arbitrary value 5

    // hashing variables
    int hashValue = 0xfa01bc96;             // starting hashValue
    int roundedEnd = len & 0xfffffffc;      // mask len to 32 bits
    int tmp;                                // holds temp data for hashing
    byte[] d = new byte[len];               // store each char from string

    // turn each char from string into its ascii value
    d = string.getBytes();

    for (int i = 0; i < roundedEnd; i = i + 4)
    {
      tmp = (d[i] & 0xff) | ((d[i + 1] & 0xff) << 8) |
      ((d[i + 2] & 0xff) << 16) | (d[i + 3] << 24);

      tmp = tmp * randVal1;
      tmp = Integer.rotateLeft(tmp, 12);                 // rotate left 12 bits
      tmp = tmp * randVal2;
      hashValue = hashValue ^ tmp;
      hashValue = Integer.rotateLeft(hashValue, 13);     // rotate left 13 bits
      hashValue = (hashValue * 5) + randVal3;
    }

      tmp = 0;

      if ((len & 0x03) == 3)
      {
        tmp = (d[roundedEnd + 2] & 0xff) << 16;
        len = len - 1;
      }

      if ((len & 0x03) == 2)
      {
        tmp = tmp | ((d[roundedEnd + 1] & 0xff) << 8);
        len = len - 1;
      }

      if ((len & 0x03) == 1)
      {
        tmp = tmp | (d[roundedEnd] & 0xff);
        tmp = tmp * randVal1;
        tmp = Integer.rotateLeft(tmp, 14);               // rotate left 14 bits
        tmp = tmp * randVal2;
        hashValue = hashValue ^ tmp;
      }

      hashValue = hashValue ^ len;
      hashValue = hashValue & randVal4;
      hashValue = hashValue ^ hashValue >>> 13;
      hashValue = hashValue * randVal5;
      hashValue = hashValue ^ hashValue >>> 16;

    return hashValue;
  }

  // print method
  public static void print(int hashValue, String string)
  {
    System.out.format("%10x:%s\n",hashValue, string);
  }

  // complexityIndicator method
  public static void complexityIndicator()
  {
    System.err.println("pr348838;1.5;4.0");
  }

  // main method
  public static void main(String[] args) throws Exception
  {
    String string;              // input string
    int len;                    // length of the string
    int hashValue;              // stores hash value of a given string

    // Scanner to open and read input file
    Scanner input = new Scanner(new FileInputStream(args[0]));

    // read until EOF
    while (input.hasNextLine())
    {
      string = input.nextLine();              // read input line into string
      len = string.length();                  // store length of the string
      hashValue = UCFxram(string, len);       // run hashing alogrithm
      print(hashValue, string);               // print the result
    }

    // print at the end
    System.out.println("Input file processed");

    // call complexityIndicator to print stderr
    complexityIndicator();
  }
}
