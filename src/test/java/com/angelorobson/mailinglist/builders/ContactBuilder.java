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

    public static ContactBuilder oneContactWithUserNameInstagramJohn() {
        Category category = new Category();
        category.setId(1L);
        category.setCategory("Patner");

       UserApp userApp = UserAppBuilder.oneUserWithNameJoao().build();
       userApp.setId(1L);

        Function model = new Function();
        model.setId(1L);
        model.setFunction("Model");
        Function modelStories = new Function();
        modelStories.setId(2L);
        modelStories.setFunction("Model Stories");

        ContactBuilder builder = new ContactBuilder();
        builder.contact = new Contact();
        builder.contact.setId(1L);
        builder.contact.setUserNameInstagram("@John");
        builder.contact.setCategory(category);
        builder.contact.setGender(MALE);
        builder.contact.setUserApp(userApp);
        builder.contact.setFunctions(asList(model, modelStories));

        return builder;
    }

    public static ContactBuilder oneContactWithUserNameInstagramRobert() {
        Category candidate = new Category();
        candidate.setCategory("Candidate");

        Function photographer = new Function();
        photographer.setFunction("Photographer");

        ContactBuilder builder = new ContactBuilder();
        builder.contact = new Contact();
        builder.contact.setUserNameInstagram("@Robert");
        builder.contact.setCategory(candidate);
        builder.contact.setGender(MALE);
        builder.contact.setUserApp(UserAppBuilder.oneUserWithNameJoao().build());
        builder.contact.setFunctions(asList(photographer));

        return builder;
    }

    public Contact build() {
        return contact;
    }
}
