package interfaces.functional;

@FunctionalInterface
public interface FI1ArgGetterReturnsNonVoid<PARAMETER_TYPE, RETURN_TYPE> {
    RETURN_TYPE get(PARAMETER_TYPE p);
}
