package main.enumcase;

public enum ExitStatus {
    Normal(0), ParameterException(1), IOException(2), SQLException(3);

    private final int code;

    private ExitStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

