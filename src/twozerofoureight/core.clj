(ns twozerofoureight.core)

(def ^:private  grid-keys [:a1 :a2 :a3 :a4
                :b1 :b2 :b3 :b4
                :c1 :c2 :c3 :c4
                :d1 :d2 :d3 :d4])

(defn ^:private sort-map [m]
  (->> m
       (mapcat identity)
       (apply sorted-map)))

(defn ^:private merge-row [row]
  "This function..."
  "Still could be better..."
  (let [b (->> row
               (filter (complement nil?))
               (partition-by identity)
               (map #(if (> (count %) 1)
                       (map (fn [coll]  (apply + coll)) (reverse (partition 2 2 [] %)))
                       %))
               (mapcat identity))]
    (into  (vec (take (- 4 (count b)) (repeat nil))) (vec b))))

(defn ^:private generate-tile [grid]
  (assoc grid (->> grid
                   (filter (comp nil? second))
                   keys
                   rand-nth)
         (-> [2 2 2 4]
             rand-nth)))

(defn ^:private rotate-rows [rows]
  (for [n (range 4)]
    (map #(nth % n) rows)))

(defn ^:private grid->rows [grid]
  (->> grid
       sort-map
       vals
       (partition 4)))

(defn ^:private rows->grid [rows]
  (->> rows
       (mapcat identity)
       (zipmap grid-keys)
       sort-map))

(defmulti ^:private move (fn [direction _]
                 direction))

(defmethod ^:private move :right [_ grid]
  (->> grid
       grid->rows
       (map merge-row)
       rows->grid))

(defmethod ^:private move :left [_ grid]
  (->> grid
       grid->rows
       (map reverse)
       (map merge-row)
       (map reverse)
       rows->grid))

(defmethod ^:private move :down [_ grid]
  (->> grid
       grid->rows
       rotate-rows
       (map merge-row)
       rotate-rows
       rows->grid))

(defmethod ^:private move :up [_ grid]
  (->> grid
       grid->rows
       rotate-rows
       (map reverse)
       (map merge-row)
       (map reverse)
       rotate-rows
       rows->grid))

(defn  grid-move [direction grid]
  (let [next-grid (generate-tile (move direction grid))
        left-grid (move :left  next-grid)
        right-grid (move :right  next-grid)
        up-grid (move :up next-grid)
        down-grid (move :down next-grid)]
    (with-meta
      next-grid
      {:over? (= left-grid
                 right-grid
                 up-grid
                 down-grid)
       :left? (not= left-grid
                    next-grid)
       :right? (not= right-grid
                     next-grid)
       :up? (not= up-grid
                  next-grid)
       :down? (not= down-grid
                    next-grid)})))


(comment
  (def grid {:a1 nil :a2 2 :a3 nil :a4 4
           :b1 nil :b2 2 :b3 8 :b4 4
           :c1 2 :c2 8 :c3 4 :c4 nil
           :d1 4 :d2 8 :d3 nil :d4 nil})
  (not= {:a1 nil, :a2 nil, :a3 nil, :a4 nil, :b1 nil, :b2 nil, :b3 nil, :b4 nil, :c1 nil, :c2 nil, :c3 nil, :c4 nil, :d1 nil, :d2 nil, :d3 nil, :d4 2}
        (move :down {:a1 nil, :a2 nil, :a3 nil, :a4 nil, :b1 nil, :b2 nil, :b3 nil, :b4 nil, :c1 nil, :c2 nil, :c3 nil, :c4 nil, :d1 nil, :d2 nil, :d3 nil, :d4 2}))
  (meta (grid-move :down {:a1 nil, :a2 nil, :a3 nil, :a4 nil, :b1 nil, :b2 nil, :b3 nil, :b4 nil, :c1 nil, :c2 nil, :c3 nil, :c4 nil, :d1 nil, :d2 2, :d3 nil, :d4 nil})))
