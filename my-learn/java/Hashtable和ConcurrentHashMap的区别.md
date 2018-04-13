## Hashtable和ConcurrentHashMap的区别
1. Hashtable容器使用synchronized来保证线程安全，但在线程竞争激烈的情况下Hashtable的效率非常低下。因为当一个线程访问Hashtable的同步方法时，其他线程访问Hashtable的同步方法时，可能会进入阻塞或轮询状态。jdk1.7中ConcurrentHashMap使用(ReenTrantLock(可重入锁) 锁分离技术，分段锁技术)实现线程安全。jdk1.8中ConcurrentHashMap一个线程每次对一个桶（链表 or 红黑树）进行加锁，其他线程仍然可以访问其他桶；一个线程进行put/remove操作时，对桶（链表 or 红黑树）加上synchronized独占锁;ConcurrentHashMap采用CAS算法保证线程安全;
2. Hashtable是强一致的,Hashtable的迭代器是强一致性的，而ConcurrentHashMap是弱一致的。 ConcurrentHashMap的get，clear，iterator 都是弱一致性的。
3. Hash方法不一样
> ConcurrentHashMap 定位index
```
static final int spread(int h) {
    return (h ^ (h >>> 16)) & HASH_BITS;
}
```
> Hashtable 定位index
```
int hash = key.hashCode();
int index = (hash & 0x7FFFFFFF) % tab.length;
```
4. get方法实现不一致
> Hashtable get使用synchronized加锁
```
public synchronized V get(Object key) {
	Entry<?,?> tab[] = table;
	int hash = key.hashCode();
	int index = (hash & 0x7FFFFFFF) % tab.length;
	for (Entry<?,?> e = tab[index] ; e != null ; e = e.next) {
		if ((e.hash == hash) && e.key.equals(key)) {
			return (V)e.value;
		}
	}
	return null;
}
```
> ConcurrentHashMap get不需要加锁 因为get方法通过CAS保证键值对的原子性，当tab[i]被锁住，CAS失败并不断重试，保证get不会出错；
```
public V get(Object key) {
	Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
	int h = spread(key.hashCode());
	if ((tab = table) != null && (n = tab.length) > 0 &&
		(e = tabAt(tab, (n - 1) & h)) != null) {
		if ((eh = e.hash) == h) {
			if ((ek = e.key) == key || (ek != null && key.equals(ek)))
				return e.val;
		}
		else if (eh < 0)
			return (p = e.find(h, key)) != null ? p.val : null;
		while ((e = e.next) != null) {
			if (e.hash == h &&
				((ek = e.key) == key || (ek != null && key.equals(ek))))
				return e.val;
		}
	}
	return null;
}

static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
	return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
}
```
