package crosswordsage;

import java.net.*;

public class SimpleAuthenticator
   extends Authenticator
{
   private String username,
                  password;

   public SimpleAuthenticator(String username,String password)
   {
      this.username = username;
      this.password = password;
   }

   protected PasswordAuthentication getPasswordAuthentication()
   {
      return new PasswordAuthentication(
             username,password.toCharArray());
   }
}
