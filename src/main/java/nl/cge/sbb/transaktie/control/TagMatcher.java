package nl.cge.sbb.transaktie.control;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 18-05-17.
 */
public class TagMatcher {

    private final static Pattern PATTERN = Pattern.compile(".*:([a-zA-Z]+).*$");

    private String searchString;

    public TagMatcher(Optional<String> searchString) {
        this.searchString = searchString.orElse("");
    }

    public Optional<String> getTag() {
        Matcher matcher = PATTERN.matcher(searchString);
        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    public Optional<String> getQuery() {
        String strippedQuery = null;
        Optional<String> tag = getTag();
        if (tag.isPresent()) {
            strippedQuery = searchString.replace(":" + tag.get(), "").trim();
        } else {
            strippedQuery = searchString.trim();
        }
        return "".equals(strippedQuery) ? Optional.empty() : Optional.of(strippedQuery);
    }


}
