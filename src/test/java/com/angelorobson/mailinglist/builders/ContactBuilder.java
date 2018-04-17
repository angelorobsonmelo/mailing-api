package com.angelorobson.mailinglist.builders;

import com.angelorobson.mailinglist.entities.Category;
import com.angelorobson.mailinglist.entities.Contact;
import com.angelorobson.mailinglist.entities.Function;
import com.angelorobson.mailinglist.entities.UserApp;

import static com.angelorobson.mailinglist.enums.GenderEnum.FEMALE;
import static com.angelorobson.mailinglist.enums.GenderEnum.MALE;
import static java.util.Arrays.asList;

public class ContactBuilder {

    private Contact contact;

    private ContactBuilder() {}

    public static ContactBuilder oneContactWithUserNameInstagramJonh(Category categoryReturned, Function modelReturned, Function model2Returned, UserApp userAppReturnd) {
        ContactBuilder builder = new ContactBuilder();
        builder.contact = new Contact();
        builder.contact.setUserNameInstagram("@john");
        builder.contact.setCategory(categoryReturned);
        builder.contact.setGender(MALE);
        builder.contact.setUserApp(userAppReturnd);
        builder.contact.setFunctions(asList(modelReturned, model2Returned));

        return builder;
    }

    public static ContactBuilder oneContactWithUserNameInstagramMary(Category categoryReturned, Function modelReturned, Function model2Returned, UserApp userAppReturnd) {
        ContactBuilder builder = new ContactBuilder();
        builder.contact = new Contact();
        builder.contact.setUserNameInstagram("@Mary");
        builder.contact.setCategory(categoryReturned);
        builder.contact.setGender(FEMALE);
        builder.contact.setUserApp(userAppReturnd);
        builder.contact.setFunctions(asList(modelReturned, model2Returned));

        return builder;
    }

    public Contact build() {
        return contact;
    }
}
