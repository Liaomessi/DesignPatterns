# StatePattern
生产实践中的状态模式应用

## 模拟场景
现有一个电影管理系统，对于电影有状态转移图，如下，对于不同权限的人员有不同的状态流转。![电影状态转移图](/state-pattern/doc/电影状态转移图.png)
现在我们设计对于监管人员它可以控制电影的上下架，对于上映方它能决定电影上下架但不能决定电影永久下架等。总之就是不同人员能操作的电影生命周期状态是不一样的。

## 场景分析
1. 为快速满足需求，最便捷的方式当然是使用if、else来判断所有可能的场景，或者switch。随着状态越来越复杂，拥有的权限越来越细化，这段代码review起来会非常费神。
伪代码可能如下：
```java
if(movie.getStatus() == INITAL){
    switch(movie.getOpLevel()){
        case OWNER:
            //....
        case MOVIE_POSTER:
            //....
        case REGULATOR:
            //....
    }
}else if(movie.getStatus() == PREPARING){
    。。。。
}
```

2. 这时我们想到使用有限状态机来实现，伪代码如下：

```java
void prepare(Movie movie){
    if(Initial == movie.getStatus()){
        if(OpLevel.OWNER == movie.getOpLevel()){
            // do some thing
            movie.setStatus(Preparing);
        }
    }
    throw new Exception("没权限或者状态不对啦");
}

void pause(Movie movie){
    if(PREPARING != movie.getStatus() && ACTIVE != movie.getStatus()){
        if(OpLevel.REGULATOR == movie.getOpLevel()){
            // do some thing
            movie.setStatus(PAUSED);
        }
    }
    throw new Exception("没权限或者状态不对啦");
}
```
但是不论是直接一顿怼if/else还是使用状态机，都避免不了一个问题，那就是有很多的逻辑分支需要判断，所以这种场景最合适的还是使用状态模式

## 最优解
使用状态模式可以干掉几乎所有的逻辑分支，通过继承和实现来将逻辑分支转换成对应的状态处理器，为了去掉不同状态下可执行的操作判断，我们可以给状态接口方法全部默认实现一个不可操作的方法。
在对应状态下只需要实现可以执行的方法，这样就去掉了所有状态判断，对于用户权限判断其实也可以实现一个类似的功能。
以下是伪代码:
定义一个拥有所有状态操作的接口，并赋予默认实现为不支持该操作。
```java
public interface MovieState {
    default void create(Movie movie) { 
        throw new IllegalArgumentException("该状态不支持筹备电影." + movie.getStatus()); 
    }

    default void preparing(Movie movie) {
        throw new IllegalArgumentException("该状态不支持拍电影" + movie.getStatus());
    }

    default void publish(Movie movie) {
        throw new IllegalArgumentException("该状态不支持上映电影" + movie.getStatus());
    }

    default void pause(Movie movie) { 
        throw new IllegalArgumentException("该状态不支持被迫下架电影" + movie.getStatus()); 
    }

    default void stop(Movie movie) {
        throw new IllegalArgumentException("该状态不支持停止上映电影" + movie.getStatus());
    }

    default void complete(Movie movie) {
        throw new IllegalArgumentException("该状态不支持计算电影收益" + movie.getStatus());
    }

    default void archive(Movie movie) {
        throw new IllegalArgumentException("该状态不支持归档电影" + movie.getStatus());
    }
}
```

然后对于不同状态可以转换的状态我们予以一一实现即可。
```java
@Service("INITIAL")
 public class InitialState implements MovieState {
     @Override
     public void preparing(Movie movie) {
         if (movie.getOpLevel() != OpLevel.OWNER) {
             throw new IllegalArgumentException("只有制片人能拍电影");
         }
         //TODO: 做些操作电影对象的行为（拍电影，剪辑，后期啊啥的）
         movie.setStatus(MovieStatus.PREPARING);
         //TODO: 保存操作过的电影
     }
 }

@Service("PREPARING")
public class PreparingState implements MovieState {
    @Override
    public void pause(Movie movie) {
        if (movie.getOpLevel()!= OpLevel.REGULATOR){
            throw new IllegalArgumentException("只有监管人员才可以下架电影。");
        }
        movie.setStatus(MovieStatus.PAUSED);
    }

    @Override
    public void publish(Movie movie) {
        if (movie.getOpLevel()!= OpLevel.OWNER && OpLevel.MOVIE_POSTER != movie.getOpLevel()){
            throw new IllegalArgumentException("只有制作者和上映者才可以上映电影。");
        }
        movie.setStatus(MovieStatus.ACTIVE);
    }
}
```
在调用状态转换时我们只需要加载对应的状态处理器来处理即可。

## 总结
在生产应用中状态模式是一个会被大量使用到的场景，尤其是状态转移本身还会牵扯到其他的判断的（比如这里的权限判断等），不是一个状态转移图可以描述的情况选，使用状态模式绝对会为你带来极大的便利。尤为明显的是，如果使用if/else,当逻辑分支越来越多，提交代码没有人能用肉眼review出来代码是否有逻辑bug。但是使用状态模式，配上状态转移图，即使是代码小白都可以帮你review代码了。单元测试也好写多了（啊歪，我绝对不是说单元测试代码覆盖率啊，别想歪了）


