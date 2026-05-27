---
layout: center
---

## Prepare your workspace

Please clone the repository:
```
git clone https://github.com/drazzib/hands-on-workshop-kotlin
cd hands-on-workshop-kotlin/
docker compose pull && ./mvnw compile
```

Or scan the QR code:

![github-repo-qrcode.png](/github-repo-qrcode.png)

---
layout: center
---

## Kotlin?

![snow-white-blow](/snow-white-blow.gif)

<!--
Speaker: Damien

We will focus our discussion on Kotlin within JVM platform.
At this time, Kotlin Multiplatform support is not addressed.

Contrary to what you may have in mind, Kotlin is a somewhat old language.
-->

---
layout: center
---

## JVM Language History

![kotlin-git](/kotlin-git.png)

<!--
Speaker: Damien

Having an alternative language that runs on the JVM is not something    new.
Groovy (2003), Scala (2004), Clojure (2007) are still active languages.

Dimitry Jemerov (lead IntelliJ IDEA plugin for Kotlin) said that most languages
did not have the features they were looking for, except Scala.

However, he cited the slow compilation time of Scala as a deficiency.
One of the stated goals of Kotlin is to compile as quickly as Java

The influence of Scala in Kotlin can be seen in the extensive
support for both object-oriented and functional programming

The first commit to the Kotlin Git repository was on November 2010  (so nearly fifteen years old, a teenager).
In February 2012, JetBrains (the company behind IntelliJ) open sourced the project under the Apache 2 licence.
-->
---
layout: three-cols
columnClass: text-center
---

## layout: three-cols

::title::

# Why Kotlin?

::left::

<div v-click.at="2">

## 2017

Spring official support for Kotlin
with version 5

</div>

::middle::

<div v-click.at="3">

<span class="text-3xl">❤️</span> <br>
Shared language for BFF

<div v-click.at="4">

<span class="text-3xl">❓</span> <br>
Shared language for Backend

</div>

</div>

::right::

<div v-click.at="1">

## 2019

Preferred language for Android

</div>

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
layout: center
---

# Just syntactic sugar?

![sugar](/sugar.png)

<!-- 
Speaker: DAMIEN

Timing: 6-7 minutes

-->
