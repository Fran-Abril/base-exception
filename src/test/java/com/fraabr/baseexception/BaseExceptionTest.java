package com.fraabr.baseexception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple BaseException.
 * It contains an example of use.
 */
public class BaseExceptionTest {

    public enum MyEnumError implements IError {
        // {%s} is optional
        // it will set the attribute in the constructor of the exception automatically
        // NOTE: characters { } do not be removed because the client need read inside
        MY_ERROR_1("Aggregate fails!"),
        MY_ERROR_2("Aggregate {%s} already exist"),
        MY_ERROR_3("Aggregate {%s} already exist with code {%s}"),
        MY_ERROR_4("Aggregate {%s} was fail!");

        private final String reason;

        MyEnumError(final String reason) {
            this.reason = reason;
        }

        @Override
        public String getCode() {
            return this.getClass().getSimpleName() + "." + this.name();
        }

        @Override
        public String getReason() {
            return reason;
        }
    }

    // create the Exception like this
    public class MyCustomException extends BaseException {

        // without arguments
        public MyCustomException() {
            super(MyEnumError.MY_ERROR_1);
        }

        // with one argument that will be replaced by {%s}
        public MyCustomException(String aggregate) {
            super(MyEnumError.MY_ERROR_2, aggregate);
        }

        // with one argument that will be replaced by {%s} at first position
        // the second argument will be replaced by {%s} in second position
        public MyCustomException(String aggregate, String code) {
            super(MyEnumError.MY_ERROR_3, aggregate, code);
        }

        // example with Throwable exception
        public MyCustomException(Throwable otherException, String aggregate) {
            super(otherException, MyEnumError.MY_ERROR_4, aggregate);
        }
    }

    @Test
    public void example1test() throws MyCustomException {
        BaseException myException = new MyCustomException();
        assertTrue(myException instanceof MyCustomException);
        assertEquals(0, myException.getArguments().size());
        assertEquals(MyEnumError.MY_ERROR_1.getCode(), myException.getCode());
        assertEquals(MyEnumError.MY_ERROR_1.getReason(), myException.getMessage());
    }

    @Test
    public void example2test() {
        BaseException myException = new MyCustomException("MyModel");
        assertTrue(myException instanceof MyCustomException);
        assertEquals(1, myException.getArguments().size());
        assertEquals(MyEnumError.MY_ERROR_2.getCode(), myException.getCode());
        assertEquals("Aggregate {MyModel} already exist", myException.getMessage());
    }

    @Test
    public void example3test() {
        BaseException myException = new MyCustomException("MyModel", "Code");
        assertTrue(myException instanceof MyCustomException);
        assertEquals(2, myException.getArguments().size());
        assertEquals(MyEnumError.MY_ERROR_3.getCode(), myException.getCode());
        assertEquals("Aggregate {MyModel} already exist with code {Code}", myException.getMessage());
    }

    @Test
    public void example4test() {
        try {
            throw new Exception();
        } catch (Exception e) {
            BaseException myException = new MyCustomException(e, "MyModel");
            assertTrue(myException instanceof MyCustomException);
            assertEquals(1, myException.getArguments().size());
            assertEquals(MyEnumError.MY_ERROR_4.getCode(), myException.getCode());
            assertEquals("Aggregate {MyModel} was fail!", myException.getMessage());

        }
    }
}
