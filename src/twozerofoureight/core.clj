(ns twozerofoureight.core)

(def grid {:a1 nil :a2 2 :a3 nil :a4 4
           :b1 nil :b2 2 :b3 8 :b4 4
           :c1 2 :c2 8 :c3 4 :c4 nil
           :d1 4 :d2 8 :d3 nil :d4 nil})

(def grid-keys [:a1 :a2 :a3 :a4
                :b1 :b2 :b3 :b4
                :c1 :c2 :c3 :c4
                :d1 :d2 :d3 :d4])

(defn sort-map [m]
  (->> m
       (mapcat identity)
       (apply sorted-map)))

(defn merge-row [row]
  "This function..."
  (let [p (partition-by identity row)
        a (map #(if (> (count %) 1)
                  (map (fn [coll]  (apply + coll)) (partition 2 2 [] %))
                  %)
               p)
        b (mapcat identity a)]
    (into  (vec (take (- 4 (count b)) (repeat nil))) (vec b))))

(defn rotate-rows [rows]
  (for [n (range 4)]
    (map #(nth % n) rows)))

(defn grid->rows [grid]
  (->> grid
       sort-map
       vals
       (partition 4)
       ; do this bit elsewehre before merging?
       (map #(filter (complement nil?) %))))

(defn rows->grid [rows]
  (->> rows
       (mapcat identity)
       (zipmap grid-keys)
       sort-map))

(comment
  (let [rows [[1 2 3 4] [5 6 7 8] [9 10 11 12] [13 14 15 16]]]
	(for [n (range 4)]
          (map #(nth % n) rows)))
  
  (->> grid
       grid->rows
       (map merge-row)
       rows->grid)

  (->> grid
       vals
       (map #(filter (complement nil?) %))
       (partition 4)))


(comment "Two parts:
      Juggle rows: juggling rows
      Merge rows: e.g turn sequence into new sequence")



