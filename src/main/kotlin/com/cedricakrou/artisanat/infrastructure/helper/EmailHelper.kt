package com.cedricakrou.artisanat.infrastructure.helper

import com.cedricakrou.artisanat.application.controlForm.Url
import com.cedricakrou.artisanat.application.event.dto.SendCodeDto
import com.cedricakrou.artisanat.domain.account.entity.User
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

    /**
     * Cette fonction permet d'envoyer le code OTP par Email
     */

    fun sendCodeEmail( sendCodeDto: SendCodeDto) {


        val context = Context()
        context.setVariable("user", sendCodeDto.user)
        context.setVariable( "code", sendCodeDto.code )

        val process = templateEngine.process("service/email/send-code-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject( sendCodeDto.user.firstname + " " + sendCodeDto.user.lastname + " ,veuillez confirmer votre compte.")
        helper.setText(process, true)
        helper.setTo(sendCodeDto.user.email)
        javaMailSender.send(mimeMessage)
    }

}