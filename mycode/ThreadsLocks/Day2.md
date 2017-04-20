#### Find
1.  ReentrantLock supports a fairness parameter. What does it mean for a lock to be "fair"? Why might you choose to use a fair lock? Why might you not?
2.  What is ReentrantReadWriteLock? How does it differ from ReentrantLock? When might you use it?
3.  What is a "spurious wakeup"? When can one happen and why doesn't a well-written program care if one does?
4.  What is AtomicIntegerFieldUpdater? How does it differ from AtomicInteger? When might you use it?

#### Answers to Find
1.  From ReentrantLock JavaDoc:  
    > The constructor for this class accepts an optional fairness parameter. When set true, under contention, locks favor granting access to the longest-waiting thread. Otherwise this lock does not guarantee any particular access order. Programs using fair locks accessed by many threads may display lower overall throughput (i.e., are slower; often much slower) than those using the default setting, but have smaller variances in times to obtain locks and guarantee lack of starvation. Note however, that fairness of locks does not guarantee fairness of thread scheduling. Thus, one of many threads using a fair lock may obtain it multiple times in succession while other active threads are not progressing and not currently holding the lock.
2.  ReentrantReadWriteLock is an implementation of the ReadWriteLock interface. One might use it for a concurrent data structure that gets read a lot more than it gets written to.
The JavaDoc has this to say about the interface:  
    > A ReadWriteLock maintains a pair of associated locks, one for read-only operations and one for writing. The read lock may be held simultaneously by multiple reader threads, so long as there are no writers. The write lock is exclusive.
3.  ***TODO***
4.  ***TODO***

#### Notes
*   When using ReentrantLock's lock method, the thread will block if another thread already owns the lock. The tryLock method however doesn't block, instead it returns false.
