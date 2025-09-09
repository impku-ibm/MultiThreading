# Java Deadlock Example

This example demonstrates a classic deadlock scenario in Java multithreading, where two threads end up waiting for resources that are held by each other, resulting in a permanent blockage.

## What is a Deadlock?

A deadlock occurs when two or more threads are blocked forever, waiting for each other to release resources that they need. In this example, we demonstrate this using two threads and two locks.

## Implementation Details

The example uses:
- Two static final Object instances (`lock1` and `lock2`) used as locks
- Two threads (`t1` and `t2`) that attempt to acquire these locks in different orders

### Thread 1's Behavior
1. Acquires `lock1`
2. Sleeps for 1 second
3. Attempts to acquire `lock2` (while still holding `lock1`)

### Thread 2's Behavior
1. Acquires `lock2`
2. Sleeps for 1 second
3. Attempts to acquire `lock1` (while still holding `lock2`)

## Code Structure

```java
Thread t1: lock1 → sleep → lock2
Thread t2: lock2 → sleep → lock1
```

## Why Deadlock Occurs

The deadlock occurs because:
1. Thread 1 holds `lock1` and waits for `lock2`
2. Thread 2 holds `lock2` and waits for `lock1`
3. Neither thread releases its lock before trying to acquire the other lock
4. Both threads end up waiting for each other indefinitely

## How to Run

```bash
java DeadlockExample.DeadlockExample
```

## Expected Output
```
Thread 1 is holding lock 1...
Thread 2 is holding lock 2...
Waiting for lock2 to be released
Waiting for lock1 to be released
[Program hangs due to deadlock]
```

## Prevention Techniques

To prevent deadlocks in real applications, consider these strategies:
1. Lock Ordering: Always acquire locks in a consistent order
2. Lock Timeouts: Use `tryLock()` with timeouts
3. Deadlock Detection: Implement deadlock detection mechanisms
4. Resource Hierarchy: Establish a hierarchy for resource allocation

## Learning Points
1. Understanding how deadlocks occur in multithreaded applications
2. Importance of proper lock ordering
3. Effects of thread synchronization
4. How to identify potential deadlock scenarios

This example is for educational purposes and demonstrates what to avoid in production code.
