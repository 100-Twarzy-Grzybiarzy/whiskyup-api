package pl.kat.ue.whiskyup.dynamometadata;

public class Expressions {

    public static final String ATTRIBUTE_NOT_EXISTS = String.format("attribute_not_exists(%s)", AttributeNames.PARTITION_KEY);
    public static final String ATTRIBUTE_EXISTS = String.format("attribute_exists(%s)", AttributeNames.PARTITION_KEY);
}