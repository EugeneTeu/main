package seedu.address.logic.parser.findcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.findcommand.FindCustomerCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.predicates.ContactNumberContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.CustomerNameContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.CustomerTagContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.EmailContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCustomerCommand object
 */
public class FindCustomerCommandParser implements Parser<FindCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCustomerCommand
     * and returns a FindCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCustomerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONTACT, PREFIX_EMAIL, PREFIX_TAG);

        //dummy predicate
        Predicate<Customer> predicate = x -> false;

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_CONTACT).isPresent()
                && !argMultimap.getValue(PREFIX_EMAIL).isPresent()
                && !argMultimap.getValue(PREFIX_TAG).isPresent()) {

            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
            }

            String[] keywords = trimmedArgs.split("\\s+");
            predicate = new CustomerNameContainsKeywordsPredicate(Arrays.asList(keywords))
                    .or(new ContactNumberContainsKeywordsPredicate(Arrays.asList(keywords)))
                    .or(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)))
                    .or(new CustomerTagContainsKeywordsPredicate(Arrays.asList(keywords)));

            return new FindCustomerCommand(predicate);

        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");

            predicate = predicate.or(new CustomerNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_CONTACT).get().split("\\s+");

            predicate = predicate.or(new ContactNumberContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");

            predicate = predicate.or(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            predicate = predicate.or(new CustomerTagContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        return new FindCustomerCommand(predicate);
    }

}