(ns twozerofoureight.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn merge-row [row]
  (let [p (partition-by identity row)
        a (map #(if (> (count %) 1)
                  (map (fn [coll]  (apply + coll)) (partition 2 2 [] %))
                  %)
               p)
        b (mapcat identity a)]
    b))

;; idea: store as map - strip values, parition by 4, then can do merge in direction

(def grid {:a1 nil :a2 2 :a3 nil :a4 4
           :b1 nil :b2 2 :b3 8 :b4 4
           :c1 2 :c2 8 :c3 4 :c4 nil
           :d1 4 :d2 8 :d3 nil :d4 nil})

(def grid-keys [:a1 :a2 :a3 :a4
                :b1 :b2 :b3 :b4
                :c1 :c2 :c3 :c4
                :d1 :d2 :d3 :d4])

(->> grid
     grid->rows
     (map #(filter (complement nil?) %))
     (map merge-row))

[[1 2 3 4]
 [2 3 4 5]]

(defn grid->rows [grid]
  (->> grid
       vals
       (partition 4)))

(def row [1 2 2 3])


[[1 5]
 [6 9]]

[[1 6]
 [5 9]]

