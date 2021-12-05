package com.cedricakrou.artisanat.infrastructure.helper

import com.b2i.neo.application.controlForm.Generate
import com.cedricakrou.artisanat.application.controlForm.Url
import com.cedricakrou.artisanat.domain.account.entity.User
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context


@Service
class EmailHelper(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: TemplateEngine, )
{

    fun sendEmail(to: String, reference: String)
    {
        val msg = SimpleMailMessage()

        msg.setTo(to)
        msg.setSubject("REFERENCE DE VOTRE RENDEZ-VOUS")
        msg.setText("La reference est : $reference")
        javaMailSender.send(msg)
    }

    fun registerEmail(user: User) {

        val baseUrl : String = Url.BASE_URL  + "/email/"

//        val link : String = baseUrl + "confirm-account/" + user.username
        val link : String = Url.BASE_URL + "/account/login"
        val unsubscribeLink : String =  baseUrl + "unsubscribe/" + user.username

        val context = Context()
        context.setVariable("user", user)
        context.setVariable("link", link)
        context.setVariable("unsubscribe_link", unsubscribeLink)

        val process = templateEngine.process("service/email/register-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject("Bienvenue " + user.firstname + " " + user.lastname)
        helper.setText(process, true)
        helper.setTo(user.email)
        javaMailSender.send(mimeMessage)
    }

    /**
     * Cette fonction permet d'envoyer le mail à l'artisan pour confirmer son mail
     */

    fun signUpStepOne(user: User) {

        val baseUrl : String = Url.BASE_URL  + "/email/"

        val link : String = baseUrl + "confirm-account/" + user.username
//        val link : String = Url.BASE_URL + "/account/login"
        val unsubscribeLink : String =  baseUrl + "unsubscribe/" + user.username

        val context = Context()
        context.setVariable("user", user)
        context.setVariable("link", link)
        context.setVariable("unsubscribe_link", unsubscribeLink)

        val process = templateEngine.process("service/email/sign-up-step-one", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject("Bienvenue " + user.firstname + " " + user.lastname)
        helper.setText(process, true)
        helper.setTo(user.email)
        javaMailSender.send(mimeMessage)
    }


    fun resetPasswordEmail(user: User) {

        val baseUrl : String = Url.BASE_URL  + "/email/"

        val token = Generate.generatedRandomCharacter( 15 ) + user.username + Generate.generatedRandomCharacter( 7 )

//        val link : String = baseUrl + "confirm-account/" + user.username
        val link : String = baseUrl + "reset-password/" + token
        val unsubscribeLink : String =  baseUrl + "unsubscribe/" + user.username

        val context = Context()
        context.setVariable("user", user)
        context.setVariable("link", link)
        context.setVariable("unsubscribe_link", unsubscribeLink)

        val process = templateEngine.process("service/email/reset-password-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject( user.firstname + " " + user.lastname + "Voulez vous renitialiser votre mot de passe")
        helper.setText(process, true)
        helper.setTo(user.email)
        javaMailSender.send(mimeMessage)
    }

/**
    fun contatUsEmail( contactUs: ContactUs) {

        val msg = SimpleMailMessage()

        msg.setTo("neob2i@djepay.com")
        msg.setSubject("Message du client ${contactUs.name}")
        msg.setText("Salut Neo," +
                " \n je suis : ${contactUs.name} , mes coordonnées sont : ${contactUs.phone} , ${contactUs.email} " +
                " \n\n mon message est : " +
                " \n ${contactUs.comments}")

        javaMailSender.send(msg)
    }


    fun sendCodeEmail( sendCodeDto: SendCodeDto) {


        val context = Context()
        context.setVariable("user", sendCodeDto.user)
        context.setVariable( "code", sendCodeDto.code )

        val process = templateEngine.process("service/email/send-code-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject( sendCodeDto.user.firstname + " " + sendCodeDto.user.lastname + " veuillez communiquer le code pour terminer la consultation.")
        helper.setText(process, true)
        helper.setTo(sendCodeDto.user.email)
        javaMailSender.send(mimeMessage)
    }
**/

}