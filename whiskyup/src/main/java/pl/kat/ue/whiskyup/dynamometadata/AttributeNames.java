package pl.kat.ue.whiskyup.dynamometadata;

public class AttributeNames {

    public final static String PARTITION_KEY = "PK";
    public final static String SORT_KEY = "SK";

    public final static String GSI1_PARTITION_KEY = "GSI1PK";
    public final static String GSI1_SORT_KEY = "GSI1SK";

    public final static String GSI2_PARTITION_KEY = "GSI2PK";
    public final static String GSI2_SORT_KEY = "GSI2SK";

    public final static String GSI3_PARTITION_KEY = "GSI3PK";
    public final static String GSI3_SORT_KEY = "GSI3SK";

    public final static String GSI4_PARTITION_KEY = "GSI4PK";
    public final static String GSI4_SORT_KEY = "GSI4SK";

    public static class User {

        public final static String ID = "Id";
        public final static String EMAIL = "Email";
        public final static String NAME = "Name";
        public final static String DISTILLERY = "Distillery";
    }

    public static class UserWhisky {

        public final static String USER_ID = "UserId";
        public final static String WHISKY_ID = "WhiskyId";
        public final static String THUMBNAIL_URL = "ThumbnailUrl";
    }

    public static class Whisky {

        public final static String ID = "Id";
        public final static String URL = "Url";
        public final static String NAME = "Name";
        public final static String ADDED_DATE = "AddedDate";
        public final static String THUMBNAIL_URL = "ThumbnailUrl";
        public final static String CATEGORY = "Category";
        public final static String DISTILLERY = "Distillery";
        public final static String BOTTLER = "Bottler";
        public final static String BOTTLING_SERIES = "BottlingSeries";
        public final static String BOTTLED = "Bottled";
        public final static String BRAND = "Brand";
        public final static String VINTAGE = "Vintage";
        public final static String STATED_AGE = "StatedAge";
        public final static String STRENGTH = "Strength";
        public final static String SIZE = "Size";
        public final static String SIZE_UNIT = "SizeUnit";
        public final static String RATING = "Rating";
        public final static String USER_RATING = "UserRating";
        public final static String AMOUNT_OF_RATINGS = "AmountOfRatings";
        public final static String PRICE = "Price";
        public final static String CURRENCY = "Currency";
        public final static String TAGS = "Tags";
    }

    public static class Brand {

        public final static String BRANDS = "Brands";
    }
}