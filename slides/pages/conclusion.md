---
layout: center
---

# Just syntactic sugar?

![sugar](/sugar.png)

<!-- 
Speaker: DAMIEN

Timing: 6-7 minutes

-->

---
layout: center
---

# A lot more to see

- Sealed classes

- Smart casts

- Higher-order functions

- Error handling

---
layout: two-columns
columnClass: text-center
---

::title::

# JVM Powered

::left::

![motor](/motor.gif)

::right::

[Loom](https://wiki.openjdk.org/display/loom/Main)

[Amber](https://openjdk.org/projects/amber/)

[Valhalla](https://openjdk.org/projects/valhalla/)

...

All JVM optimizations will be in Kotlin

Some of them optimize existing features

---

# Interoperability

<v-clicks depth="1">

- Kotlin is designed to be used alongside Java code to simplify migration

  - ✅ Call Java from Kotlin

  - ✅ Call (easy most of the time) Kotlin from Java

- ✅ Same monitoring, profiling, APM, ... as plain Java applications

</v-clicks>

<!--
Speaker: Damien

(some advanced features can create hard-to-call functions, like all @JvmInline methods).

Use all your existing production tools (profilers, APM, ...) and JVM settings

-->

---
layout: cover
---

# Learning Kotlin

---

# Learning Kotlin - Koans

Free courses (called [Kotlin Koans](https://play.kotlinlang.org/koans/overview)) using TDD:

![koan](/koan.png)


<!--
Speaker: Damien

Easy to learn for Java developers comfortable with streams

TDD stands for Test Driven Development and not Tech Digital Day 😅

Kotlin Koans is a series of exercises to get you familiar with the Kotlin syntax and some idioms.

Each exercise is created as a failing unit test, and your job is to make it pass.

2/ Koans online
2/ inside IntelliJ IDEA or Android Studio.
-->

---

# Learning Kotlin - syntax

IntelliJ - Convert Java to Kotlin

![java-to-kotlin](/java-to-kotlin.gif)

---

# Learning Kotlin - Pluralsight

Pluralsight has a full [Kotlin learning path](https://app.pluralsight.com/paths/skill/kotlin-1):

![pluralsight-kotlin-path.png](/pluralsight-kotlin-path.png)

<!--
Speaker: Damien

There are a "Kotlin Path" in our learning platform (Pluralsight) too
with more than 15 courses
-->

---

# Learning Kotlin - within Spring

Each Spring _Java code snippet_ has its Kotlin counterpart:

![](/spring-doc.gif)

---
layout: center
---

# Conclusion

---
layout: full
---

# Key Takeaways

<v-clicks>

- Kotlin and Java are highly interoperable
- Positive developer and production experience
- Easy to learn for a Java/Spring Boot developer

</v-clicks>

<!--
Speaker: Damien

interoperable = usage of Kotlin lib in a Java project and vice versa

production = standard tools, JVM monitoring and optimizations

easy to learn = koans & Spring snippets (1 week for a Java dev)
-->
