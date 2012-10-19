(ns bfcks.simple-test
  (:use clojure.test
        bfcks.simple))

(deftest basic-interpret-test
  (is (= [0 0 0] (interpret "" 3)))
  (is (= [1 0 0] (interpret "+" 3)))
  (is (= [0 1 0] (interpret ">+" 3)))
  (is (= [0 0 -1] (interpret ">>-" 3)))
  (is (= [3 0 0] (interpret "><+++" 3)))
  (is (= [2 0 0] (interpret "++nocode" 3)))
)

(deftest stream-interpret-test
  (let [in  (java.io.ByteArrayInputStream. (byte-array (map byte [97 98 99])))
        out (java.io.ByteArrayOutputStream.)]
    (is (and (= [97 98 99] (interpret ",.>,.>,." 3 in out))
             (= "abc" (str out))))
))
