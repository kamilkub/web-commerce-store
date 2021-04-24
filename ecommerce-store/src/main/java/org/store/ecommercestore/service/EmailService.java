package org.store.ecommercestore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.model.ProductEntity;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;


/**
 * EmailService@ Main functionality: sending activation account email and forgot password
 *
 */
@Service
public class EmailService {

    private JavaMailSender mail;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private static final String EMAIL_FROM = "muhammad-store.eu";

    @Autowired
    public EmailService(JavaMailSender mail) {
        this.mail = mail;
    }


    /**
     *
     * @param recipient email of a person which will get the email
     * @param token token variable, corresponding to account activation. That variable is send for user to activate account
     */
    @Async
    public void sendActivationTokenMail(String recipient, String token) {

        try{
            MimeMessage mimeMessage = mail.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeHelper.setSubject("Account activation link from online store");
            mimeHelper.setFrom(EMAIL_FROM);
            mimeHelper.setTo(recipient);
            mimeHelper.setText("<h2>Account confirmation token is down below: </h2><br /><a href=http://localhost:4200/confirm/" + token + ">Activate your account</a>", true);
            mail.send(mimeMessage);
        } catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
        }
    }

    @Async
    public void sendOrderInfo(String recipient, List<ProductEntity> productList, String price) {
        StringBuilder productListRep = new StringBuilder();

        for(ProductEntity productEntity : productList){
            productListRep.append("<p>" + "<a href='http://localhost:4200/product-info/").
                    append(productEntity.getId())
                    .append("'>")
                    .append(productEntity.getName())
                    .append("</a>  Price:")
                    .append(productEntity.getPrice())
                    .append("</p>");
        }
        try{
            MimeMessage mimeMessage = mail.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeHelper.setSubject("Order has been placed on online store");
            mimeHelper.setFrom(EMAIL_FROM);
            mimeHelper.setTo(recipient);
            mimeHelper.setText("<h1>Order with " + UUID.randomUUID() +" id has been placed</h1>" +
                    "<p>You have bought " + productList.size() + " products for " + price +"</p>"
                    +"<h3> Order products </h3>" + productListRep, true);
            mail.send(mimeMessage);
        } catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
        }
    }

    /**
     *
     * @param recipient email of a person which will get the email
     * @param token token forgotPassword, it's send to verify if that user wants to change password
     */

    @Async
    void sendForgotPasswordMail(String recipient, String token){
        try{
            MimeMessage mimeMessage = mail.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeHelper.setSubject("Forgot password link from e-commerce store");
            mimeHelper.setFrom(EMAIL_FROM);
            mimeHelper.setTo(recipient);
            mimeHelper.setText("<h2>Forgot password link is here: </h2><br /><a href=http://localhost:4200/forgot-password/" + token + ">Change your password</a>", true);
            mail.send(mimeMessage);
        } catch (Exception e){
            logger.error(e.getMessage(), e.getCause());
        }
    }
}