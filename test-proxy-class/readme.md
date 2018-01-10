## 基于 Java 动态代理实现 AOP 编程

- 根据提供的接口数组创建代理类 proxyClass
- 获取代理类 proxyClass 的构造器 proxyClassConstructor, 参数必须实现 InvocationHandler 相关接口
- 根据构造器 proxyClassConstructor 创建 IConnection 类实例, 该实例由jvm创建, 
- 拦截器实现了对代理对象方法在执行过程中的拦截, 从而实现在待拦截方法执行的前后,抛出异常等情况下埋入相关逻辑.
- 拦截器形成拦截器链, 每个拦截器完成不同的拦截任务, 最后一个拦截器必须实现对待拦截对象的调用, 也就是说最后一个拦截器中必须有代理对象的一个实例, 其他前面的拦截器就负责执行前的拦截处理.
- 关于 InvocationHandler 的接口的 invoke 方法实现的说明如下

```
/**
 * proxy 由 jvm 创建的类实例, 没有数据状态, 但是可以获取到类的相关信息,比如方法, 注解等信息
 * method 代理对象的方法
 * args 代理对象的方法的参数
 * 
 * method.invoke(conn, args) 中的 conn 表示在执行过程中具体执行哪个接口实现类, 也可以根据传入方法参数的不同来动态选择实现类, 这里是根据构造函数传入的实现类
 */
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	System.out.println("DefaultInterceptor ...");
	return method.invoke(conn, args);
}
```