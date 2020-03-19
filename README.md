## What is this
Sometimes we want to limit the frequency of a certain function call, and this debouncer can do that for us.

## How to use
1、The function to be debounce
```kotlin
fun test(s: String) {
  println(s)
}
```

2、create a Debouncer with a timeout in 1 second
```kotlin
val debouncer = Debouncer.create<String>({ str ->
     test(str)
    }, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
```

3、invoke the function like this
```kotlin
debouncer.onNext("Hello world")
```
