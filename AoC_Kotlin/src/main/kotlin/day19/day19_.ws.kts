import java.util.regex.Matcher
import java.util.regex.Pattern

val regex = """(abc)(?R)(bb)"""
val string = "abcbbabcbbabcbb";

val pattern = Pattern.compile(regex);
val matcher = pattern.matcher(string);

while (matcher.find()) {
    System.out.println("Full match: " + matcher.group(0));
    for (i in 1 .. matcher.groupCount()) {
        System.out.println("Group " + i + ": " + matcher.group(i));
    }
}