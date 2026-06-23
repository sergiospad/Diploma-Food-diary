package org.kane.domain.mappers;

public interface CopyMapper<F, T> {
    T copyMap(F from, T to);
}
