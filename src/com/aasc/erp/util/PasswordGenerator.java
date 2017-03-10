package com.aasc.erp.util;


/**********************************************************************

History

17-Dec-2014   Eshwari M   Merged Sunanda code for SC Lite

***********************************************************************/
public class PasswordGenerator
{
  public PasswordGenerator()
  {
  }

  /** 
   * Minimum length for a decent password 
   */
  public static int passwordLength = 8;

  /** 
   * The random number generator. 
   */
  protected static java.util.Random r = new java.util.Random();

  /* Set of characters that is valid. Must be printable, memorable,
       * and "won't break HTML" (i.e., not '<', '>', '&', '=', ...).
       * or break shell commands (i.e., not '<', '>', '$', '!', ...).
       * I, L and O are good to leave out, as are numeric zero and one.
       */
  protected static char[] goodChar =
  // Comment out next two lines to make upper-case-only, then  
  // use String toUpper() on the user's input before validating.  
  { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 
    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    protected static char[] goodChar2 =
    // Comment out next two lines to make upper-case-only, then  
    // use String toUpper() on the user's input before validating.  
    { 'A', 'B', 'C', 'D', 'E', 
      'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
      'W', 'X', 'Y', 'Z' };
      
    protected static char[] goodChar3 =
    // Comment out next two lines to make upper-case-only, then  
    // use String toUpper() on the user's input before validating.  
    {  '2', '3', '4', '5', '6', '7', '8', '9' };
      
    protected static char[] goodChar4 =
    // Comment out next two lines to make upper-case-only, then  
    // use String toUpper() on the user's input before validating.  
    { '+', '-', '@','#','$','%','&','*' };


  /* Generate a Password object with a random password. */

  public static String getPassword() {
        if (passwordLength < 1) {
            throw new IllegalArgumentException("Invalid password length " + 
                                               passwordLength);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < passwordLength; i++) {
            if ((i == 1) || (i == 5))
                sb.append(goodChar2[r.nextInt(goodChar2.length)]);
            else if (i == 3)
                sb.append(goodChar3[r.nextInt(goodChar3.length)]);
            else if (i == 7)
                sb.append(goodChar4[r.nextInt(goodChar4.length)]);
            else
                sb.append(goodChar[r.nextInt(goodChar.length)]);
        }
        return sb.toString();
  }

  public static String getNext()
  {
    if (passwordLength < 1)
    {
      throw new IllegalArgumentException("Invalid password length " + passwordLength);
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < passwordLength; i++)
    {
        if((i==1) || (i==5))
            sb.append(goodChar2[r.nextInt(goodChar2.length)]);
        else if(i==3)
            sb.append(goodChar3[r.nextInt(goodChar3.length)]);
        else if(i==7)
            sb.append(goodChar4[r.nextInt(goodChar4.length)]);
        else
            sb.append(goodChar[r.nextInt(goodChar.length)]);
    }
    return sb.toString();
  }
  
  public static void main (String args[]) 
  {
    PasswordGenerator pg = new PasswordGenerator();
  //  for (int i=0; i<20;i++)
      System.out.println("New Password :"+pg.getNext());
  }
}
