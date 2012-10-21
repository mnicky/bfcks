(ns ^{:doc "Very simple interpreter of brainf**k programming language.
See http://en.wikipedia.org/wiki/Brainfuck for more information."}
  bfcks.simple)

(defn skip
  "Skips all following instructions until the end of the [...] block and
  returns the instruction pointer after that block.
  instr-p is a pointer to the instruction where the [...] block (that shall
  be skipped) begins."
  [instr-p code]
  (loop [instr-p instr-p stack [] first-run true]
    (if (and (empty? stack) (not first-run))
      instr-p
      (case (code instr-p)
        \[ (recur (inc instr-p) (conj stack :skipped) false)
        \] (recur (inc instr-p) (pop stack) false)
        (recur (inc instr-p) stack false)))))

(defn interpret
  "Interprets given bfck code and returns the resulting cells."
  ([code]
    (interpret code 30000))
  ([code cell-size]
    (interpret code cell-size *in* *out*))
  ([code cell-size in out]
    (let [code (vec code)]
      (loop [instr-p 0 cell-p 0 cells (vec (repeat cell-size 0)) stack []]
        (if (>= instr-p (count code))
          cells
          (let [wrap #(mod % 256)
                cell (cells cell-p)]
            (case (code instr-p)
              \> (recur (inc instr-p) (inc cell-p) cells stack)
              \< (recur (inc instr-p) (dec cell-p) cells stack)
              \+ (recur (inc instr-p) cell-p (assoc cells cell-p (wrap (inc cell))) stack)
              \- (recur (inc instr-p) cell-p (assoc cells cell-p (wrap (dec cell))) stack)
              \. (do (.write out cell)
                     (recur (inc instr-p) cell-p cells stack))
              \, (recur (inc instr-p) cell-p (assoc cells cell-p (wrap (.read in))) stack)
              \[ (if (zero? cell)
                   (recur (skip instr-p code) cell-p cells stack)
                   (recur (inc instr-p) cell-p cells (conj stack instr-p)))
              \] (recur (if (zero? cell) (inc instr-p) (peek stack)) cell-p cells (pop stack))
              (recur (inc instr-p) cell-p cells stack))))))))

(comment
;print 'Hello World!'
(use 'bfcks.simple)
(dorun (interpret "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."))
)
