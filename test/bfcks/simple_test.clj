(ns bfcks.simple-test
  (:use clojure.test
        bfcks.simple))

(deftest basic-interpret-test
  (is (= [0 0 0] (interpret "" 3)))
  (is (= [1 0 0] (interpret "+" 3)))
  (is (= [0 1 0] (interpret ">+" 3)))
  (is (= [0 0 255] (interpret ">>-" 3)))
  (is (= [3 0 0] (interpret "><+++" 3)))
  (is (= [0 3 0] (interpret ">><+++" 3)))
  (is (= [2 0 0] (interpret "++no code" 3)))
)

(deftest stream-interpret-test
  (let [in  (java.io.ByteArrayInputStream. (byte-array (map byte [97 98 99])))
        out (java.io.ByteArrayOutputStream.)]
    (is (and (= [97 98 99] (interpret ",.>,.>,." 3 in out))
             (= "abc" (str out))))
  ))

(deftest skip-test
  (is (= 1 (skip 0 (vec "+--+"))))
  (is (= 4 (skip 0 (vec "[--]+"))))
  (is (= 7 (skip 0 (vec "[-[-]-]+"))))
  (is (= 9 (skip 0 (vec "[-[-[]]-]+"))))
  (is (= 14 (skip 0 (vec "[[---]-[[-]]-]+"))))
  (is (= 12 (skip 7 (vec "[[---]-[[-]]-]+"))))
)

(deftest conditionals-test
  (is (= [0 0 0] (interpret "[]" 3)))
  (is (= [0 0 0] (interpret "+[-]" 3)))
  (is (= [0 5 0] (interpret "+++++[->+<]" 3)))
  (is (= [1 0 0] (interpret "[++++]+" 3)))
  (is (= [0 0 3] (interpret "+++[->[]>+<<]" 3)))
  (is (= [0 0 9] (interpret "+++[->++[->+<]>+<<]" 3)))
)

(deftest hello-world-test
  (let [out (java.io.ByteArrayOutputStream.)]
    (is (and (= [0 87 100 33 10] (interpret "++++++++++[>+++++++>++++++++++>+
                                             ++>+<<<<-]>++.>+.+++++++..+++.>+
                                             +.<<+++++++++++++++.>.+++.------
                                             .--------.>+.>." 5 *in* out))
             (= "Hello World!\n" (str out))))
  ))
