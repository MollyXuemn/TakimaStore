package io.takima.master3.store.core.presentation;

import org.springframework.lang.Nullable;

public class DebugExceptionEntity extends ExceptionEntity{
    String[] stack = new String[]{};

    public DebugExceptionEntity(ExceptionEntity exceptionEntity,  @Nullable String[] stack) {
        super(exceptionEntity);

            this.stack = stack;

    }
}
