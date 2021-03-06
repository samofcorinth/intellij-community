
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.api.extension.ExtendWith;

class ValueSourcesTest {
  @ParameterizedTest
  @ValueSource(ints = {1})
  void testWithIntValues(int i) { }

  @ParameterizedTest
  @ValueSource(longs = {1L})
  void testWithLongValues(long l) { }

  @ParameterizedTest
  @ValueSource(doubles = {0.5})
  void testWithDoubleValues(double d) { }

  @ParameterizedTest
  @ValueSource(strings = {""})
  void testWithStringValues(String s) { }

  @ParameterizedTest
  @ValueSource(booleans = {<warning descr="No implicit conversion found to convert object of type 'boolean' to 'int'">false</warning>})
  void testWithBooleanSource(int argument) { }

  @ParameterizedTest
  <warning descr="Exactly one type of input must be provided">@ValueSource(ints = {1},
    strings = "str")</warning>
  void testWithMultipleValues(int i) { }

  @ParameterizedTest
  <warning descr="No value source is defined">@ValueSource()</warning>
  void testWithNoValues(int i) { }

  <warning descr="Multiple parameters are not supported by this source">@ParameterizedTest</warning>
  @ValueSource(ints = 1)
  void testWithValuesMultipleParams(int i, int j) { }

  @ParameterizedTest
  @ValueSource(ints = {1})
  <warning descr="Suspicious combination '@Test' and '@ParameterizedTest'">@org.junit.jupiter.api.Test</warning>
  void testWithTestAnnotation(int i) { }

  @ValueSource(ints = {1})
  <warning descr="Suspicious combination '@Test' and parameterized source">@org.junit.jupiter.api.Test</warning>
  void testWithTestAnnotationNoParameterized(int i) { }

}

@ExtendWith( String.class ) //fake extension
@interface RunnerExtension { }

@RunnerExtension
abstract class AbstractValueSource {}
class ValueSourcesWithCustomProvider extends AbstractValueSource {
  @ParameterizedTest
  @ValueSource(ints = {1})
  void testWithIntValues(int i, String fromExtension) { }
}

class ParameterizedTestsDemo {

  <warning descr="No sources are provided, the suite would be empty">@ParameterizedTest</warning>
  void testWithParamsNoSource(int i) { }

  @ParameterizedTest
  @EnumSource(<warning descr="No implicit conversion found to convert object of type 'E' to 'int'">E.class</warning>)
  void testWithEnumSource(int i) { }

  @ParameterizedTest
  @EnumSource(E.class)
  void testWithEnumSourceCorrect(E e) { }

  enum E {
    A, B;
  }

  @ParameterizedTest
  @CsvSource({"foo, 1"})
  void testWithCsvSource(String first, int second) {}
}

@org.junit.jupiter.params.provider.ArgumentsSource()
@interface CustomSource { }

class CustomArgProviderTest {
  @ParameterizedTest
  @CustomSource
  void jsonSourceTest(String param) { }
}

class ArgSources {
  @ParameterizedTest
  @org.junit.jupiter.params.provider.ArgumentsSources({@org.junit.jupiter.params.provider.ArgumentsSource})
  void args(String param) { }

  <warning descr="No sources are provided, the suite would be empty">@ParameterizedTest</warning>
  @org.junit.jupiter.params.provider.ArgumentsSources({})
  void emptyArgs(String param) { }
}