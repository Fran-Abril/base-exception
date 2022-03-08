package com.fraabr.baseexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseException extends Exception {

  private final String code;
  private final List<String> arguments;

  public BaseException(final IError reason) {
    super(reason.getReason());
    this.code = reason.getCode();
    this.arguments = mapObjectToString();
  }

  public BaseException(final IError reason, Object... args) {
    super(String.format(reason.getReason(), args));
    this.code = reason.getCode();
    this.arguments = mapObjectToString(args);
  }

  public BaseException(Throwable e, final IError reason, Object... args) {
    super(String.format(reason.getReason(), args), e);
    this.code = reason.getCode();
    this.arguments = mapObjectToString(args);
  }

  public String getCode() {
    return code;
  }

  public List<String> getArguments() {
    return Collections.unmodifiableList(arguments);
  }

  private List<String> mapObjectToString(Object... args) {
    if (args.length <= 0) {
      return new ArrayList<>();
    }
    return Arrays.asList(args).stream().map(Object::toString).collect(Collectors.toList());
  }
}
