# bfcks

Rather simple [Brainf**k](http://en.wikipedia.org/wiki/Brainfuck) interpreter written in [Clojure](http://clojure.org).

Usage
=====

```clojure
(use 'bfcks.simple)
(dorun (interpret "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++
                   ..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."))
;=> Hello World!
```

Copyright © 2012 [Mnicky](http://mnicky.github.com)

Distributed under the Eclipse Public License, the same as Clojure.
