# Statistical Calculator
The source code `StatisticalCalculator.java` is under `src/main/java` and the test code `StatisticalCalculatorTest.java`
is under `src/test/java`.
## User Story
This program is s statistical calculator where user can type integers and get statistical values:
mean, median, standard deviation, and sample size.

## Use Cases
### Calculating mean
From the entered input by the user, comma separated integers are stored as list of integers and the mean value of all
elements in the list is calculated. User can see the result on the screen. 
Result is displayed with two decimal places included.
### Calculating median
From the entered input by the user, comma separated integers are stored as sorted list of integers and the median value 
is calculated. In case of even sample size, the mean of two numbers in the middle is displayed. 
User can see the result on the screen. Result is displayed as double.
### Calculating standard deviation
From the entered input by the user, comma separated integers are stored as list of integers and the standard deviation
value is calculated. User can see the result on the screen. Result is displayed with two decimal places included.
### Calculating sample size
From the entered input by the user, comma separated integers are stored as list of integers and the sample size value 
is calculated. User can see the result on the screen. Result is displayed as integer.

## Design Pattern

### Factory pattern
`ControllerFactory` class is implemented to apply a factory pattern. This class has 4 methods to create the controller
for each use case. By creating instances of related classes (input boundary and output boundary), code in `main` has been
shortened, and we do not need to care about instantiation of input boundary and output boundary objects when executing 
the program.
### Dependency Inversion pattern
In `CalculatorView`, dependency inversion pattern is applied. Controller objects made by `ControllerFactory` in `main` 
are injected to `CalculatorView`. Therefore, `CalculatorView` constructor does not need to create a new controller objects
inside its class. 

## Java version and Testing Framework
Java11 is used. Test is based on interactors. 
For unit test, mocked objects are used to create objects which interacts with interactos for testing.

## Remaining Code smell, CA, and SOLID
- Long Method code smell: the constructor of `CalculatorView` is long and there are some duplicate codes
that might be shortened with a helper method. <br>
- SRP violation: `CalculatorView` creats all buttons and panels needed for GUI. This class might be separated into
small classes and can be combined in view manager class.
