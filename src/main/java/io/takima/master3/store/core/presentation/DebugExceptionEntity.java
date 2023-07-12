package io.takima.master3.store.core.presentation;


import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebugExceptionEntity extends ExceptionEntity {
    private String[] stack = new String[]{};

    /**
     * @param exceptionEntity The {@link ExceptionEntity} to wrap into a debug payload
     * @param stack           the exception's stack trace.
     */
    public DebugExceptionEntity(ExceptionEntity exceptionEntity,
                                @Nullable String[] stack) {
        super(exceptionEntity);
        if (stack != null) {
            this.stack = stack;
        }
    }
}

