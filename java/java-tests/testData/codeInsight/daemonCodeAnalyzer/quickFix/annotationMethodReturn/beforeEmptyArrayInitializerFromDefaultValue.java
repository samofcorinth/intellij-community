// "Make 'value' return 'java.lang.String[]'" "true"
class X {
  @interface MyAnnotation {
    String value() default {}<caret>;
  }
}