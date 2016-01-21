相信只要使用过Spring框架的，大家对于AOP都不陌生，尤其提起它就能立刻随口说出，一般用在日志处理、异常处理、权限验证等方面。但刚开始接触难免会有各种各样的疑惑，今天抽时间，按照之前的理解整理了一份关于Spring AOP的简单教程，希望能够帮助大家尽快的了解它的实现过程及原理。首先来明确几个概念：

JointPoint
系统在运行之前，AOP的功能模块需要织入到OOP的功能模块中。要进行这种织入过程，需要知道在系统的哪些功能点上进行织入操作，这些将要在其上进行织入操作的系统功能点就称为JointPoint。如某方法调用的时候或者处理异常的时候，在Spring AOP中，一个连接点总是表示一个方法的执行。常见的几种类型的JoinPoint:

Ø 方法调用：当某个方法被调用的时候所处的程序执行点；

Ø 方法执行：该类型表示的是某个方法内部执行开始时的点，应该与方法调用相区分；

Ø 构造方法调用：程序执行过程中对某个对象调用其构造方法进行初始化时的点；

Ø 构造方法执行：它与构造方法调用关系如同方法调用与方法执行间的关系；

Ø 字段设置：对象的某个属性通过setter方法被设置或直接被设置的执行点；

Ø 字段获取：某个对象相应属性被访问的执行点；

Ø 异常处理执行：某些类型异常抛出后，对应的异常处理逻辑执行点；

Ø 类初始化：类中某些静态类型或静态块的初始化时的执行点。

Pointcut
Pointcut代表的是JoinPoint的表述方式。在将横切逻辑织入当前系统的过程中，虽然知道需要在哪些功能点上织入AOP的功能模块，但需要一种表达方法。Pointcut和一个切入点表达式关联，并在满足这个切入点的Joinpoint上运行。目前通常使用的Pointcut方式有以下几种：

Ø 直接指定Joinpoint所在的方法名称；

Ø 正则表达式，Spring的AOP支持该种方式；

Ø 使用特定的Pointcut表述语言，Spring 2.0后支持该方式。

Advice
Advice是单一横切关注点逻辑的载体，它代表将会织入到JoinPoint的横切逻辑。在切面的某个特定的连接点上执行的逻辑。根据它在Joinpoint位置执行时机的差异或完成功能的不同，可分为以下几种形式：

Ø Before Advice：在Joinpoint指定位置之前执行的Advice类型，可以采用它来做一些系统的初始化工作，如设置系统初始值，获取必要系统资源等。

Ø After Advice：在相应连接点之后执行的Advice类型，它还可以细分为以下三种：

² After Returning Advice：只有当前Joinpoint处执行流程正常完成后，它才会执行；

² After throwing Advice：在当前Joinpoint执行过程中抛出异常的情况下会执行；

² After Advice：该类型的Advice不管JoinPoint处执行流程是正常还是抛出异常都会执行。

Ø Around Advice：对附加其上的Joinpoint进行包裹，可以在joinpoint之前和之后都指定相应的逻辑，甚至中断或忽略joinpoint处原来程序流程的执行。

 Aspect
它是对系统中横切关注点逻辑进行模块化封装的AOP概念实体，它可以包含多个Pointcut以及相关的Advice定义。

 织入器
经过织入过程后，以Aspect模块化的横切关注点才会集成到oop的现存系统中，而完成织入过程实体称为织入器。Spring中使用一组类来完成最终的织入操作，ProxyFactory类是Spring AOP最通用的织入器。

 目标对象
符合Pointcut所指定的条件，将在织入过程中被织入横切逻辑的对象，称之为目标对象。

单看上述的概念，可能会觉得有点眼花缭乱，其实通过一个简单的AOP的实例即可以帮助我们很快的了解其内部的机制。其实对于方法拦截有不同的实现方式，常用的即有直接采用Spring提供的各种Advice进行拦截，另一种则是采用MethodInterceptor方式进行拦截。


----------------------------advice

execute before (by com.test.aop.interfaces.IBusinessLogic.foo)
foo() ... 
execute before (by com.test.aop.interfaces.IBusinessLogic.bar)
bar() ... 
execute before (by com.test.aop.interfaces.IBusinessLogic.time)
time() ... 
com.test.aop.interfaces.IBusinessLogic.timespend time: 0
execute after (by com.test.aop.interfaces.IBusinessLogic.time)

-----------------------------interceptor
start intercept the method: com.test.aop.interfaces.IBusinessLogic.foo
foo() ... 
end intercept the method: com.test.aop.interfaces.IBusinessLogic.foo
start intercept the method: com.test.aop.interfaces.IBusinessLogic.bar
bar() ... 
end intercept the method: com.test.aop.interfaces.IBusinessLogic.bar
start intercept the method: com.test.aop.interfaces.IBusinessLogic.time
time() ... 
end intercept the method: com.test.aop.interfaces.IBusinessLogic.time

