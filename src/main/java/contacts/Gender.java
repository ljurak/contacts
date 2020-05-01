package contacts;

public enum Gender {

    MAN("M"),
    WOMAN("F");

    private String abbr;

    Gender(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    public static Gender valueByAbbr(String abbr) {
        for (Gender value : values()) {
            if (value.getAbbr().equalsIgnoreCase(abbr)) {
                return value;
            }
        }
        return null;
    }
}
