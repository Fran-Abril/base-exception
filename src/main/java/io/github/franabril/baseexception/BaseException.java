package io.github.franabril.baseexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All Exceptions must extend from this
 */
public abstract class BaseException extends Exception {

  private final String code;
  private final List<String> arguments;

  /**
   *
   * @param reason Custom enum that implements IError
   */
  public BaseException(final IError reason) {
    super(reason.getReason());
    this.code = reason.getCode();
    this.arguments = mapObjectToString();
  }

  /**
   *
   * @param reason Custom enum that implements IError
   * @param args   arguments that will be replaced in the IError
   */
  public BaseException(final IError reason, Object... args) {
    super(String.format(reason.getReason(), args));
    this.code = reason.getCode();
    this.arguments = mapObjectToString(args);
  }

  /**
   *
   * @param e      Throwable exception
   * @param reason Custom enum that implements IError
   * @param args   arguments that will be replaced in the IError
   */
  public BaseException(Throwable e, final IError reason, Object... args) {
    super(String.format(reason.getReason(), args), e);
    this.code = reason.getCode();
    this.arguments = mapObjectToString(args);
  }

  /**
   *
   * @return IError code for easy detection cause
   */
  public String getCode() {
    return code;
  }

  /**
   *
   * @return all arguments
   */
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
