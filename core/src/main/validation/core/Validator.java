package validation.core;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.Collection;

public class Validator implements Validate {
    private final Collection<ValidationError> validationErrors = new ArrayList<ValidationError>(10);
    private final Collection<ErrorId> alreadyValidated = new ArrayList<ErrorId>(10);
    private Collection<State> states = new ArrayList<State>(10);

    @Override
    public Validate validateThat(Field field, Matcher<? extends String> matcher) {
        final ErrorId errorId = new ErrorId();
        field.describeTo(errorId);
        if (hasNoRecordedErrorFor(errorId) && !field.matches(matcher)) {
            alreadyValidated.add(errorId);
            validationErrors.add(createValidationError(field, matcher));
        }
        return this;
    }

    private ValidationError createValidationError(Field field, Matcher<? extends String> matcher) {
        final ValidationError validationError = new ValidationError();
        field.describeTo(validationError);
        final StringDescription stringDescription = new StringDescription();
        matcher.describeTo(stringDescription);
        validationError.setDescription(stringDescription.toString());
        return validationError;
    }

    private boolean hasNoRecordedErrorFor(ErrorId errorId) {
        return !alreadyValidated.contains(errorId);
    }

    public void describeErrorsTo(ErrorMessageWriter errorMessageWriter) {
        for (ValidationError validationError : this.validationErrors) {
            validationError.describeTo(errorMessageWriter);
        }
    }

    public <T> Validate whenStates(final Matcher<Iterable<? super T>> statesMatcher) {
        return new Validate() {
            @Override
            public Validate validateThat(Field field, Matcher<? extends String> matcher) {
                if (statesMatcher.matches(states)) {
                    Validator.this.validateThat(field, matcher);
                }
                return this;
            }
        };
    }

    public void addStates(Collection<? extends State> states) {
        this.states.addAll(states);
    }
}
