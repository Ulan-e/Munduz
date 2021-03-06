package ulanapp.munduz.helpers

import android.os.AsyncTask
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmailAsync : AsyncTask<Void, Void, Void>() {

    private lateinit var session: Session

    private lateinit var email: String
    private lateinit var subject: String
    private lateinit var body: String

    fun setMessage(message: ulanapp.munduz.data.models.Message) {
        this.email = message.email
        this.subject = message.subject
        this.body = message.body
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

        session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
            }
        })
        try {
            val mime = MimeMessage(session)
            mime.setFrom(InternetAddress(Config.EMAIL))
            mime.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            mime.setSubject(subject)
            mime.setText("$body\n")
            Transport.send(mime)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        return null
    }

    class Config {
        companion object {
            const val EMAIL = "ulanbek255@gmail.com"
            const val PASSWORD = "ulitsapolbina16"
        }
    }
}