# Base Exception

<img src="https://app.travis-ci.com/Fran-Abril/base-exception.svg?branch=main"></img>

Base exception for using in all your projects with easy hierarchy to build


## 🚀 How to use

Go to your package exception.

The new feature needs a registry of code errors. Then create an enum that `implements IError` like this:

```Java
    public enum MyEnumError implements IError {
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
```

Go to your exceptions and `extends BaseException` like this:

```Java
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
            super(MyEnumError.MY_ERROR_4, aggregate);
        }
    }
```

Simple and easy, now use your new exceptions easily:
```Java
    throw new MyCustomException();
```

```Java
    public MyCustomException(MyAggregate aggregate) {
        super(MyEnumError.MY_ERROR_2, aggregate.getCode());
    }

    ...


    MyAggregate aggregate = new MyAggregate("123");
    // your code ...
    throw new MyCustomException(aggregate);
```

```Java
    // ficticial code
    int key = 0;
    switch (key) {
        case 1:
            throw new InvalidCodeException();
        case 2:
            throw new InvalidCodeException("Error!");
        case 2:
            throw new InvalidCodeFormatException("Error!", "It fails with key: " + key);
        default:
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new CannotFindCodeException(e, "Error!");
            }
    }
```

You can use your new exceptions like you want, I hope I've helped!