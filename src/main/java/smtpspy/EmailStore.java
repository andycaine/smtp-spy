package smtpspy;

import com.dumbster.smtp.MailMessage;
import com.dumbster.smtp.SmtpServer;
import com.dumbster.smtp.SmtpServerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy Caine
 */
@Component
public class EmailStore {

    private static final int PORT = 4444;

    private SmtpServer smtpServer;

    public EmailStore() {
        smtpServer = SmtpServerFactory.startServer(PORT);
    }

    public synchronized List<Email> getEmails() {
        List<Email> emails = new ArrayList<Email>();
        for (MailMessage mailMessage : smtpServer.getMessages()) {
            emails.add(new EmailBuilder()
                               .from(mailMessage.getFirstHeaderValue("From"))
                               .to(mailMessage.getFirstHeaderValue("To"))
                               .subject(mailMessage.getFirstHeaderValue("Subject"))
                               .body(mailMessage.getBody())
                               .build());
        }
        return emails;
    }

    public synchronized void clear() {
        smtpServer.clearMessages();
    }

    private static class EmailBuilder {

        private String from;

        private String to;

        private String subject;

        private String body;

        public EmailBuilder from(String from) {
            this.from = from;
            return this;
        }

        public EmailBuilder to(String to) {
            this.to = to;
            return this;
        }

        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }

        public Email build() {
            return new Email(from, to, subject, body);
        }

    }
}
