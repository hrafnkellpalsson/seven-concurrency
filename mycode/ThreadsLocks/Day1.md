#### Find
1.  Check out William Pugh's "Java memory model" website.
2.  Acquaint yourself with the JSR 133 (Java memory model) FAQ.
3.  What guarantees does the Java memory model make regarding initialization safety? Is it always necessary to use locks to safely publish objects between threads?
4.  What is the double-checked locking anti-pattern? Why is it an anti-pattern?

#### Answers to Find
1.  The website is available at `https://www.cs.umd.edu/~pugh/java/memoryModel/`.
2.  The FAQ is available at `http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html`.
    It talks about the reordering of code, much like in class Puzzle on page 13.
    A particularly interesting segment:
    > "Synchronization has several aspects. The most well-understood is mutual exclusion -- only one thread can hold a monitor at once, so synchronizing on a monitor means that once one thread enters a synchronized block protected by a monitor, no other thread can enter a block protected by that monitor until the first thread exits the synchronized block.  
    > But there is more to synchronization than mutual exclusion. Synchronization ensures that memory writes by a thread before or during a synchronized block are made visible in a predictable manner to other threads which synchronize on the same monitor. After we exit a synchronized block, we release the monitor, which has the effect of flushing the cache to main memory, so that writes made by this thread can be visible to other threads. Before we can enter a synchronized block, we acquire the monitor, which has the effect of invalidating the local processor cache so that variables will be reloaded from main memory. We will then be able to see all of the writes made visible by the previous release."

    Another particularly interesting segment:
    > "Another implication is that the following pattern, which some people use to force a memory barrier, doesn't work:
    > synchronized (new Object()) {}  
    > This is actually a no-op, and your compiler can remove it entirely, because the compiler knows that no other thread will synchronize on the same monitor. You have to set up a happens-before relationship for one thread to see the results of another.  
    > Important Note: Note that it is important for both threads to synchronize on the same monitor in order to set up the happens-before relationship properly. It is not the case that everything visible to thread A when it synchronizes on object X becomes visible to thread B after it synchronizes on object Y. The release and acquire have to "match" (i.e., be performed on the same monitor) to have the right semantics. Otherwise, the code has a data race."

3.  The synchronized keyword is not allowed on constructors, instead we get the following guarantee. If an object is properly constructed (which means that references to it do not escape during construction), then all threads which see a reference to that object will also see the values for its _final_ fields that were set in the constructor, without the need for synchronization. What does it mean for an object to be properly constructed? Do not place a reference to the object being constructed anywhere where another thread might be able to see it; do not assign it to a static field, do not register it as a listener with any other object, and so on. These tasks should be done after the constructor completes, not in the constructor.
4.  It's a broken way of implementing a thread-safe singleton. Don't use it. Instead use the Initialization-on-demand holder idiom, see `https://www.wikiwand.com/en/Initialization-on-demand_holder_idiom`.

#### Do
1.  Experiment with the original, broken "dining philosophers" example. Try modifying the length of time that philosophers think and eat and the number of philosophers. What effect does this have on how long it takes until deadlock? Imagine that you were trying to debug this and wanted to increase the likelihood of reproducing the deadlock - what would you do?
2.  (Hard) Create a program that demonstrates writes to memory appearing to be reordered in the absence of synchronization. This is difficult because although the Java memory model allows things to be reordered most simple examples won't be optimized to the point of actually demonstrating the problem.

#### Answers to Do
1.  Holding the number of philosophers fixed, if we reduce the time they think and/or eat, we increase the likelihood of a deadlock. This makes sense since it's more likely that two philosophers will attempt to pick up a chopstick when their random wait time has been decreased.  
If we fix the time, then increasing the number of philosophers decreases the chance of a deadlock. This makes sense because with more philosophers it's more likely that some two philosophers will not pick up the chopstick at the same time.
2.  I ran the Puzzle code on page 13 multiple times and saw both the result "The meaning of life is: 42" and "I don't know the answer" but I never saw the result due to reordering "The meaning of life is: 0".  
**TODO** Create a program where this happens.

#### Notes
*   Threads communicate through shared memory. This form of communication is extremely efficient, but makes two kinds of errors possible: _thread interference_ and _memory consistency errors_. The tool needed to prevent these errors is synchronization.
*   You acquire a _lock_ to _synchronize_ access to some piece of code. Every object has a _monitor lock_ (also called intrinsic lock) that you can acquire by using the synchronize keyword. When you want to synchronize access to a given block of an object's code you don't necessarily have to acquire that object's monitor lock. You can choose to acquire the monitor lock of some other object (maybe a field of the object in question) for the purpose of synchronizing that particular block of code.  
Why would you want to do that? Because you don't necessarily want to lock down the whole object. If we only ever used the monitor lock then another totally unrelated synchronized block might not be able to run at the same time because you locked down the whole object.
*   When you acquire an object's monitor lock you are locking down the whole object, but you are only locking it down for the duration of the synchronized block.  
*   When using the synchronized keyword, if thread B attempts to acquire a lock already owned by thread A, thread B will block until the lock has been released by A and B can acquire it (assuming another thread doesn't beat B to the lock).
