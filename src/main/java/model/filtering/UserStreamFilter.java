package model.filtering;

import model.User;

public class UserStreamFilter extends StreamFilter<User> {
    public static final String NAME_KEY = "name";

    public void setName(String name) {
        getFilterAttributes().put(NAME_KEY, name);
    }

    @Override
    public StreamFilter<User> buildPredicate() {
        predicate = user -> getFilterAttributes().get(NAME_KEY).equals(user.getName());
        return this;
    }
}
