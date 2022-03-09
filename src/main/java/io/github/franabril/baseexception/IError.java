package io.github.franabril.baseexception;

public interface IError {
  /**
   *
   * @return Code of Error
   */
  String getCode();

  /**
   *
   * @return Cause or Reason of Exception Throw
   */
  String getReason();
}
