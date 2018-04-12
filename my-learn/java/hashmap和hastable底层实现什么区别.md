## hashmap hastable 底层实现什么区别?
1. 两者最主要的区别在于Hashtable是线程安全，而HashMap则非线程安全
Hashtable的实现方法里面都添加了synchronized关键字来确保线程同步，因此相对而言HashMap性能会高一些，我们平时使用时若无特殊需求建议使用HashMap，在多线程环境下若使用HashMap需要使用Collections.synchronizedMap()方法来获取一个线程安全的集合（Collections.synchronizedMap()实现原理是Collections定义了一个SynchronizedMap的内部类，这个类实现了Map接口，在调用方法时使用synchronized来保证线程同步,当然了实际上操作的还是我们传入的HashMap实例，简单的说就是Collections.synchronizedMap()方法帮我们在操作HashMap时自动添加了synchronized来实现线程同步，类似的其它Collections.synchronizedXX方法也是类似原理）
2. HashMap可以使用null作为key，而Hashtable则不允许null作为key
虽说HashMap支持null值作为key，不过建议还是尽量避免这样使用，因为一旦不小心使用了，若因此引发一些问题，排查起来很是费事
HashMap以null作为key时，总是存储在table数组的第一个节点上
3. HashMap是对Map接口的实现，Hashtable实现了Map接口和Dictionary抽象类
4. HashMap的初始容量为16，Hashtable初始容量为11，两者的填充因子默认都是0.75
HashMap扩容时是当前容量翻倍即:capacity\*2，Hashtable扩容时是容量翻倍+1即:capacity\*2+1
5. 两者计算hash的方法不同
Hashtable计算hash是直接使用key的hashcode对table数组的长度直接进行取模
```
int hash = key.hashCode();
int index = (hash & 0x7FFFFFFF) % tab.length;
```
HashMap计算hash对key的hashcode进行了二次hash，以获得更好的散列值，然后对table数组长度取摸
```
static final hash(Object key){
    int h;
    return (key == null)? 0 : (h = key.hashCode()) ^ ( h >>> 16);
}
```
6.HashMap的迭代器(Iterator)是fail-fast迭代器，而Hashtable的enumerator迭代器不是fail-fast的。所以当有其它线程改变了HashMap的结构（增加或者移除元素），将会抛出ConcurrentModificationException，但迭代器本身的remove()方法移除元素则不会抛出ConcurrentModificationException异常。但这并不是一个一定发生的行为，要看JVM。这条同样也是Enumeration和Iterator的区别。

