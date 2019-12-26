package com.ulan.app.munduz.helpers

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmailHelper : AsyncTask<Void, Void, Void>{

    private val context: Context
    private lateinit var session: Session

    private var email: String
    private var subject: String
    private var message: String

    constructor(context: Context, email: String, subject: String, message: String) {
        this.context = context
        this.email = email
        this.subject = subject
        this.message = message
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        Toast.makeText(context, "Письмо отправлено", Toast.LENGTH_LONG).show()
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        val props = Properties()
        props.setProperty("mail.transport.protocol", "smtp")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.auth", "true")
        props.put("mail.smpt.port", "465")
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smpt.socketFactory.port", "465")
        props.put("mail.smpt.socketFactory.class", "javax.net.ssl.SSLSocketFactory")

        session = Session.getDefaultInstance(props, object : javax.mail.Authenticator(){
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
            }
        })
        try{
            var mime = MimeMessage(session)
            mime.setFrom(InternetAddress(Config.EMAIL))
            mime.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            mime.setSubject(subject)
            mime.setText(message)
            Transport.send(mime)
        }catch (e: MessagingException){
            e.printStackTrace()
        }
        return null
    }


    // Sender Configuration class
    class Config {

        companion object{
            const val EMAIL = "ulanbek255@gmail.com"
            const val PASSWORD = "ulitsapolbina16"
        }
    }


}