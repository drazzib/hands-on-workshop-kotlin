---
# You can also start simply with 'default'
theme: ./theme
# random image from a curated Unsplash collection by Anthony
# like them? see https://unsplash.com/collections/94734566/slidev
background: https://cover.sli.dev
# some information about your slides (markdown enabled)
title: Kotlin ❤️ Backend
info: |
  ## For the TDD
# apply unocss classes to the current slide
class: text-center
# https://sli.dev/features/drawing
drawings:
  persist: false
# slide transition: https://sli.dev/guide/animations.html#slide-transitions
transition: slide-left
# enable MDC Syntax: https://sli.dev/features/mdc
mdc: true

fonts:
  # basically the text
  sans: Roboto
  mono: JetBrains Mono

# open graph
# seoMeta:
#  ogImage: https://cover.sli.dev
---

# Kotlin ❤️ Backend

<div class="abs-br m-6 text-xl">
  <button @click="$slidev.nav.openInEditor()" title="Open in Editor" class="slidev-icon-btn">
    <carbon:edit />
  </button>
  <a href="https://github.com/dktunited/tdd-kotlin" target="_blank" class="slidev-icon-btn">
    <carbon:logo-github />
  </a>
</div>

<!--
Speaker: Damien

As you may know, Kotlin is the main language for Android application.
But we are witnessing a growing adoption of Kotlin for backend in various companies.

-->

---
src: ./pages/intro.md
---

---
src: ./pages/base.md
---

---
src: ./pages/spring.md
---

---
src: ./pages/dkt.md
---

---
src: ./pages/conclusion.md
---
