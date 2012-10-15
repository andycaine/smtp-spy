package smtpspy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Andy Caine
 */
@Controller
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailStore emailStore;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Email> list() {
        return emailStore.getEmails();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void clear() {
        emailStore.clear();
    }

}
