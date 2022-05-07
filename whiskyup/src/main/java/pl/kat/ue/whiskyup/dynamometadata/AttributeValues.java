package pl.kat.ue.whiskyup.dynamometadata;

public class AttributeValues {

    public static class User {

        public final static String PARTITION_KEY = "USER#";
        public final static String SORT_KEY = "USER#";
    }

    public static class UserWhisky {

        public final static String PARTITION_KEY = "USER#";
        public final static String SORT_KEY = "WHISKY#";
    }

    public static class Whisky {

        public final static String PARTITION_KEY = "WHISKY#";
        public final static String SORT_KEY = "WHISKY#";
        public final static String GSI1_PARTITION_KEY = "WHISKIES#";
        public final static String GSI1_SORT_KEY = "WHISKY#";
        public final static String GSI2_PARTITION_KEY = "PRICERANGE#";
        public final static String GSI2_SORT_KEY = "PRICE#%.2f#WHISKY#";
        public final static String GSI3_PARTITION_KEY = "BRAND#";
        public final static String GSI3_SORT_KEY = "WHISKY#";
        public final static String GSI4_PARTITION_KEY = "URLS";
        public final static String GSI4_SORT_KEY = "URL#";
    }

    public static class Brand {

        public final static String PARTITION_KEY = "BRANDS";
        public final static String SORT_KEY = "BRANDS";
    }
}