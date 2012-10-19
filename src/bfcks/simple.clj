(ns bfcks.simple)

(defn interpret
  "Interprets given bfck code and returns the resulting cells."
  ([code]
    (interpret code 30000))
  ([code cell-size]
    (interpret code cell-size *in* *out*))
  ([code cell-size in out]
    (let [code (vec code)]
      (loop [instr-p 0 cell-p 0 cells (vec (repeat cell-size 0))]
        (if (>= instr-p (count code))
          cells
          (case (code instr-p)
            \> (recur (inc instr-p) (inc cell-p) cells)
            \< (recur (inc instr-p) (dec cell-p) cells)
            \+ (recur (inc instr-p) cell-p (assoc cells cell-p (inc (cells cell-p))))
            \- (recur (inc instr-p) cell-p (assoc cells cell-p (dec (cells cell-p))))
            \. (do (.write out (cells cell-p))
                   (recur (inc instr-p) cell-p cells))
            \, (recur (inc instr-p) cell-p (assoc cells cell-p (.read in)))
            \[ (recur (inc instr-p) cell-p cells)
            \] (recur (inc instr-p) cell-p cells)
            (recur (inc instr-p) cell-p cells)))))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
