package org.kane.database.enum_types;

public enum ImageType {
    RECIPE,
    USER,
    COOKING_STAGE,
    COMMENT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
