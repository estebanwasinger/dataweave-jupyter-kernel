package com.github.estebanwasinger.dataweave;

import java.util.Optional;

public class ExecutionDescriptor {
    private final String body;
    private MagicDescriptor magic;

    public ExecutionDescriptor(String body, MagicDescriptor magic) {
        this.body = body;
        this.magic = magic;
    }

    public ExecutionDescriptor(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public Optional<MagicDescriptor> getMagic() {
        return Optional.ofNullable(magic);
    }

    @Override
    public String toString() {
        return "ExecutionDescriptor{" +
                "body='" + body + '\'' +
                ", magic=" + magic +
                '}';
    }
}
