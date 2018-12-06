/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ferramentas;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
    
    public static void EnviarEmail(String assunto, String endDest, String corpoEmail)
    {
        new Thread() {
            @Override
            public void run() {


                Properties props = new Properties();
                /** Parâmetros de conexão com servidor de email */
                props.put("mail.smtp.host", "smtp-mail.outlook.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.auth", "true"); 

                Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                 protected PasswordAuthentication getPasswordAuthentication() 
                                 {
                                       return new PasswordAuthentication("Insira aqui o email", "Insira aqui a senha");
                                 }
                            });

                /** Ativa Debug para sessão */
                session.setDebug(true);

                try {

                      Message message = new MimeMessage(session);
                      message.setFrom(new InternetAddress("Insira aqui o email")); //Remetente

                      Address[] toUser = InternetAddress //Destinatário(s)
                                 .parse(endDest);  

                      message.setRecipients(Message.RecipientType.TO, toUser);
                      message.setSubject(assunto);//Assunto
                      message.setText(corpoEmail);
                      /**Método para enviar a mensagem criada*/
                      Transport.send(message);

                 } catch (MessagingException e) {
                      throw new RuntimeException(e);
                }
            }
        }.start();
    }
}
