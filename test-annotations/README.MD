

## 注解的用处

1. 编译器(Compiler instructions), 源码编译的时候用到,在 Java 中内置了如下三个

- `@Deprecated` 用于注解一个类,方法或者字段,如果你的代码中使用了该注解,编译器在编译的时候会提示警告(warning).
- `@Override` 作用于方法上，如果你这个方法并不是覆盖了父类方法，编译器在编译的时候会提示错误(error).
- `@SuppressWarnings` 作用于方法上, 比如方法A调用了带有`@Deprecated`注解的方法B,这时编译器在编译的时候会提示警告(warning), 如果在方法A上加上`@SuppressWarnings`注解,那么这时编译器就不会提示警告.

2. 构建工具(Build-time instructions)

比如 Maven 在构建项目的时候会根据源码中的相关注解来编译源代码.

3. 运行时(Runtime instructions)

正常情况下 java 文件生成 .class 文件后所有的注解都不存在了, 但是如果把注解定义在运行时,这类注解还会存在,因此可以通过 Java 反射技术来读取注解的信息来扩展更多的功能,这样还可以减少重复的代码.
比如在 Spring 中通过 `@Component` 注解可以将一个类自动实例化后放到 Ioc 容器中, 通过  `@Transactional` 注解将一个方法加上事务的功能.

## 元注解

元注解就是定义注解的注解

1. `@Documented` 
2. `@Retention` 
3. `@Target`
4. `@Inherited`

- `@Documented` 注解是否将包含在 JavaDoc 中

- `@Retention` 什么时候使用该注解

```java
@Retention(RetentionPolicy.RUNTIME)
```

其中 RetentionPolicy 类包含了三种不同的值：

1. RetentionPolicy.RUNTIME
指定本 Annotation 在运行时可见, 意味着可以通过反射来读取  Annotation  的信息

2. RetentionPolicy.CLASS
指定本 Annotation 将被存储在 .class 文件中，且在运行时不可见。如果没有指定任何 retention 策略，则 RetentionPolicy.CLASS 将是默认的策略

3. RetentionPolicy.SOURCE
指定本 Annotation 只存在于源代码中，在 .class 文件中不存在，且在运行时不可见。如果希望只让构建工具来利用这些 Annotation 可以使用该策略

- `@Target` 注解用于什么地方

```
@Target({ElementType.METHOD})  // 限制本annotation只能用于Java中的方法  
```

ElementType 类还有以下值：

1. ElementType.ANNOTATION_TYPE 
2. ElementType.CONSTRUCTOR
3. ElementType.FIELD
4. ElementType.LOCAL_VARIABLE
5. ElementType.METHOD
6. ElementType.PACKAGE
7. ElementType.PARAMETER
8. ElementType.TYPE

其中，ElementType.TYPE可以应用于任何Java类型，包括class，interface, enum, 以及annotation。

- `@Inherited` 是否允许子类继承该注解,并且只能用于类继承,不能用于接口继承

@Inherited 元注解是一个标记注解，@Inherited 阐述了某个被标注的类型是被继承的。如果一个使用了 @Inherited 修饰的 Annotation 类型被用于一个class，则这个 Annotation 将被用于该 class 的子类。

具体如下

```java
java.lang.annotation.Inherited

@Inherited
public @interface MyAnnotation {

}
```

```java
@MyAnnotation
public class MySuperClass { ... }
```

```java
public class MySubClass extends MySuperClass { ... }
```

在这个例子中  MySubClass 也拥有了 @MyAnnotation 注解,因为其继承了 MySuperClass 类,并且  MySuperClass 类中拥有含有 @Inherited 元注解的 @MyAnnotation 注解.

## 运用时机

Java 注解现在之所以能够流行起来,很大原因在于可以通过编程的手段获取到注解定义的相关信息.

1. 在运行时

设置 Retention 为 RUNTIME,再通过反射.具体是调用 `getAnnotation()` 和  `getAnnotations()` 方法来获取注解的定义信息,根据这些信息来扩展功能.

2. 在编译时
设置 Retention 为 CLASS,通过实现 Annotation Processor API 来定义相关功能,在 Java 编译器或者其他编译工具在编译源码的时候来扩展功能.

