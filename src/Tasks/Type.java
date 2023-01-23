package Tasks;

public enum Type {
    WORK("Рабочая"),
    PERSONAL("Личная");
    private final String translate;
    Type(String translate){
        this.translate = translate;
    }

    @Override
    public String toString() {
        return translate;
    }
}
