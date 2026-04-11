package org.kane.database.repository.coefficient;

public interface CustomCoefficientRepository {
    Double getCoefficientByProductID(Long productID, Long measureUnitID);
}
