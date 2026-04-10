package org.kane.domain.mappers;

public interface Mapper<F, T> {
    T map(F from);
}
